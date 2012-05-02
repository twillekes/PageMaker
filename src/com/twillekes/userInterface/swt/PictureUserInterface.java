package com.twillekes.userInterface.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.twillekes.portfolio.Metadata;
import com.twillekes.portfolio.Picture;

public class PictureUserInterface {
	interface TextChangeHandler {
		void textChanged(Picture picture, String value);
	}
	private class TextModifyListener implements ModifyListener {
		Text text;
		Combo combo;
		Picture picture;
		TextChangeHandler handler;
		public TextModifyListener(Text textField, Picture pic, TextChangeHandler changeHandler) {
			text = textField;
			picture = pic;
			handler = changeHandler;
		}
		public TextModifyListener(Combo textField, Picture pic, TextChangeHandler changeHandler) {
			combo = textField;
			picture = pic;
			handler = changeHandler;
		}
		@Override
		public void modifyText(ModifyEvent e) {
			if (text != null) {
				handler.textChanged(picture, text.getText());
			} else if (combo != null) {
				handler.textChanged(picture, combo.getText());
			}
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
	private class PictureComboBox {
		private Group group;
		private Label label;
		private Combo combo;
		public PictureComboBox(Composite parent, Picture picture, String sLabel, String initialText, String[] items, TextChangeHandler handler) throws Exception {
			group = new Group(parent, SWT.SHADOW_NONE);
			group.setLayout(new RowLayout(SWT.HORIZONTAL));
			
			label = new Label(group, SWT.SHADOW_OUT);
			label.setText(sLabel);
			
			combo = new Combo(group, SWT.DROP_DOWN);
			combo.setItems(items);
			combo.addModifyListener(new TextModifyListener(combo, picture, handler));
			
			int selectedItem = 0;
			for (selectedItem = 0; selectedItem < items.length; selectedItem++) {
				if (items[selectedItem].equals(initialText)) {
					break;
				}
			}
			if (selectedItem == items.length){
				System.out.println("Could not find initial string (" + initialText + ") in " + sLabel);
				return;
			}
			combo.select(selectedItem);
		}
	}
	private Group pictureGroup;
	private Group metadataGroup;
	private Group textGroup;
	private Group uberGroup;
	public PictureUserInterface(Device device, Composite parent, Picture picture) {
		pictureGroup = new Group(parent, SWT.NONE);
		pictureGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		new PreviewUserInterface(device, pictureGroup, picture.getLocalFilePath());
		
		uberGroup = new Group(pictureGroup, SWT.NONE);
		uberGroup.setLayout(new RowLayout(SWT.VERTICAL));
		
		textGroup = new Group(uberGroup, SWT.NONE);
		textGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		metadataGroup = new Group(uberGroup, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		metadataGroup.setLayout(gridLayout);
		
		Label filePathLabel = new Label(textGroup, SWT.SHADOW_OUT);
		filePathLabel.setText(picture.getLocalFilePath());
		
		new PictureTextField(textGroup, picture, "Title:", picture.getMetadata().getTitle(), 30, new TextChangeHandler(){
			@Override
			public void textChanged(Picture picture, String value) {
				picture.getMetadata().setTitle(value);
				uberGroup.layout();
			}
		});
		new PictureTextField(textGroup, picture, "Description:", picture.getMetadata().getDescription(), 30, new TextChangeHandler(){
			@Override
			public void textChanged(Picture picture, String value) {
				picture.getMetadata().setDescription(value);
				uberGroup.layout();
			}
		});
		try {
			new PictureComboBox(metadataGroup, picture, "Subject:", picture.getMetadata().getSubject(),
					Metadata.schema.subjects.toArray(new String[Metadata.schema.subjects.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setSubject(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Orientation:", picture.getMetadata().getOrientation(),
					Metadata.schema.orientations.toArray(new String[Metadata.schema.orientations.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setOrientation(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Season:", picture.getMetadata().getSeason(),
					Metadata.schema.seasons.toArray(new String[Metadata.schema.seasons.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setSeason(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Camera:", picture.getMetadata().getCamera(),
					Metadata.schema.cameras.toArray(new String[Metadata.schema.cameras.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setCamera(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Lens:", picture.getMetadata().getLens(),
					Metadata.schema.lenses.toArray(new String[Metadata.schema.lenses.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setLens(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Filters:", picture.getMetadata().getFilters(),
					Metadata.schema.filterss.toArray(new String[Metadata.schema.filterss.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setFilters(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Film:", picture.getMetadata().getFilm(),
					Metadata.schema.films.toArray(new String[Metadata.schema.films.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setFilm(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Chrome:", picture.getMetadata().getChrome(),
					Metadata.schema.chromes.toArray(new String[Metadata.schema.chromes.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setChrome(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Format:", picture.getMetadata().getFormat(),
					Metadata.schema.formats.toArray(new String[Metadata.schema.formats.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setFormat(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Year:", picture.getMetadata().getYear(),
					Metadata.schema.years.toArray(new String[Metadata.schema.years.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setYear(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Month:", picture.getMetadata().getMonth(),
					Metadata.schema.months.toArray(new String[Metadata.schema.months.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setMonth(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Direction:", picture.getMetadata().getDirection(),
					Metadata.schema.directions.toArray(new String[Metadata.schema.directions.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setDirection(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Rating:", picture.getMetadata().getRating(),
					Metadata.schema.ratings.toArray(new String[Metadata.schema.ratings.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setRating(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Is new:", picture.getMetadata().getIsNew(),
					Metadata.schema.isNews.toArray(new String[Metadata.schema.isNews.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setIsNew(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Is Favorite:", picture.getMetadata().getIsFavorite(),
					Metadata.schema.isFavorites.toArray(new String[Metadata.schema.isFavorites.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setIsFavorite(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Do Not Show:", picture.getMetadata().getDoNotShow(),
					Metadata.schema.doNotShows.toArray(new String[Metadata.schema.doNotShows.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setDoNotShow(value);
				}
			});
			new PictureComboBox(metadataGroup, picture, "Is Discarded:", picture.getMetadata().getIsDiscarded(),
					Metadata.schema.isDiscardeds.toArray(new String[Metadata.schema.isDiscardeds.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(Picture picture, String value) {
					picture.getMetadata().setIsDiscarded(value);
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		metadataGroup.layout();
		pictureGroup.layout();
	}
}
