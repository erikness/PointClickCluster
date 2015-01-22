package edu.nmsu;


import java.awt.*;
import javax.swing.*;

/**
 * Created by Erik Ness at 1/21/15 6:15 PM
 */
public class ToolsPanel extends JPanel
{
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, 500, 500);
	}
}
