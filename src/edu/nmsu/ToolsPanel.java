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
	private List<ColorObserver> colorObservers;
	
	private int margin;
	private int columns;
	private int boxLength;
	
	public ToolsPanel()
	{
		super();
		currentColor = Point.DEFAULT_COLOR;
		
		availableColors = Arrays.asList(
				new Color(166, 61, 18), // orange v
				new Color(129, 135, 235), // violet ^
				new Color(31, 113, 171), // blue v
				new Color(166, 191, 0), // green ^
				new Color(250, 64, 154), // magenta ^
				new Color(32, 122, 116) // cyan v
		);
		// Pastel colors, original
		/*availableColors = Arrays.asList(
				new Color(203, 75, 22), // orange
				new Color(108, 113, 196), // violet
				new Color(38, 139, 210), // blue
				new Color(133, 153, 0), // green
				new Color(211, 54, 130), // magenta
				new Color(42, 161, 152) // cyan
		);*/
		colorObservers = new ArrayList<ColorObserver>();
		margin = 20;
		columns = 2;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(238, 232, 213));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		paintColoringBoxes(g);
		paintGrader(g);
	}
	
	public void paintColoringBoxes(Graphics g)
	{
		// setting an instance variable in somewhere other than the constructor is dangeous;
		// however, the width of the component is not set when the constructor is called.
		// it IS set when paintComponent is called, which is pretty early on and before
		// anything important happens.
		boxLength = this.getWidth() / 2 - margin;
		
		int col = 0;
		int row = 0;
		
		for (Color c : availableColors) {
			// THIS IS WHERE WE FIX THE BLACK DEFAULT THING
			g.setColor(c);
			paintSingleBox(g, margin + col * boxLength, margin + row * boxLength,
					boxLength, c.equals(currentColor));
			col = (col + 1) % columns;
			if (col == 0) row++;
		}
	}
	
	public void paintGrader(Graphics g)
	{
		int rectWidth = this.getWidth() - 2 * margin;
		int rectHeight = 400; // make more dynamic later
		
		g.setColor(Color.BLACK);
		g.fillRect(margin, this.getHeight() - margin - rectHeight,
			rectWidth, rectHeight);
	}
	
	public void paintSingleBox(Graphics g, int x, int y, int length, boolean border)
	{
		g.fillRect(x, y, length, length);
		if (border) {
			g.setColor(Color.BLACK);
			g.drawLine(x, y, x + length - 1, y);  // top
			g.drawLine(x + length - 1, y, x + length - 1, y + length - 1);  // right
			g.drawLine(x + length - 1, y + length - 1, x, y + length - 1);  // bottom
			g.drawLine(x, y + length - 1, x, y);  // left
		}
	}
	
	public void setSelectedColor(Color c)
	{
		currentColor = c;
		for (ColorObserver observer : colorObservers) {
			observer.colorUpdate(c);
		}
	}
	
	public void addColorObserver(ColorObserver obs)
	{
		colorObservers.add(obs);
		obs.colorUpdate(currentColor);
	}
	
	public int getMargin()
	{
		return margin;
	}
	
	public int getColumns()
	{
		return columns;
	}
	
	/*public Color getDefaultHighlightColor()
	{
		return defaultHighlightColor;
	}*/
	
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
		Area area = new Area(margin, margin + ((availableColors.size() + 1) / columns) * boxLength,
			margin, margin + boxLength * columns);
		return area;
	}
}
