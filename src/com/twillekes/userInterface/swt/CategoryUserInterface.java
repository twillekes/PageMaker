package com.twillekes.userInterface.swt;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import com.twillekes.portfolio.Picture;

public class CategoryUserInterface {
	public CategoryUserInterface(Composite parent, List<Picture> pictures, String categoryValue) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 10;
		group.setLayout(groupLayout);
		group.setText(categoryValue + " (" + pictures.size() + " images)");
		Iterator<Picture> it = pictures.iterator();
		while(it.hasNext()) {
			Picture pic = it.next();
			new PreviewUserInterface(group, pic);
		}
		//group.layout();
		//group.pack();
	}
}
