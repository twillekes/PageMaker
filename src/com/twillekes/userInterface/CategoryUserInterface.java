package com.twillekes.userInterface;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.portfolio.Picture;

public class CategoryUserInterface {
	private class ClickObserver extends ClickListener {
		Picture picture;
		public ClickObserver(Picture picture) {
			this.picture = picture;
		}
		@Override
		public void click(MouseEvent e) {
			if (e.button != 1) {
				Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				shell.setLayout(new GridLayout());
				shell.setText("Edit Picture Metadata");
				new PictureUserInterface(shell, picture);
				shell.layout();
				shell.pack();
				shell.open();
			} else {
				this.getPreviewUserInterface().toggleSelected();
			}
		}
	}
	public CategoryUserInterface(Composite parent, List<Picture> pictures, String categoryValue) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 10;
		group.setLayout(groupLayout);
		group.setText(categoryValue + " (" + pictures.size() + " images)");
		Iterator<Picture> it = pictures.iterator();
		while(it.hasNext()) {
			Picture pic = it.next();
			new PreviewUserInterface(group, pic, pic.getThumbFilePath(), new ClickObserver(pic));
		}
		//group.layout();
		//group.pack();
	}
}
