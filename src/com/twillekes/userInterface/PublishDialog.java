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
	@Override
	public void log(String message) {
		text.append(message + "\n");
	}
	public PublishDialog() {
		final Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new FillLayout());
		
		Group group = new Group(shell, SWT.NONE);
		group.setLayout(new RowLayout(SWT.VERTICAL));
		
		text = new Text(group, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		RowData layoutData = new RowData();
		layoutData.height = 260;
		layoutData.width = 260;
		text.setLayoutData(layoutData);
		
	    final Button done = new Button(group, SWT.PUSH);
	    done.setText("Done");
	    done.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
	    		shell.close();
	    	}
	    });
	    shell.setDefaultButton(done);
	    shell.setSize(300,360);
		shell.layout();
		shell.pack();
		shell.open();
	}
}
