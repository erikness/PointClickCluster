package edu.nmsu;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * Created by Erik Ness at 1/21/15 6:15 PM
 */
public class ToolsPanel extends JPanel
{
	private Color currentColor;
	private List<Color> availableColors;
	
	private int margin;
	private int columns;
	private int boxLength;
	
	public ToolsPanel()
	{
		super();
		currentColor = Color.RED;
		availableColors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, 
			Color.YELLOW, Color.MAGENTA, Color.ORANGE);
			
		margin = 20;
		columns = 2;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, 600, 500);
		paintColoringBoxes(g);
	}
	
	public void paintColoringBoxes(Graphics g)
	{
		// setting an instance variable in somewhere other than the constructor is dangeous;
		// however, the width of the component is not set when the constructor is called.
		// it IS set when paintComponent is called, which is pretty early on and before
		// anything important happens.
		int boxLength = this.getWidth() / 2 - margin;
		
		int col = 0;
		int row = 0;
		
		for (Color c : availableColors) {
			g.setColor(c);
			paintSingleBox(g, margin + col * boxLength, margin + row * boxLength,
				boxLength, c == currentColor);
			col = (col + 1) % columns;
			if (col == 0) row++;
		}
	}
	
	public void paintSingleBox(Graphics g, int x, int y, int length, boolean border)
	{
		g.fillRect(x, y, length, length);
		if (border) {
			g.setColor(Color.BLACK);
			g.drawLine(x, y, x + length, y);
			g.drawLine(x + length, y, x + length, y + length);
			g.drawLine(x + length, y + length, x, y + length);
			g.drawLine(x, y + length, x, y);
		}
	}
	
	public void setSelectedColor(Color c)
	{
		currentColor = c;
	}
	
	public int getMargin()
	{
		return margin;
	}
	
	public int getColumns()
	{
		return columns;
	}
	
	public int getBoxLength()
	{
		return boxLength;
	}
	
	public List<Color> getAvailableColors()
	{
		// Accessing the list object directly can be dangerous; colors are immutable,
		// so let's make a (shallow) copy.
		return new ArrayList<Color>(availableColors);
	}
	
	public Area getColorSelectionArea()
	{
		return new Area(margin, margin + (availableColors.size() + 1 / columns) * boxLength,
			margin, margin + boxLength * columns);
	}
}
