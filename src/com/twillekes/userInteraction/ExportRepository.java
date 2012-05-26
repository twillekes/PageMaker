package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.json.Exporter;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.repoExporter.FileSystemExporter;
import com.twillekes.userInterface.Application;

public class ExportRepository implements SelectionListener  {
	@Override
	public void widgetSelected(SelectionEvent e) {
		Exporter exporter = new Exporter();
		exporter.export();
		
		Portfolio portfolio = Application.getPortfolio();
		portfolio.findAllCategories();
		exporter.exportToJS(portfolio);
		try {
			FileSystemExporter.export("export/");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		com.twillekes.xml.Exporter xmlExporter = new com.twillekes.xml.Exporter();
		xmlExporter.export(Application.getPortfolio());
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
