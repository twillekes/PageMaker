package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.repoExporter.SiteExporter;
import com.twillekes.userInterface.PasswordDialog;
import com.twillekes.userInterface.PublishDialog;

public class PublishRepository implements SelectionListener {
	String pass;
	@Override
	public void widgetSelected(SelectionEvent e) {
		new PasswordDialog(new PasswordDialog.Observer() {
			@Override
			public void PasswordReceived(String password) {
				SiteExporter siteExporter = new SiteExporter();
				siteExporter.export(password, new PublishDialog());
//				siteExporter.test(password, new PublishDialog());
			}
			@Override
			public void DialogCancelled() {
			}
		});
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
