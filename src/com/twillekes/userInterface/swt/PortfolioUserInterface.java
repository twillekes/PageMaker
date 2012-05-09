package com.twillekes.userInterface.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
	Group categoryGroup;
	public PortfolioUserInterface(Composite parent, Portfolio portfolio) {
		this.portfolio = portfolio;
		setupCategoryUserInterface("subject", parent);
		//setupFullPortfolioUserInterface(parent, portfolio);
	}
	public void setupCategoryUserInterface(String categorization, Composite parent) {
		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scroll.setLayout(new FillLayout());
		categoryGroup = new Group(scroll, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		categoryGroup.setLayout(rowLayout);
		scroll.setContent(categoryGroup);
		setupCategoryButtons(categorization, categoryGroup);
		buildCategoryUserInterface(categorization);
		scroll.layout();
		scroll.pack();
	}
	public void buildCategoryUserInterface(String categorization) {
		Control [] children = categoryGroup.getChildren ();
		for (int i=1; i<children.length; i++) {
			Control child = children [i];
			child.dispose();
		}
		children = ((Group)children[0]).getChildren();
		for (int i=0; i<children.length; i++) {
			Control child = children [i];
			if (child instanceof Button && (child.getStyle () & SWT.TOGGLE) != 0) {
				if (((Button)child).getText().equals(categorization)) {
					((Button)child).setSelection(true);
				} else {
					((Button)child).setSelection(false);
				}
			}
		}
		setupCategories(categorization, categoryGroup);
		categoryGroup.layout();
		categoryGroup.pack();
	}
	public void setupCategoryButtons(String categorization, Composite parent) {
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
					buildCategoryUserInterface(((Button)event.widget).getText());
				}
			});
			if (category.equals(categorization)) {
				butt.setSelection(true);
			}
		}
		final Combo combo = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		List<String> flagCategories = Metadata.getFlagCategories();
		combo.setItems(flagCategories.toArray(new String[flagCategories.size()]));
		combo.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				// Need to send an event...
				buildCategoryUserInterface(combo.getItem(combo.getSelectionIndex()));
			}
		});
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
				if (catValue != null && catValue.equals(subject)) {
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
