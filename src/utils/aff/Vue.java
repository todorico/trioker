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
import utils.go.Vector;
import utils.go.Point;
import utils.go.Line;
import utils.io.ReadWritePoint;


public class Vue extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	Color bgColor;
	Color fgColor; 
	
	int width, height;
	
	Point initialLocation, previousLocation, newLocation;
	Rectangle rectangleElastique;
	
	private List<Point> points;
	private List<Line> aretes;
	private List<PieceTrioker> pieces;
	//private List<PieceTriokerTrioker> PieceTriokers;
	
	PieceTrioker draggedPieceTrioker;

	public Vue(int width, int height, String fileName, List<PieceTrioker> pieces) {
		super();
		
		Couleur.forPrinter(true);
		
		this.bgColor = Couleur.bg;
		this.fgColor = Couleur.fg;
		this.width = width;
		this.height = height;
		
		this.points = new ArrayList<>();
		this.aretes = new ArrayList<>();
		this.pieces = pieces;
		
		this.setBackground(Couleur.bg);
		this.setPreferredSize(new Dimension(width, width));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
		
		this.initFromLog(fileName);
		
		draggedPieceTrioker = null;
	}

	private void initFromLog(String fileName) {
		ReadWritePoint rw = new ReadWritePoint(fileName);
		points = rw.read();
		aretes = new ArrayList<Line>();
		int n = points.size();
		for (int i = 0 ; i < n; i++) {
			aretes.add(new Line(points.get(i), points.get((i+1)%n)));
		}
	}

	public void export(String logFile) {
		ReadWritePoint rw = new ReadWritePoint(logFile);
		for (Point p: points){
			rw.add((int)p.x+";"+(int)p.y+";"+p.toString());
		}
		rw.write();
	}

	public void setPoints(ArrayList<Point> points) {
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
		
		for (Line v: aretes)
			v.draw(g2d);
		
		for (Point p : points)
			p.draw(g2d);
		
		for (PieceTrioker pi : pieces)
			pi.draw(g2d);
	}

	private PieceTrioker getPieceTriokerSelected(int x, int y) {
		for (PieceTrioker PieceTrioker : pieces)
			if (PieceTrioker.contains(x, y))
				return PieceTrioker;
		
		return null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		PieceTrioker piece = getPieceTriokerSelected(e.getX(), e.getY());
        
		if (piece == null)
        	return;
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			this.draggedPieceTrioker = piece;
			this.initialLocation = new Point(e.getX(), e.getY());
		} else if (SwingUtilities.isRightMouseButton(e)) {
			piece.reverse();
			repaint();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.draggedPieceTrioker != null) {
			this.newLocation = new Point(e.getX(), e.getY());
			this.deplacerPieceTrioker();
			repaint();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.draggedPieceTrioker != null) {
			this.newLocation = new Point(e.getX(), e.getY());
			this.deplacerPieceTrioker();
			snapAll(draggedPieceTrioker, 20);
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
        
        if (notches < 0) // up
        	piece.rotate(5);
        else if (notches > 0) // down
        	piece.rotate(-5);
        
        repaint();
    }
	
	private void deplacerPieceTrioker() {
		this.draggedPieceTrioker.translate(new Vector(this.initialLocation, this.newLocation));
		this.initialLocation = this.newLocation;
	}
	
	private void snapAll(PieceTrioker p, double dist) {
		
		for (Point pt : points) {
			snap(p, pt, dist);
		}
		
		for (PieceTrioker pi : pieces) {
			snap(p, pi.up, dist);
			snap(p, pi.left, dist);
			snap(p, pi.right, dist);
		}
	}
	
	private boolean snap(PieceTrioker pi, Point pt, double dist) {
		if (near(pi.up, pt, dist)) {
			pi.translate(new Vector(pi.up, pt));
			return true;
		} else if (near(pi.left, pt, dist)) {
			pi.translate(new Vector(pi.left, pt));
			return true;
		} else if (near(pi.right, pt, dist)) {
			pi.translate(new Vector(pi.right, pt));
			return true;
		} else {
			return false;
		}
	}

	private boolean near(Point a, Point b, double dist) {
		return (a.x <= b.x + dist) && (a.x >= b.x - dist) && 
				(a.y <= b.y + dist) && (a.y >= b.y - dist);
	}
	
	private boolean lineMatch(Line a, int aLabelFrom, int aLabelTo, Line b, int bLabelFrom, int bLabelTo, double dist) {
		return (near(a.from, b.from, dist) && near(a.to, b.to, dist) && aLabelFrom == bLabelFrom && aLabelTo == bLabelTo) ||
				(near(a.from, b.to, dist) && near(a.to, b.from, dist) && aLabelFrom == bLabelTo && aLabelTo == bLabelFrom);
	}
	
	//private nbMatchNeeded
	/*
	// Si on a assez de ligne qui match avec l
	private boolean wellPlaced(PieceTrioker p1, PieceTrioker p2, int nbMatchNeeded) {		
		lineMatch(new Line(p1.left, p1.right), p1.leftLabel, p1.rightLabel, new Line(p2.left, p2.right),  p2.leftLabel, p2.rightLabel);
		
	}
	*/
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
	
}


