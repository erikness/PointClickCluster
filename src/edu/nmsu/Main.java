package edu.nmsu;

import javax.swing.*;
import java.awt.*;

import com.google.common.collect.Lists;

public class Main
{
    public static void main(String[] args)
	{
		int appHeight = 500;
		int toolsWidth = 100;
		int appWidth = appHeight + toolsWidth;
		
		/* Initialize the static stuff in the Point class. */
		Point.pixelWidth = appHeight + toolsWidth;
		Point.pixelHeight = appHeight;
		Point.gridXMax = 5;
		Point.gridXMin = -5;
		Point.gridYMax = 5;
		Point.gridYMin = -5;
		
        DataSet ds = new DataSet(Lists.newArrayList(
			Point.fromGrid(1, 1),
			Point.fromGrid(2, 2),
			Point.fromGrid(3, 4)));
			
		for (Point p : ds) {
			System.out.println(p.asPixels().x);
			System.out.println(p.asPixels().y);
		}

		JFrame applicationFrame = new JFrame();
		applicationFrame.setSize(new Dimension(appWidth, appHeight));
		applicationFrame.setVisible(true);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Put the application in the center of the screen */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		applicationFrame.setLocation(
				screenSize.width / 2 - applicationFrame.getSize().width / 2,
				screenSize.height / 2 - applicationFrame.getSize().height / 2);

		JPanel toolsPanel = new ToolsPanel();
        applicationFrame.getContentPane().add(BorderLayout.CENTER, toolsPanel);
        toolsPanel.setPreferredSize(new Dimension(toolsWidth, appHeight));

        GraphPanel graphPanel = new GraphPanel();
		
		graphPanel.addDataSet(ds);
		graphPanel.setPreferredSize(new Dimension(appWidth - toolsWidth, appHeight));
		applicationFrame.getContentPane().add(BorderLayout.EAST, graphPanel);
		
		
	}
}
