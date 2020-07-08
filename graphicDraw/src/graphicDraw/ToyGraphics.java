package graphicDraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class ToyGraphics extends JComponent{

	int width, height;
	BufferedImage image;
	protected Graphics2D g2d;
	public boolean isMousePressed = false;
	public void initImage(int w,int h) {
		width = w;
		height = h;
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		g2d = image.createGraphics();
		setFont();
		clear();
	}
	public void initFrame() {
		JFrame frame = new JFrame("Graphic Window");
		Container c = frame.getContentPane();
		Dimension size = getPreferredSize();
		int w = size.width;
		int h = size.height;
		c.setSize(w,h);
		c.add(this,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(),image.getHeight());
	}
	public ToyGraphics() { this(1280,720); }
	public ToyGraphics(int w,int h) {
		initImage(w,h);
		initFrame();
	}
	public void setFont() { setFont("Serif",24); }
	public void setFont(String name,int size) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font(name,Font.PLAIN,size);
		g2d.setFont(font);
	}
	public void setColor(Color col) { g2d.setColor(col); }
	public void drawText(String txt,int x,int y) {
		g2d.drawString(txt,x,y);
	}
	public void paint(Graphics g) {
		g.drawImage(image,0,0,this);
	}
	public static int rgb2pixel(int r,int g,int b)
			throws IllegalArgumentException {
		return rgb2pixel(255,r,g,b);
	}
	public static int rgb2pixel(int a,int r,int g,int b)
			throws IllegalArgumentException {
		if (a<0 || a>255 || r<0 || r>255 || g<0 || g>255 || b<0 || b>255)
			throw new IllegalArgumentException("bad color:("+a+","+r+","+g+","+b+")");
		return (a<<24) | (r << 16) | (g << 8) | (b);
	}
	public void drawRGB(int x,int y,int r,int g,int b) {
		setRGB(x,y,r,g,b);
		repaint(x,y,1,1);
	}
	public void drawRGB(int x,int y,int pxl) {
		setRGB(x,y,pxl);
		repaint(x,y,1,1);
	}
	public void setRGB(int x,int y,int r,int g,int b) {
		setRGB(x,y,rgb2pixel(r,g,b));
	}
	public void setRGB(int x,int y,int pxl) {
		if (x < 0 || x>width || y<0 || y>height) return;
		image.setRGB(x,y,pxl);
	}
	public void clear() {
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				setRGB(x,y,0,0,0);
			}
		}
	}
	private void fillRandomColor(Random random) {
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				int pxl = random.nextInt() | 0xff000000;
				setRGB(x,y,pxl);
			}
		}
	}

}
