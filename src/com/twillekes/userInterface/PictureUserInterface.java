package com.twillekes.userInterface;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.twillekes.portfolio.Picture;

public class PictureUserInterface extends JPanel {
	private static final long serialVersionUID = 1L;
	private PreviewUserInterface previewUserInterface;
	
	private JPanel metadataPanel;
	private JPanel titlePanel;
	private JPanel descriptionPanel;
	private JPanel ratingPanel;
	
	private interface SetterFunction {
		void function(Picture picture, String str);
	}
	
	private class TextActionListener implements DocumentListener {
		private Picture picture;
		private SetterFunction setter;
		private JTextField textField;
		public TextActionListener(Picture picture, JTextField textField, SetterFunction setter) {
			this.picture = picture;
			this.textField = textField;
			this.setter = setter;
		}
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			this.setter.function(this.picture, this.textField.getText());
		}
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			this.setter.function(this.picture, this.textField.getText());
		}
		@Override
		public void removeUpdate(DocumentEvent arg0) {
			this.setter.function(this.picture, this.textField.getText());
		}
	}
	
	private class PictureTextPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel label;
		private JTextField textField;
		public PictureTextPanel(String labelValue, String initialValue, int fieldSize, Picture picture, SetterFunction setterFunction) {
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.label = new JLabel(labelValue);
			this.add(this.label);
			this.textField = new JTextField(initialValue, fieldSize);
			this.textField.getDocument().addDocumentListener(new TextActionListener(picture, textField, setterFunction));
			this.add(this.textField);
			this.doLayout();
		}
	}

	public PictureUserInterface(Picture picture) {
		Border raised = BorderFactory.createRaisedBevelBorder();
		this.setBorder(raised);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		previewUserInterface = new PreviewUserInterface(picture.getLocalFilePath());
		this.add(previewUserInterface);
		
		metadataPanel = new JPanel();
		metadataPanel.setLayout(new GridLayout(3,3));
		
		titlePanel = new PictureTextPanel("Title:", picture.getMetadata().getTitle(), 20, picture, new SetterFunction() {
			public void function(Picture picture, String str) {
				picture.getMetadata().setTitle(str);
				System.out.println("Title changed to "+str);
			}
		});
		metadataPanel.add(titlePanel);
		descriptionPanel = new PictureTextPanel("Description:", picture.getMetadata().getDescription(), 40, picture, new SetterFunction() {
			public void function(Picture picture, String str) {
				picture.getMetadata().setDescription(str);
				System.out.println("Description changed to "+str);
			}
		});
		metadataPanel.add(descriptionPanel);
		ratingPanel = new PictureTextPanel("Rating:", picture.getMetadata().getRating(), 5, picture, new SetterFunction() {
			public void function(Picture picture, String str) {
				picture.getMetadata().setRating(str);
				System.out.println("Rating changed to "+str);
			}
		});
		metadataPanel.add(ratingPanel);
		this.add(metadataPanel);
		this.doLayout();
	}
}
