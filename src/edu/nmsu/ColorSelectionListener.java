package edu.nmsu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class ColorSelectionListener implements MouseListener, MouseMotionListener
{
	private Component parentComponent;
	public void mousePressed(MouseEvent e)
	{
		ToolsPanel toolsPanel = ((ToolsPanel) parentComponent);
		Area selectionArea = toolsPanel.getColorSelectionArea();
		Point.Pair location = new Point.Pair(e.getX(), e.getY());
		if (!selectionArea.withinBounds(location)) {
			return;
		}
		
		int boxLength = (selectionArea.right - selectionArea.left) / 
			((ToolsPanel) parentComponent).getColumns();
		int row = ((int) location.x - selectionArea.left) / boxLength;
		int col = ((int) location.y - selectionArea.top) / boxLength;
		int index = row + col * toolsPanel.getColumns();
		Color pressedColor = toolsPanel.getAvailableColors().get(index);
		toolsPanel.setSelectedColor(pressedColor);
		toolsPanel.repaint();
		// first, make sure it's in the selection area
		// second, figure out which color is being clicked
		// third, swap to that color
	}
	
	public void mouseMoved(MouseEvent e)
	{
		// goal: change the cursor to a hand when you're over the selection area
	}
	
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public void setParentComponent(Component comp)
	{
		parentComponent = comp;
	}
}
