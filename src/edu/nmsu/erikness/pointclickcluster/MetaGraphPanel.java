package edu.nmsu.erikness.pointclickcluster;

import com.google.common.collect.Lists;
import edu.nmsu.erikness.miningcommon.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Erik Ness at 3/22/15 9:51 AM
 *
 * Manages a collection of GraphPanels in a CardLayout.
 *
 * Each GraphPanel is a
 */
public class MetaGraphPanel extends JPanel
{
	private CardLayout layout;
	// I don't think I can access the Layout's private list of components, but that's why we have a container class!
	private List<GraphPanel> panels;
	private GraphPanel currentGraphPanel;

	private ToolsPanel toolsPanel;

	public MetaGraphPanel()
	{
		layout = new CardLayout();
		panels = Lists.newArrayList();
		this.setLayout(layout);
	}

	public void addGraphPanel(GraphPanel gp)
	{
		this.add(gp, Integer.toString(gp.hashCode()));
		panels.add(gp);
	}

	public List<GraphPanel> getGraphPanels()
	{
		return panels;
	}

	public GraphPanel getCurrentGraphPanel()
	{
		// for loop is not a big deal, as we only have a couple cards
		for (Component comp : this.getComponents()) {
			if (comp.isVisible()) {
				return (GraphPanel) comp;
			}
		}
		return null;
	}

	public void next()
	{
		toolsPanel.repaint();
		toolsPanel.setSelectedColor(Point.DEFAULT_COLOR);
		layout.next(this);
	}

	public void previous()
	{
		toolsPanel.repaint();
		toolsPanel.setSelectedColor(Point.DEFAULT_COLOR);
		layout.previous(this);
	}

	public void first()
	{
		toolsPanel.repaint();
		toolsPanel.setSelectedColor(Point.DEFAULT_COLOR);
		layout.first(this);
	}

	public void last()
	{
		toolsPanel.repaint();
		toolsPanel.setSelectedColor(Point.DEFAULT_COLOR);
		layout.last(this);
	}

	public ToolsPanel getToolsPanel()
	{
		return toolsPanel;
	}

	public void setToolsPanel(ToolsPanel toolsPanel)
	{
		this.toolsPanel = toolsPanel;
	}
}
