package TP1.utils.go;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class PieceTrioker extends TriangleEqui {
	
	private static int pointMidSize = 3;
	
	public int leftLabel, rightLabel, upLabel;
	private boolean isReversed = false;

	public PieceTrioker(Point center, double rayon, int leftLabel, int rightLabel, int upLabel) {
		super(center, rayon);
		this.leftLabel = leftLabel;
		this.rightLabel = rightLabel;
		this.upLabel = upLabel;
	}
	
	public PieceTrioker(Point left, Point right, Point up, int leftLabel, int rightLabel, int upLabel) {
		super(left, right, up);
		this.leftLabel = leftLabel;
		this.rightLabel = rightLabel;
		this.upLabel = upLabel;
	}
	
	public void rotateLeft() {
		rotate(-120);
	}
	
	public void rotateRight() {
		rotate(120);
	}
	
	public void reverse() {
		
		Vector direction = new Vector(0, 0);
		
		if (!isReversed())
			direction.add(Vector.multiplication(Vector.up, this.radius()/2));
		else
			direction.add(Vector.multiplication(Vector.down, this.radius()/2));
		
		translate(direction);
		
		this.isReversed = !this.isReversed;
		
		rotate(180);
	}
	
	public boolean isReversed() {
		return this.isReversed;
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		drawPoints(g); // dessine des points en fonction des labels 
		drawLabel(g);  // dessine une chaine en fonction des labels
	}
	
	private void drawPoints(Graphics2D g2d) {
		
		// Vecteurs de direction vers le centre
		
		Vector upToCenter = new Vector(up, center()).normalize();
		Vector leftToCenter = new Vector(left, center()).normalize();
		Vector rightToCenter = new Vector(right, center()).normalize();
				
		Vector upToLeft = new Vector(up, left).normalize();
		Vector upToRight = new Vector(up, right).normalize();
	
		Vector leftToUp = new Vector(left, up).normalize();
		Vector leftToRight = new Vector(left, right).normalize();
		
		Vector rightToLeft = new Vector(right, left).normalize();
		Vector rightToUp = new Vector(right, up).normalize();
		
		// Vecteurs pour les points qui represente les labels
		
		double arete = 12;
		double distClosest = 16;
		double distFarthest = 26;
		
		// Point up
		switch (upLabel) {
			case 1:
				Point.translation(up, Vector.multiplication(upToCenter, distClosest)).draw(g2d, pointMidSize);
			break;
			case 2:
				Point.translation(up, Vector.multiplication(upToCenter, distClosest)).draw(g2d, pointMidSize);
				Point.translation(up, Vector.multiplication(upToCenter, distFarthest)).draw(g2d, pointMidSize);
			break;
			case 3:
				Point closest = Point.translation(up, Vector.multiplication(upToCenter, distClosest));
				closest.draw(g2d, pointMidSize);
				Point.translation(closest, Vector.multiplication(upToLeft, arete)).draw(g2d, pointMidSize);
				Point.translation(closest, Vector.multiplication(upToRight, arete)).draw(g2d, pointMidSize);
			break;
		}
		
		//Points left
		switch (leftLabel) {
			case 1:
				Point.translation(left, Vector.multiplication(leftToCenter, distClosest)).draw(g2d, pointMidSize);
			break;
			case 2:
				Point.translation(left, Vector.multiplication(leftToCenter, distClosest)).draw(g2d, pointMidSize);
				Point.translation(left, Vector.multiplication(leftToCenter, distFarthest)).draw(g2d, pointMidSize);
			break;
			case 3:
				Point closest = Point.translation(left, Vector.multiplication(leftToCenter, distClosest));
				closest.draw(g2d, pointMidSize);
				Point.translation(closest, Vector.multiplication(leftToUp, arete)).draw(g2d, pointMidSize);
				Point.translation(closest, Vector.multiplication(leftToRight, arete)).draw(g2d, pointMidSize);
			break;
		}
		
		// Point right
		switch (rightLabel) {
			case 1:
				Point.translation(right, Vector.multiplication(rightToCenter, distClosest)).draw(g2d, pointMidSize);
			break;
			case 2:
				Point.translation(right, Vector.multiplication(rightToCenter, distClosest)).draw(g2d, pointMidSize);
				Point.translation(right, Vector.multiplication(rightToCenter, distFarthest)).draw(g2d, pointMidSize);
			break;
			case 3:
				Point closest = Point.translation(right, Vector.multiplication(rightToCenter, distClosest));
				closest.draw(g2d, pointMidSize);
				Point.translation(closest, Vector.multiplication(rightToLeft, arete)).draw(g2d, pointMidSize);
				Point.translation(closest, Vector.multiplication(rightToUp, arete)).draw(g2d, pointMidSize);
			break;
		}
	}

	public void drawLabel(Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics();
		String labelStr = "" + this.leftLabel + " " + this.rightLabel + " " + this.upLabel;
		Point center = this.center();
		int centeredText = (int) (center.x - fm.stringWidth(labelStr) / 2);
		g2d.drawString(labelStr, centeredText, (int) (center.y + 5)); // - midWidth - fm.getDescent()
	}
}

