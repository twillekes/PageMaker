package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.jsonExporter.Exporter;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.Application;


public class SaveRepository implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Portfolio portfolio = Application.getPortfolio();
		portfolio.findAllCategories();
		Exporter exporter = new Exporter();
		exporter.export();
		exporter.exportToJS(portfolio);
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}