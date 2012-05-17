package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;


public class SaveRepository implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		new SavePortfolio();
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}