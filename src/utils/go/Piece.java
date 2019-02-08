package utils.go;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Piece {
	
	private Point pGauche, pDroite, pHaut;
	private int labelGauche, labelDroite, labelHaut;
	private boolean isReversed;


	public Piece(Point pGauche, Point pDroite, Point pHaut, int labelGauche, int labelDroite, int labelHaut) {
		super();
		this.pGauche = pGauche;
		this.pDroite = pDroite;
		this.pHaut = pHaut;
		this.labelGauche = labelGauche;
		this.labelDroite = labelDroite;
		this.labelHaut = labelHaut;
		this.isReversed = false;
	}
	
	public void reverse() {
		this.isReversed = !this.isReversed;
	}
	
	public void rotateLeft() {
		
	}
	
	public Point center() {
		return new Point((this.pGauche.x + this.pDroite.x + this.pHaut.x)/3, (this.pGauche.y + this.pDroite.y + this.pHaut.y)/3);
	}

	public void dessine(Graphics2D g2d) {
		Vecteur v1 = new Vecteur(this.pGauche, this.pDroite);
		Vecteur v2 = new Vecteur(this.pDroite, this.pHaut);
		Vecteur v3 = new Vecteur(this.pHaut, this.pGauche);
		v1.dessine(g2d);
		v2.dessine(g2d);
		v3.dessine(g2d);

		drawLabel(g2d);
	}

	public void drawLabel(Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics();
		String labelStr = "" + this.labelGauche + " " + this.labelDroite + " " + this.labelHaut;
		Point center = this.center();
		int centeredText = (int) (center.x - fm.stringWidth(labelStr) / 2);
		g2d.drawString(labelStr, centeredText, (int) (center.y + 5)); // - midWidth - fm.getDescent()
	}
}
