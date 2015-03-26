package edu.nmsu.erikness.miningcommon;

import edu.nmsu.erikness.miningcommon.Point;

public class Area
{
	public int top;
	public int bottom;
	public int left;
	public int right;
	
	public Area(int top, int bottom, int left, int right)
	{
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}
	
	public boolean withinBounds(Point.Pair pixelCoords)
	{
		return pixelCoords.y >= top &&
			pixelCoords.y <= bottom &&
			pixelCoords.x >= left &&
			pixelCoords.x <= right;
	}
	
	public String toString()
	{
		return String.format("Area: vertical [%1$d, %2$d], horizontal [%3$d, %4$d]",
			top, bottom, left, right);
	}
}
