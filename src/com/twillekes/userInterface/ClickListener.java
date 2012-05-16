package com.twillekes.userInterface;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class ClickListener {
	private MouseListener mouseListener;
	private PreviewUserInterface previewUserInterface;
	public ClickListener() {
		this.mouseListener = new PreviewListener(this);
	}
	public MouseListener getMouseListener() {
		return this.mouseListener;
	}
	private class PreviewListener implements MouseListener {
		private ClickListener clickListener;
		public PreviewListener(ClickListener clickListener) {
			this.clickListener = clickListener;
		}
		@Override
		public void mouseDoubleClick(MouseEvent e) {
		}
		@Override
		public void mouseDown(MouseEvent e) {
			clickListener.click(e);
		}
		@Override
		public void mouseUp(MouseEvent e) {
		}
	}
	public void click(MouseEvent e) {
		// Override in subclass
	}
	public PreviewUserInterface getPreviewUserInterface() {
		return previewUserInterface;
	}
	public void setPreviewUserInterface(PreviewUserInterface previewUserInterface) {
		this.previewUserInterface = previewUserInterface;
	}
}