package edu.nmsu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Erik Ness at 3/25/15 4:51 PM
 */
public class FlipListener implements ActionListener
{
	public static int PREV = 0;
	public static int NEXT = 1;

	private int direction;
	private MetaGraphPanel metaGraphPanel;

	public FlipListener(int direction, MetaGraphPanel metaGraphPanel)
	{
		this.direction = direction;
		this.metaGraphPanel = metaGraphPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (direction == PREV) {
			metaGraphPanel.previous();
		} else if (direction == NEXT) {
			metaGraphPanel.next();
		}
	}
}
