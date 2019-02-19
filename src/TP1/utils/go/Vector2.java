package TP1.utils.go;

import java.awt.Graphics2D;

public class Vector2 {

	public double x, y;
	
	public static final Vector2 up = new Vector2(0, 1);
	public static final Vector2 down = new Vector2(0, -1);
	public static final Vector2 left = new Vector2(-1, 0);
	public static final Vector2 right = new Vector2(1, 0);

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Point2 from, Point2 to) {
		this.x = to.x - from.x;
		this.y = to.y - from.y;
	}
	
	public void add(Vector2 v) {
		this.x += v.x;
		this.y += v.y;
	}
	
	public void soustract(Vector2 v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	
	public void multiply(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}
	
	public void divide(double scalar) {
		this.x /= scalar;
		this.y /= scalar;
	}
	
	public void normalize() {
		this.divide(this.length());
	}
	
	public double angle() {
		return Math.toDegrees(Math.atan2(this.y, this.x));
	}
	
	public static Vector2 ToVector2(double angle) {
		double radians = Math.toRadians(angle);
		return new Vector2(Math.acos(radians), Math.asin(radians));
	}
	
	public double lengthSquared() {
		return (this.x * this.x) + (this.y * this.y);
	}
	
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	static public Vector2 addition(Vector2 left, Vector2 right) {
		return new Vector2(left.x + right.x, left.y + right.y);
	}
	
	static public Vector2 soustraction(Vector2 left, Vector2 right) {
		return new Vector2(left.x - right.x, left.y - right.y);
	}
	
	static public Vector2 multiplication(Vector2 v, double scalar) {
		return new Vector2(v.x * scalar, v.y * scalar);
	}
	
	static public Vector2 division(Vector2 v, double scalar) {
		return new Vector2(v.x / scalar, v.y / scalar);
	}
	
	static public Vector2 normalization(Vector2 v) {
		return division(v, v.length());
	}
	
	public void draw(Graphics2D g) {
		
	}
	/*
	public PointVisible toPointVisible() {
		return new PointVisible(x, y);
	}
	
	public void dessine(Graphics2D g2d) {
		toPointVisible().dessine(g2d);
	}
	*/
}


