package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.json.Exporter;


public class SaveRepository implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Exporter exporter = new Exporter();
		exporter.saveRepository();
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}