package utils.aff;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import utils.go.Piece;
import utils.go.PointVisible;
import utils.go.Vecteur;
import utils.io.ReadWritePoint;


public class Vue extends JPanel implements MouseListener, MouseMotionListener{
	Color bgColor;
	Color fgColor; 
	int width, height;
	
	Point initialLocation, previousLocation, newLocation;
	Rectangle rectangleElastique;
	
	private List<PointVisible> points;
	private List<Vecteur> aretes;
	
	private List<Piece> alPieces;
	Piece draggedPieceInitial, draggedPiecePrevious;

	public Vue(int width, int height, String fileName, List<Piece> pieces) {
		super();
		Couleur.forPrinter(true);
		this.bgColor = Couleur.bg;
		this.fgColor = Couleur.fg;
		this.width = width;
		this.height = height;
		
		this.points = new ArrayList<>();
		this.aretes = new ArrayList<>();
		this.alPieces = new ArrayList<>();
		
		this.setBackground(Couleur.bg);
		this.setPreferredSize(new Dimension(width, width));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.initFromLog(fileName);
		
		this.alPieces = pieces;
		draggedPieceInitial = null;
		draggedPiecePrevious = null;
	}

	private void initFromLog(String fileName) {
		ReadWritePoint rw = new ReadWritePoint(fileName);
		points = rw.read();
		aretes = new ArrayList<Vecteur>();
		int n = points.size();
		for (int i = 0 ; i < n; i++) {
			aretes.add(new Vecteur(points.get(i), points.get((i+1)%n)));
		}
	}

	public void export(String logFile) {
		ReadWritePoint rw = new ReadWritePoint(logFile);
		for (PointVisible p: points){
			rw.add((int)p.getMC().x+";"+(int)p.getMC().y+";"+p.toString());
		}
		rw.write();
	}

	public void setPoints(ArrayList<PointVisible> points) {
		this.points = points;
	}	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.setColor(fgColor);
		
		if (rectangleElastique != null)
			g2d.draw(rectangleElastique);
		
		for (Vecteur v: aretes)
			v.dessine(g2d);
		
		for (PointVisible p : points)
			p.dessine(g2d);
		
		for (Piece pi : alPieces)
			pi.dessine(g2d);
		
	}

	private Piece getPieceSelected(int x, int y) {
		for (Piece piece : alPieces)
			if (piece.isSelected(x, y))
				return piece;
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {		
		this.draggedPieceInitial = this.getPieceSelected(e.getX(), e.getY());
		this.draggedPiecePrevious = this.draggedPieceInitial;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//TODO: last update
		this.draggedPieceInitial = null;
		this.draggedPiecePrevious = null;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.draggedPieceInitial != null) {
			//TODO: create update
			// On calcule un vecteur de déplacement entre la position précédente et la nouvelle
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}


