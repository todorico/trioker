package utils.go;

import java.util.*;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class TriangleEqui {
	
	// Sens des sommets anti horraire
	
	protected Point2 left, right, up;
	
	public TriangleEqui(Point2 center, double rayon) {
		this.left = new Point2(center.x - rayon, center.y - rayon);
		this.right = new Point2(center.x + rayon, center.y - rayon);
		this.up = new Point2(center.x, center.y + rayon);
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
		Point2 c = this.center();
		this.left.rotate(c, angle);
		this.right.rotate(c, angle);
		this.up.rotate(c, angle);
	}
	
	public void draw(Graphics2D g) {
		g.drawLine((int)this.left.x , (int)this.left.y , (int)this.right.x, (int)this.right.y);
		g.drawLine((int)this.right.x , (int)this.right.y , (int)this.up.x, (int)this.up.y);
		g.drawLine((int)this.up.x , (int)this.up.y , (int)this.left.x, (int)this.left.y);
	}
}
