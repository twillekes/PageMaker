package com.twillekes.userInterface;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.portfolio.Metadata;
import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.portfolio.Repository;
import com.twillekes.repoExporter.FileSystemExporter;

public class PortfolioUserInterface {
	private Portfolio portfolio;
	private Group categoryGroup;
	private ScrolledComposite scroll;
	public PortfolioUserInterface(Composite parent, Portfolio portfolio) {
		this.portfolio = portfolio;
		setupCategoryUserInterface("subject", parent);
		//setupFullPortfolioUserInterface(parent, portfolio);
	}
	public void setupCategoryUserInterface(String categorization, Composite parent) {
		scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
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
		if (categorization.equals("repository")) {
			setupRepositoryCategories(categoryGroup);
		} else {
			setupCategories(categorization, categoryGroup);
		}
		categoryGroup.layout();
		categoryGroup.pack();
		Application.getShell().layout();
		Application.getShell().pack();
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
		Button repoButt = new Button(group, SWT.TOGGLE);
		repoButt.setText("repository");
		repoButt.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				buildCategoryUserInterface(((Button)event.widget).getText());
			}
		});
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
	public void setupRepositoryCategories(Composite parent) {
		List<Repository> repos = Repository.get();
		Iterator<Repository> it = repos.iterator();
		while(it.hasNext()) {
			Repository repo = it.next();
			new CategoryUserInterface(parent, repo.getPictures(), repo.getPath() + " (" + FileSystemExporter.getPercent(repo) + "% of 20Meg)");
		}
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
			String subject = subjectIt.next();
			List<Picture> list = portfolio.getPictures(categorization, subject);
			new CategoryUserInterface(parent, list, subject + " (" + list.size() + " images)");
		}
	}
	public static Composite create(List<Picture> pictures) {
		Rectangle rect = Application.getShell().getBounds();
		final Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new GridLayout());
		shell.setText("Edit Picture Metadata");
		
	    final Button done = new Button(shell, SWT.PUSH);
	    done.setText("Done");
	    done.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
	    		shell.close();
	    	}
	    });
	    shell.setDefaultButton(done);

		ScrolledComposite portfolioScroll = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		portfolioScroll.setLayout(new FillLayout());

		Group portfolioGroup = new Group(portfolioScroll, SWT.SHADOW_ETCHED_OUT);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		portfolioGroup.setLayout(rowLayout);

		portfolioScroll.setContent(portfolioGroup);

		Iterator<Picture> it = pictures.iterator();
		int count = 0;
		while(it.hasNext()) {
			Picture pic = it.next();
			new PictureUserInterface(portfolioGroup, pic);
			if (count++ == 10) {
				break;
			}
		}
		portfolioGroup.layout();
		portfolioGroup.pack();
		portfolioScroll.layout();
		portfolioScroll.pack();
		shell.layout();
		shell.pack();
		Rectangle pRect = portfolioScroll.getBounds();
		final int HEIGHT_OFFSET = 100;
		if (pRect.height > rect.height-HEIGHT_OFFSET) {
			GridData layoutData = new GridData();
			layoutData.heightHint = rect.height - HEIGHT_OFFSET;
			portfolioScroll.setLayoutData(layoutData);
			portfolioScroll.layout();
			shell.pack();
		}
		shell.open();
		return shell;
	}
	public void dispose() {
		scroll.dispose();
	}
}
