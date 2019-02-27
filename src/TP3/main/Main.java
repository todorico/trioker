package TP3.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import TP3.affichage.NoeudGraphique;
import TP3.affichage.VueGraphique;
import TP3.couleurs.Couleur;

@SuppressWarnings("serial")
public class Main extends JPanel {

	public static final int PANEL_WIDTH = 800;
	public static final int PANEL_HEIGHT = 800;

	public static void main(String s[]) {
		
		int nbNoeuds = 9;
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

		
		
		JPanel menus = new JPanel(new GridLayout(0, 3));
		menus.setBackground(Couleur.nw);
		
		JPanel boutonsSelections = new JPanel(new GridLayout(0, 1));
		boutonsSelections.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		menus.add(boutonsSelections, BorderLayout.WEST);
		
		ButtonGroup groupe = new ButtonGroup();
		
		JRadioButton choixH = new JRadioButton("Horizontal");
		choixH.setSelected(true);
		JRadioButton choixV = new JRadioButton("Vertical");
		JRadioButton choixC = new JRadioButton("Circulaire");
		
		groupe.add(choixH);
		boutonsSelections.add(choixH);
		groupe.add(choixV);
		boutonsSelections.add(choixV);
		groupe.add(choixC);
		boutonsSelections.add(choixC);
		
		choixH.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vue.setAffichage(VueGraphique.AFFICHAGE_HORIZONTAL);
			}
		});
		
		choixV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vue.setAffichage(VueGraphique.AFFICHAGE_VERTICAL);
			}
		});
		
		choixC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vue.setAffichage(VueGraphique.AFFICHAGE_CIRCULAIRE);
			}
		});
		
		
		
		JButton algoSimple = new JButton("Algo Simple");
		algoSimple.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vue.algoSimple(nbNoeuds);
				vue.repaint();
			}
		});
		menus.add(algoSimple, BorderLayout.CENTER);
		
		
		JButton algoOptimise = new JButton("Algo Optimise");
		algoOptimise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vue.algoOptimise();
				vue.repaint();
			}
		});
		menus.add(algoOptimise, BorderLayout.EAST);
	
		
		JFrame frame = new JFrame("Arbres binaires");
		frame.add(menus, BorderLayout.NORTH);

		frame.add(vue, BorderLayout.CENTER);
		frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}

