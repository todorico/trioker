package TP2.utils.vecteur;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import TP2.utils.couleurs.Couleur;


public class Vecteur {

	private PointVisible from;
	PointVisible to;
	Color lineColor;
	public String label;

	public Vecteur(PointVisible f, PointVisible t) {
		setFrom(f);
		to = t;
		lineColor =  Couleur.nw;
		getFrom().segment = this;
		to.segment = this;
	}

	public Vecteur(int x, int y, int x2, int y2) {
		setFrom(new PointVisible(x,y, Role.begin));
		to = new PointVisible(x2,y2, Role.end);		
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String toString(){
		return this.getFrom().x +" - "+ to.x;
	}

	// principe - calcul de deux � quatre d�terminants suffisent selon les cas 
	// cas "pathologiques" non trait�s ici: (a) vecteur nul et (b) vecteurs parall�les qui se recouvrent
	public boolean intersect(Vecteur s){
		if (s == null) return false;
		Vecteur s1 = new Vecteur(this.getFrom().x, this.getFrom().y, s.getFrom().x,s.getFrom().y);
		Vecteur s2 = new Vecteur(this.getFrom().x, this.getFrom().y, s.to.x,s.to.y);
		int d1 = detSign(s1) * detSign(s2);
		if (d1 > 0 ) {// les d�terminants ont le m�me signe, les extr�mit�s de s sont du m�me c�t� de this => pas de croisement entre s et this
			return false;
		}else{
			s1 = new Vecteur(s.getFrom().x, s.getFrom().y, this.getFrom().x, this.getFrom().y);
			s2 = new Vecteur(s.getFrom().x, s.getFrom().y, this.to.x, this.to.y);
			d1 = s.detSign(s1) * s.detSign(s2);
			return (d1 < 0);
		}
	}

	// retourne le signe du d�terminant ou 0 si le d�terminant est nul
	public int detSign(Vecteur s1){
		int d = determinant(s1);
		if (d == 0) return 0;
		if (d > 0) return 1;
		else 
			return -1;
	}

	// calcul d�terminant s.det(s1) = det(s,s1)
	public int determinant(Vecteur s1){
		return (this.to.x - this.getFrom().x) * (s1.to.y - s1.getFrom().y) - (this.to.y - this.getFrom().y) * (s1.to.x - s1.getFrom().x);
	}

	public void drawLabel(Graphics2D g){
		if (label != null) {
			FontMetrics fm = g.getFontMetrics();
			g.drawString(label, (int) (getFrom().x + fm.stringWidth("_")), (int) (getFrom().y ));
		}
	}

	public void dessine(Graphics2D g) {
		g.setColor(lineColor);
		g.drawLine((int) getFrom().x , (int) getFrom().y , (int) to.x, (int) to.y);
		drawLabel(g);
		getFrom().drawLabel(g);
		to.drawLabel(g);
	}

	public int getX(){
		return to.x - getFrom().x;
	}

	public int getY(){
		return to.y - getFrom().y;
	}
	
	public int getX2(){
		int d = to.x - getFrom().x;
		return d*d;
	}
	
	public int getY2(){
		int d = to.y - getFrom().y;
		return d*d;
	}

	public double getCosTetaCarre(){
		double den = this.normeCarree();
		return (getY()*getY() / den) ;
	}

	private double normeCarree() {
		return getX2() +getY2();
	}

	public String printColor() {
		return lineColor.getRed() + "-" + lineColor.getGreen() + "-" + lineColor.getBlue();
	}

	public PointVisible getFrom() {
		return from;
	}

	public void setFrom(PointVisible from) {
		this.from = from;
	}
}
