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
	static final int FILLRECTANGLE = 4;
	static final int FILLCIRCLE = 5;
	static final int FILLLINE = 6;
	private static final int menuWidth = 140;
	private static final int menuHeight = 30;

	boolean isDragging = false;
	int sx, sy, ex, ey; // Mouse pressed (sx,sy), released (ex,ey)
	Color strokeColor = Color.red;
	int shapeType = RECTANGLE;
	

	int[][] menuArea = { { 0, 0, menuWidth, menuHeight }, { 0, 30, menuWidth, menuHeight }, { 0, 60, menuWidth, menuHeight }, { 0, 90, menuWidth, menuHeight },
			{ 0, 120, menuWidth, menuHeight },{ 0, 150, menuWidth, menuHeight },{ 0, 180, menuWidth, menuHeight },{ 0, 210, menuWidth, menuHeight },{0, 240, menuWidth, menuHeight},{0, 270, menuWidth, menuHeight},{0, 300, menuWidth, menuHeight},{ 0, 330, menuWidth, menuHeight },
			{ 0, 360, menuWidth, menuHeight },{ 0, 390, menuWidth, menuHeight },{0,420,menuWidth,menuHeight} };
	String[] menuString = { "Redraw", "Red", "Blue", "Yellow","Pink","Rectangle", "Circle","Line","FillRectangle", "FillCircle","Undo","Redo","MoveShape","AllClear","AllClearCancel"};
	ArrayList<Shape> shapes;
	ArrayList<Shape> shapesClone;
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
					shapeType = FILLRECTANGLE;
					break;
				case 9:
					shapeType = FILLCIRCLE;
					break;
				case 10:
					undo();
					break;
				case 11:
					redo();
					break;
				case 12:
					moveShape();
					break;
				case 13:
					allClear();
					break;
				case 14:
					allClearCancel();
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
		
		shapesClone = (ArrayList<Shape>) shapes.clone();
		shapes.clear();
		redraw();
	}
	void allClearCancel() {
		shapes = shapesClone;
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
	void moveShape() {
		Shape doubleShape;
		doubleShape = shapes.get(shapes.size()-1);
		shapes.add(doubleShape);
		doubleShape.x += 30;
		doubleShape.y -= 30;
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
		case FILLRECTANGLE:
			shape = new Rect(x, y, w, h, 3, strokeColor, strokeColor);
			break;
		case FILLCIRCLE:
			shape = new Circle(x, y, w, h, 3, strokeColor, strokeColor);
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
