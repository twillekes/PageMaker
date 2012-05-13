package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInterface.swt.PortfolioUserInterface;

public class Application {
	private static Shell shell;
	private static Display display;
	public Application() {
        Display.setAppName("Page Maker");
        display = new Display();

        shell = new Shell(display, SWT.SHELL_TRIM);
//        GridLayout layout = new GridLayout();
//        layout.numColumns = 1;
        FillLayout layout = new FillLayout();
//        RowLayout layout = new RowLayout(SWT.VERTICAL);
        shell.setLayout(layout);
        shell.setText("Page Maker");

        //center(shell);

        Portfolio portfolio = new Portfolio();
		Importer importer = new Importer();
		importer.populateFromMasterList(portfolio);
		new PortfolioUserInterface(shell, portfolio);

		shell.layout();
		shell.pack();
        shell.open();
        
//        shell.addDragDetectListener(new DragDetectListener() {
//			@Override
//			public void dragDetected(DragDetectEvent e) {
//				System.out.println("Drag! "+e);
//			}
//        });
        DropTarget dt = new DropTarget(shell, DND.DROP_COPY);
        dt.addDropListener(new DropTargetListener(){
			@Override
			public void dragEnter(DropTargetEvent event) {
			}
			@Override
			public void dragLeave(DropTargetEvent event) {
			}
			@Override
			public void dragOperationChanged(DropTargetEvent event) {
			}
			@Override
			public void dragOver(DropTargetEvent event) {
			}
			@Override
			public void drop(DropTargetEvent event) {
				System.out.println("Drop! "+event);
			}
			@Override
			public void dropAccept(DropTargetEvent event) {
			}
        });
        
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
        
        shell.dispose();
        display.dispose();
    }

	public static Device getDevice() {
		return display;
	}
	public static Shell getShell() {
		return shell;
	}

    public void center(Shell shell) {

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }


    public static void main(String[] args) {
        new Application();
    }
}
