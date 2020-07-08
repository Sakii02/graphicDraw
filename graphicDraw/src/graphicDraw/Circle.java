package graphicDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

public class Circle  extends Shape{

	int w, h, lineWidth;
    public Circle(int x,int y,int w,int h,int lineWidth,Color strokeColor,Color fillColor) {
	this.x = x; this.y = y; this.w = w; this.h = h; this.lineWidth = lineWidth;
	this.strokeColor = strokeColor; this.fillColor = fillColor;
    }
    void draw(Graphics2D g2d) {
	Arc2D shape = new Arc2D.Double(x,y,w,h,0,360,Arc2D.OPEN);
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
