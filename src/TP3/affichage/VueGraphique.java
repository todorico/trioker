package TP3.affichage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import TP3.couleurs.Couleur;

@SuppressWarnings("serial")
public class VueGraphique extends JPanel {
	
	public static final int AFFICHAGE_HORIZONTAL = 1;
	public static final int AFFICHAGE_VERTICAL = 2;
	public static final int AFFICHAGE_CIRCULAIRE = 3;
	
	public static final int ESPX = 50;
	public static final int ESPY = 40;

	private NoeudGraphique racine;
	private int typeAffichage;
	
	public VueGraphique() {
		super();
		typeAffichage = VueGraphique.AFFICHAGE_HORIZONTAL;
		this.racine = null;
	}

	public VueGraphique(NoeudGraphique racine) {
		this();
		this.setRacine(racine);
	}
	
	//Ne copie pas les fils de l'actuelle racine
	public void setRacine(NoeudGraphique newRacine) {
		this.racine = newRacine;
		this.racine.setProfondeur(1);
	}
	
	public NoeudGraphique getRacine() {
		return this.racine;
	}
	
	//Appel de d�part algo simple
	public void algoSimple(int nbNoeuds) {
		NoeudGraphique.compteurRang = 1;
		this.racine.algoSimple(nbNoeuds, typeAffichage);
	}
	
	//Appel de d�part algo optimis�
	public void algoOptimise() {
		this.racine.algoOptimisePostOrdre(); //Etape 1
		this.racine.algoOptimisePreOrdre(0); //Etape 2
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.setColor(Couleur.bg);
		
		racine.dessine(g2d);
	}

	public void setAffichage(int affichageHorizontal) {
		this.typeAffichage = affichageHorizontal;
	}
	
}