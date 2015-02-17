package edu.nmsu;

/**
 * Created by Erik Ness at 1/21/15 6:02 PM
 */
public class Point
{
	public double x;
	public double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

    public static Point transform(Point p)
    {
        // Rotate-around-y matrix:
        // [1  0]
        // [0 -1]

        // Scale matrix, where r is pixel length / grid length
        // [r 0]
        // [0 r]

        return null;
    }
}
