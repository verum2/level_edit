package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;

	public EditorPanel()
	{
		super(new BorderLayout());
		setMaximumSize(new Dimension(320,10000));
		setPreferredSize(new Dimension(320,480));
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	    add(scrollpane, BorderLayout.CENTER);
	}

	public boolean load()
	{
		image = new BufferedImage(320, 480, BufferedImage.TYPE_INT_RGB);
		for( int i = 0; i < 320; i++){
			for( int j = 0; j < 480; j++){
				image.setRGB(i, j, Color.red.getRGB());
			}
		}
		repaint();
		return true;
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		
		if( null == image)
			return;
		g.drawImage(image,0,0,320,480,this);
	}
	
	
}
