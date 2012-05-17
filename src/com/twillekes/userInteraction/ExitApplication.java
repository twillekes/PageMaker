package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.userInterface.Application;

public class ExitApplication implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Application.getDisplay().asyncExec(new Runnable(){
			@Override
			public void run() {
	    		Application.getShell().close();
			}
		});
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}