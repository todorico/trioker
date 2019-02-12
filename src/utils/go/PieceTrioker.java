package utils.go;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class PieceTrioker extends TriangleEqui {
	
	private static int pointMidSize = 3;
	
	private int leftLabel, rightLabel, upLabel;

	public PieceTrioker(Point2 center, double rayon, int leftLabel, int rightLabel, int upLabel) {
		super(center, rayon);
		this.leftLabel = leftLabel;
		this.rightLabel = rightLabel;
		this.upLabel = upLabel;
	}
	
	public void rotateLeft() {
		rotate(-60);
	}
	
	public void rotateRight() {
		rotate(60);
	}
	
	public void reverse() {
		rotate(180);
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		drawPoints(g); // dessine des points en fonction des labels 
		drawLabel(g);  // dessine une chaine en fonction des labels
	}

	public void dessine(Graphics2D g2d) {
		//Affichage triangle
		Vecteur v1 = new Vecteur(this.pGauche, this.pDroite);
		Vecteur v2 = new Vecteur(this.pDroite, this.pHaut);
		Vecteur v3 = new Vecteur(this.pHaut, this.pGauche);
		v1.dessine(g2d);
		v2.dessine(g2d);
		v3.dessine(g2d);

		//Affichage points
		drawPoints(g2d);
		
		//Affichage label
		drawLabel(g2d);
	}

	private void drawPoints(Graphics2D g2d) {
		
		//0 : ne rien faire

//		Point p1 = new Point(pHaut.x, pHaut.y + 20);
//		p1.dessine(g2d);
		
		//Points Haut
		switch (upLabel) {
			case 1:
				afficherPoint(g2d, up.x, pHaut.y + 16);
				break;
			case 2:
				afficherPoint(g2d, pHaut.x, pHaut.y + 16);
				afficherPoint(g2d, pHaut.x, pHaut.y + 26);
				break;
			case 3:
				afficherPoint(g2d, pHaut.x, pHaut.y + 16);
				afficherPoint(g2d, pHaut.x - 6, pHaut.y + 26);
				afficherPoint(g2d, pHaut.x + 6, pHaut.y + 26);
				break;
		}
		
		//Points Gauche
		switch (labelGauche) {
			case 1:
				afficherPoint(g2d, pGauche.x + 16, pGauche.y - 8);
				break;
			case 2:
				afficherPoint(g2d, pGauche.x + 16, pGauche.y - 8);
				afficherPoint(g2d, pGauche.x + 26, pGauche.y - 14);
				break;
			case 3:
				afficherPoint(g2d, pGauche.x + 16, pGauche.y - 8);
				afficherPoint(g2d, pGauche.x + 22, pGauche.y - 18);
				afficherPoint(g2d, pGauche.x + 28, pGauche.y - 8);
				break;
		}
		
		//Points Droite
		switch (labelDroite) {
			case 1:
				afficherPoint(g2d, pDroite.x - 16, pDroite.y - 8);
				break;
			case 2:
				afficherPoint(g2d, pDroite.x - 16, pDroite.y - 8);
				afficherPoint(g2d, pDroite.x - 26, pDroite.y - 14);
				break;
			case 3:
				afficherPoint(g2d, pDroite.x - 16, pDroite.y - 8);
				afficherPoint(g2d, pDroite.x - 22, pDroite.y - 18);
				afficherPoint(g2d, pDroite.x - 28, pDroite.y - 8);
				break;
		}
	}

	private void afficherPoint(Graphics2D g2d, double x, double y) {
		g2d.fill(new Ellipse2D.Double(x - pointMidSize, y - pointMidSize, 2*pointMidSize, 2*pointMidSize));
	}

	public void drawLabel(Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics();
		String labelStr = "" + this.labelGauche + " " + this.labelDroite + " " + this.labelHaut;
		Point center = this.center();
		int centeredText = (int) (center.x - fm.stringWidth(labelStr) / 2);
		g2d.drawString(labelStr, centeredText, (int) (center.y + 5)); // - midWidth - fm.getDescent()
	}

	public boolean isSelected(int x, int y) {
		if (!isReversed)
			return (y <= this.pHaut.y && y >= this.pGauche.y) && (x >= this.pGauche.x && x <= this.pDroite.x);
		else
			return (y >= this.pHaut.y && y <= this.pGauche.y) && (x >= this.pGauche.x && x <= this.pDroite.x);
	}
}
