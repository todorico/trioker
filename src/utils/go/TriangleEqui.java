package utils.go;

import java.util.*;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class TriangleEqui {
	
	// Sens des sommets anti horraire
	
	public Point left, right, up;
	
	public TriangleEqui(Point center, double radius) {
		Vector toUp = Vector.multiplication(Vector.up, radius);
		this.up = Point.translation(center, toUp);
		this.left = Point.translation(center, Vector.rotation(toUp, 120));
		this.right = Point.translation(center, Vector.rotation(toUp, -120));
	}
	
	public TriangleEqui(Point left, Point right, Point up) {
		this.left = left;
		this.right = right;
		this.up = up;
	}
	
	public double radius() {
		Vector toUp = new Vector(center(), up);
		return toUp.length();
	}
	
	public double width() {
		return new Vector(left, right).length();
	}
	
	public double height() {
		Point middle = Point.translation(left, new Vector(left, right).divide(2));
		return new Vector(middle, up).length();
	}
	
	public Point center() {
		return new Point((this.left.x + this.right.x + this.up.x)/3, (this.left.y + this.right.y + this.up.y)/3);
	}

	public void translate(Vector v) {
		this.left.translate(v);
		this.right.translate(v);
		this.up.translate(v);
	}
	
	public void rotate(double angle) {
		Point c = center();
		this.left.rotate(c, angle);
		this.right.rotate(c, angle);
		this.up.rotate(c, angle);
	}
	
	public double angle() {
		Vector toUp = new Vector(this.center(), this.up);
		return toUp.angle();
	}
	
	public boolean contains(double x, double y) {
		return contains(new Point(x, y));
	}

	public boolean contains(Point p) {
		Point c = center();
		return !(Point.lineIntersect(p, c, left, right) || Point.lineIntersect(p, c, right, up) || Point.lineIntersect(p, c, up, left));
	}
	
	public void draw(Graphics2D g) {
		//center().draw(g);
		up.draw(g);
		//left.draw(g);
		//right.draw(g);
		g.drawLine((int)this.left.x , (int)this.left.y , (int)this.right.x, (int)this.right.y);
		g.drawLine((int)this.right.x , (int)this.right.y , (int)this.up.x, (int)this.up.y);
		g.drawLine((int)this.up.x , (int)this.up.y , (int)this.left.x, (int)this.left.y);
	}
}
