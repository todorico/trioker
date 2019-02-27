package TP3.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import TP3.affichage.NoeudGraphique;
import TP3.affichage.VueGraphique;

@SuppressWarnings("serial")
public class Main extends JPanel {

	public static final int PANEL_WIDTH = 800;
	public static final int PANEL_HEIGHT = 800;

	public static void main(String s[]) {
		
		//Construction des noeuds
		NoeudGraphique a = new NoeudGraphique("A");
		VueGraphique vue = new VueGraphique(a);
		
		//Toujours ajouter les fils APRES avoir ajout� le p�re au pr�c�dents
		NoeudGraphique b = new NoeudGraphique("B");
		NoeudGraphique c = new NoeudGraphique("C");
		a.setFilsGauche(b);
		a.setFilsDroit(c);
		
		NoeudGraphique d = new NoeudGraphique("D");
		NoeudGraphique e = new NoeudGraphique("E");
		b.setFilsGauche(d);
		b.setFilsDroit(e);
		
		NoeudGraphique f = new NoeudGraphique("F");
		NoeudGraphique g = new NoeudGraphique("G");
		c.setFilsGauche(f);
		c.setFilsDroit(g);
		
		NoeudGraphique h = new NoeudGraphique("H");
		NoeudGraphique i = new NoeudGraphique("I");
		d.setFilsGauche(h);
		d.setFilsDroit(i);
		
		//Pour calculer les coordonn�es
		vue.algoSimple(9, VueGraphique.AFFICHAGE_VERTICAL);
		//vue.algoOptimise();
		
		JFrame frame = new JFrame("Arbres binaires");
		frame.add(vue, BorderLayout.CENTER);
		frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}

