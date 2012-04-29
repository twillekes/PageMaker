package com.twillekes.userInterface;

import java.awt.Panel;
import java.awt.ScrollPane;
import java.util.Iterator;

import javax.swing.BoxLayout;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;

public class PortfolioUserInterface extends ScrollPane {
	private static final long serialVersionUID = 1L;
	private Panel middlePanel;
	
	public PortfolioUserInterface(Portfolio portfolio) {
		super();
		
		this.setSize(600, 400);
		
		middlePanel = new Panel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		
		Iterator<Picture> it = portfolio.getPictures().iterator();
		while(it.hasNext()) {
			Picture pic = it.next();
			PictureUserInterface picUi = new PictureUserInterface(pic);
			middlePanel.add(picUi);
		}
		middlePanel.doLayout();
		this.add(middlePanel);
		this.doLayout();
	}
}
