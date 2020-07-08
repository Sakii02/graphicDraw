package graphicDraw;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class WinDraw extends ToyGraphics implements MouseListener {

	static final int RECTANGLE = 1;
	static final int CIRCLE = 2;
	static final int LINE = 3;

	boolean isDragging = false;
	int sx, sy, ex, ey; // Mouse pressed (sx,sy), released (ex,ey)
	Color strokeColor = Color.red;
	int shapeType = RECTANGLE;

	int[][] menuArea = { { 0, 0, 100, 30 }, { 0, 30, 100, 30 }, { 0, 60, 100, 30 }, { 0, 90, 100, 30 },
			{ 0, 120, 100, 30 },{ 0, 150, 100, 30 },{ 0, 180, 100, 30 },{ 0, 210, 100, 30 },{ 0, 240, 100, 30 },
			{ 0, 270, 100, 30 },{ 0, 300, 100, 30 } };
	String[] menuString = { "Redraw", "Red", "Blue", "Yellow","Pink","Rectangle", "Circle","Line","Undo","Redo","All Clear"};
	ArrayList<Shape> shapes;
	Shape shapeUndo;

	public WinDraw() {
		this(1280, 720);
	}

	public WinDraw(int w, int h) {
		shapes = new ArrayList<Shape>();
		addMouseListener(this);

	}

	public void run() {
		redraw();
	}

	boolean inArea(int mx, int my, int x, int y, int w, int h) {
		return (mx >= x && mx < x + w && my >= y && my < y + h);
	}

	boolean inArea(int mx, int my, int[] r) {
		int x = r[0], y = r[1], w = r[2], h = r[3];
		return (mx >= x && mx < x + w && my >= y && my < y + h);
	}

	boolean checkMenu(int sx, int sy) {
		for (int i = 0; i < menuArea.length; i++) {
			int[] r = menuArea[i];
			if (inArea(sx, sy, r)) {
				System.err.println("menu " + i);
				switch (i) {
				case 0:
					redraw();
					break;
				case 1:
					strokeColor = Color.red;
					break;
				case 2:
					strokeColor = Color.blue;
					break;
				case 3:
					strokeColor = Color.yellow;
					break;
				case 4:
					strokeColor = Color.pink;
					break;
				case 5:
					shapeType = RECTANGLE;
					break;
				case 6:
					shapeType = CIRCLE;
					break;
				case 7:
					shapeType = LINE;
					break;
				case 8:
					undo();
					break;
				case 9:
					redo();
					break;
				case 10:
					allClear();
					break;
				default:
					System.err.println("no menu");
					System.exit(-1);
					break;
				}
				return true;
			}
		}
		return false;
	}

	void redraw() {
		g2d.setPaint(Color.black);
		g2d.fill(new Rectangle2D.Double(0, 0, width, height));
		drawMenu();
		for (Shape shape : shapes) {
			shape.draw(g2d);
		}
		repaint(0, 0, width, height);
	}
	void allClear() {
		shapes.clear();
		redraw();
	}
	void undo() {
		shapeUndo = shapes.remove(shapes.size()-1);
		redraw();
	}
	void redo() {
		shapes.add(shapeUndo);
		redraw();
	}

	void drawMenu() {
		for (int i = 0; i < menuArea.length; i++) {
			int[] r = menuArea[i];
			Rectangle2D shape = new Rectangle2D.Double(r[0], r[1], r[2], r[3]);
			g2d.setPaint(Color.white);
			g2d.fill(shape);
			g2d.setPaint(Color.black);
			g2d.draw(shape);
			g2d.drawString(menuString[i], r[0] + 5, r[1] + 20);
			repaint(r[0], r[1], r[2], r[3]);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		sx = e.getX();
		sy = e.getY();
		System.err.println("Pressed " + sx + " " + sy);
		isDragging = !checkMenu(sx, sy);
	}

	public void mouseReleased(MouseEvent e) {
		ex = e.getX();
		ey = e.getY();
		System.err.println("Released " + ex + " " + ey);
		if (!isDragging)
			return; // select menu
		int x = Math.min(sx, ex), y = Math.min(sy, ey), w = Math.abs(ex - sx), h = Math.abs(ey - sy);
		Shape shape;
		switch(shapeType) {
		case RECTANGLE:
			shape = new Rect(x, y, w, h, 3, strokeColor, null);
			break;
		case CIRCLE:
			shape = new Circle(x, y, w, h, 3, strokeColor, null);
			break;
		case LINE:
			shape = new Line(sx,sy,ex,ey,3,strokeColor,null);
			break;
		default:
			shape = new Rect(x, y, w, h, 3, strokeColor, null);
			break;


		}

		/*if (shapeType == RECTANGLE) {
			shape = new Rect(x, y, w, h, 3, strokeColor, null);
		}else { // (shapeType == CIRCLE)
			shape = new Circle(x, y, w, h, 3, strokeColor, null);
		}*/


		shape.draw(g2d);
		repaint(x, y, w, h);
		shapes.add(shape);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public static void main(String[] args) {
		WinDraw wd = new WinDraw();
		wd.run();
	}

}
