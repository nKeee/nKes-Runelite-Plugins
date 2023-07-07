/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
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
package nKesNpchighlight;

import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.game.npcoverlay.HighlightedNpc;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

class NpcRespawnOverlay extends Overlay
{
	// Anything but white text is quite hard to see since it is drawn on
	// a dark background
	private static final Color TEXT_COLOR = Color.WHITE;

	private static final NumberFormat TIME_LEFT_FORMATTER = DecimalFormat.getInstance(Locale.US);

	static
	{
		((DecimalFormat)TIME_LEFT_FORMATTER).applyPattern("#0.0");
	}

	private final Client client;
	private final NpcIndicatorsConfig config;
	private final nKesNpcIndicatorsPlugin plugin;

	@Inject
	NpcRespawnOverlay(Client client, NpcIndicatorsConfig config, nKesNpcIndicatorsPlugin plugin)
	{
		this.client = client;
		this.config = config;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}
	public double boxMuller(double mean, double variance) {
		double s,x,y;
		do {
			x = Math.random() * 2.0 - 1.0;
			y = Math.random() * 2.0 - 1.0;
			s = Math.pow(x, 2) + Math.pow(y, 2);
		} while ( (s > 1) || (s == 0) );

		double gaussian = x * Math.sqrt(-2*Math.log(s)/s);
		return mean + gaussian * Math.sqrt(variance);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Map<Integer, MemorizedNpc> deadNpcsToDisplay = plugin.getDeadNpcsToDisplay();
		Map<NPC, HighlightedNpc> aliveNpcsToDisplay = plugin.getHighlightedNpcs();

		for (NPC n: aliveNpcsToDisplay.keySet()) {
			Rectangle r = n.getConvexHull().getBounds();
			double x, y;
			do {
				x = boxMuller(r.getWidth()/2,r.getWidth()*3);
				y = boxMuller(r.getHeight()/2,r.getHeight()*3);
			} while(!n.getConvexHull().contains(x,y));
			Ellipse2D.Double dotRender = new Ellipse2D.Double(x,y, config.dotSize(), config.dotSize());
			graphics.setColor(config.getDotColor());
			graphics.fill(dotRender);
		}
		
		if (deadNpcsToDisplay.isEmpty() || !config.showRespawnTimer())
		{
			return null;
		}

		deadNpcsToDisplay.forEach((id, npc) -> renderNpcRespawn(npc, graphics));
		return null;
	}

	private void renderNpcRespawn(final MemorizedNpc npc, final Graphics2D graphics)
	{
		if (npc.getPossibleRespawnLocations().isEmpty())
		{
			return;
		}

		final WorldPoint respawnLocation = npc.getPossibleRespawnLocations().get(0);
		final LocalPoint lp = LocalPoint.fromWorld(client, respawnLocation.getX(), respawnLocation.getY());

		if (lp == null)
		{
			return;
		}

		final LocalPoint centerLp = new LocalPoint(
			lp.getX() + Perspective.LOCAL_TILE_SIZE * (npc.getNpcSize() - 1) / 2,
			lp.getY() + Perspective.LOCAL_TILE_SIZE * (npc.getNpcSize() - 1) / 2);

		final Polygon poly = Perspective.getCanvasTileAreaPoly(client, centerLp, npc.getNpcSize());
		renderPoly(graphics, config.highlightColor(), config.fillColor(), poly);

		final Instant now = Instant.now();
		final double baseTick = ((npc.getDiedOnTick() + npc.getRespawnTime()) - client.getTickCount()) * (Constants.GAME_TICK_LENGTH / 1000.0);
		final double sinceLast = (now.toEpochMilli() - plugin.getLastTickUpdate().toEpochMilli()) / 1000.0;
		final double timeLeft = Math.max(0.0, baseTick - sinceLast);
		final String timeLeftStr = TIME_LEFT_FORMATTER.format(timeLeft);

		final int textWidth = graphics.getFontMetrics().stringWidth(timeLeftStr);
		final int textHeight = graphics.getFontMetrics().getAscent();

		final Point canvasPoint = Perspective
			.localToCanvas(client, centerLp, respawnLocation.getPlane());

		if (canvasPoint != null)
		{
			final Point canvasCenterPoint = new Point(
				canvasPoint.getX() - textWidth / 2,
				canvasPoint.getY() + textHeight / 2);

			OverlayUtil.renderTextLocation(graphics, canvasCenterPoint, timeLeftStr, TEXT_COLOR);
		}
	}

	private void renderPoly(Graphics2D graphics, Color borderColor, Color fillColor, Shape polygon)
	{
		if (polygon != null)
		{
			graphics.setColor(borderColor);
			graphics.setStroke(new BasicStroke((float) config.borderWidth()));
			graphics.draw(polygon);
			graphics.setColor(fillColor);
			graphics.fill(polygon);
		}
	}
}
