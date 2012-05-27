package com.twillekes.userInteraction;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;
import com.twillekes.userInterface.Application;

public class DeletePictures implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		List<Picture> pics = Selection.instance.getPictures();
		if (pics.size() == 0) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("You need to select some images first");
			mBox.open();
		} else {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			mBox.setMessage("Are you sure you want to permanently delete the selected images?");
			int value = mBox.open();
			if (value == SWT.OK) {
				Repository.deletePictures(pics);
			}
		}
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}
