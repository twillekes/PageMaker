package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInteraction.ImportPicture;

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
	    
	    MenuItem fileImportItem = new MenuItem(fileMenu, SWT.NONE);
	    fileImportItem.setText("&Import");
	    fileImportItem.addSelectionListener(new ImportFileListener());
	    
	    MenuItem fileExitItem = new MenuItem(fileMenu, SWT.NONE);
	    fileExitItem.setText("E&xit");
	    
	    fileExitItem.addSelectionListener(new ExitListener());
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
}
