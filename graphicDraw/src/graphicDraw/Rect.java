package graphicDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Rect extends Shape{

	int w, h, lineWidth;

	public Rect(int x, int y, int w, int h, int lineWidth, Color strokeColor, Color fillColor) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.lineWidth = lineWidth;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
	}

	void draw(Graphics2D g2d) {
		Rectangle2D shape = new Rectangle2D.Double(x, y, w, h);
		if (fillColor != null) {
			g2d.setPaint(fillColor);
			g2d.fill(shape);
		}
		if (strokeColor != null) {
			g2d.setStroke(new BasicStroke((float) lineWidth));
			g2d.setPaint(strokeColor);
			g2d.draw(shape);
		}
	}

}
