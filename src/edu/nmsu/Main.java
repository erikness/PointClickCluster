package edu.nmsu;

import javax.swing.*;
import java.awt.*;

import com.google.common.collect.Lists;

public class Main
{
    public static void main(String[] args)
	{
		int appHeight = 700;
		int toolsWidth = 100;
		int appWidth = appHeight + toolsWidth;
		
		/* Initialize the static stuff in the Point class. */
		Point.pixelWidth = appHeight;
		Point.pixelHeight = appHeight;
		Point.gridXMax = 5;
		Point.gridXMin = -5;
		Point.gridYMax = 5;
		Point.gridYMin = -5;

		DataSet ds = DataSet.fromFile("A:/Erik/Documents/PointClickCluster/datasets/1.txt");

		JFrame applicationFrame = new JFrame();
		applicationFrame.setPreferredSize(new Dimension(appWidth, appHeight));
		applicationFrame.pack();

		adjustSize(applicationFrame, appWidth, appHeight);

		centerWindow(applicationFrame);

		ToolsPanel toolsPanel = new ToolsPanel();
        applicationFrame.getContentPane().add(BorderLayout.CENTER, toolsPanel);
        toolsPanel.setPreferredSize(new Dimension(toolsWidth, appHeight));
        
        ColorSelectionListener csl = new ColorSelectionListener();
        toolsPanel.addMouseListener(csl);
        toolsPanel.addMouseMotionListener(csl);
        csl.setParentComponent(toolsPanel);

        GraphPanel graphPanel = new GraphPanel();
		graphPanel.addDataSet(ds);

		toolsPanel.graphPanel = graphPanel;
		
        HighlightListener hl = new HighlightListener();
        hl.setDataSets(graphPanel.getDataSets());
        hl.setParentComponent(graphPanel);  // Tight coupling; careful of this architecture
        toolsPanel.addColorObserver(hl);
        graphPanel.addMouseListener(hl);
        graphPanel.addMouseMotionListener(hl);
		
		graphPanel.setPreferredSize(new Dimension(appWidth - toolsWidth, appHeight));
		applicationFrame.getContentPane().add(BorderLayout.EAST, graphPanel);
		
		applicationFrame.setResizable(false);
		applicationFrame.setVisible(true);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	private static void adjustSize(JFrame frame, int intendedWidth, int intendedHeight)
	{
		// We "set" the size initially, but JFrame doesn't entirely agree and includes things like the border
		// in the size. So we let it render invisibly, and resize based on that.
		Dimension actualSize = frame.getContentPane().getSize();

		int extraW = intendedWidth - actualSize.width;
		int extraH = intendedHeight - actualSize.height;

		// Now set the size.
		frame.setSize(intendedWidth + extraW, intendedHeight + extraH);
	}

	private static void centerWindow(JFrame frame)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(
				screenSize.width / 2 - frame.getSize().width / 2,
				screenSize.height / 2 - frame.getSize().height / 2);
	}
}
