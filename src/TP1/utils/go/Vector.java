package TP1.utils.go;

import java.awt.Graphics2D;

import TP1.utils.aff.Couleur;

public class Vector {

	public double x, y;
	
	public static final Vector up = new Vector(0, -1);
	public static final Vector down = new Vector(0, 1);
	public static final Vector left = new Vector(-1, 0);
	public static final Vector right = new Vector(1, 0);

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Point from, Point to) {
		this.x = to.x - from.x;
		this.y = to.y - from.y;
	}
	
	public Vector add(Vector v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector soustract(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vector multiply(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	public Vector divide(double scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}
	
	public Vector normalize() {
		return this.divide(this.length());
	}
	
	public double angle() {
		return Math.toDegrees(Math.atan2(this.y, this.x));
	}
	
	public double angle(Vector v) {
		return Math.toDegrees(Math.acos(this.dot(v)/(this.length() * v.length())));
	}
	
	public double lengthSquared() {
		return (this.x * this.x) + (this.y * this.y);
	}
	
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	public double dot(Vector v) {
		return (this.x * v.x) + (this.y * v.y);
	}
	
	public void draw(Graphics2D g, Point from) {
		g.setColor(Couleur.fg);
		g.drawLine((int)from.x , (int)from.y , (int)(from.x + this.x), (int)(from.y + this.y));
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	// Methodes statiques
	
	public static Vector ToVector2(double angle) {
		double radians = Math.toRadians(angle);
		return new Vector(Math.acos(radians), -Math.asin(radians));
	}
	
	public static Vector rotation(Vector v, double angle) {
		double radians = Math.toRadians(angle);

		double cs = Math.cos(radians);
		double sn = -Math.sin(radians);

		return new Vector(v.x * cs - v.y * sn, v.x * sn + v.y * cs);
	}
	
	public static double ToAngle(Vector v) {
		return Math.toDegrees(Math.atan2(v.y, v.x));
	}
	
	public static Vector addition(Vector left, Vector right) {
		return new Vector(left.x + right.x, left.y + right.y);
	}
	
	public static Vector soustraction(Vector left, Vector right) {
		return new Vector(left.x - right.x, left.y - right.y);
	}
	
	public static Vector multiplication(Vector v, double scalar) {
		return new Vector(v.x * scalar, v.y * scalar);
	}
	
	public static Vector division(Vector v, double scalar) {
		return new Vector(v.x / scalar, v.y / scalar);
	}
	
	public static Vector normalization(Vector v) {
		return division(v, v.length());
	}
}


