package com.twillekes.userInteraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;
import com.twillekes.userInterface.Application;
import com.twillekes.userInterface.PictureUserInterface;

public class EditProperties implements SelectionListener, Observer {
	List<Picture> pictures;
	List<Picture> clones;
	@Override
	public void widgetSelected(SelectionEvent e) {
		pictures = Selection.instance().getPictures();
		if (pictures.size() == 0) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("You need to select some images first");
			mBox.open();
			return;
		}
		editProperties();
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	public void perform(Picture picture) {
		pictures = Selection.instance().getPictures();
		if (pictures.size() == 0) {
			pictures.add(picture);
		}
		editProperties();
	}
	private void editProperties() {
		clones = new ArrayList<Picture>();
		Iterator<Picture> it = pictures.iterator();
		while(it.hasNext()) {
			Picture pic = it.next();
			try {
				clones.add(pic.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		PictureUserInterface.create(clones, this);
	}
	@Override
	public void update(Observable o, Object arg) {
		Iterator<Picture> origIt = pictures.iterator();
		Iterator<Picture> clonIt = clones.iterator();
		while(origIt.hasNext() && clonIt.hasNext()) {
			Picture orig = origIt.next();
			Picture clon = clonIt.next();
			if (!orig.equals(clon)) {
				try {
					Repository.instance().replace(orig, clon);
					Application.getPortfolio().replace(orig, clon);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}