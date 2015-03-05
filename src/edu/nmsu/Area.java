package edu.nmsu;

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
}
