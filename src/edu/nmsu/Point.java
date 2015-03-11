package edu.nmsu;

import java.awt.Color;

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
	public static Color DEFAULT_COLOR = Color.RED;
	public static final int RADIUS = 8;
    public static int pixelWidth;
    public static int pixelHeight;
    public static double gridXMax;
    public static double gridXMin;
    public static double gridYMax;
    public static double gridYMin;
    
    private Color background;

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

	public static Point fromGrid(double x, double y)
	{
		Point p = new Point();
		p.gridX = x;
		p.gridY = y;
		return p;
	}

	public static Point fromPixels(double x, double y)
	{
		Pair gridPair = gridFromPixels(x, y);
		Point p = new Point();
		p.gridX = gridPair.x;
		p.gridY = gridPair.y;
		return p;
	}

	private static Pair pixelsFromGrid(double gridX, double gridY)
	{
		double newX = gridX;
		double newY = gridY;
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
		newY = 0 * newX + yScale * newY;

		// If we were linear algebra purists, we'd do some added-dimension
		// transformation in order to translate, but here we'll just add.
		newX += pixelWidth / 2;
		newY += pixelHeight / 2;

		return new Pair(newX, newY);
	}

	private static Pair gridFromPixels(double gridX, double gridY)
	{
		// inverse of gridFromPixels()
		double newX = gridX;
		double newY = gridY;
		double xScale = pixelWidth / (gridXMax - gridXMin);
		double yScale = pixelHeight / (gridYMax - gridYMin);

		// translation
		newX -= pixelWidth / 2;
		newY -= pixelHeight / 2;

		// scaling
		newX = newX / xScale;
		newX = newY / yScale;

		// rotation
		newY = -1 * newY;

		return new Pair(newX, newY);
	}

    private double gridX;
    private double gridY;

    private Point() // I may not want a regular constructor...
    { 
    	background = DEFAULT_COLOR;
    }

    /**
     * For context-free operations where you only need two numbers.
     */

    public Pair asGrid()
    {
        return new Pair(gridX, gridY);
    }

    public Pair asPixels()
    {
        return pixelsFromGrid(this.gridX, this.gridY);
	}
	
	public String toString()
	{
		Pair px = pixelsFromGrid(gridX, gridY);
		return String.format("Point: on grid {%1$f, %2$f}, on the screen {%3$f, %4$f}",
			gridX, gridY, px.x, px.y);
	}
	
	public Color getColor()
	{
		return background;
	}
	
	public void setColor(Color c)
	{
		background = c;
	}
}
