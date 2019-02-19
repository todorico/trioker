package TP1.utils.go;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import TP1.utils.aff.Couleur;

public class Point2 {

	public double x, y;

	public Point2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void translate(Vector2 v) {
		this.x += v.x;
		this.y += v.y;
	}
	
	public Point2 rotate(Point2 center, double angle) {	      
		Point2 p = rotation(this, center, angle);
		this.x = p.x;
		this.y = p.y;
		return p;
	}
	
	public void draw(Graphics2D g) {
		int midWidth = 5;
		g.setColor(Couleur.fg);
		g.fill(new Ellipse2D.Double(this.x - midWidth, this.y - midWidth, 2 * midWidth, 2 * midWidth));
	}
	
	public static Point2 rotation(Point2 p, Point2 center, double angle) {
		double radians = Math.toRadians(angle);
		return new Point2(
					(p.x - center.x) * Math.cos(radians) + (p.y - center.y) * Math.sin(radians) + center.x,
					-(p.x - center.x) * Math.sin(radians) + (p.y - center.y) * Math.cos(radians) + center.y
				);
	}
	
	public static Point2 translation(Point2 p, Vector2 v) {
		return new Point2(p.x + v.x, p.y + v.y);
	}
	
	static public double distance(Point2 p1, Point2 p2) {
		return Math.sqrt(distanceSquared(p1, p2));
	}

	static public double distanceSquared(Point2 p1, Point2 p2) {
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
	}
}

