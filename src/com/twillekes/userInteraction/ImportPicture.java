package com.twillekes.userInteraction;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Folder;
import com.twillekes.portfolio.Repository;
import com.twillekes.repoExporter.FileSystemExporter;
import com.twillekes.userInterface.Application;
import com.twillekes.userInterface.PictureUserInterface;
import com.twillekes.userInterface.PictureUserInterface.PictureEditDelegate;

public class ImportPicture implements PictureEditDelegate {
	static final String NEW_IMAGES_FOLDER = "newImages/";
	static private Picture templatePicture = null;
	public ImportPicture(String filePath){
		try {
			FileSystemExporter.copyOrMoveFiles(filePath, Repository.instance().getBasePath(), false);
		} catch (IOException e4) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("Could not copy image files");
			mBox.open();
			return;
		} catch (Exception e3) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("Could not find thumbnail file.\n\nThe file you choose should have a peer with \"_thumb.jpg\"");
			mBox.open();
			return;
		}
        File pictureFile = new File(filePath);
        String fileName = pictureFile.getName();
        
        final Picture pic;
        if (templatePicture == null) {
        	pic = new Picture();
        } else {
        	try {
				pic = templatePicture.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return;
			}
        }
        Folder folder;
		try {
			folder = Repository.instance().getFolder("newImages");
	        folder.add(pic);
			pic.setFileName(fileName);
		} catch (Exception e2) {
			e2.printStackTrace();
			return;
		}
        pic.getMetadata().setIsNew("1");
        pic.getMetadata().setIsInFeed("1");
        try {
			Application.getPortfolio().addPicture(pic);
		} catch (Exception e2) {
			e2.printStackTrace();
			return;
		}
        new PictureUserInterface(pic, this);
	}
	@Override
	public boolean hasPrevious() {
		return false;
	}
	@Override
	public boolean hasNext() {
		return false;
	}
	@Override
	public Picture getPrevious() throws Exception {
		return null;
	}
	@Override
	public Picture getNext() throws Exception {
		return null;
	}
	@Override
	public void done() {
	}
}
