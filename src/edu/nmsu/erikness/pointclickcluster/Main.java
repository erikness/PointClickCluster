package edu.nmsu.erikness.pointclickcluster;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.common.collect.Lists;
import edu.nmsu.erikness.miningcommon.Point;

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

		String internalDataSetsPath = "edu/nmsu/erikness/datasets/";
		List<DataSetWithScores> loadedDataSets;

		/* Look, I just can't figure out the screwy build process with resources that IntelliJ has going on.
		 * For now, the quick fix is detecting whether we're in a jar or in the IDE, and loading based on that.
		 * Fight me.
		 */
		if (inJar()) {
			loadedDataSets = loadDataSetsAsJar(internalDataSetsPath);
		} else {
			loadedDataSets = loadDataSetsAsProject(internalDataSetsPath);
		}

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
		metaGraphPanel.setToolsPanel(toolsPanel);
		for (DataSetWithScores ds : loadedDataSets) {
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
		applicationFrame.setTitle("Point & Click Clustering");
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

	private static boolean inJar()
	{
		URL here = Main.class.getClassLoader().getResource("edu/nmsu/erikness/pointclickcluster/Main.class");
		return here.toString().startsWith("jar");
	}

	private static List<DataSetWithScores> loadDataSetsAsJar(String internalDataSetsPath)
	{
		List<DataSetWithScores> dataSets = Lists.newArrayList();

		CodeSource src = Main.class.getProtectionDomain().getCodeSource();
		ClassLoader cl = Main.class.getClassLoader();
		URL jar = src.getLocation();

		try {
			ZipInputStream zip = new ZipInputStream(jar.openStream());
			ZipEntry entry = null;
			String name = null;
			while ((entry = zip.getNextEntry()) != null) {
				name = entry.getName();
				if (name.startsWith(internalDataSetsPath) && name.endsWith(".txt")) {
					dataSets.add(DataSetWithScores.fromStream(cl.getResourceAsStream(name)));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return dataSets;
	}

	private static List<DataSetWithScores> loadDataSetsAsProject(String internalDataSetsPath)
	{
		List<DataSetWithScores> dataSets = Lists.newArrayList();

		ClassLoader cl = Main.class.getClassLoader();
		String dataSetDirectory = cl.getResource(internalDataSetsPath).getPath();

		for (File f : new File(dataSetDirectory).listFiles()) {
			dataSets.add(DataSetWithScores.fromFile(f));
		}

		return dataSets;
	}

	private static void centerWindow(JFrame frame)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(
				screenSize.width / 2 - frame.getSize().width / 2,
				screenSize.height / 2 - frame.getSize().height / 2);
	}
}
