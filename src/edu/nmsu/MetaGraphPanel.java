package edu.nmsu;

import com.google.common.collect.Lists;

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

	public MetaGraphPanel()
	{
		layout = new CardLayout();
		panels = Lists.newArrayList();
		this.setLayout(layout);
	}

	public void addGraphPanel(JPanel gp)
	{
		//layout.addLayoutComponent(gp, Integer.toString(gp.hashCode()));
		this.add(gp, Integer.toString(gp.hashCode()));
		//panels.add(gp);
	}

	public List<GraphPanel> getGraphPanels()
	{
		return panels;
	}

	public void next()
	{
		layout.next(this);
	}

	public void previous()
	{
		layout.previous(this);
	}

	public void first()
	{
		layout.first(this);
	}

	public void last()
	{
		layout.last(this);
	}
}
