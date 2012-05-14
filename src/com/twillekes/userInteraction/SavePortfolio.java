package com.twillekes.userInteraction;

import com.twillekes.jsonExporter.Exporter;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.swt.Application;

public class SavePortfolio {
	public SavePortfolio() {
		Portfolio portfolio = Application.getPortfolio();
		portfolio.findAllCategories();
		Exporter exporter = new Exporter();
		exporter.export();
		exporter.exportToJS(portfolio);
	}
}
