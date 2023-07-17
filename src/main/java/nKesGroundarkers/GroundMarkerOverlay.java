/*
 * Copyright (c) 2018, TheLonelyDev <https://github.com/TheLonelyDev>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
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
package nKesGroundarkers;

import com.google.common.base.Strings;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import javax.annotation.Nullable;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

public class GroundMarkerOverlay extends Overlay
{
	private static final int MAX_DRAW_DISTANCE = 32;

	private final Client client;
	private final GroundMarkerConfig config;
	private final nKesGroundMarkerPlugin plugin;

	@Inject
	private GroundMarkerOverlay(Client client, GroundMarkerConfig config, nKesGroundMarkerPlugin plugin)
	{
		this.client = client;
		this.config = config;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.LOW);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final Collection<ColorTileMarker> points = plugin.getPoints();
		if (points.isEmpty())
		{
			return null;
		}

		Stroke stroke = new BasicStroke((float) config.borderWidth());
		for (final ColorTileMarker point : points)
		{
			WorldPoint worldPoint = point.getWorldPoint();
			if (worldPoint.getPlane() != client.getPlane())
			{
				continue;
			}

			Color tileColor = point.getColor();
			if (tileColor == null || !config.rememberTileColors())
			{
				// If this is an old tile which has no color, or rememberTileColors is off, use marker color
				tileColor = config.markerColor();
			}
			drawTile(graphics, worldPoint, tileColor, point.getLabel(), stroke);
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

	private void drawTile(Graphics2D graphics, WorldPoint point, Color color, @Nullable String label, Stroke borderStroke)
	{
		WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();

		if (point.distanceTo(playerLocation) >= MAX_DRAW_DISTANCE)
		{
			return;
		}

		LocalPoint lp = LocalPoint.fromWorld(client, point);
		if (lp == null)
		{
			return;
		}

		Polygon poly = Perspective.getCanvasTilePoly(client, lp);
		if (poly != null)
		{
			dotRender(graphics,poly);
			OverlayUtil.renderPolygon(graphics, poly, color, new Color(0, 0, 0, config.fillOpacity()), borderStroke);
		}

		if (!Strings.isNullOrEmpty(label))
		{
			Point canvasTextLocation = Perspective.getCanvasTextLocation(client, graphics, lp, label, 0);
			if (canvasTextLocation != null)
			{
				OverlayUtil.renderTextLocation(graphics, canvasTextLocation, label, color);
			}
		}
	}
}
