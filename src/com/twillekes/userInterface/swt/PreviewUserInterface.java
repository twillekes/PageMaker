package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.twillekes.jsonImporter.Importer;

public class PreviewUserInterface {
	private Image previewImage;
	private static int WIDTH = 125;
	private static int HEIGHT = 125;
	public PreviewUserInterface(Device device, Composite parent, String filePath) {
		int dotPos = filePath.lastIndexOf(".");
		String thumbExt = filePath.substring(dotPos);
		String thumbName = filePath.substring(0, dotPos) + "_thumb" + thumbExt;
		
		previewImage = new Image(device, Importer.PATH_TO_IMAGES + thumbName);
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
		//canvas.layout();
	}
}
