package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.json.Importer;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.Application;

public class ImportRepository implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Importer importer = new Importer();
		Portfolio portfolio = importer.createPortfolioFromRepository();
		Application.setPortfolio(portfolio);
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
