package edu.nmsu;

import java.util.List;
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
	
	public ToolsPanel()
	{
		super();
		currentColor = Color.RED;
		availableColors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, 
			Color.YELLOW, Color.MAGENTA, Color.ORANGE);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, 600, 500);
		paintColoringBoxes(g);
	}
	
	public void paintColoringBoxes(Graphics g)
	{
		int margin = 20;
		int boxLength = this.getWidth() / 2 - margin;
		int columns = 2;
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
}
