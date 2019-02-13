package utils.go;

import java.lang.Math.*;

import utils.aff.Couleur;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Point {

	public double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point translate(Vector v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Point rotate(Point center, double angle) {	      
		Point p = rotation(this, center, angle);
		this.x = p.x;
		this.y = p.y;
		return this;
	}
	
	public void draw(Graphics2D g) {
		int midWidth = 5;
		g.setColor(Couleur.fg);
		g.fill(new Ellipse2D.Double(this.x - midWidth, this.y - midWidth, 2 * midWidth, 2 * midWidth));
	}
	
	public void draw(Graphics2D g, int midWidth) {
		g.setColor(Couleur.fg);
		g.fill(new Ellipse2D.Double(this.x - midWidth, this.y - midWidth, 2 * midWidth, 2 * midWidth));
	}
	
	public static Point rotation(Point p, Point center, double angle) {
		double radians = Math.toRadians(angle);
		return new Point(
					(p.x - center.x) * Math.cos(radians) + (p.y - center.y) * Math.sin(radians) + center.x,
					-(p.x - center.x) * Math.sin(radians) + (p.y - center.y) * Math.cos(radians) + center.y
				);
	}
	
	public static Point translation(Point p, Vector v) {
		return new Point(p.x + v.x, p.y + v.y);
	}
	
	public static double distance(Point p1, Point p2) {
		return Math.sqrt(distanceSquared(p1, p2));
	}
	
	public static double distanceSquared(Point p1, Point p2) {
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
	}

	// Detection de collision entre le segment a-b et c-d
	public static boolean lineIntersect(Point a, Point b, Point c, Point d) {
		
		double denominator = ((b.x - a.x) * (d.y - c.y)) - ((b.y - a.y) * (d.x - c.x));
		double numerator1 = ((a.y - c.y) * (d.x - c.x)) - ((a.x - c.x) * (d.y - c.y));
		double numerator2 = ((a.y - c.y) * (b.x - a.x)) - ((a.x - c.x) * (b.y - a.y));

		// Detect coincident lines (has a problem, read below)
		if (denominator == 0) 
			return numerator1 == 0 && numerator2 == 0;

		double r = numerator1 / denominator;
		double s = numerator2 / denominator;

		return (r >= 0 && r <= 1) && (s >= 0 && s <= 1);
	}
}


