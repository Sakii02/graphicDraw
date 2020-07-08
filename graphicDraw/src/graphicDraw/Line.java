package graphicDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Line extends Shape{
	int x1, x2, y1, y2,lineWidth;

	public Line(int x1, int y1, int x2, int y2, int lineWidth, Color strokeColor, Color fillColor) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.lineWidth = lineWidth;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
	}

	void draw(Graphics2D g2d) {

		Line2D shape = new Line2D.Double(x1, y1, x2, y2);

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
