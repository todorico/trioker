package TP2.utils.affichage;

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
	
	//algoDemiPlan
	ArrayList<Vecteur> segments = new ArrayList<Vecteur>();
	
	//algoJarvis
	ArrayList<PointVisible> envConv = new ArrayList<PointVisible>();

	// n : le nombre de lignes
	// width, height : largeur, hauteur de la fen�tre
	public Vue(int n, int width) {
		super();
		JButton b1 = new JButton("Enregistrer");
		b1.setActionCommand("b1");
		b1.addActionListener(this);
		JButton b2 = new JButton("Charger");
		b2.addActionListener(this);
		Box b = Box.createHorizontalBox();
		b.add(b1);
		b.add(Box.createHorizontalStrut(10));
		b.add(b2);
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
		
		this.envConv.clear();
		
		// 1ère étape : Trouver [a0, b0]
		PointVisible A0 = this.getPointMinXY();
		PointVisible B0 = this.getPointMinFromPoint(A0);
		this.envConv.add(A0);
		this.envConv.add(B0);
		
		// 2ème étape : A partir d'un segment [ai, bi] trouver le point bi+1
		PointVisible Bi = B0;
		
		PointVisible Ai1;
		PointVisible Bi1;
		do {
			Ai1 = Bi;
			Bi1 = this.getPointMinFromPoint(Ai1);
			this.envConv.add(Bi1);
		} while (!Bi1.equals(A0));
		
	}

	private PointVisible getPointMinXY() {
		PointVisible toReturn = new PointVisible(width, width);
		for (PointVisible p : points) {
			if (p.x <= toReturn.x && p.y <= toReturn.y)
				toReturn = p;
		}
		return toReturn;
	}
	
	private PointVisible getPointMinFromPoint(PointVisible depart) {
		
		for (int j = 0; j < this.points.size(); j++) {
			PointVisible Bi = this.points.get(j);
			
			boolean tourGauche = true;
			if (this.determinant(depart, depart, Bi) < 0)
				tourGauche = false;
			
			boolean testTour = true;
			
			for (int k = 1; k < this.points.size(); k++) {
				PointVisible Pi = this.points.get(k);
				
				if (Pi.equals(depart) || Pi.equals(Bi))
					continue;
				
				int resultat = this.determinant(Pi, depart, Bi);
				
				if ( (tourGauche && resultat < 0) || (!tourGauche && resultat >= 0)) {
					testTour = false;
					break;
				}
					
			}
			
			if (testTour)
				return Bi;
			
		}
		return null;
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
		//this.algoDemiPlans();
		this.algoJarvis();
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
		
		// algoDemiPlans
		for (Vecteur v : segments)
			v.dessine(g2d);
		
		// algoJarvis
		for (int i = 0; i < this.envConv.size() - 1; i++) {
			Vecteur v = new Vecteur(this.envConv.get(i), this.envConv.get(i+1));
			v.dessine(g2d);
		}
		
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
		}else{
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


