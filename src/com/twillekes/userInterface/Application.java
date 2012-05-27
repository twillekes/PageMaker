package com.twillekes.userInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.json.Importer;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.userInteraction.Selection;

// Need to run JAR as:
// java -XstartOnFirstThread -jar PageMaker.jar

// TODO: Add ability to delete old images
// TODO: Application icon/tray icon
// TODO: Package as application
// TODO: Ability to auto-FTP page template files
// TODO: Drag and drop images in/out
// TODO: Drag and drop images between categories
// TODO: See how windows behaves
// TODO: Automatic load balancer across web repositories
// TODO: Edit common properties between selected images
// TODO: Tags and tag clouds
// TODO: Undo/redo
// TODO: Preview page in an embedded browser
public class Application {
	private static Shell shell;
	private static Display display;
	private static Portfolio portfolio;
	private static PortfolioUserInterface portfolioUi;
	public Application() {
        Display.setAppName("Page Maker");
        Display.setAppVersion("Alpha.0");
        display = new Display();
        
//        image = new Image(display, BalloonExample.class.getResourceAsStream("tray_icon.gif"));
//		 Tray tray = display.getSystemTray();
//		 if(tray != null) {
//		 	TrayItem trayItem = new TrayItem(tray, SWT.NONE);
//		 	trayItem.setImage(image);
//		 }
        shell = new Shell(display, SWT.SHELL_TRIM);
        FillLayout layout = new FillLayout();
        shell.setLayout(layout);
        shell.setText("Page Maker");
        
        ApplicationMenu applicationMenu = new ApplicationMenu();
        new Selection();

        //center(shell);

		Importer importer = new Importer();
		portfolio = importer.createPortfolioFromRepository();
        //portfolio = importer.createPortfolioFromMetadata();
		portfolioUi = new PortfolioUserInterface(shell, portfolio);

        shell.setMenuBar(applicationMenu.menuBar);
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
	public static Display getDisplay() {
		return display;
	}
	public static Device getDevice() {
		return display;
	}
	public static Shell getShell() {
		return shell;
	}
	public static Portfolio getPortfolio() {
		return portfolio;
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
	public static void setPortfolio(Portfolio port) {
		portfolioUi.dispose();
		portfolio = port;
		portfolioUi = new PortfolioUserInterface(shell, portfolio);
		shell.layout();
		shell.pack();
	}
}
