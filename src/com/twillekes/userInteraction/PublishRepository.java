package com.twillekes.userInteraction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.twillekes.repoExporter.SiteExporter;
import com.twillekes.userInterface.Application;

public class PublishRepository implements SelectionListener {
	String pass;
	@Override
	public void widgetSelected(SelectionEvent e) {
		Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new GridLayout());
	    (new Label(shell, SWT.NULL)).setText("Password: ");
	    Text textPassword = new Text(shell, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
	    textPassword.setEchoChar('*');
	    textPassword.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				pass = ((Text)e.widget).getText();
			}
	    });
	    textPassword.addDisposeListener(new DisposeListener(){
			@Override
			public void widgetDisposed(DisposeEvent e) {
				SiteExporter siteExporter = new SiteExporter();
//				siteExporter.export("testpw");
				siteExporter.test(pass);
			}
	    });
		shell.layout();
		shell.pack();
		shell.open();
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
