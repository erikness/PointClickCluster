package edu.nmsu.erikness.pointclickcluster;

import com.google.common.collect.Lists;
import edu.nmsu.erikness.miningcommon.DataSet;
import edu.nmsu.erikness.miningcommon.Point;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

/**
 * Created by Erik Ness at 3/28/2015 12:47 AM
 */
public class DataSetWithScores extends DataSet
{
	private int intendedClusterNumber;
	private double highScore;
	private double lowScore;

	public DataSetWithScores(Collection<Point> points)
	{
		// -1 for any of these values means "I don't know" - usually, an entire dataset may have meta metrics
		// that we've calculated beforehand, but its subsets won't have them and won't need them.
		this(points, -1, -1, -1);
	}

	public DataSetWithScores(Collection<Point> points, int intendedClusterNumber, double highScore, double lowScore)
	{
		super(points);
		this.intendedClusterNumber = intendedClusterNumber;
		this.highScore = highScore;
		this.lowScore = lowScore;
	}
	public static DataSetWithScores fromStream(InputStream stream)
	{
		String line = null;
		StringBuilder all = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			while ((line = reader.readLine()) != null) {
				all.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return fromDirectContents(all.toString());
	}

	public static DataSetWithScores fromFile(File f)
	{
		return fromFile(f.getPath());
	}

	public static DataSetWithScores fromFile(String path)
	{
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException ex) {
			throw new RuntimeException("Gotta have a valid dataset! " + path, ex);
		}

		String contents = new String(encoded, StandardCharsets.US_ASCII);
		return fromDirectContents(contents);
	}

	public static DataSetWithScores fromDirectContents(String contents)
	{
		contents = contents.replaceAll("\\s+","");  // get rid of whitespace
		String[] sections = contents.split("\\$");
		String[] metaParts = sections[0].split(",");
		int intendedClusters = Integer.parseInt(metaParts[0]);
		double highScore = Double.parseDouble(metaParts[1]);
		double lowScore = Double.parseDouble(metaParts[2]);
		String[] tuples = sections[1].split(";");

		List<Point> points = Lists.newArrayList();
		for (String tuple : tuples) {
			String[] numbers = tuple.substring(1, tuple.length() - 1).split(",");
			Point point = Point.fromGrid(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
			points.add(point);
		}

		return new DataSetWithScores(points, intendedClusters, highScore, lowScore);
	}

	public static double score(Collection<DataSet> clusters, DataSetWithScores original)
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

		System.out.println(withinClusterErrors);
		System.out.println(withinClusterErrors.stream().mapToDouble(Double::doubleValue).sum());
		System.out.println(original.getHighScore());
		System.out.println(original.getLowScore());

		// now how do we come up with a score based on these? HMMMMMMMMMM
		// answer: use the highScore (which is really the lowest possible WCSS), scale it between the worst possible
		// clustering, and return the ratio. May have to re-represent in a log or exponential format.

		double withinClusterTotalError = withinClusterErrors.stream().mapToDouble(Double::doubleValue).sum();

		double inverseScore = (withinClusterTotalError - original.getHighScore()) /
				(original.getLowScore() - original.getHighScore());

		return 1 - inverseScore;
	}

	public int getIntendedClusterNumber()
	{
		return intendedClusterNumber;
	}

	public double getHighScore()
	{
		return highScore;
	}

	public double getLowScore()
	{
		return lowScore;
	}
}
