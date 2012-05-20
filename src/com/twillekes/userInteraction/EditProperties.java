package com.twillekes.userInteraction;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInterface.Application;
import com.twillekes.userInterface.PortfolioUserInterface;

public class EditProperties implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		List<Picture> pics = Selection.instance.getPictures();
		if (pics.size() == 0) {
			MessageBox mBox = new MessageBox(Application.getShell(), SWT.ICON_ERROR | SWT.OK);
			mBox.setMessage("You need to select some images first");
			mBox.open();
			return;
		}
		PortfolioUserInterface.create(pics);
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}