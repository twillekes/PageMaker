package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.swt.PortfolioUserInterface;

public class Application {
	public Application(Display display) {
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setText("Page Maker");

        //center(shell);

        Portfolio portfolio = new Portfolio();
		Importer importer = new Importer();
		importer.populate(portfolio);
		new PortfolioUserInterface(display, shell, portfolio);

		shell.layout();
		shell.pack();
        shell.open();
        
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
        
        shell.dispose();
    }


    public void center(Shell shell) {

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }


    public static void main(String[] args) {
        Display.setAppName("Page Maker");
        Display display = new Display();
        new Application(display);
        display.dispose();
    }
}
