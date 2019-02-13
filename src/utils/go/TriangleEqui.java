package utils.go;

import java.util.*;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class TriangleEqui {
	
	// Sens des sommets anti horraire
	
	protected Point2 left, right, up;
	
	public TriangleEqui(Point2 center, double radius) {
		this.left = new Point2(center.x - radius, center.y - radius);
		this.right = new Point2(center.x + radius, center.y - radius);
		this.up = new Point2(center.x, center.y + radius);
	}
	
	public TriangleEqui(Point2 left, Point2 right, Point2 up) {
		this.left = left;
		this.right = right;
		this.up = up;
	}
	
	public double radius() {
		Vector2 toUp = new Vector2(center(), up);
		return toUp.length();
	}
	
	public Point2 center() {
		return new Point2((this.left.x + this.right.x + this.up.x)/3, (this.left.y + this.right.y + this.up.y)/3);
	}

	public void translate(Vector2 v) {
		this.left.translate(v);
		this.right.translate(v);
		this.up.translate(v);
	}
	
	public void rotate(double angle) {
		Point2 c = center();
		this.left.rotate(c, angle);
		this.right.rotate(c, angle);
		this.up.rotate(c, angle);
	}
	
	public double angle() {
		Vector2 toUp = new Vector2(this.center(), this.up);
		return toUp.angle();
	}
	
	public double area() {
		return area(left, right, up);
	}
	/* Calcul l'aire entre 3 points. */
	public static double area(Point2 left, Point2 right, Point2 up) {
		
		Vector2 leftToRight = new Vector2(left, right);
		
		double largeur = leftToRight.length();
		
		Point2 middle = Point2.translation(left, Vector2.division(leftToRight, 2));
		Vector2 upToMiddle = new Vector2(up, middle);
		
		double hauteur = upToMiddle.length();
		
		return Math.abs((largeur * hauteur)/2.0);
		//return Math.abs((left.x * (up.y - right.y) + right.x * (left.y - up.y) + up.x * (right.y - left.y))/2.0); 
	}
	
	public boolean contains(double x, double y) {
		return contains(new Point2(x, y));
	}
	
	public boolean contains(Point2 p) {
		   /* Calcule l'aire du triangle courant */
		   double A = this.area(); 
		  
		   /* Calcule l'aire du triangle p right up */   
		   double A1 = area (p, right, up); 
		  
		   /* Calcule l'aire du triangle left p up */    
		   double A2 = area (left, p, up); 
		   
		   /* Calcule l'aire triangle left right p */   
		   double A3 = area (left, right, p); 
		   
		   double SA = (A1 + A2 + A2);

		   /* Si la somme de A1 A2 A3 est egale à A alors le p est dans le triangle */ 
		   return (SA >= (A - 5000)) && (SA <= (A + 5000));
		   //return (A == A1 + A2 + A3);
	}
	
	public void draw(Graphics2D g) {
		//center().draw(g);
		//left.draw(g);
		//right.draw(g);
		up.draw(g);
		g.drawLine((int)this.left.x , (int)this.left.y , (int)this.right.x, (int)this.right.y);
		g.drawLine((int)this.right.x , (int)this.right.y , (int)this.up.x, (int)this.up.y);
		g.drawLine((int)this.up.x , (int)this.up.y , (int)this.left.x, (int)this.left.y);
	}
}
