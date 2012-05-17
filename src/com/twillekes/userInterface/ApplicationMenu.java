package com.twillekes.userInterface;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInteraction.ImportPicture;
import com.twillekes.userInteraction.SavePortfolio;
import com.twillekes.userInteraction.Selection;

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
	    saveItem.addSelectionListener(new SaveListener());
	    
	    MenuItem fileExitItem = new MenuItem(fileMenu, SWT.NONE);
	    fileExitItem.setText("E&xit");
	    fileExitItem.addSelectionListener(new ExitListener());
	    
	    // Edit menu **********************************************************
	    MenuItem editItem = new MenuItem(menuBar, SWT.CASCADE);
	    editItem.setText("Edit");
	    
	    Menu editMenu = new Menu(menuBar);
	    editItem.setMenu(editMenu);
	    
	    MenuItem fileImportItem = new MenuItem(editMenu, SWT.NONE);
	    fileImportItem.setText("&Import");
	    fileImportItem.addSelectionListener(new ImportFileListener());
	    
	    MenuItem editPropertiesItem = new MenuItem(editMenu, SWT.NONE);
	    editPropertiesItem.setText("&Properties");
	    editPropertiesItem.addSelectionListener(new EditPropertiesListener());
	}
	class ImportFileListener implements SelectionListener {
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
	class SaveListener implements SelectionListener {
    	@Override
		public void widgetSelected(SelectionEvent e) {
    		new SavePortfolio();
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}
	class ExitListener implements SelectionListener {
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
	class EditPropertiesListener implements SelectionListener {
    	@Override
		public void widgetSelected(SelectionEvent e) {
    		List<Picture> pics = Selection.instance.getPictures();
    		PortfolioUserInterface.create(pics);
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}
}
