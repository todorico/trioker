package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import utils.aff.Couleur;
import utils.aff.Vue;
import utils.go.PieceTrioker;
import utils.go.Point2;

public class Main {
	

	public static void main(String s[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int x = 10, y = 10, w = 800, h = 600;
				String fileNamePuzzle = "puzzle.csv";
				JFrame frame = new JFrame("Transformations dans le plan");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				Vue vue = new Vue(w, h, fileNamePuzzle, this.createPieceTriokers());
				vue.setBorder(BorderFactory.createLineBorder(Couleur.fg) );
				
				frame.add(vue);
				frame.pack();
				frame.setLocation(x, y);
				frame.setVisible(true);
			}

			private List<PieceTrioker> createPieceTriokers() {
				
				ArrayList<PieceTrioker> alPieceTriokers = new ArrayList<>(18);
				
				/***** Point2s Valeurs Triples *****/
				Point2 pGauche = new Point2(40, 136);
				Point2 pDroite = new Point2(140, 136);
				Point2 pHaut = new Point2(90, 50);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 0, 0, 0));
				
				pGauche = new Point2(40, 232);
				pDroite = new Point2(140, 232);
				pHaut = new Point2(90, 146);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 1, 1, 1));
				
				pGauche = new Point2(40, 328);
				pDroite = new Point2(140, 328);
				pHaut = new Point2(90, 242);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 2, 2, 2));
				
				pGauche = new Point2(40, 424);
				pDroite = new Point2(140, 424);
				pHaut = new Point2(90, 338);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 3, 3, 3));
				
				/***** Point2s Valeurs Doubles *****/
				
				pGauche = new Point2(150, 136);
				pDroite = new Point2(250, 136);
				pHaut = new Point2(200, 50);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 0, 0, 1));
				
				alPieceTriokers.get(4).reverse();
				alPieceTriokers.get(4).reverse();

				
				pGauche = new Point2(150, 232);
				pDroite = new Point2(250, 232);
				pHaut = new Point2(200, 146);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 1, 1, 2));
				
				
				
				pGauche = new Point2(150, 328);
				pDroite = new Point2(250, 328);
				pHaut = new Point2(200, 242);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 2, 2, 3));
				
				pGauche = new Point2(150, 424);
				pDroite = new Point2(250, 424);
				pHaut = new Point2(200, 338);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 3, 3, 0));
				
				
				pGauche = new Point2(250, 136);
				pDroite = new Point2(350, 136);
				pHaut = new Point2(300, 50);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 0, 0, 2));
				
				pGauche = new Point2(250, 232);
				pDroite = new Point2(350, 232);
				pHaut = new Point2(300, 146);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 1, 1, 3));
				
				pGauche = new Point2(250, 328);
				pDroite = new Point2(350, 328);
				pHaut = new Point2(300, 242);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 2, 2, 0));
				
				pGauche = new Point2(250, 424);
				pDroite = new Point2(350, 424);
				pHaut = new Point2(300, 338);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 3, 3, 1));
				
				
				pGauche = new Point2(350, 136);
				pDroite = new Point2(450, 136);
				pHaut = new Point2(400, 50);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 0, 0, 3));
				
				pGauche = new Point2(350, 232);
				pDroite = new Point2(450, 232);
				pHaut = new Point2(400, 146);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 1, 1, 0));
				
				pGauche = new Point2(350, 328);
				pDroite = new Point2(450, 328);
				pHaut = new Point2(400, 242);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 2, 2, 1));
				
				pGauche = new Point2(350, 424);
				pDroite = new Point2(450, 424);
				pHaut = new Point2(400, 338);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 3, 3, 2));
				
				/***** Point2s Valeurs Simples *****/
				
				pGauche = new Point2(460, 136);
				pDroite = new Point2(560, 136);
				pHaut = new Point2(510, 50);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 0, 1, 2));
				
				pGauche = new Point2(460, 232);
				pDroite = new Point2(560, 232);
				pHaut = new Point2(510, 146);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 1, 2, 3));
				
				pGauche = new Point2(460, 328);
				pDroite = new Point2(560, 328);
				pHaut = new Point2(510, 242);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 2, 3, 0));
				
				pGauche = new Point2(460, 424);
				pDroite = new Point2(560, 424);
				pHaut = new Point2(510, 338);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 3, 0, 1));
				
				
				pGauche = new Point2(560, 136);
				pDroite = new Point2(660, 136);
				pHaut = new Point2(610, 50);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 0, 2, 1));
				
				pGauche = new Point2(560, 232);
				pDroite = new Point2(660, 232);
				pHaut = new Point2(610, 146);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 1, 3, 2));
				
				pGauche = new Point2(560, 328);
				pDroite = new Point2(660, 328);
				pHaut = new Point2(610, 242);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 2, 0, 3));
				
				pGauche = new Point2(560, 424);
				pDroite = new Point2(660, 424);
				pHaut = new Point2(610, 338);
				alPieceTriokers.add(new PieceTrioker(pGauche, pDroite, pHaut, 3, 1, 0));
				
				return alPieceTriokers;
				
			}
		});
	}
}
