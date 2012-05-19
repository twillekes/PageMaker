package com.twillekes.userInterface;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.userInteraction.EditProperties;
import com.twillekes.userInteraction.ExitApplication;
import com.twillekes.userInteraction.ExportRepository;
import com.twillekes.userInteraction.ImportFile;
import com.twillekes.userInteraction.SaveRepository;

public class ApplicationMenu {
	public Menu menuBar;
	public ApplicationMenu() {
		final Shell shell = Application.getShell();
	    menuBar = new Menu(shell, SWT.BAR);

	    // File menu **********************************************************
	    MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
	    fileItem.setText("File");
	    
	    Menu fileMenu = new Menu(menuBar);
	    fileItem.setMenu(fileMenu);
	    
	    MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
	    saveItem.setText("&Save");
	    saveItem.addSelectionListener(new SaveRepository());
	    
	    MenuItem exportItem = new MenuItem(fileMenu, SWT.NONE);
	    exportItem.setText("&Export");
	    exportItem.addSelectionListener(new ExportRepository());
	    
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    
	    MenuItem fileExitItem = new MenuItem(fileMenu, SWT.NONE);
	    fileExitItem.setText("E&xit");
	    fileExitItem.addSelectionListener(new ExitApplication());
	    
	    // Edit menu **********************************************************
	    MenuItem editItem = new MenuItem(menuBar, SWT.CASCADE);
	    editItem.setText("Edit");
	    
	    Menu editMenu = new Menu(menuBar);
	    editItem.setMenu(editMenu);
	    
	    MenuItem fileImportItem = new MenuItem(editMenu, SWT.NONE);
	    fileImportItem.setText("&Import");
	    fileImportItem.addSelectionListener(new ImportFile());
	    
	    MenuItem editPropertiesItem = new MenuItem(editMenu, SWT.NONE);
	    editPropertiesItem.setText("&Properties");
	    editPropertiesItem.addSelectionListener(new EditProperties());
	}
}
