package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Picture;

public class PictureUserInterface {
	interface TextChangeHandler {
		void textChanged(Picture picture, String value);
	}
	private class TextModifyListener implements ModifyListener {
		Text text;
		Picture picture;
		TextChangeHandler handler;
		public TextModifyListener(Text textField, Picture pic, TextChangeHandler changeHandler) {
			text = textField;
			picture = pic;
			handler = changeHandler;
		}
		@Override
		public void modifyText(ModifyEvent e) {
			handler.textChanged(picture, text.getText());
		}
	}
	private class PictureTextField {
		public Group group;
		private Label label;
		private Text text;
		public PictureTextField(Composite parent, Picture picture, String sLabel, String initialText, int textFieldSize, TextChangeHandler handler) {
			group = new Group(parent, SWT.SHADOW_NONE);
			group.setLayout(new RowLayout(SWT.HORIZONTAL));
			
			label = new Label(group, SWT.SHADOW_OUT);
			label.setText(sLabel);
			
			text = new Text(group, SWT.LEFT);
			text.setText(initialText);
			text.addModifyListener(new TextModifyListener(text, picture, handler));
			
			group.layout();
		}
	}
	private Group pictureGroup;
	private Group metadataGroup;
	private PreviewUserInterface previewUserInterface;
	public PictureUserInterface(Device device, Composite parent, Picture picture) {
		pictureGroup = new Group(parent, SWT.SHADOW_ETCHED_OUT);
		pictureGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		previewUserInterface = new PreviewUserInterface(device, pictureGroup, picture.getLocalFilePath());
		
		metadataGroup = new Group(pictureGroup, SWT.SHADOW_ETCHED_OUT);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		metadataGroup.setLayout(gridLayout);
		
		new PictureTextField(metadataGroup, picture, "Title:", picture.getMetadata().getTitle(), 30, new TextChangeHandler(){
			@Override
			public void textChanged(Picture picture, String value) {
				picture.getMetadata().setTitle(value);
				System.out.println("Title changed to "+value);
			}
		});
		new PictureTextField(metadataGroup, picture, "Description:", picture.getMetadata().getDescription(), 30, new TextChangeHandler(){
			@Override
			public void textChanged(Picture picture, String value) {
				picture.getMetadata().setDescription(value);
				System.out.println("Description changed to "+value);
			}
		});
		metadataGroup.layout();
		pictureGroup.layout();
	}
}
