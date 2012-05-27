package com.twillekes.userInterface;


import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.userInteraction.EmptyTrash;
import com.twillekes.userInteraction.MovePicturesToTrash;
import com.twillekes.userInteraction.EditProperties;
import com.twillekes.userInteraction.ExitApplication;
import com.twillekes.userInteraction.GeneratePage;
import com.twillekes.userInteraction.ImportFile;
import com.twillekes.userInteraction.ImportMetadata;
import com.twillekes.userInteraction.ImportRepository;
import com.twillekes.userInteraction.PublishRepository;
import com.twillekes.userInteraction.SaveRepository;
import com.twillekes.userInteraction.Selection;
import com.twillekes.userInteraction.Trash;

public class ApplicationMenu implements Observer {
	public Menu menuBar;
	private MenuItem editPropertiesItem;
	private MenuItem deletePictureItem;
	private MenuItem emptyTrashItem;
	public ApplicationMenu() {
		final Shell shell = Application.getShell();
	    menuBar = new Menu(shell, SWT.BAR);

	    // File menu **********************************************************
	    MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
	    fileItem.setText("File");
	    
	    Menu fileMenu = new Menu(menuBar);
	    fileItem.setMenu(fileMenu);
	    
	    MenuItem importMetadataItem = new MenuItem(fileMenu, SWT.NONE);
	    importMetadataItem.setText("&Import from Metadata");
	    importMetadataItem.addSelectionListener(new ImportMetadata());
	    
	    MenuItem importRepositoryItem = new MenuItem(fileMenu, SWT.NONE);
	    importRepositoryItem.setText("Import from &Repository");
	    importRepositoryItem.addSelectionListener(new ImportRepository());
	    
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    
	    MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
	    saveItem.setText("&Save Repository");
	    saveItem.addSelectionListener(new SaveRepository());
	    
	    MenuItem exportItem = new MenuItem(fileMenu, SWT.NONE);
	    exportItem.setText("&Generate Page");
	    exportItem.addSelectionListener(new GeneratePage());
	    
	    MenuItem publishItem = new MenuItem(fileMenu, SWT.NONE);
	    publishItem.setText("&Publish to Web");
	    publishItem.addSelectionListener(new PublishRepository());
	    
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    
	    emptyTrashItem = new MenuItem(fileMenu, SWT.NONE);
	    emptyTrashItem.setText("&Empty the Trash");
	    emptyTrashItem.addSelectionListener(new EmptyTrash());
	    emptyTrashItem.setEnabled(Trash.instance().collect().size() > 0);
	    
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
	    fileImportItem.setText("&Import an Image");
	    fileImportItem.addSelectionListener(new ImportFile());
	    
	    new MenuItem(editMenu, SWT.SEPARATOR);
	    
	    editPropertiesItem = new MenuItem(editMenu, SWT.NONE);
	    editPropertiesItem.setText("Edit &Properties");
	    editPropertiesItem.addSelectionListener(new EditProperties());
	    editPropertiesItem.setEnabled(false);
	    
	    deletePictureItem = new MenuItem(editMenu, SWT.NONE);
	    deletePictureItem.setText("&Move Pictures to Trash");
	    deletePictureItem.addSelectionListener(new MovePicturesToTrash());
	    deletePictureItem.setEnabled(false);
	    
	    Selection.instance().addObserver(this);
	    Trash.instance().addObserver(this);
	}
	@Override
	public void update(Observable source, Object value) {
		boolean enabled = false;
		if ((Integer)value > 0) {
			enabled = true;
		}
		if (source == Selection.instance()) {
			editPropertiesItem.setEnabled(enabled);
			deletePictureItem.setEnabled(enabled);
		} else if (source == Trash.instance()) {
			emptyTrashItem.setEnabled(enabled);
		}
	}
}
