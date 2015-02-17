package edu.nmsu;

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
	{
		JFrame applicationFrame = new JFrame();
		applicationFrame.setSize(new Dimension(600, 500));
		applicationFrame.setVisible(true);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Put the application in the center of the screen */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		applicationFrame.setLocation(
				screenSize.width / 2 - applicationFrame.getSize().width / 2,
				screenSize.height / 2 - applicationFrame.getSize().height / 2);

		JPanel toolsPanel = new ToolsPanel();
        applicationFrame.getContentPane().add(BorderLayout.CENTER, toolsPanel);
        toolsPanel.setPreferredSize(new Dimension(100, 500));

        //applicationFrame.getContentPane().add(BorderLayout.EAST, new JButton("Y'ALL!"));

        JPanel graphPanel = new GraphPanel();
		graphPanel.setPreferredSize(new Dimension(500, 500));
		applicationFrame.getContentPane().add(BorderLayout.EAST, graphPanel);

	}
}
