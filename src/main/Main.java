package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import utils.aff.Couleur;
import utils.aff.Vue;
import utils.go.Piece;
import utils.go.Point;

public class Main {
	

	public static void main(String s[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int x = 10, y = 10, w = 800, h = 600;
				String fileNamePuzzle = "puzzle.csv";
				JFrame frame = new JFrame("Transformations dans le plan");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				Vue vue = new Vue(w, h, fileNamePuzzle, this.createPieces());
				vue.setBorder(BorderFactory.createLineBorder(Couleur.fg) );
				
				frame.add(vue);
				frame.pack();
				frame.setLocation(x, y);
				frame.setVisible(true);
			}

			private List<Piece> createPieces() {
				
				ArrayList<Piece> alPieces = new ArrayList<>(18);
				
				/***** Points Valeurs Triples *****/
				Point pDroite = new Point(40, 136);
				Point pGauche = new Point(140, 136);
				Point pHaut = new Point(90, 50);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 0, 0, 0));
				
				pDroite = new Point(40, 232);
				pGauche = new Point(140, 232);
				pHaut = new Point(90, 146);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 1, 1, 1));
				
				pDroite = new Point(40, 328);
				pGauche = new Point(140, 328);
				pHaut = new Point(90, 242);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 2, 2, 2));
				
				pDroite = new Point(40, 424);
				pGauche = new Point(140, 424);
				pHaut = new Point(90, 338);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 3, 3, 3));
				
				/***** Points Valeurs Doubles *****/
				
				pDroite = new Point(150, 136);
				pGauche = new Point(250, 136);
				pHaut = new Point(200, 50);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 0, 0, 1));
				
				pDroite = new Point(150, 232);
				pGauche = new Point(250, 232);
				pHaut = new Point(200, 146);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 1, 1, 2));
				
				pDroite = new Point(150, 328);
				pGauche = new Point(250, 328);
				pHaut = new Point(200, 242);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 2, 2, 3));
				
				pDroite = new Point(150, 424);
				pGauche = new Point(250, 424);
				pHaut = new Point(200, 338);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 3, 3, 0));
				
				
				pDroite = new Point(250, 136);
				pGauche = new Point(350, 136);
				pHaut = new Point(300, 50);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 0, 0, 2));
				
				pDroite = new Point(250, 232);
				pGauche = new Point(350, 232);
				pHaut = new Point(300, 146);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 1, 1, 3));
				
				pDroite = new Point(250, 328);
				pGauche = new Point(350, 328);
				pHaut = new Point(300, 242);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 2, 2, 0));
				
				pDroite = new Point(250, 424);
				pGauche = new Point(350, 424);
				pHaut = new Point(300, 338);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 3, 3, 1));
				
				
				pDroite = new Point(350, 136);
				pGauche = new Point(450, 136);
				pHaut = new Point(400, 50);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 0, 0, 3));
				
				pDroite = new Point(350, 232);
				pGauche = new Point(450, 232);
				pHaut = new Point(400, 146);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 1, 1, 0));
				
				pDroite = new Point(350, 328);
				pGauche = new Point(450, 328);
				pHaut = new Point(400, 242);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 2, 2, 1));
				
				pDroite = new Point(350, 424);
				pGauche = new Point(450, 424);
				pHaut = new Point(400, 338);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 3, 3, 2));
				
				/***** Points Valeurs Simples *****/
				
				pDroite = new Point(460, 136);
				pGauche = new Point(560, 136);
				pHaut = new Point(510, 50);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 0, 1, 2));
				
				pDroite = new Point(460, 232);
				pGauche = new Point(560, 232);
				pHaut = new Point(510, 146);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 1, 2, 3));
				
				pDroite = new Point(460, 328);
				pGauche = new Point(560, 328);
				pHaut = new Point(510, 242);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 2, 3, 0));
				
				pDroite = new Point(460, 424);
				pGauche = new Point(560, 424);
				pHaut = new Point(510, 338);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 3, 0, 1));
				
				
				pDroite = new Point(560, 136);
				pGauche = new Point(660, 136);
				pHaut = new Point(610, 50);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 0, 2, 1));
				
				pDroite = new Point(560, 232);
				pGauche = new Point(660, 232);
				pHaut = new Point(610, 146);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 1, 3, 2));
				
				pDroite = new Point(560, 328);
				pGauche = new Point(660, 328);
				pHaut = new Point(610, 242);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 2, 0, 3));
				
				pDroite = new Point(560, 424);
				pGauche = new Point(660, 424);
				pHaut = new Point(610, 338);
				alPieces.add(new Piece(pDroite, pGauche, pHaut, 3, 1, 0));
				
				return alPieces;
				
			}
		});
	}
}
