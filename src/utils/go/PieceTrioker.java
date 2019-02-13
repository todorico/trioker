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
		
		//0 : ne rien faire

//		Point p1 = new Point(up.x, up.y + 20);
//		p1.dessine(g2d);
		
		//Points Haut
		switch (upLabel) {
			case 1:
				afficherPoint(g2d, up.x, up.y + 16);
				break;
			case 2:
				afficherPoint(g2d, up.x, up.y + 16);
				afficherPoint(g2d, up.x, up.y + 26);
				break;
			case 3:
				afficherPoint(g2d, up.x, up.y + 16);
				afficherPoint(g2d, up.x - 6, up.y + 26);
				afficherPoint(g2d, up.x + 6, up.y + 26);
				break;
		}
		
		//Points Gauche
		switch (leftLabel) {
			case 1:
				afficherPoint(g2d, left.x + 16, left.y - 8);
				break;
			case 2:
				afficherPoint(g2d, left.x + 16, left.y - 8);
				afficherPoint(g2d, left.x + 26, left.y - 14);
				break;
			case 3:
				afficherPoint(g2d, left.x + 16, left.y - 8);
				afficherPoint(g2d, left.x + 22, left.y - 18);
				afficherPoint(g2d, left.x + 28, left.y - 8);
				break;
		}
		
		//Points Droite
		switch (rightLabel) {
			case 1:
				afficherPoint(g2d, right.x - 16, right.y - 8);
				break;
			case 2:
				afficherPoint(g2d, right.x - 16, right.y - 8);
				afficherPoint(g2d, right.x - 26, right.y - 14);
				break;
			case 3:
				afficherPoint(g2d, right.x - 16, right.y - 8);
				afficherPoint(g2d, right.x - 22, right.y - 18);
				afficherPoint(g2d, right.x - 28, right.y - 8);
				break;
		}
	}

	private void afficherPoint(Graphics2D g2d, double x, double y) {
		g2d.fill(new Ellipse2D.Double(x - pointMidSize, y - pointMidSize, 2*pointMidSize, 2*pointMidSize));
	}

	public void drawLabel(Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics();
		String labelStr = "" + this.leftLabel + " " + this.rightLabel + " " + this.upLabel;
		Point2 center = this.center();
		int centeredText = (int) (center.x - fm.stringWidth(labelStr) / 2);
		g2d.drawString(labelStr, centeredText, (int) (center.y + 5)); // - midWidth - fm.getDescent()
	}
/*
	public boolean isSelected(int x, int y) {
		if (!isReversed)
			return (y <= this.up.y && y >= this.left.y) && (x >= this.left.x && x <= this.right.x);
		else
			return (y >= this.up.y && y <= this.left.y) && (x >= this.left.x && x <= this.up.x);
	}
*/
}

