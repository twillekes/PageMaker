package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.repoExporter.SiteExporter;

public class PublishRepository implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent e) {
		SiteExporter siteExporter = new SiteExporter();
		siteExporter.export("testpw");
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
