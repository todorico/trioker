package utils.aff;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.go.PieceTrioker;
import utils.go.Point2;
import utils.go.Vector2;
import utils.go.PointVisible;
import utils.go.VecteurVisible;
import utils.io.ReadWritePoint;


public class Vue extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener{
	Color bgColor;
	Color fgColor; 
	int width, height;
	
	Point2 initialLocation, previousLocation, newLocation;
	Rectangle rectangleElastique;
	
	private List<PointVisible> points;
	private List<VecteurVisible> aretes;
	
	private List<PieceTrioker> alPieceTriokers;
	//private List<PieceTriokerTrioker> PieceTriokers;
	
	PieceTrioker draggedPieceTrioker;

	public Vue(int width, int height, String fileName, List<PieceTrioker> PieceTriokers) {
		super();
		Couleur.forPrinter(true);
		this.bgColor = Couleur.bg;
		this.fgColor = Couleur.fg;
		this.width = width;
		this.height = height;
		
		this.points = new ArrayList<>();
		this.aretes = new ArrayList<>();
		this.alPieceTriokers = new ArrayList<>();
		//this.PieceTriokers = new ArrayList<>();
		
		this.setBackground(Couleur.bg);
		this.setPreferredSize(new Dimension(width, width));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
		
		this.initFromLog(fileName);
		
		this.alPieceTriokers = PieceTriokers;
		draggedPieceTrioker = null;
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
		
		for (PieceTrioker pi : alPieceTriokers)
			pi.draw(g2d);
		
	}

	private PieceTrioker getPieceTriokerSelected(int x, int y) {
		for (PieceTrioker PieceTrioker : alPieceTriokers)
			if (PieceTrioker.contains(x, y))
				return PieceTrioker;
		return null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (SwingUtilities.isLeftMouseButton(e))
			this.leftMouseButtonPressed(e);
		
		if (SwingUtilities.isRightMouseButton(e))
			this.rightMouseButtonPressed(e);
	}
	
	private void leftMouseButtonPressed(MouseEvent e) {
		this.draggedPieceTrioker = this.getPieceTriokerSelected(e.getX(), e.getY());
		
		
		this.initialLocation = new Point2(e.getX(), e.getY());
	}
	
	//Modifier l'appel de rotate si besoin
	private void rightMouseButtonPressed(MouseEvent e) {
		PieceTrioker PieceTriokerSelected = this.getPieceTriokerSelected(e.getX(), e.getY());
		PieceTriokerSelected.reverse();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.draggedPieceTrioker != null) {
			this.newLocation = new Point2(e.getX(), e.getY());
			this.deplacerPieceTrioker();
			repaint();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.draggedPieceTrioker != null) {
			this.newLocation = new Point2(e.getX(), e.getY());
			this.deplacerPieceTrioker();
			this.draggedPieceTrioker = null;
			repaint();
		}
	}
	
	@Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
		PieceTrioker piece = getPieceTriokerSelected(e.getX(), e.getY());
        if (piece == null)
        	return;
        
        int notches = e.getWheelRotation();
        
        if (notches < 0) { //up
        	piece.rotateRight();
        } else if (notches > 0){ // down
        	piece.rotateLeft();
        }
        
        repaint();
    }
	
	private void deplacerPieceTrioker() {
		Vector2 translation = new Vector2(this.initialLocation, this.newLocation);
		this.draggedPieceTrioker.translate(translation);
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


