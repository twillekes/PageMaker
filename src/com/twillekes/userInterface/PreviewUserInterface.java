package com.twillekes.userInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;

public class PreviewUserInterface {
	private Image previewImage;
	private Canvas canvas;
	private static int WIDTH = 125;
	private static int HEIGHT = 125;
	private boolean isSelected = false;
	private Picture picture;
	public PreviewUserInterface(Composite parent, Picture picture, String filePath, ClickListener clickListener) {
		this.picture = picture;
		previewImage = new Image(Application.getDevice(), Repository.BASE_PATH + filePath);
		final Rectangle rect = previewImage.getBounds();
		int width = WIDTH;
		if (rect.width > WIDTH) {
			width = rect.width;
		}
		int height = HEIGHT;
		if (rect.height > HEIGHT) {
			height = rect.height;
		}
		
		canvas = new Canvas(parent, SWT.NO_REDRAW_RESIZE | SWT.SHADOW_OUT);
		canvas.setLayout(new GridLayout(1, false));
		GridData layoutData = new GridData();
		layoutData.widthHint = width;
		layoutData.heightHint = height;
		canvas.setLayoutData(layoutData);
		canvas.setSize(width, height);
		canvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
			    float cWidth = (float)canvas.getBounds().width;
			    float cHeight = (float)canvas.getBounds().height;
				float xOffset = (cWidth - rect.width) / 2;
				if (xOffset < 0) {
					xOffset = 0;
				}
				float yOffset = (cHeight - rect.height) / 2;
				if (yOffset < 0) {
					yOffset = 0;
				}
				e.gc.drawImage(previewImage, (int)xOffset, (int)yOffset);
				
				if (isSelected) {
				      e.gc.setLineWidth(30);
				      e.gc.setForeground(Application.getDisplay().getSystemColor(SWT.COLOR_BLUE));
				      final int PADDING = 0;
				      e.gc.drawRoundRectangle(PADDING, PADDING, (int)cWidth - PADDING, (int)cHeight - PADDING, 2*PADDING, 2*PADDING);
				}
			}
		});
		if (clickListener != null) {
			clickListener.setPreviewUserInterface(this);
			canvas.addMouseListener(clickListener.getMouseListener());
		}
		//canvas.layout();
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		this.canvas.redraw();
	}
	public void toggleSelected() {
		this.isSelected = !this.isSelected;
		this.canvas.redraw();
	}
	public Picture getPicture() {
		return this.picture;
	}
}
