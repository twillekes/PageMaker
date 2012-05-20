package com.twillekes.userInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PasswordDialog {
	public interface Observer {
		public void PasswordReceived(String password);
		public void DialogCancelled();
	}
	public PasswordDialog(final Observer observer) {
		final Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		Group pwGroup = new Group(shell, SWT.NONE);
		pwGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
	    (new Label(pwGroup, SWT.NULL)).setText("Password: ");
	    final Text textPassword = new Text(pwGroup, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
	    textPassword.setEchoChar('*');
	    textPassword.addDisposeListener(new DisposeListener(){
			public void widgetDisposed(DisposeEvent e) {
				observer.DialogCancelled();
			}
	    });
	    Group butts = new Group(shell, SWT.NONE);
	    butts.setLayout(new RowLayout(SWT.HORIZONTAL));
	    final Button ok = new Button(butts, SWT.PUSH);
	    ok.setText("Ok");
	    ok.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
	    		observer.PasswordReceived(textPassword.getText());
	    	}
	    });
	    Button cancel = new Button(butts, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
	    		observer.DialogCancelled();
	    	}
	    });
	    shell.setDefaultButton(ok);
		shell.layout();
		shell.pack();
		shell.open();
	}
}
