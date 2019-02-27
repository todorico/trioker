package TP3.affichage;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import TP3.couleurs.Couleur;
import TP3.main.Main;

public class NoeudGraphique {
	
	private static final int midWidth = 5;
	private static int compteurRang = 1;
	
	private String name;
	private NoeudGraphique parent;
	private NoeudGraphique filsGauche;
	private NoeudGraphique filsDroit;
	
	public int x;
	public int y;
	
	private boolean drawLabel;
	private int profondeur;
	private int rang;
	
	private static final int tailleNoeud = 20;
	private static final int espInterNoeuds = 30;
	private int xprelim;
	private int modifier;
	
	public NoeudGraphique(String name){
		super();
		
		this.name = name;
		this.parent = null;
		this.filsGauche = null;
		this.filsDroit = null;
		
		drawLabel = true;
	}
	
	
	/**** ALGO SIMPLE ****/
	public void algoSimple(int nbNoeuds, int type) {
		
		if (this.hasFilsGauche())
			this.filsGauche.algoSimple(nbNoeuds, type);
		
		this.visiteN_AlgoSimple(nbNoeuds, type);
		
		if (this.hasFilsDroit())
			this.filsDroit.algoSimple(nbNoeuds, type);
	}

	private void visiteN_AlgoSimple(int nbNoeuds, int type) {
		this.rang = NoeudGraphique.compteurRang;
		NoeudGraphique.compteurRang++;
		
		switch (type) {
			case VueGraphique.AFFICHAGE_VERTICAL: // = Horizontal avec x et y inversés
				this.y = this.rang * VueGraphique.ESPX;
				this.x = this.profondeur * VueGraphique.ESPY;
				break;
				
			case VueGraphique.AFFICHAGE_CIRCULAIRE:
				int k = this.profondeur * VueGraphique.ESPX;
				double teta = (2*Math.PI / nbNoeuds) * this.rang;
				this.x = (int) (k* Math.cos(teta)) + Main.PANEL_WIDTH/2;
				this.y = (int) (k* Math.sin(teta)) + Main.PANEL_HEIGHT/2;
				break;
				
			default: // = AFFICHAGE HORIZONTAL
				this.x = this.rang * VueGraphique.ESPX;
				this.y = this.profondeur * VueGraphique.ESPY;
				break;
				
		}
	}
	

	/**** ALGO OPTIMISE ****/
	public void algoOptimisePostOrdre() {
		//Parcours post-fixe : 1ère étape
		if (this.hasFilsGauche())
			this.filsGauche.algoOptimisePostOrdre();
		
		if (this.hasFilsDroit())
			this.filsDroit.algoOptimisePostOrdre();
		
		this.visiteN_AlgoOptimise();
	}
	
	public void algoOptimisePreOrdre() {
		//Parcours pré-fixe : 2ème étape
		this.visiteN_AlgoOptimise();
		
		if (this.hasFilsGauche())
			this.filsGauche.algoOptimisePreOrdre();
		
		if (this.hasFilsDroit())
			this.filsDroit.algoOptimisePreOrdre();

	}
	
	//Visite N
	private void visiteN_AlgoOptimise() {

		if (this.hasFrereGauche()) {
			this.xprelim = this.xprelimFrereGauche() + NoeudGraphique.tailleNoeud + NoeudGraphique.espInterNoeuds;
			if (this.hasChildren())
				this.modifier = this.xprelim - (this.getFirstChild().xprelim + this.getLastChild().xprelim)/2;

		}else if (this.hasChildren()) {
			this.xprelim = (this.getFirstChild().xprelim + this.getLastChild().xprelim) / 2;
			this.modifier = 0;

		}else {
			this.xprelim = 0;
			this.modifier = 0;
		}
	}

	private NoeudGraphique getFirstChild() {
		return this.filsGauche;
	}
	
	private NoeudGraphique getLastChild() {
		return this.filsDroit;
	}


	//ATTENTION ! Peut provoquer un NullPointerException
	private int xprelimFrereGauche() {
		return this.parent.filsGauche.xprelim;
	}


	/**** UTILITAIRE ****/
	public void dessine(Graphics2D g) {
		
		// On dessine les fils en 1er
		if (this.hasFilsGauche()) {
			g.setColor(Couleur.bg);
			g.drawLine(this.x, this.y, this.filsGauche.x, this.filsGauche.y);
			this.filsGauche.dessine(g);
		}
		
		if (this.hasFilsDroit()) {
			g.setColor(Couleur.bg);
			g.drawLine(this.x, this.y, this.filsDroit.x, this.filsDroit.y);
			this.filsDroit.dessine(g);
		}
		
		FontMetrics fm = g.getFontMetrics();
		int h = fm.getHeight();
		int w = fm.stringWidth(name) + 2*midWidth;
		
		int c = x - w/2;
		int d = y - h/2;
		
		g.setColor(Couleur.fg);
		g.fillRect(c, d, w, h);
		
		g.setColor(Couleur.bg);
		g.drawRect(c, d, w, h);
		
		g.drawString(name, (c + midWidth), (y + h/2 - fm.getDescent()));
		
		if (drawLabel)
			drawLabel(g);
		
	}
	
	public void drawLabel(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		//"(" + x + "," + y + ")";
		String longLabel = "P:" + profondeur + " | R:" + rang;
		int centeredText = (int) (x - fm.stringWidth(longLabel) / 2);
		g.drawString(longLabel, centeredText, (int) (y - midWidth - fm.getDescent()));
	}
	
	public void changeDrawLabel() {
		this.drawLabel = !this.drawLabel;
	}
	
	public void setCoords(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private boolean hasParent() {
		return this.parent != null;
	}
	
	private boolean hasFilsGauche() {
		return this.filsGauche != null;
	}

	private boolean hasFilsDroit() {
		return this.filsDroit != null;
	}
	
	private boolean hasFrereGauche() {
		if (!this.hasParent())
			return false;
		return this.parent.hasFilsGauche();
	}
	
	private boolean hasChildren() {
		return this.hasFilsGauche() || this.hasFilsDroit();
	}
	
	public void setParent(NoeudGraphique parent) {
		this.parent = parent;
	}

	public void setFilsGauche(NoeudGraphique filsGauche) {
		this.filsGauche = filsGauche;
		this.filsGauche.setParent(this);
		this.filsGauche.setProfondeur(this.profondeur + 1);
	}

	public void setFilsDroit(NoeudGraphique filsDroit) {
		this.filsDroit = filsDroit;
		this.filsDroit.setParent(this);
		this.filsDroit.setProfondeur(this.profondeur + 1);
	}
	
	public NoeudGraphique getParent() {
		return parent;
	}
	
	public NoeudGraphique getFilsGauche() {
		return filsGauche;
	}

	public NoeudGraphique getFilsDroit() {
		return filsDroit;
	}

	public void setProfondeur(int p) {
		this.profondeur = p;
	}
	
	public int getProfondeur() {
		return profondeur;
	}
	
}
