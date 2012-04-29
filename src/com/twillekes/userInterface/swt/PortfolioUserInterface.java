package com.twillekes.userInterface.swt;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.swt.PictureUserInterface;

public class PortfolioUserInterface {
	private Group portfolioGroup;
	private ScrolledComposite portfolioScroll;
	public PortfolioUserInterface(Device device, Composite parent, Portfolio portfolio) {
		portfolioScroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		//portfolioScroll.setLayout(rowLayout);
//		portfolioScroll.setExpandVertical(true);
//		portfolioScroll.setExpandHorizontal(true);

		portfolioGroup = new Group(portfolioScroll, SWT.SHADOW_ETCHED_OUT);
//		portfolioGroup.setText("portfolioGroup");
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		//rowLayout.wrap = false;
		portfolioGroup.setLayout(rowLayout);
		//portfolioGroup.setSize(250,250);

		portfolioScroll.setContent(portfolioGroup);
		//portfolioScroll.setSize(250,250);

		//		Picture picture = portfolio.getPictures().first();
//		new PictureUserInterface(device, this.portfolioGroup, picture);
		Iterator<Picture> it = portfolio.getPictures().iterator();
		int count = 0;
		while(it.hasNext()) {
			Picture pic = it.next();
			//PictureUserInterface picUi = 
			new PictureUserInterface(device, portfolioGroup, pic);
			if (count++ == 20) {
				break;
			}
		}
		portfolioGroup.layout();
		portfolioGroup.pack();
		System.out.println("Portfolio group width is "+portfolioGroup.getBounds().width+" height is "+portfolioGroup.getBounds().height);
		portfolioScroll.layout();
		portfolioScroll.pack();
		System.out.println("Portfolio scroll width is "+portfolioScroll.getBounds().width+" height is "+portfolioScroll.getBounds().height);
	}

}
