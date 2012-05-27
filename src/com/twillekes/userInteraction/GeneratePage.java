package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.json.Exporter;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.Application;

public class GeneratePage implements SelectionListener  {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Portfolio portfolio = Application.getPortfolio();
		portfolio.findAllCategories();
		Exporter exporter = new Exporter();
		exporter.exportToJS(portfolio);
		com.twillekes.xml.Exporter xmlExporter = new com.twillekes.xml.Exporter();
		xmlExporter.export(Application.getPortfolio());
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
