package TP1.utils.go;

public class Vecteur {

	public double x, y;
	
	public Vecteur() {
		this.x = 0;
		this.y = 0;
	}

	public Vecteur(double x, double y) {
		this.x = 0;
		this.y = 0;
	}
	
	public Vecteur(Point p1, Point p2) { //p1 = départ | p2 = arrivée
		this.x = p2.x - p1.x;
		this.y = p2.y - p1.y;
	}
	
}