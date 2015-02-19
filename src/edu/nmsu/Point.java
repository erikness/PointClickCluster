package edu.nmsu;

/**
 * Created by Erik Ness at 1/21/15 6:02 PM
 * 
 * A Point represents two things simultaneously:
 * - A pixel location relative to the top left corner of the program window
 * - A coordinate relative to the coordinate system drawn on the program.
 * 
 * Behind the scenes, this stores the point's position on the logical grid,
 * and calculates the pixel coordinates on demand (using the static variables
 * given to the Point class).
 */
public class Point
{
    private static int pixelWidth;
    private static int pixelHeight;
    private static double gridXMax;
    private static double gridXMin;
    private static double gridYMax;
    private static double gridYMin;
    private static Point center;

    private double gridX;
    private double gridY;

	/*public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}*/

    private Point() {}  // I may not want a regular constructor...

    public static Point fromGrid(double x, double y)
    {
        return null;   
    }

    public static Point fromPixels(double x, double y)
    {
        return null;
    }


    /**
     * For context-free operations where you only need two numbers.
     */
    public static class Pair
    {
        public double x;
        public double y;
        
        public Pair(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
    }

    public Pair asGrid()
    {
        return Pair(gridX, gridY);
    }

    public Pair asPixels()
    {
        return gridFromPixels();
    }

    private Pair gridFromPixels()
    {
        newX = gridX;
        newY = gridY;
        double xScale = pixelWidth / (gridXMax - gridXMin);
        double yScale = pixelHeight / (gridYMax - gridYMin);
        // Rotate-around-y matrix:
        // [1  0]
        // [0 -1]
        newX = 1 * newX + 0 * newY;
        newY = 0 * newX + -1 * newY;

        // Scale matrix, where r is pixel length / grid length
        // [xScale 0]
        // [0      yScale]
        newX = xScale * newX + 0 * newY;
        newX = 0 * newX + yScale * newY;
        
        // If we were linear algebra purists, we'd do some added-dimension
        // transformation in order to translate, but here we'll just add.
        Pair centerPixels = center.asPixels();
        newX += centerPixels.x;
        newY += centerPixels.y;

        return Pair(newX, newY);
    }
}
