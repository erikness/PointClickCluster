package edu.nmsu;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

/**
 * Created by Erik Ness at 1/21/15 6:15 PM
 */
public class GraphPanel extends JPanel
{
	private List<DataSet> datasets;
	
	public GraphPanel()
	{	
		super();
		datasets = new ArrayList<DataSet>();
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(253, 246, 227));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		paintAxes(g);
		paintDataSets(g);
	}
	
	public void addDataSet(DataSet ds)
	{
		datasets.add(ds);
	}
	
	public List<DataSet> getDataSets()
	{
		// Useful to pass to other objects who need the current list of datasets at
		// any given time.
		return datasets;
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
		for (DataSet ds : datasets) {
			for (Point p : ds) {
				int x = (int) p.asPixels().x;
				int y = (int) p.asPixels().y;
				g.setColor(p.getColor());
				g.fillRect(x - Point.RADIUS, y - Point.RADIUS, Point.RADIUS * 2, Point.RADIUS * 2);
			}
		}
	} 
}
