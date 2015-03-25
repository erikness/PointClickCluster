package edu.nmsu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.List;

public class HighlightListener implements MouseListener, MouseMotionListener, ColorObserver
{
	private DataSet currentDataSet;
	private Component parentComponent;
	
	private Color highlightColor;
	private Cursor handCursor;
	private Cursor defaultCursor;
	
	public HighlightListener()
	{
		super();
		handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		defaultCursor = Cursor.getDefaultCursor();
	}
	
	public void mousePressed(MouseEvent e)
	{
		Point.Pair clickedPosition = new Point.Pair(e.getX(), e.getY());
		// On what data point (if any)?
		// I guess we gotta loop...note if two points are overlapping, the one
		// in the dataset that was added first will be chosen.
		for (Point p : currentDataSet) {
			if (withinBounds(clickedPosition, p.asPixels())) {
				highlight(p);
			}
		}
	}
	
	public void mouseMoved(MouseEvent e)
	{
		// We REALLY need a more efficient solution to looping on every mouse move...
		// Maybe some sort of set membership data structure?
		Point.Pair clickedPosition = new Point.Pair(e.getX(), e.getY());
		
		boolean cursorChanged = false;

		for (Point p : currentDataSet) {
			if (withinBounds(clickedPosition, p.asPixels())) {
				parentComponent.setCursor(handCursor);
				cursorChanged = true;
			}
		}
		
		if (!cursorChanged) {
			parentComponent.setCursor(defaultCursor);
		}
	}
	
	private void highlight(Point p)
	{
		p.setColor(highlightColor);
		parentComponent.repaint();
	}
	
	private boolean withinBounds(Point.Pair clickedPixel, Point.Pair candidatePair)
	{		
		boolean xInBounds = candidatePair.x - Point.RADIUS <= clickedPixel.x && 
							clickedPixel.x <= candidatePair.x + Point.RADIUS;
		boolean yInBounds = candidatePair.y - Point.RADIUS <= clickedPixel.y && 
							clickedPixel.y <= candidatePair.y + Point.RADIUS;
							
		return xInBounds && yInBounds;
	}
		
	public void setDataSet(DataSet set)
	{
		currentDataSet = set;
	}
	
	public void setParentComponent(Component comp)
	{
		parentComponent = comp;
	}
	
	public void colorUpdate(Color c)
	{
		highlightColor = c;
	}
	
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}









