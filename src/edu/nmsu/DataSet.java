package edu.nmsu;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Erik Ness at 1/21/15 5:55 PM
 *
 * For the purposes of this project, any data point can be represented as a point in R^2.
 */
public class DataSet implements Iterable<Point>
{
	private ImmutableList<Point> points;

	public DataSet(Collection<Point> points)
	{
		this.points = ImmutableList.copyOf(points);
	}

	public Iterator<Point> iterator()
	{
		return points.iterator();
	}

	public Point mean()
	{
		// TODO
		return null;
	}

}
