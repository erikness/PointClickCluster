package edu.nmsu;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;

public class HighlightListener implements MouseListener
{
	private List<DataSet> currentDataSets;
	
	public void mousePressed(MouseEvent e)
	{
		Point.Pair clickedPosition = new Point.Pair(e.getX(), e.getY());
		// On what data point (if any)?
		// I guess we gotta loop...note if two points are overlapping, the one
		// in the dataset that was added first will be chosen.
		for (DataSet ds : currentDataSets) {
			for (Point p : ds) {
				if (withinBounds(clickedPosition, p.asPixels())) {
					highlight(p);
				}
			}
		}
	}
	
	private void highlight(Point p)
	{
		System.out.println("Congratulations!");
		System.out.println(p);
		/* TODO */
	}
	
	private boolean withinBounds(Point.Pair clickedPixel, Point.Pair candidatePair)
	{		
		int radius = 4; // hardcoded for now
		boolean xInBounds = candidatePair.x - radius <= clickedPixel.x && 
							clickedPixel.x <= candidatePair.x + radius;
		boolean yInBounds = candidatePair.y - radius <= clickedPixel.y && 
							clickedPixel.y <= candidatePair.y + radius;
							
		return xInBounds && yInBounds;
	}
		
	public void setDataSets(List<DataSet> sets)
	{
		currentDataSets = sets;
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
