package utils.go;

import java.awt.Graphics2D;

public class Point {

	public double x, y;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public void dessine(Graphics2D g2d) {
		toPointVisible().dessine(g2d);
	}
	
	public PointVisible toPointVisible() {
		return new PointVisible(x, y);
	}
	
}
