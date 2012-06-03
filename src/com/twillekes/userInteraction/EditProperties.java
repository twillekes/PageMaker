package com.twillekes.userInteraction;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;
import com.twillekes.userInterface.Application;
import com.twillekes.userInterface.PictureUserInterface;
import com.twillekes.userInterface.PictureUserInterface.PictureEditDelegate;

public class EditProperties implements SelectionListener, PictureEditDelegate {
	private Picture currentPicture;
	private Picture currentClone;
	private List<Picture> pictures;
	@Override
	public void widgetSelected(SelectionEvent e) {
		pictures = Selection.instance().getPictures();
		if (pictures.size() == 0) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("You need to select some images first");
			mBox.open();
			return;
		}
		currentPicture = pictures.iterator().next();
		editProperties();
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	public void perform(Picture picture, List<Picture> pictureList) {
		currentPicture = picture;
		pictures = pictureList;
		editProperties();
	}
	private void editProperties() {
		new PictureUserInterface(cloneCurrent(), this);
	}
	private Picture cloneCurrent() {
		try {
			currentClone = currentPicture.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		return currentClone;
	}
	public void completeEditing() {
		if (!currentPicture.equals(currentClone)) {
			try {
				Repository.instance().replace(currentPicture, currentClone);
				Application.getPortfolio().replace(currentPicture, currentClone);
				int idx = this.pictures.indexOf(currentPicture);
				if (!this.pictures.remove(currentPicture)) {
					throw new Exception("Unable to remove " + currentPicture.getRepositoryFilePath());
				}
				this.pictures.add(idx, currentClone);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public boolean hasPrevious() {
		int currentIndex = pictures.indexOf(currentPicture);
		if (currentIndex > 0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean hasNext() {
		int currentIndex = pictures.indexOf(currentPicture);
		if (currentIndex < (pictures.size()-1)) {
			return true;
		}
		return false;
	}
	@Override
	public Picture getPrevious() throws Exception {
		completeEditing();
		if (!hasPrevious()) {
			throw new Exception("No previous element");
		}
		currentPicture = pictures.get(pictures.indexOf(currentPicture)-1);
		return cloneCurrent();
	}
	@Override
	public Picture getNext() throws Exception {
		completeEditing();
		if (!hasNext()) {
			throw new Exception("No next element");
		}
		currentPicture = pictures.get(pictures.indexOf(currentPicture)+1);
		return cloneCurrent();
	}
	@Override
	public void done() {
		completeEditing();
	}
}