package TP2.utils.vecteur;

import java.awt.Color;

import TP2.utils.couleurs.Couleur;

public enum Role {
	
	begin(Couleur.beginPoint),
	end(Couleur.intersection),
	intersect(Couleur.endPoint),
	undefined(Couleur.fg);
	
	private Color color = Couleur.nw;
	
	Role(Color c){
		this.color = c;
	}
	
	public Color getColor() {
		return this.color;
	}
	
}