package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import utils.aff.Couleur;
import utils.aff.Vue;
import utils.go.PieceTrioker;
import utils.go.TriangleEqui;
import utils.go.Point;
import utils.go.Vector;

public class Main {
	
	public static int canvasX = 10;
	public static int canvasY = 10;
	public static int canvasWidth = 800;
	public static int canvasHeight = 600;
	
	public static void main(String s[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				String fileNamePuzzle = "puzzle.csv";
				JFrame frame = new JFrame("Transformations dans le plan");
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				Vue vue = new Vue(canvasWidth, canvasHeight, fileNamePuzzle, this.createPieces());
				vue.setBorder(BorderFactory.createLineBorder(Couleur.fg));
				
				frame.add(vue);
				frame.pack();
				frame.setLocation(canvasX, canvasY);
				frame.setVisible(true);
			}

			private List<PieceTrioker> createPieces() {
				
				ArrayList<PieceTrioker> pieces = new ArrayList<>(18);
				
				// Point2s Valeurs Triples 
				Point left = new Point(40, 136);
				Point right = new Point(140, 136);
				Point up = new Point(90, 50);
				pieces.add(new PieceTrioker(left, right, up, 0, 0, 0));
				
				left = new Point(40, 232);
				right = new Point(140, 232);
				up = new Point(90, 146);
				pieces.add(new PieceTrioker(left, right, up, 1, 1, 1));
				
				left = new Point(40, 328);
				right = new Point(140, 328);
				up = new Point(90, 242);
				pieces.add(new PieceTrioker(left, right, up, 2, 2, 2));
				
				left = new Point(40, 424);
				right = new Point(140, 424);
				up = new Point(90, 338);
				pieces.add(new PieceTrioker(left, right, up, 3, 3, 3));
				
				// Point2s Valeurs Doubles 
				
				left = new Point(150, 136);
				right = new Point(250, 136);
				up = new Point(200, 50);
				pieces.add(new PieceTrioker(left, right, up, 0, 0, 1));
				
				pieces.get(4).reverse();
				pieces.get(4).reverse();

				
				left = new Point(150, 232);
				right = new Point(250, 232);
				up = new Point(200, 146);
				pieces.add(new PieceTrioker(left, right, up, 1, 1, 2));
				
				
				
				left = new Point(150, 328);
				right = new Point(250, 328);
				up = new Point(200, 242);
				pieces.add(new PieceTrioker(left, right, up, 2, 2, 3));
				
				left = new Point(150, 424);
				right = new Point(250, 424);
				up = new Point(200, 338);
				pieces.add(new PieceTrioker(left, right, up, 3, 3, 0));
				
				
				left = new Point(250, 136);
				right = new Point(350, 136);
				up = new Point(300, 50);
				pieces.add(new PieceTrioker(left, right, up, 0, 0, 2));
				
				left = new Point(250, 232);
				right = new Point(350, 232);
				up = new Point(300, 146);
				pieces.add(new PieceTrioker(left, right, up, 1, 1, 3));
				
				left = new Point(250, 328);
				right = new Point(350, 328);
				up = new Point(300, 242);
				pieces.add(new PieceTrioker(left, right, up, 2, 2, 0));
				
				left = new Point(250, 424);
				right = new Point(350, 424);
				up = new Point(300, 338);
				pieces.add(new PieceTrioker(left, right, up, 3, 3, 1));
				
				
				left = new Point(350, 136);
				right = new Point(450, 136);
				up = new Point(400, 50);
				pieces.add(new PieceTrioker(left, right, up, 0, 0, 3));
				
				left = new Point(350, 232);
				right = new Point(450, 232);
				up = new Point(400, 146);
				pieces.add(new PieceTrioker(left, right, up, 1, 1, 0));
				
				left = new Point(350, 328);
				right = new Point(450, 328);
				up = new Point(400, 242);
				pieces.add(new PieceTrioker(left, right, up, 2, 2, 1));
				
				left = new Point(350, 424);
				right = new Point(450, 424);
				up = new Point(400, 338);
				pieces.add(new PieceTrioker(left, right, up, 3, 3, 2));
				
				// Point2s Valeurs Simples 
				
				left = new Point(460, 136);
				right = new Point(560, 136);
				up = new Point(510, 50);
				pieces.add(new PieceTrioker(left, right, up, 0, 1, 2));
				
				left = new Point(460, 232);
				right = new Point(560, 232);
				up = new Point(510, 146);
				pieces.add(new PieceTrioker(left, right, up, 1, 2, 3));
				
				left = new Point(460, 328);
				right = new Point(560, 328);
				up = new Point(510, 242);
				pieces.add(new PieceTrioker(left, right, up, 2, 3, 0));
				
				left = new Point(460, 424);
				right = new Point(560, 424);
				up = new Point(510, 338);
				pieces.add(new PieceTrioker(left, right, up, 3, 0, 1));
				
				
				left = new Point(560, 136);
				right = new Point(660, 136);
				up = new Point(610, 50);
				pieces.add(new PieceTrioker(left, right, up, 0, 2, 1));
				
				left = new Point(560, 232);
				right = new Point(660, 232);
				up = new Point(610, 146);
				pieces.add(new PieceTrioker(left, right, up, 1, 3, 2));
				
				left = new Point(560, 328);
				right = new Point(660, 328);
				up = new Point(610, 242);
				pieces.add(new PieceTrioker(left, right, up, 2, 0, 3));
				
				left = new Point(560, 424);
				right = new Point(660, 424);
				up = new Point(610, 338);
				pieces.add(new PieceTrioker(left, right, up, 3, 1, 0));
				
/*								
				double radius = 57;
				
				TriangleEqui T = new TriangleEqui(new Point(0,0), radius+5);

				Point startPos = new Point(T.width()/2, radius);
				
				int offsetX = 0;
				int offsetY = 0;
				
				for (int i = 0 ; i < 4 ; i++) {
					
					pieces.add(new PieceTrioker(
							new Point(startPos.x + (T.width() * offsetX), startPos.y + (T.height() * offsetY)),
							radius, i, i, i));
					
					offsetY++;

					if (startPos.x + (2 * radius * offsetX)  > canvasWidth - radius) {
						offsetY = 0;
						offsetX++;
					}
				}
*/

				return pieces;
			}
		});
	}
}
