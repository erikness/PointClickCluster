package edu.nmsu;


import java.awt.*;
import javax.swing.*;

/**
 * Created by Erik Ness at 1/21/15 6:15 PM
 */
public class GraphPanel extends JPanel
{

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 500, 600);
		System.out.println("Graph width: " + this.getWidth());
		System.out.println("Graph height: " + this.getWidth());

		paintAxes(g);
	}

	private void paintAxes(Graphics g)
	{
		g.setColor(Color.WHITE);

		int width = this.getWidth();
		int height = this.getHeight();

		// x axis
		g.drawLine(0, height / 2 + 1, width, height / 2 + 1);
		g.drawLine(0, height / 2, width, height / 2);

		// y axis
		g.drawLine(width / 2, 0, width / 2, height);
		g.drawLine(width / 2 + 1, 0, width / 2 + 1, height);
	}
}
