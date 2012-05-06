package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Picture;

public class PreviewUserInterface {
	private class ClickHandler implements MouseListener {
		Picture picture;
		public ClickHandler(Picture picture) {
			this.picture = picture;
		}
		@Override
		public void mouseDoubleClick(MouseEvent e) {
		}
		@Override
		public void mouseDown(MouseEvent e) {
			Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			shell.setLayout(new GridLayout());
			System.out.println("Button is "+e.button);
			if (e.button != 1) {
				shell.setText("Edit Picture Metadata");
				new PictureUserInterface(shell, picture);
			} else {
				shell.setText("View Image");
				new PreviewUserInterface(shell, picture, false);
			}
			shell.layout();
			shell.pack();
			shell.open();
		}
		@Override
		public void mouseUp(MouseEvent e) {
		}
	}
	private Image previewImage;
	private static int WIDTH = 125;
	private static int HEIGHT = 125;
	public PreviewUserInterface(Composite parent, Picture picture) {
		createPreviewUserInterface(parent, picture, true);
	}
	public PreviewUserInterface(Composite parent, Picture picture, boolean preview) {
		createPreviewUserInterface(parent, picture, preview);
	}
	public void createPreviewUserInterface(Composite parent, Picture picture, boolean preview) {
		String filePath = picture.getLocalFilePath();
		if (preview) {
			int dotPos = filePath.lastIndexOf(".");
			String thumbExt = filePath.substring(dotPos);
			filePath = filePath.substring(0, dotPos) + "_thumb" + thumbExt;
		}
		
		previewImage = new Image(Application.getDevice(), Importer.PATH_TO_IMAGES + filePath);
		final Rectangle rect = previewImage.getBounds();
		int width = WIDTH;
		if (rect.width > WIDTH) {
			width = rect.width;
		}
		int height = HEIGHT;
		if (rect.height > HEIGHT) {
			height = rect.height;
		}
		
		Canvas canvas = new Canvas(parent, SWT.NO_REDRAW_RESIZE);
		canvas.setLayout(new GridLayout(1, false));
		GridData layoutData = new GridData();
		layoutData.widthHint = width;
		layoutData.heightHint = height;
		canvas.setLayoutData(layoutData);
		canvas.setSize(width, height);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				float xOffset = (WIDTH - rect.width) / 2;
				if (xOffset < 0) {
					xOffset = 0;
				}
				float yOffset = (HEIGHT - rect.height) / 2;
				if (yOffset < 0) {
					yOffset = 0;
				}
				e.gc.drawImage(previewImage, (int)xOffset, (int)yOffset);
			}
		});
		canvas.addMouseListener(new ClickHandler(picture));
		//canvas.layout();
	}
}
