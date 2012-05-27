package com.twillekes.userInteraction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.twillekes.repoExporter.SiteExporter;
import com.twillekes.repoExporter.SiteExporter.CompletionObserver;
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
				final PublishDialog publishDialog = new PublishDialog();
				siteExporter.export(password, publishDialog, new CompletionObserver(){
					@Override
					public void complete() {
						publishDialog.CanBeDisposed();
					}
				});
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
