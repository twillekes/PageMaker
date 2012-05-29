package com.twillekes.userInteraction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.json.Exporter;
import com.twillekes.portfolio.Repository;
import com.twillekes.userInterface.Application;

public class ExitApplication implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Application.getDisplay().asyncExec(new Runnable(){
			@Override
			public void run() {
				if (Repository.instance().getIsDirty()) {
					MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
					mBox.setMessage("There are unsaved changes.\nSave the changes before exiting?");
					int value = mBox.open();
					if (value == SWT.YES) {
						Exporter exporter = new Exporter();
						exporter.export();
					} else if (value == SWT.CANCEL) {
						return;
					}
				}
	    		Application.getShell().close();
			}
		});
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}