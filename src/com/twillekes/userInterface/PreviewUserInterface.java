package com.twillekes.userInterface;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInteraction.Selection;

public class PreviewUserInterface implements Observer {
	private Image previewImage;
	private Canvas canvas;
	private static int WIDTH = 125;
	private static int HEIGHT = 125;
	private boolean isSelected = false;
	private Picture picture;
	private PreviewMouseListener previewMouseListener;
	public interface ClickObserver {
		void click(MouseEvent mouseEvent, Picture picture);
	}
	private class PreviewMouseListener implements MouseListener {
		private ClickObserver clickObserver;
		private Picture picture;
		public PreviewMouseListener(ClickObserver clickObserver, Picture picture) {
			this.clickObserver = clickObserver;
			this.picture = picture;
		}
		public void setPicture(Picture picture) {
			this.picture = picture;
		}
		@Override
		public void mouseDoubleClick(MouseEvent e) {
		}
		@Override
		public void mouseDown(MouseEvent e) {
			clickObserver.click(e, picture);
		}
		@Override
		public void mouseUp(MouseEvent e) {
		}
	}
	public PreviewUserInterface(Composite parent, Picture picture, String filePath) {
		this.picture = picture;
		previewImage = new Image(Application.getDevice(), filePath);
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
				      e.gc.setLineWidth(20);
				      e.gc.setForeground(Application.getDisplay().getSystemColor(SWT.COLOR_BLUE));
				      final int PADDING = 0;
				      e.gc.drawRoundRectangle(PADDING, PADDING, (int)cWidth - PADDING, (int)cHeight - PADDING, 2*PADDING, 2*PADDING);
				}
			}
		});
		this.picture.addObserver(this);
		//canvas.layout();
	}
	public void setClickObserver(ClickObserver clickObserver) {
		this.previewMouseListener = new PreviewMouseListener(clickObserver, this.picture);
		canvas.addMouseListener(this.previewMouseListener);
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (this.isSelected) {
			Selection.instance().add(this);
		} else {
			Selection.instance().remove(this);
		}
		this.canvas.redraw();
	}
	public void toggleSelected() {
		this.isSelected = !this.isSelected;
		if (this.isSelected) {
			Selection.instance().add(this);
		} else {
			Selection.instance().remove(this);
		}
		this.canvas.redraw();
	}
	public Picture getPicture() {
		return this.picture;
	}
	@Override
	public void update(Observable o, Object arg) {
		Picture.Changed change = (Picture.Changed)arg;
		if (change.type == Picture.ChangeType.REPLACING) {
			this.picture = change.picture;
			this.previewMouseListener.setPicture(this.picture);
			change.picture.deleteObserver(this);
			this.picture.addObserver(this);
		}
	}
}
