package com.twillekes.userInteraction;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;
import com.twillekes.userInterface.swt.Application;
import com.twillekes.userInterface.swt.PictureUserInterface;

public class ImportPicture {
	static final String NEW_IMAGES_FOLDER = "newImages/";
	public ImportPicture(String filePath){
        String thumbPath = Picture.getThumbName(filePath);
        if (!(new File(thumbPath).isFile())) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("Could not find thumbnail file.\n\nThe file you choose should have a peer with \"_thumb.jpg\"");
			mBox.open();
			return;
        }
        
        // Copy the files into the "new" folder
        File pictureFile = new File(filePath);
        String fileName = pictureFile.getName();
        File destPictureFile = new File(Repository.BASE_PATH + NEW_IMAGES_FOLDER + fileName);
        File thumbFile = new File(thumbPath);
        String thumbFileName = thumbFile.getName();
        File destThumbFile = new File(Repository.BASE_PATH + NEW_IMAGES_FOLDER + thumbFileName);
        try {
			FileUtils.copyFile(pictureFile, destPictureFile);
			FileUtils.copyFile(thumbFile, destThumbFile);
		} catch (IOException e) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("Could not copy image files");
			mBox.open();
			return;
		}
        
        Picture pic = new Picture();
        pic.setLocalFilePath(NEW_IMAGES_FOLDER + fileName);
        pic.setFilePath(NEW_IMAGES_FOLDER + fileName); // URL is relative
        pic.getMetadata().setIsNew("1");
        Application.getPortfolio().addPicture(pic);
		
		Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new GridLayout());
		shell.setText("Edit Picture Metadata");
		new PictureUserInterface(shell, pic);
		shell.layout();
		shell.pack();
		shell.open();
	}
}
