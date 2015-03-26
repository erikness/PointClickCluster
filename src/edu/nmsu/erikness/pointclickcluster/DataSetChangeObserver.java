package edu.nmsu.erikness.pointclickcluster;

/**
 * Created by Erik Ness at 3/23/15 4:39 PM
 *
 * These observers are notified every time the active dataset is changed.
 */
public interface DataSetChangeObserver
{
	public void update(GraphPanel newGraphPanel);
}
