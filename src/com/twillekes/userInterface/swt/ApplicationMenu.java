package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class ApplicationMenu {
	public Menu menuBar;
	public ApplicationMenu() {
		final Display display = (Display)Application.getDevice();
		final Shell shell = Application.getShell();
	    menuBar = new Menu(shell, SWT.BAR);

	    MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
	    fileItem.setText("File");
	    
	    Menu fileMenu = new Menu(menuBar);
	    fileItem.setMenu(fileMenu);
	    
	    MenuItem fileExitItem = new MenuItem(fileMenu, SWT.NONE);
	    fileExitItem.setText("E&xit");
	    
	    fileExitItem.addSelectionListener(new SelectionListener(){
	    	@Override
			public void widgetSelected(SelectionEvent e) {
	    		display.asyncExec(new Runnable(){
					@Override
					public void run() {
			    		shell.close();
					}
	    		});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
	    });
	}
}
