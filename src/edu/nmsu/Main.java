package edu.nmsu;

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
	{
		int appHeight = 500;
		int toolsWidth = 100;
		int appWidth = appHeight + toolsWidth;

		JFrame applicationFrame = new JFrame();
		applicationFrame.setSize(new Dimension(appWidth, appHeight));
		applicationFrame.setVisible(true);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Put the application in the center of the screen */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		applicationFrame.setLocation(
				screenSize.width / 2 - applicationFrame.getSize().width / 2,
				screenSize.height / 2 - applicationFrame.getSize().height / 2);

		JPanel toolsPanel = new ToolsPanel();
        applicationFrame.getContentPane().add(BorderLayout.CENTER, toolsPanel);
        toolsPanel.setPreferredSize(new Dimension(toolsWidth, appHeight));

        JPanel graphPanel = new GraphPanel();
		graphPanel.setPreferredSize(new Dimension(appWidth - toolsWidth, appHeight));
		applicationFrame.getContentPane().add(BorderLayout.EAST, graphPanel);

	}
}
