package com.twillekes.userInteraction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.json.Exporter;
import com.twillekes.userInterface.Application;

public class EmptyTrash implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
		mBox.setMessage("Are you sure you want to permanently delete all the pictures in the trash bin?\nThis will export the repository to disk.");
		int value = mBox.open();
		if (value == SWT.OK) {
			Trash.instance().empty();
			Exporter exporter = new Exporter();
			exporter.saveRepository();
		}
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}
