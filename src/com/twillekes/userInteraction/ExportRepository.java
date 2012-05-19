package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.jsonExporter.Exporter;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.Application;

public class ExportRepository implements SelectionListener  {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Exporter exporter = new Exporter();
		exporter.export();
		
		Portfolio portfolio = Application.getPortfolio();
		portfolio.findAllCategories();
		exporter.exportToJS(portfolio);
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
