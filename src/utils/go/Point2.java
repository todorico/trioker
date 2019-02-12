package utils.go;

import java.lang.Math.*;

import utils.aff.Couleur;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

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
	
	public void rotate(Point2 center, double angle) {	      
		double radians = Math.toRadians(angle);
		this.x = (this.x - center.x) * Math.cos(radians) + (this.y - center.y) * Math.sin(radians) + center.x;
		this.y = -(this.x - center.x) * Math.sin(radians) + (this.y - center.y) * Math.cos(radians) + center.y;
	}
	
	public void draw(Graphics2D g) {
		int midWidth = 5;
		g.setColor(Couleur.fg);
		g.fill(new Ellipse2D.Double(this.x - midWidth, this.y - midWidth, 2 * midWidth, 2 * midWidth));
	}
	
	static double distance(Point2 p1, Point2 p2) {
		return Math.sqrt(distanceSquared(p1, p2));
	}

	static double distanceSquared(Point2 p1, Point2 p2) {
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
	}
}


