package utils.go;

import java.lang.Math.*;
import java.awt.Graphics2D;

public class Vector2 {

	public double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
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
		
	}
	
	public double lengthSquared() {
		return (this.x * this.x) + (this.y * this.y);
	}
	
	public double length() {
		return Math.sqrt(this.lengthSquared());
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


