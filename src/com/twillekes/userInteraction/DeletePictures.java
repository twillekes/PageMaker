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
				// TODO
				// Move pictures into their trashedPictures holder
				// Invoke moveToTrash on them, which
				//   Marks the picture as changed
				//   Notifies observers of the change (which the UI listens to)
				//   Removes the picture from the Porfolio
				// Note: Will want a "Empty trash" option
				// Note: Will need to serialize/deserialize the trash bin
				// Note: May want to have the ability to view the trash bin
				//       (Model it as a separate special repository? How to save it's origin repo? Via "account"?
				//        Need a special repo that has a new record type in its list.)
				Repository.moveToTrash(pics);
			}
		}
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}
