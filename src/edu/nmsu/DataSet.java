package edu.nmsu;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

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

    public int size()
    {
        return points.size();
    }

	public Point mean()
	{
		// For now, assume that no numbers will be big enough to overflow.
        double xTotal = 0;
        double yTotal = 0;
        
        for (Point p : this) {
            xTotal += p.asGrid().x;
            yTotal += p.asGrid().y;
        }

        return Point.fromGrid(xTotal / this.size(), yTotal / this.size());
	}

	public static DataSet fromFile(String path)
	{
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException ex) {
			throw new RuntimeException("Gotta have a valid dataset!", ex);
		}

		String contents = new String(encoded, StandardCharsets.US_ASCII);
		contents = contents.replaceAll("\\s+","");  // get rid of whitespace
		String[] tuples = contents.split(";");

		List<Point> points = Lists.newArrayList();
		for (String tuple : tuples) {
			String[] numbers = tuple.substring(1, tuple.length() - 1).split(",");
			Point point = Point.fromGrid(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
			points.add(point);
		}

		return new DataSet(points);
	}

	public static double score(Collection<DataSet> clusters)
	{
		// a good clustering minimizes within-cluster sum of squares, while maximizing the distance
		// between the clusters
		List<Point> means = Lists.newArrayList();
		for (DataSet clust : clusters) {
			means.add(clust.mean());
		}

		DataSet meansDataSet = new DataSet(means);
		// mean of the whole set of points
		Point totalMean = meansDataSet.mean();
		double betweenClusterError = 0;
		for (Point mean : meansDataSet) {
			Point.Pair gridMean = mean.asGrid();
			Point.Pair gridTotalMean = totalMean.asGrid();
			double error = Math.pow(gridMean.x - gridTotalMean.x, 2) + Math.pow(gridMean.y - gridTotalMean.y, 2);
			betweenClusterError += error;
		}

		List<Double> withinClusterErrors = Lists.newArrayList();
		for (DataSet clust : clusters) {
			withinClusterErrors.add(sumOfSquares(clust));
		}

		// now how do we come up with a score based on these? HMMMMMMMMMM
		return 0.5;
	}

	public static double sumOfSquares(DataSet cluster)
	{
		Point.Pair centroid = cluster.mean().asGrid();

		// iterate again now y'all! Use euclidean distance (though no square root, because we square it again for
		// squared error
		double errorSum = 0;
		for (Point p : cluster) {
			Point.Pair pair = p.asGrid();
			double sqError = Math.pow(pair.x - centroid.x, 2) + Math.pow(pair.y - centroid.y, 2);
			errorSum += sqError;
		}

		return errorSum;
	}

}
