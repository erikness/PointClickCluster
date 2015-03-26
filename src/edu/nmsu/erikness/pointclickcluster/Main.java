package edu.nmsu.erikness.pointclickcluster;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import com.google.common.collect.Lists;
import edu.nmsu.erikness.miningcommon.DataSet;

public class Main
{
    public static void main(String[] args)
	{
		int appHeight = 700;
		int toolsWidth = 100;
		int appWidth = appHeight + toolsWidth;
		
		/* Initialize the static stuff in the Point class. */
		edu.nmsu.erikness.miningcommon.Point.pixelWidth = appHeight;
		edu.nmsu.erikness.miningcommon.Point.pixelHeight = appHeight;
		edu.nmsu.erikness.miningcommon.Point.gridXMax = 5;
		edu.nmsu.erikness.miningcommon.Point.gridXMin = -5;
		edu.nmsu.erikness.miningcommon.Point.gridYMax = 5;
		edu.nmsu.erikness.miningcommon.Point.gridYMin = -5;

		String dataSetDirectory = "A:/Erik/Documents/PointClickCluster/datasets/";
		List<DataSet> loadedDataSets = Lists.newArrayList();
		for (File f : new File(dataSetDirectory).listFiles()) {
			loadedDataSets.add(DataSet.fromFile(f));
		}
		//DataSet ds = DataSet.fromFile("A:/Erik/Documents/PointClickCluster/datasets/2.txt");

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

		MetaGraphPanel metaGraphPanel = new MetaGraphPanel();
		for (DataSet ds : loadedDataSets) {
			GraphPanel graphPanel = new GraphPanel();
			graphPanel.setPreferredSize(new Dimension(appWidth - toolsWidth, appHeight));
			graphPanel.setDataSet(ds);

			HighlightListener hl = new HighlightListener();
			hl.setDataSet(graphPanel.getDataSet());
			hl.setParentComponent(graphPanel);  // Tight coupling; careful of this architecture
			toolsPanel.addColorObserver(hl);
			graphPanel.addMouseListener(hl);
			graphPanel.addMouseMotionListener(hl);

			metaGraphPanel.addGraphPanel(graphPanel);
		}

		toolsPanel.metaGraphPanel = metaGraphPanel;

		metaGraphPanel.setPreferredSize(new Dimension(appWidth - toolsWidth, appHeight));
		applicationFrame.getContentPane().add(BorderLayout.EAST, metaGraphPanel);

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
