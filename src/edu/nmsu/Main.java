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
		Point.pixelWidth = appHeight;
		Point.pixelHeight = appHeight;
		Point.gridXMax = 5;
		Point.gridXMin = -5;
		Point.gridYMax = 5;
		Point.gridYMin = -5;
		
        DataSet ds = new DataSet(Lists.newArrayList(
			Point.fromGrid(1, 1),
			Point.fromGrid(2, 2),
			Point.fromGrid(3, 4)));

		JFrame applicationFrame = new JFrame();
		//applicationFrame.setSize(new Dimension(appWidth, appHeight));
		//applicationFrame.setPreferredSize(new Dimension(appWidth, appHeight));
		
		
		
		
		
		applicationFrame.setPreferredSize(new Dimension(appWidth, appHeight));
		applicationFrame.pack();

		// This is not the actual-sized frame. get the actual size
		Dimension actualSize = applicationFrame.getContentPane().getSize();

		int extraW = appWidth - actualSize.width;
		int extraH = appHeight - actualSize.height;

		// Now set the size.
		applicationFrame.setSize(appWidth + extraW, appHeight + extraH);
		
		
		//applicationFrame.pack();
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
        
        ColorSelectionListener csl = new ColorSelectionListener();
        toolsPanel.addMouseListener(csl);
        toolsPanel.addMouseMotionListener(csl);
        csl.setParentComponent(toolsPanel);

        GraphPanel graphPanel = new GraphPanel();
		graphPanel.addDataSet(ds);
		
        HighlightListener hl = new HighlightListener();
        hl.setDataSets(graphPanel.getDataSets());
        hl.setParentComponent(graphPanel);  // Tight coupling; careful of this architecture
        graphPanel.addMouseListener(hl);
        graphPanel.addMouseMotionListener(hl);
		
		graphPanel.setPreferredSize(new Dimension(appWidth - toolsWidth, appHeight));
		applicationFrame.getContentPane().add(BorderLayout.EAST, graphPanel);
		
		
	}
}
