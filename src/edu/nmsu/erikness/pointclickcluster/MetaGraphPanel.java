package edu.nmsu.erikness.pointclickcluster;

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
	private List<DataSetChangeObserver> observers;
	private GraphPanel currentGraphPanel;

	public MetaGraphPanel()
	{
		layout = new CardLayout();
		panels = Lists.newArrayList();
		observers = Lists.newArrayList();
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

	public void addDataSetChangeObserver(DataSetChangeObserver obs)
	{
		observers.add(obs);
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

	private void updateObservers()
	{
		for (DataSetChangeObserver obs : observers) {
			obs.update(currentGraphPanel);
		}
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
