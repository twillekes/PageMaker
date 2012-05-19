package com.twillekes.userInterface;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.twillekes.portfolio.Picture;

public class CategoryUserInterface {
	public CategoryUserInterface(Composite parent, List<Picture> pictures, String categoryLabel) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 10;
		group.setLayout(groupLayout);
		group.setText(categoryLabel);
		Iterator<Picture> it = pictures.iterator();
		while(it.hasNext()) {
			final Picture pic = it.next();
			final PreviewUserInterface prevUi = new PreviewUserInterface(group, pic, pic.getRepositoryThumbFilePath());
			prevUi.setClickObserver(new PreviewUserInterface.ClickObserver() {
				@Override
				public void click(MouseEvent mouseEvent) {
					if (mouseEvent.button != 1) {
						PictureUserInterface.create(pic);
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
