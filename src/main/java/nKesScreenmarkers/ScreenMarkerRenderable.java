/*
 * Copyright (c) 2018, Kamiel, <https://github.com/Kamielvf>
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
package nKesScreenmarkers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.RenderableEntity;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class ScreenMarkerRenderable implements RenderableEntity
{
	private Dimension size;
	private int borderThickness;
	private Color color;
	private Color fill;
	private Color dot;
	private int dotSize;
	private int deviation;
	private Stroke stroke;
	private String label;

	@Override
	public Dimension render(Graphics2D graphics)
	{
		int thickness = borderThickness;
		int width = size.width;
		int height = size.height;

		//draw the fill
		graphics.setColor(fill);
		graphics.fillRect(thickness, thickness, width - thickness * 2, height - thickness * 2);

		//because the stroke is centered on the rectangle we draw, we need to translate where we draw the rectangle
		//this is to ensure that the rectangle we draw is our preferred size
		int offset = thickness / 2;
		graphics.setColor(color);
		graphics.setStroke(stroke);
		graphics.drawRect(offset, offset, width - thickness, height - thickness);

		if (!label.isEmpty())
		{
			graphics.drawString(label, 0, 0);
		}

		//draw the dot
		Rectangle2D r = new Rectangle(width,height);
		double x, y;
		do {
			x = r.getBounds2D().getX() + boxMuller(r.getWidth()/2,r.getWidth()/deviation);
			if (x <= 1)
				x++;
			y = r.getBounds2D().getY() + boxMuller(r.getHeight()/2,r.getHeight()/deviation);
			if (y <= 1)
				y++;
		} while(!r.contains(x,y));
		Shape dotRender = new Ellipse2D.Double(x,y, dotSize, dotSize);
		if(r.contains(dotRender.getBounds2D())){
			graphics.setColor(dot);
			graphics.fill(dotRender);
		}

		return size;
	}
	public double boxMuller(double mean, double variance) {
		double U = Math.random();
		double V = (Math.random() * 5.2831853071795862);
		U = Math.sqrt(-2*Math.log(U))*variance;
		return mean + U*Math.cos(V);
	}
}
