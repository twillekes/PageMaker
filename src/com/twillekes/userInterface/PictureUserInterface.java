package com.twillekes.userInterface;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import com.twillekes.portfolio.Picture;

public class PictureUserInterface extends Panel {
	private static final long serialVersionUID = 1L;
	private PreviewUserInterface previewUserInterface;
	
	private Panel titlePanel;
	private Label titleLabel;
	private TextField titleUserInterface;
	
	private interface SetterFunction {
		void function(Picture picture, String str);
	}
	
	private class TextActionListener implements TextListener {
		private Picture picture;
		private SetterFunction setter;
		private TextField textField;
		public TextActionListener(Picture picture, TextField textField, SetterFunction setter) {
			this.picture = picture;
			this.textField = textField;
			this.setter = setter;
		}
		@Override
		public void textValueChanged(TextEvent arg0) {
			this.setter.function(this.picture, this.textField.getText());
		}
	}

	public PictureUserInterface(Picture picture) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		previewUserInterface = new PreviewUserInterface(picture.getLocalFilePath());
		this.add(previewUserInterface);
		
		titlePanel = new Panel();
		titleLabel = new Label("Title:");
		titlePanel.add(titleLabel);
		titleUserInterface = new TextField(picture.getMetadata().getTitle(), 20);
		titleUserInterface.addTextListener(new TextActionListener(picture, titleUserInterface, new SetterFunction() {
			public void function(Picture picture, String str) {
				picture.getMetadata().setTitle(str);
				System.out.println("Title changed to "+str);
			}
		}));
		titlePanel.add(titleUserInterface);
		titlePanel.doLayout();
		this.add(titlePanel);
		
		this.doLayout();
	}
}
