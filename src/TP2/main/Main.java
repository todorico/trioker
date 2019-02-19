package TP2.main;

import javax.swing.JFrame;

import TP2.utils.affichage.Vue;

public class Main {
	
	public static void main(String s[]) {
		JFrame frame = new JFrame("Calcul d'enveloppe convexe");
		Vue v = new Vue(10,800);
		frame.add(v);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
