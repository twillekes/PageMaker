package com.twillekes.userInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.twillekes.repoExporter.SiteExporter.Logger;

public class PublishDialog implements Logger {
	private Text text;
	private Group group;
	private Shell shell;
	@Override
	public void log(String message) {
		if (text.isDisposed()) {
			return;
		}
		text.append(message + "\n");
	}
	public PublishDialog() {
		shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new FillLayout());
		
		group = new Group(shell, SWT.NONE);
		group.setLayout(new RowLayout(SWT.VERTICAL));
		
		text = new Text(group, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		RowData layoutData = new RowData();
		layoutData.height = 260;
		layoutData.width = 800;
		text.setLayoutData(layoutData);

		shell.layout();
		shell.pack();
		shell.open();
	}
	public void CanBeDisposed() {
		if (shell.isDisposed()) {
			return;
		}
	    final Button done = new Button(group, SWT.PUSH);
	    done.setText("Done");
	    done.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
	    		shell.close();
	    	}
	    });
		shell.layout();
		shell.pack();
	    shell.setDefaultButton(done);
	}
}
