package com.twillekes.userInteraction;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInterface.Application;

public class MovePicturesToTrash implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		List<Picture> pics = Selection.instance().getPictures();
		if (pics.size() == 0) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("You need to select some images first");
			mBox.open();
		} else {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			mBox.setMessage("Are you sure you want to move the selected images to the trash bin?");
			int value = mBox.open();
			if (value == SWT.OK) {
				try {
					Trash.instance().moveToTrash(pics);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}
