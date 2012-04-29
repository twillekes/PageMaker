package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Picture;

public class PictureUserInterface {
	private Group pictureGroup;
	private PreviewUserInterface previewUserInterface;
	public PictureUserInterface(Device device, Composite parent, Picture picture) {
		pictureGroup = new Group(parent, SWT.SHADOW_ETCHED_OUT);
		pictureGroup.setLayout (new FillLayout ());
		pictureGroup.setText ("a square");
		previewUserInterface = new PreviewUserInterface(device, pictureGroup, picture.getLocalFilePath());
		pictureGroup.pack();
	}

}
