package utils.go;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class PieceTrioker extends TriangleEqui {
	
	private static int pointMidSize = 3;
	
	private int leftLabel, rightLabel, upLabel;
	private boolean isReversed = false;

	public PieceTrioker(Point2 center, double rayon, int leftLabel, int rightLabel, int upLabel) {
		super(center, rayon);
		this.leftLabel = leftLabel;
		this.rightLabel = rightLabel;
		this.upLabel = upLabel;
	}
	
	public PieceTrioker(Point2 left, Point2 right, Point2 up, int leftLabel, int rightLabel, int upLabel) {
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
		
		Vector2 direction = new Vector2(0, 0);
		
		if (!isReversed()) {
			direction.add(Vector2.multiplication(Vector2.up, -this.radius()/2));
		} else {
			direction.add(Vector2.multiplication(Vector2.down, -this.radius()/2));
		}
		
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
		
		Vector2 upToCenter = new Vector2(up, center()).normalize();
		Vector2 leftToCenter = new Vector2(left, center()).normalize();
		Vector2 rightToCenter = new Vector2(right, center()).normalize();
				
		Vector2 upToLeft = new Vector2(up, left).normalize();
		Vector2 upToRight = new Vector2(up, right).normalize();
	
		Vector2 leftToUp = new Vector2(left, up).normalize();
		Vector2 leftToRight = new Vector2(left, right).normalize();
		
		Vector2 rightToLeft = new Vector2(right, left).normalize();
		Vector2 rightToUp = new Vector2(right, up).normalize();
		
		// Vecteurs pour les points qui represente les labels
		
		double arete = 12;
		double distClosest = 16;
		double distFarthest = 26;
		
		// Point up
		switch (upLabel) {
			case 1:
				Point2.translation(up, Vector2.multiplication(upToCenter, distClosest)).draw(g2d, pointMidSize);
			break;
			case 2:
				Point2.translation(up, Vector2.multiplication(upToCenter, distClosest)).draw(g2d, pointMidSize);
				Point2.translation(up, Vector2.multiplication(upToCenter, distFarthest)).draw(g2d, pointMidSize);
			break;
			case 3:
				Point2 closest = Point2.translation(up, Vector2.multiplication(upToCenter, distClosest));
				closest.draw(g2d, pointMidSize);
				Point2.translation(closest, Vector2.multiplication(upToLeft, arete)).draw(g2d, pointMidSize);
				Point2.translation(closest, Vector2.multiplication(upToRight, arete)).draw(g2d, pointMidSize);
			break;
		}
		
		//Points left
		switch (leftLabel) {
			case 1:
				Point2.translation(left, Vector2.multiplication(leftToCenter, distClosest)).draw(g2d, pointMidSize);
			break;
			case 2:
				Point2.translation(left, Vector2.multiplication(leftToCenter, distClosest)).draw(g2d, pointMidSize);
				Point2.translation(left, Vector2.multiplication(leftToCenter, distFarthest)).draw(g2d, pointMidSize);
			break;
			case 3:
				Point2 closest = Point2.translation(left, Vector2.multiplication(leftToCenter, distClosest));
				closest.draw(g2d, pointMidSize);
				Point2.translation(closest, Vector2.multiplication(leftToUp, arete)).draw(g2d, pointMidSize);
				Point2.translation(closest, Vector2.multiplication(leftToRight, arete)).draw(g2d, pointMidSize);
			break;
		}
		
		// Point right
		switch (rightLabel) {
			case 1:
				Point2.translation(right, Vector2.multiplication(rightToCenter, distClosest)).draw(g2d, pointMidSize);
			break;
			case 2:
				Point2.translation(right, Vector2.multiplication(rightToCenter, distClosest)).draw(g2d, pointMidSize);
				Point2.translation(right, Vector2.multiplication(rightToCenter, distFarthest)).draw(g2d, pointMidSize);
			break;
			case 3:
				Point2 closest = Point2.translation(right, Vector2.multiplication(rightToCenter, distClosest));
				closest.draw(g2d, pointMidSize);
				Point2.translation(closest, Vector2.multiplication(rightToLeft, arete)).draw(g2d, pointMidSize);
				Point2.translation(closest, Vector2.multiplication(rightToUp, arete)).draw(g2d, pointMidSize);
			break;
		}
	}

	public void drawLabel(Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics();
		String labelStr = "" + this.leftLabel + " " + this.rightLabel + " " + this.upLabel;
		Point2 center = this.center();
		int centeredText = (int) (center.x - fm.stringWidth(labelStr) / 2);
		g2d.drawString(labelStr, centeredText, (int) (center.y + 5)); // - midWidth - fm.getDescent()
	}
}

