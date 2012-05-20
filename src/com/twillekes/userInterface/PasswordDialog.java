package com.twillekes.userInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PasswordDialog {
	private String pass;
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
	    Text textPassword = new Text(pwGroup, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
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
				observer.DialogCancelled();
			}
	    });
	    Group butts = new Group(shell, SWT.NONE);
	    butts.setLayout(new RowLayout(SWT.HORIZONTAL));
	    final Button ok = new Button(butts, SWT.PUSH);
	    ok.setText("Ok");
	    Button cancel = new Button(butts, SWT.PUSH);
	    cancel.setText("Cancel");
	    Listener listener = new Listener() {
	      public void handleEvent(Event event) {
	        if (event.widget == ok) {
	        	observer.PasswordReceived(pass);
	        } else {
	        	observer.DialogCancelled();
	        }
	        shell.close();
	      }
	    };
	    ok.addListener(SWT.Selection, listener);
	    cancel.addListener(SWT.Selection, listener);
	    shell.setDefaultButton(ok);
		shell.layout();
		shell.pack();
		shell.open();
	}
}
