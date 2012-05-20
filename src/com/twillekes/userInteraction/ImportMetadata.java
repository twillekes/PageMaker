package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.Application;

public class ImportMetadata implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Importer importer = new Importer();
		Portfolio portfolio = importer.createPortfolioFromMetadata();
		Application.setPortfolio(portfolio);
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
