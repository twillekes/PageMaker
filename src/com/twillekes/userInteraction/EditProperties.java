package com.twillekes.userInteraction;

import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInterface.PortfolioUserInterface;

public class EditProperties implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		List<Picture> pics = Selection.instance.getPictures();
		PortfolioUserInterface.create(pics);
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
}