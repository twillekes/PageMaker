package com.twillekes.userInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInteraction.EditProperties;
import com.twillekes.userInteraction.Selection;

public class CategoryUserInterface {
	private List<Picture> pictures;
	private boolean shown;
	private Group group;
	private Group previewGroup = null;
	private List<PreviewUserInterface> previewUserInterfaces;
	public CategoryUserInterface(final Composite parent, List<Picture> pictures, String categoryLabel) {
		this.pictures = pictures;
		this.shown = false;
		
		group = new Group(parent, SWT.NONE);
		group.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button catButton = new Button(group, SWT.PUSH);
		catButton.setText(categoryLabel);
		catButton.setAlignment(SWT.LEFT);
		catButton.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				if (shown) {
					previewGroup.dispose();
					Selection.instance().remove(previewUserInterfaces);
				} else {
					populatePreviews();
				}
				shown = !shown;
				group.layout();
				group.pack();
				parent.layout();
				parent.pack();
				Application.getShell().layout();
				Application.getShell().pack();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
	public void populatePreviews() {		
		previewGroup = new Group(group, SWT.SHADOW_ETCHED_IN);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 10;
		previewGroup.setLayout(groupLayout);
		//previewGroup.setText(categoryLabel);
		Iterator<Picture> it = pictures.iterator();
		previewUserInterfaces = new ArrayList<PreviewUserInterface>();
		while(it.hasNext()) {
			final Picture pic = it.next();
			final PreviewUserInterface prevUi = new PreviewUserInterface(previewGroup, pic, pic.getRepositoryThumbFilePath());
			previewUserInterfaces.add(prevUi);
			prevUi.setClickObserver(new PreviewUserInterface.ClickObserver() {
				@Override
				public void click(MouseEvent mouseEvent) {
					if (mouseEvent.button != 1) {
						(new EditProperties()).perform(pic);
					} else {
						prevUi.toggleSelected();
					}
				}
			});
		}
		//group.layout();
		//group.pack();
	}
}
