package edu.nmsu;

import com.google.common.collect.Maps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 * Created by Erik Ness at 1/21/15 6:15 PM
 */
public class ToolsPanel extends JPanel
{
	public GraphPanel graphPanel; // fuck your fascist coupling standards

	private Color currentColor;
	private List<Color> availableColors;
	private List<ColorObserver> colorObservers;

	private JButton scoreButton;

	private double currentScore; // ranges from 0 to 1
	
	private int margin;
	private int columns;
	private int boxLength;
	
	public ToolsPanel()
	{
		super();
		currentColor = Point.DEFAULT_COLOR;
		currentScore = 0;
		
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
		scoreButton = new JButton("Score");
		scoreButton.setFocusPainted(false);

		ToolsPanel thisTmp = this;  // because "this" in the anonymous function is something else entirely
		scoreButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DataSet ds = graphPanel.getCurrentDataSet();
				Map<Color, List<Point>> clustersTmp =
						ds.stream().collect(Collectors.groupingBy(Point::getColor));
				Map<Color, DataSet> clusters = Maps.transformValues(clustersTmp, lst -> new DataSet(lst));
				double score = DataSet.score(clusters.values());
				currentScore = score;
				thisTmp.repaint();
			}
		});
		this.add(scoreButton);
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
		int x = margin;
		int y = this.getHeight() - margin - rectHeight;

		Paint redToGreen = new LinearGradientPaint(x, y, x + rectWidth, y + rectHeight,
			new float[]{0f, 0.5f, 1f}, new Color[]{Color.GREEN, Color.YELLOW, Color.RED});
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(redToGreen);
		g2.fill(new Rectangle.Double(x, y, rectWidth, rectHeight));


		paintTriangle(g, (int) (y + rectHeight * (1 - currentScore)));
		paintButton(g, x, y, rectWidth, rectHeight);
	}

	public void paintButton(Graphics g, int graderX, int graderY, int graderWidth, int graderHeight)
	{
		scoreButton.setPreferredSize(new Dimension(graderWidth, 35));
		((FlowLayout) this.getLayout()).setVgap(graderY - scoreButton.getHeight() - margin);
		((FlowLayout) this.getLayout()).setHgap(graderX);
		this.revalidate();
	}

	public void paintTriangle(Graphics g, int verticalOffset)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.BLACK);

		int height = 50;
		int yOffset = verticalOffset - height / 2;
		// how much the point sticks out - pi / 6 is based on the equilateral triangle
		int xOut = (int) (this.getWidth() - (Math.sqrt(3) * height / 2));
		int[] xs = new int[]{this.getWidth(), xOut, this.getWidth()};
		int[] ys = new int[]{yOffset, yOffset + height / 2, yOffset + height};

		g2.fill(new Polygon(xs, ys, 3));
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
