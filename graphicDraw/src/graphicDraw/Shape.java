package graphicDraw;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {

	int x, y;
    Color strokeColor, fillColor;
    abstract void draw(Graphics2D g2d);

}
