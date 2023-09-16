/*
 * Copyright (c) 2017, Seth <Sethtroll3@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package nKesFishing;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.GraphicID;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.game.FishingSpot;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.ProgressPieComponent;
import net.runelite.client.util.ImageUtil;

class FishingSpotOverlay extends Overlay
{
	private static final Duration MINNOW_MOVE = Duration.ofSeconds(15);
	private static final Duration MINNOW_WARN = Duration.ofSeconds(3);
	private static final int ONE_TICK_AERIAL_FISHING = 3;

	private final nKesFishingPlugin plugin;
	private final nKesFishingConfig config;
	private final Client client;
	private final ItemManager itemManager;

	@Setter(AccessLevel.PACKAGE)
	private boolean hidden;

	@Inject
	private FishingSpotOverlay(nKesFishingPlugin plugin, nKesFishingConfig config, Client client, ItemManager itemManager)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.plugin = plugin;
		this.config = config;
		this.client = client;
		this.itemManager = itemManager;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (hidden)
		{
			return null;
		}

		FishingSpot previousSpot = null;
		WorldPoint previousLocation = null;
		for (NPC npc : plugin.getFishingSpots())
		{
			FishingSpot spot = FishingSpot.findSpot(npc.getId());

			if (spot == null)
			{
				continue;
			}

			if (config.onlyCurrentSpot() && plugin.getCurrentSpot() != null && plugin.getCurrentSpot() != spot)
			{
				continue;
			}

			// This relies on the sort order to keep identical npcs on the same tile adjacent to each other
			if (previousSpot == spot && previousLocation.equals(npc.getWorldLocation()))
			{
				continue;
			}

			Color color;
			if (npc.getGraphic() == GraphicID.FLYING_FISH)
			{
				color = config.getMinnowsOverlayColor();
			}
			else if (spot == FishingSpot.COMMON_TENCH && npc.getWorldLocation().distanceTo2D(client.getLocalPlayer().getWorldLocation()) <= ONE_TICK_AERIAL_FISHING)
			{
				color = config.getAerialOverlayColor();
			}
			else if (spot == FishingSpot.HARPOONFISH && npc.getId() == NpcID.FISHING_SPOT_10569)
			{
				color = config.getHarpoonfishOverlayColor();
			}
			else
			{
				color = config.getOverlayColor();
			}

			if (spot == FishingSpot.MINNOW && config.showMinnowOverlay())
			{
				MinnowSpot minnowSpot = plugin.getMinnowSpots().get(npc.getIndex());
				if (minnowSpot != null)
				{
					long millisLeft = MINNOW_MOVE.toMillis() - Duration.between(minnowSpot.getTime(), Instant.now()).toMillis();
					if (millisLeft < MINNOW_WARN.toMillis())
					{
						color = Color.ORANGE;
					}

					LocalPoint localPoint = npc.getLocalLocation();
					Point location = Perspective.localToCanvas(client, localPoint, client.getPlane());

					if (location != null)
					{
						ProgressPieComponent pie = new ProgressPieComponent();
						pie.setFill(color);
						pie.setBorderColor(color);
						pie.setPosition(location);
						pie.setProgress((float) millisLeft / MINNOW_MOVE.toMillis());
						pie.render(graphics);
					}
				}
			}

			if (config.showSpotTiles())
			{
				Polygon poly = npc.getCanvasTilePoly();

				if (poly != null)
				{
					OverlayUtil.renderPolygon(graphics, poly, color.darker());
					dotRender(graphics,poly);
				}
			}

			if (config.showSpotIcons())
			{
				BufferedImage fishImage = itemManager.getImage(spot.getFishSpriteId());

				if (spot == FishingSpot.COMMON_TENCH
					&& npc.getWorldLocation().distanceTo2D(client.getLocalPlayer().getWorldLocation()) <= ONE_TICK_AERIAL_FISHING)
				{
					fishImage = ImageUtil.outlineImage(itemManager.getImage(spot.getFishSpriteId()), color);
				}

				if (fishImage != null)
				{
					Point imageLocation = npc.getCanvasImageLocation(fishImage, npc.getLogicalHeight());
					if (imageLocation != null)
					{
						OverlayUtil.renderImageLocation(graphics, imageLocation, fishImage);
					}
				}
			}

			if (config.showSpotNames())
			{
				String text = spot.getName();
				Point textLocation = npc.getCanvasTextLocation(graphics, text, npc.getLogicalHeight() + 40);

				if (textLocation != null)
				{
					OverlayUtil.renderTextLocation(graphics, textLocation, text, color.darker());
				}
			}

			previousSpot = spot;
			previousLocation = npc.getWorldLocation();
		}

		return null;
	}
	private void dotRender(Graphics2D graphics, Shape s){
		if (s == null) return;

		Rectangle2D r = s.getBounds();
		double x, y;
		do {
			x = r.getBounds2D().getX() + boxMuller(r.getWidth()/2,r.getWidth()/config.deviation());
			if (x <= 1)
				x++;
			y = r.getBounds2D().getY() + boxMuller(r.getHeight()/2,r.getHeight()/config.deviation());
			if (y <= 1)
				y++;
		} while(!s.contains(x,y));
		Shape dotRender = new Ellipse2D.Double(x,y, config.dotSize(), config.dotSize());
		if(r.contains(dotRender.getBounds2D())){
			graphics.setColor(config.getDotColor());
			graphics.fill(dotRender);
		}
	}

	public double boxMuller(double mean, double variance) {
		double U = Math.random();
		double V = (Math.random() * 5.2831853071795862);
		U = Math.sqrt(-2*Math.log(U))*variance;
		return mean + U*Math.cos(V);
	}
}
