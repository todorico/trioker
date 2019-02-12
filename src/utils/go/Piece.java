package utils.go;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Piece {
	
	private static int pointMidSize = 3;
	
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
		//Affichage triangle
		VecteurVisible v1 = new VecteurVisible(this.pGauche, this.pDroite);
		VecteurVisible v2 = new VecteurVisible(this.pDroite, this.pHaut);
		VecteurVisible v3 = new VecteurVisible(this.pHaut, this.pGauche);
		v1.dessine(g2d);
		v2.dessine(g2d);
		v3.dessine(g2d);

		//Affichage points
		dessinePoints(g2d);
		
		//Affichage label
		drawLabel(g2d);
	}

	private void dessinePoints(Graphics2D g2d) {
		
		//0 : ne rien faire

//		Point p1 = new Point(pHaut.x, pHaut.y + 16);
//		p1.dessine(g2d);
		
		//Points Haut
		switch (labelHaut) {
			case 1:
				afficherPoint(g2d, pHaut.x, pHaut.y + 16);
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
		if ( !(x >= this.pGauche.x && x <= this.pDroite.x) ) {
			return false;
			
		}else {
			if (!isReversed)
				return (y >= this.pHaut.y && y <= this.pGauche.y);
			else
				return (y <= this.pHaut.y && y >= this.pGauche.y);
		}
	}
	
	public void translater(Vecteur v) {
		this.pHaut.translater(v);
		this.pGauche.translater(v);
		this.pDroite.translater(v);
	}
	
}