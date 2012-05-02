package com.twillekes.userInterface.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.twillekes.portfolio.Metadata;
import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.swt.PictureUserInterface;

public class PortfolioUserInterface {
	public PortfolioUserInterface(Device device, Composite parent, Portfolio portfolio) {
		setupCategoryUserInterface("subject", device, parent, portfolio);
	}
	public void setupCategoryUserInterface(String category, Device device, Composite parent, Portfolio portfolio) {
		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		Group group = new Group(scroll, SWT.HORIZONTAL);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.wrap = true;
		group.setLayout(rowLayout);
		
//		RowData layoutData = new RowData();
//		layoutData.width = 400;
//		layoutData.height = 130;
//		group.setLayoutData(layoutData);
		
		scroll.setContent(group);
		
		List<String> catValues;
		try {
			catValues = Metadata.schema.getCategoryValues(category);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		Iterator<String> subjectIt = catValues.iterator();
		while(subjectIt.hasNext()) {
			List<Picture> list = new ArrayList<Picture>();
			String subject = subjectIt.next();
			Iterator<Picture> picsIt = portfolio.getPictures().iterator();
			while(picsIt.hasNext()) {
				Picture pic = picsIt.next();
				String catValue = "";;
				try {
					catValue = pic.getMetadata().getCategoryValue(category);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if (catValue.equals(subject)) {
					list.add(pic);
				}
			}
			new CategoryUserInterface(device, group, list, subject);
		}
		group.layout();
		group.pack();
		scroll.layout();
		scroll.pack();
	}
	public void FullPortfolioUserInterface(Device device, Composite parent, Portfolio portfolio) {
		ScrolledComposite portfolioScroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		Group portfolioGroup = new Group(portfolioScroll, SWT.SHADOW_ETCHED_OUT);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		//rowLayout.wrap = false;
		portfolioGroup.setLayout(rowLayout);

		portfolioScroll.setContent(portfolioGroup);

		Iterator<Picture> it = portfolio.getPictures().iterator();
		int count = 0;
		while(it.hasNext()) {
			Picture pic = it.next();
			//PictureUserInterface picUi = 
			new PictureUserInterface(device, portfolioGroup, pic);
			if (count++ == 10) {
				break;
			}
		}
		portfolioGroup.layout();
		portfolioGroup.pack();
		portfolioScroll.layout();
		portfolioScroll.pack();
	}

}
