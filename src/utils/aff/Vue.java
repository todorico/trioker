package utils.aff;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.go.Piece;
import utils.go.Point;
import utils.go.PointVisible;
import utils.go.Vecteur;
import utils.go.VecteurVisible;
import utils.io.ReadWritePoint;


public class Vue extends JPanel implements MouseListener, MouseMotionListener{
	Color bgColor;
	Color fgColor; 
	int width, height;
	
	Point initialLocation, previousLocation, newLocation;
	Rectangle rectangleElastique;
	
	private List<PointVisible> points;
	private List<VecteurVisible> aretes;
	
	private List<Piece> alPieces;
	Piece draggedPiece;

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
		draggedPiece = null;
	}

	private void initFromLog(String fileName) {
		ReadWritePoint rw = new ReadWritePoint(fileName);
		points = rw.read();
		aretes = new ArrayList<VecteurVisible>();
		int n = points.size();
		for (int i = 0 ; i < n; i++) {
			aretes.add(new VecteurVisible(points.get(i), points.get((i+1)%n)));
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
		
		for (VecteurVisible v: aretes)
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
	public void mousePressed(MouseEvent e) {
		
		if (SwingUtilities.isLeftMouseButton(e))
			this.rightMouseButtonPressed(e);
		
		if (SwingUtilities.isRightMouseButton(e))
			this.leftMouseButtonPressed(e);
	}
	
	private void rightMouseButtonPressed(MouseEvent e) {
		this.draggedPiece = this.getPieceSelected(e.getX(), e.getY());
		this.initialLocation = new Point(e.getX(), e.getY());
	}
	
	//Modifier l'appel de rotate si besoin
	private void leftMouseButtonPressed(MouseEvent e) {
		Piece pieceSelected = this.getPieceSelected(e.getX(), e.getY());
		pieceSelected.rotateLeft();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.draggedPiece != null) {
			this.newLocation = new Point(e.getX(), e.getY());
			this.deplacerPiece();
			repaint();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.draggedPiece != null) {
			this.newLocation = new Point(e.getX(), e.getY());
			this.deplacerPiece();
			this.draggedPiece = null;
			repaint();
		}
	}
	
	private void deplacerPiece() {
		Vecteur translation = new Vecteur(this.initialLocation, this.newLocation);
		this.draggedPiece.translater(translation);
		this.initialLocation = this.newLocation;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
	
}


