package TP1.utils.go;

import java.awt.Graphics2D;

public class Line {
	
	public Point from;
	public Point to;
	
	public Line(Point from, Point to) {
		this.from = from;
		this.to = to;
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared() {
		return (from.x - to.x) * (from.x - to.x) + (from.y - to.y) * (from.y - to.y);
	}
	
	public void draw(Graphics2D g) {
		g.drawLine((int)from.x , (int)from.y , (int)to.x, (int)to.y);
	}
	
	public static boolean intersection(Line a, Line b) {
		return Point.lineIntersect(a.from, a.to, b.from, b.to);
	}
}
