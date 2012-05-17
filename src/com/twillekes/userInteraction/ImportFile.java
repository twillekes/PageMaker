package com.twillekes.userInteraction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

import com.twillekes.userInterface.Application;

public class ImportFile implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
        FileDialog fd = new FileDialog(Application.getShell(), SWT.OPEN);
        fd.setText("Open");
        String[] filterExt = { "*.jpg", "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
		new ImportPicture(selected);
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}