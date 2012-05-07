package com.twillekes.userInterface.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import com.twillekes.portfolio.Metadata;
import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.swt.PictureUserInterface;

public class PortfolioUserInterface {
	Portfolio portfolio;
	public PortfolioUserInterface(Composite parent, Portfolio portfolio) {
		this.portfolio = portfolio;
		setupCategoryUserInterface("subject", parent);
		//setupFullPortfolioUserInterface(parent, portfolio);
	}
	public void setupCategoryUserInterface(String categorization, Composite parent) {
//		Group topLevelGroup = new Group(parent, SWT.NONE);
//		topLevelGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
			
		setupCategoryButtons(parent);
		
		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scroll.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Group group = new Group(scroll, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		group.setLayout(rowLayout);
		
		scroll.setContent(group);
		setupCategories(categorization, group);
				
		group.layout();
		group.pack();
		scroll.layout();
		scroll.pack();
//		topLevelGroup.layout();
//		topLevelGroup.pack();
//		System.out.println("V bar: "+group.getVerticalBar().toString());
	}
	public void setupCategoryButtons(Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		final Label label = new Label(group, SWT.NONE);
		label.setText("Categorization:");
		
		List<String> categories = Metadata.getCategories();
		Iterator<String> it = categories.iterator();
		while(it.hasNext()) {
			String category = it.next();
			Button butt = new Button(group, SWT.TOGGLE);
			butt.setText(category);
			butt.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					Control [] children = group.getChildren ();
					for (int i=0; i<children.length; i++) {
						Control child = children [i];
						if (event.widget != child && child instanceof Button && (child.getStyle () & SWT.TOGGLE) != 0) {
							((Button) child).setSelection (false);
						}
					}
					((Button)event.widget).setSelection(true);
					String category = ((Button)event.widget).getText();
				}
			});
			if (category.equals(categories.get(0))) {
				butt.setSelection(true);
			}
		}
//		group.layout();
//		group.pack();
	}
	public void setupCategories(String categorization, Composite parent) {
		List<String> catValues;
		try {
			catValues = Metadata.schema.getCategoryValues(categorization);
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
					catValue = pic.getMetadata().getCategoryValue(categorization);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if (catValue.equals(subject)) {
					list.add(pic);
				}
			}
			new CategoryUserInterface(parent, list, subject);
		}
	}
	public void setupFullPortfolioUserInterface(Composite parent) {
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
			new PictureUserInterface(portfolioGroup, pic);
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
