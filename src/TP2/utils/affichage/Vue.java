package TP2.utils.affichage;

import java.util.Comparator;
import java.util.Stack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import TP2.utils.couleurs.Couleur;
import TP2.utils.fileIo.ReadWritePoint;
import TP2.utils.vecteur.PointVisible;
import TP2.utils.vecteur.Vecteur;;

public class Vue extends JPanel implements MouseWheelListener, MouseListener, ActionListener {

	Color bgColor = Couleur.bg; // la couleur de fond de la fenêtre
	Color fgColor = Couleur.fg; // la couleur des lignes
	int width;
	ArrayList<PointVisible> points = new ArrayList<PointVisible>();
	
	ArrayList<Vecteur> segments = new ArrayList<Vecteur>();
	
	// n : le nombre de lignes
	// width, height : largeur, hauteur de la fen�tre
	public Vue(int n, int width) {
		super();
		
		JButton b1 = new JButton("Enregistrer");
		b1.setActionCommand("b1");
		b1.addActionListener(this);
		
		JButton b2 = new JButton("Charger");
		b2.addActionListener(this);
		
		JButton b0 = new JButton("clear");
		b0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				segments.clear();
				repaint();
			}
		});		
		
		JButton b3 = new JButton("demi-plan");
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				algoDemiPlans();
				repaint();
			}
		});
		
		JButton b4 = new JButton("jarvis");
		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				algoJarvis();
				repaint();
			}
		});
		
		JButton b5 = new JButton("graham");
		b5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				algoGraham();
				repaint();
			}
		});
		
		Box b = Box.createHorizontalBox();
		b.add(b1);
		b.add(Box.createHorizontalStrut(10));
		b.add(b2);
		b.add(b0);
		b.add(b3);
		b.add(b4);
		b.add(b5);

		add(b);
		
		setBackground(bgColor);
		this.width = width;
		setPreferredSize(new Dimension(width, width));
		
		System.out.println("initialisation avec n = "+n);
		
		initPoints(n, (width - 100)/2 , width/2, width/2);
		
		addMouseListener(this);
		addMouseWheelListener(this);
	}
	
	public int determinant(PointVisible p0, PointVisible p1, PointVisible p2) {
		return ( (p1.x - p0.x)*(p2.y - p0.y) - (p2.x - p0.x)*(p1.y - p0.y) );
	}
	
	public void algoDemiPlans() {
		
		this.segments.clear();
		
		PointVisible p0 = this.points.get(0);
		
		for (int i = 0; i < this.points.size(); i++) {
			PointVisible Ai = this.points.get(i);
			
			for (int j = 0; j < this.points.size(); j++) {
				PointVisible Bi = this.points.get(j);
				
				boolean tourGauche = true;
				if (this.determinant(p0, Ai, Bi) < 0)
					tourGauche = false;
				
				boolean testTour = true;
				
				for (int k = 1; k < this.points.size(); k++) {
					PointVisible Pi = this.points.get(k);
					
					if (Pi.equals(Ai) || Pi.equals(Bi))
						continue;
					
					int resultat = this.determinant(Pi, Ai, Bi);
					
					if ( (tourGauche && resultat < 0) || (!tourGauche && resultat >= 0)) {
						testTour = false;
						break;
					}
						
				}
				
				if (testTour)
					segments.add(new Vecteur(Ai, Bi));
				
			}
		}
		
	}
	
	public void algoJarvis() {
		
        this.segments.clear();
        
        /** Etape 1 : r�cup�ration du point tel que ses coords X et Y son,t les plus faibles (i.e. le plus en haut � gauche de l'�cran) **/
        PointVisible A0 = this.getPointMinXY();
        
        /** Etape 2 : On calcule le point suivant tant que != A0 **/
        PointVisible Ai = A0;
        PointVisible Bi;
        
        do {
        	Bi = this.getNextPoint(Ai);
            this.segments.add(new Vecteur(Ai, Bi));
            Ai = Bi;
        } while (!Bi.equals(A0));
		
	}
	
	public PointVisible getPivot() {
		PointVisible toReturn = new PointVisible(Integer.MAX_VALUE, 0);
		for (PointVisible p : points) {
			if (p.y > toReturn.y || p.y == toReturn.y && p.x < toReturn.x)
				toReturn = p;
		}
		return toReturn;
	}
	
	public void algoGraham() {
        this.segments.clear();

		PointVisible pivot = getPivot();
		
		System.out.println("Pivot : " + pivot.getLabel());

		this.points.remove(pivot);
		
		// Comparaison de l'angle par rapport au pivot
		
		Comparator<PointVisible> angleComparator = new Comparator<PointVisible>() {
			public int compare(PointVisible p1, PointVisible p2) {
				
				// Vecteurs entre le pivot et les points
				
				double vPivotP1X = (long)p1.x - pivot.x;
				double vPivotP1Y = (long)p1.y - pivot.y;
				
				double vPivotP2X = (long)p2.x - pivot.x;
				double vPivotP2Y = (long)p2.y - pivot.y;
				
				double anglePivotP1 = Math.atan2(-vPivotP1Y, vPivotP1X);
				double anglePivotP2 = Math.atan2(-vPivotP2Y, vPivotP2X);
				
				if (anglePivotP1 < anglePivotP2)
					return -1;
				else if (anglePivotP2 > anglePivotP2)
					return 1;
		
				return 0;
			}
		};
		
		this.points.sort(angleComparator);
		this.points.add(0, pivot);
		
		Stack<PointVisible> parcours = new Stack<>();
		parcours.push(this.points.get(0));
		parcours.push(this.points.get(1));
		
		// Creation du parcours
		
		for (int i = 2 ; i < this.points.size() ; ++i) {
			//determinant > 0 -> points[i] a droite du segment
			
			PointVisible summit = parcours.pop();
			PointVisible second = parcours.peek();
			parcours.push(summit);
			
			while (parcours.size() >= 2 && determinant(this.points.get(i), second, parcours.peek()) > 0) {
				parcours.pop();
			}
			
			parcours.push(this.points.get(i));
		}
		
		// Creation des segments de l'envellope convexe
		
		//System.out.println("Taille parcours : " + parcours.size());
		
		for (int i = 0 ; i < parcours.size()-1 ; ++i) {
			this.segments.add(new Vecteur(parcours.get(i), parcours.get(i+1)));
		}
		
		if (parcours.size() >= 2)
			this.segments.add(new Vecteur(parcours.get(parcours.size()-1), parcours.get(0)));		
	}

	private PointVisible getPointMinXY() {
		PointVisible toReturn = new PointVisible(width, width);
		for (PointVisible p : points) {
			if (p.x <= toReturn.x && p.y <= toReturn.y)
				toReturn = p;
		}
		return toReturn;
	}
	
	private PointVisible getNextPoint(PointVisible Ai) {
		
		//On r�cup�re le point suivant dans la liste
		//Permet le calcul du determinant selon un point au hasard
		//Ce point Sera certainement modifi� dans la boucle suivante
		
		int q = (this.points.indexOf(Ai) + 1) % this.points.size();
		PointVisible qTemp = this.points.get(q);
		
		//Pour tout les points, on regarde si c'est un tour droit
		for (int i = 0; i < this.points.size(); i++) {
			PointVisible iTemp = this.points.get(i);
			
			if (determinant(Ai, iTemp, qTemp) < 0)
				qTemp = iTemp;
		}
		
		return qTemp;
	}

	

	// initialisation random
	// NB: l'initialisation dans disque est � faire (exercice 1)
	public void initPoints(int n, int r, int x, int y){
		int xp, yp;
		points = new ArrayList<PointVisible>();
		
		for (int i = 0; i <n; i++){
			xp = random(0, width);
			yp = random(0, width);
			points.add(new PointVisible(xp,  yp));
			points.get(i).setLabel("Point "+i);
		}
		this.algoDemiPlans();
		//this.algoJarvis();
		//this.algoGraham();
	}

	// m�thode utilitaire 
	// retourne un entier compris entre xmin et xmax
	int random(int xmin,int xmax){
		double dr = Math.random() * (double) (xmax - xmin) + (double) xmin;
		return (int) dr;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	

		g2d.setColor(fgColor);

		for (PointVisible p: points)
			p.dessine(g2d);
		
		for (Vecteur v : segments)
			v.dessine(g2d);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int n = points.size();
		if (e.getButton() == MouseEvent.BUTTON1){
			initPoints(n, width-50, width/2, width/2);
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String testFile = "tmp.txt";
		ReadWritePoint rw = new ReadWritePoint(testFile);

		if(e.getActionCommand().equals("b1")){
			for (PointVisible s: points){
				rw.add(s.x+";" + s.y+";"+s.getLabel());
			}
			try {
				rw.write();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			initFromFile(testFile);
			repaint();
		}
	}
	
	public void initFromFile(String f){
		ReadWritePoint rw = new ReadWritePoint(f);
		try {
			points = rw.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


