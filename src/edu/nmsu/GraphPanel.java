package edu.nmsu;

import com.google.common.hash.HashCode;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

/**
 * Created by Erik Ness at 1/21/15 6:15 PM
 */
public class GraphPanel extends JPanel
{
	private DataSet dataset;
	
	public GraphPanel()
	{	
		super();
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(253, 246, 227));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		paintAxes(g);
		paintDataSets(g);
	}
	
	public void setDataSet(DataSet ds)
	{
		dataset = ds;
	}
	
	public DataSet getDataSet()
	{
		return dataset;
	}

	private void paintAxes(Graphics g)
	{
		g.setColor(new Color(0, 43, 54));

		int width = this.getWidth();
		int height = this.getHeight();

		// x axis
		g.drawLine(0, height / 2 + 1, width, height / 2 + 1);
		g.drawLine(0, height / 2, width, height / 2);

		// y axis
		g.drawLine(width / 2, 0, width / 2, height);
		g.drawLine(width / 2 + 1, 0, width / 2 + 1, height);
		
	}
	
	private void paintDataSets(Graphics g)
	{
		for (Point p : dataset) {
			int x = (int) p.asPixels().x;
			int y = (int) p.asPixels().y;
			g.setColor(p.getColor());
			g.fillRect(x - Point.RADIUS, y - Point.RADIUS, Point.RADIUS * 2, Point.RADIUS * 2);
		}
	}
}
