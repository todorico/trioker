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
				
				Point p1 = new Point(100, 50);
				Point p2 = new Point(50, 136);
				Point p3 = new Point(150, 136);
				alPieces.add(new Piece(p2, p3, p1, 1, 1, 1));
				
				p1 = new Point(200, 50);
				p2 = new Point(150, 136);
				p3 = new Point(250, 136);
				alPieces.add(new Piece(p2, p3, p1, 2, 2, 2));
				
				p1 = new Point(300, 50);
				p2 = new Point(250, 136);
				p3 = new Point(350, 136);
				alPieces.add(new Piece(p2, p3, p1, 3, 3, 3));
				
				return alPieces;
				
			}
		});
	}
}
