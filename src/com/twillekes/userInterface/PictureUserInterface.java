package com.twillekes.userInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.twillekes.portfolio.Metadata;
import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;

public class PictureUserInterface {
	public interface PictureEditDelegate {
		public boolean hasPrevious();
		public boolean hasNext();
		public Picture getPrevious() throws Exception;
		public Picture getNext() throws Exception;
		public void done();
	}
	private class PictureTextField {
		public Group group;
		private Label label;
		private Text text;
		public PictureTextField(Composite parent, String sLabel, String initialText, int textFieldSize) {
			group = new Group(parent, SWT.SHADOW_NONE);
			group.setLayout(new RowLayout(SWT.HORIZONTAL));
			
			label = new Label(group, SWT.SHADOW_OUT);
			label.setText(sLabel);
			
			text = new Text(group, SWT.LEFT);
			setText(initialText);
			
			group.layout();
		}
		public void setText(String initialText) {
			if (initialText == null) {
				initialText = "";
			}
			text.setText(initialText);
			text.pack();
			group.layout();
			group.pack();
		}
		public String getText() {
			return text.getText();
		}
	}
	private class PictureComboBox {
		private Group group;
		private Label label;
		private Combo combo;
		private String[] items;
		public PictureComboBox(Composite parent, String sLabel, String initialText, String[] items) throws Exception {
			create(parent, sLabel, initialText, items, 0);
		}
		public PictureComboBox(Composite parent, String sLabel, String initialText, String[] items, int styles) throws Exception {
			create(parent, sLabel, initialText, items, styles);
		}
		public void create(Composite parent, String sLabel, String initialText, String[] items, int styles) throws Exception {
			group = new Group(parent, SWT.SHADOW_NONE);
			group.setLayout(new RowLayout(SWT.HORIZONTAL));
			
			label = new Label(group, SWT.SHADOW_OUT);
			label.setText(sLabel);
			
			combo = new Combo(group, SWT.DROP_DOWN | styles);
			setItems(items);
			
			setItem(initialText);
		}
		public void setItems(String[] items) {
			this.items = items;
			combo.setItems(this.items);
		}
		public void setItem(String initialText) {
			combo.clearSelection();
			int selectedItem = 0;
			for (selectedItem = 0; selectedItem < items.length; selectedItem++) {
				if (items[selectedItem].equals(initialText)) {
					break;
				}
			}
			if (selectedItem == items.length){
				//System.out.println("Could not find initial string (" + initialText + ") in " + sLabel);
				return;
			}
			combo.select(selectedItem);
		}
		public String getItem() {
			return combo.getText();
		}
	}
	public Composite getTopLevel() {
		return this.pictureGroup;
	}
	private Picture picture;
	private Group pictureGroup;
	private Group metadataGroup;
	private Group textGroup;
	private Group uberGroup;
	public Shell shell;
	private Button previous, next;
	private PictureEditDelegate editDelegate;
	private PreviewUserInterface previewUI;
	private Label filePathLabel;
	private PictureTextField titleTextField, descriptionTextField, timeTextField, dateTextField;
	private PictureComboBox subjectComboBox, orientationComboBox, seasonComboBox, cameraComboBox, lensComboBox,
							filtersComboBox, filmComboBox, chromeComboBox, formatComboBox, yearComboBox, 
							monthComboBox, directionComboBox, ratingComboBox, isNewComboBox, isFavoriteComboBox,
							doNotShowComboBox, isDiscardedComboBox, isInFeedComboBox, folderComboBox;
	public PictureUserInterface(Composite parent, Picture pic) {
		this.picture = pic;
		createPictureUserInterface(parent);
	}
	public PictureUserInterface(Picture pic, final PictureEditDelegate editDelegate) {
		this.picture = pic;
		this.editDelegate = editDelegate;
		Rectangle rect = Application.getShell().getBounds();
		shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new GridLayout());
		shell.setText("Edit Picture Metadata");
		
		Group buttonGroup = new Group(shell, SWT.NONE);
		buttonGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		previous = new Button(buttonGroup, SWT.PUSH);
		previous.setText("Previous");
		boolean enabled = false;
		if (editDelegate.hasPrevious()) {
			enabled = true;
		}
		previous.setEnabled(enabled);
	    previous.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
				Application.getDisplay().asyncExec(new Runnable(){
					@Override
					public void run() {
						try {
							writeTo(picture);
							switchTo(editDelegate.getPrevious());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	    	}
	    });
		
	    final Button done = new Button(buttonGroup, SWT.PUSH);
	    done.setText("Done");
	    done.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
				Application.getDisplay().asyncExec(new Runnable(){
					@Override
					public void run() {
						writeTo(picture);
						editDelegate.done();
						shell.close();
					}
				});
	    	}
	    });
	    shell.setDefaultButton(done);

		next = new Button(buttonGroup, SWT.PUSH);
		next.setText("Next");
		enabled = false;
		if (editDelegate.hasNext()) {
			enabled = true;
		}
		next.setEnabled(enabled);
	    next.addListener(SWT.Selection, new Listener(){
	    	public void handleEvent(Event event) {
				Application.getDisplay().asyncExec(new Runnable(){
					@Override
					public void run() {
						try {
							writeTo(picture);
							switchTo(editDelegate.getNext());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	    	}
	    });
		
		ScrolledComposite portfolioScroll = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		portfolioScroll.setLayout(new FillLayout());

		Group portfolioGroup = new Group(portfolioScroll, SWT.SHADOW_ETCHED_OUT);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		portfolioGroup.setLayout(rowLayout);

		portfolioScroll.setContent(portfolioGroup);

		createPictureUserInterface(portfolioGroup);
		
		portfolioGroup.layout();
		portfolioGroup.pack();
		portfolioScroll.layout();
		portfolioScroll.pack();
		shell.layout();
		shell.pack();
		Rectangle pRect = portfolioScroll.getBounds();
		final int HEIGHT_OFFSET = 100;
// The following was causing the "isNew" shell/dialog to be too short.
// I don't remember why it was here...
//		if (pRect.height > rect.height-HEIGHT_OFFSET) {
//			GridData layoutData = new GridData();
//			layoutData.heightHint = rect.height - HEIGHT_OFFSET;
//			portfolioScroll.setLayoutData(layoutData);
//			portfolioScroll.layout();
//			shell.pack();
//		}
		shell.open();
	}
	private void writeTo(Picture pic) {
		try {
			Repository.instance().movePictureToFolder(pic, folderComboBox.getItem());
		} catch (Exception e) {
			e.printStackTrace();
		}
		pic.getMetadata().setTitle(titleTextField.getText());
		pic.getMetadata().setDescription(descriptionTextField.getText());
		pic.getMetadata().setTime(timeTextField.getText());
		pic.getMetadata().setDate(dateTextField.getText());
		pic.getMetadata().setSubject(subjectComboBox.getItem());
		pic.getMetadata().setOrientation(orientationComboBox.getItem());
		pic.getMetadata().setSeason(seasonComboBox.getItem());
		pic.getMetadata().setCamera(cameraComboBox.getItem());
		pic.getMetadata().setLens(lensComboBox.getItem());
		pic.getMetadata().setFilters(filtersComboBox.getItem());
		pic.getMetadata().setFilm(filmComboBox.getItem());
		pic.getMetadata().setChrome(chromeComboBox.getItem());
		pic.getMetadata().setFormat(formatComboBox.getItem());
		pic.getMetadata().setYear(yearComboBox.getItem());
		pic.getMetadata().setMonth(monthComboBox.getItem());
		pic.getMetadata().setDirection(directionComboBox.getItem());
		pic.getMetadata().setRating(ratingComboBox.getItem());
		pic.getMetadata().setIsNew(isNewComboBox.getItem());
		pic.getMetadata().setIsFavorite(isFavoriteComboBox.getItem());
		pic.getMetadata().setDoNotShow(doNotShowComboBox.getItem());
		pic.getMetadata().setIsDiscarded(isDiscardedComboBox.getItem());
		pic.getMetadata().setIsInFeed(isInFeedComboBox.getItem());
	}
	private void switchTo(Picture pic) {
		this.picture = pic;
		try {
			filePathLabel.setText(picture.getRepositoryFilePath());
			previewUI.changeImage(picture, picture.getRepositoryThumbFilePath());
			titleTextField.setText(picture.getMetadata().getTitle());
			descriptionTextField.setText(picture.getMetadata().getDescription());
			timeTextField.setText(picture.getMetadata().getTime());
			dateTextField.setText(picture.getMetadata().getDate());
			subjectComboBox.setItems(Metadata.schema.getCategoryValues("subject"));
			subjectComboBox.setItem(picture.getMetadata().getSubject());
			orientationComboBox.setItems(Metadata.schema.getCategoryValues("orientation"));
			orientationComboBox.setItem(picture.getMetadata().getOrientation());
			seasonComboBox.setItems(Metadata.schema.getCategoryValues("season"));
			seasonComboBox.setItem(picture.getMetadata().getSeason());
			cameraComboBox.setItems(Metadata.schema.getCategoryValues("camera"));
			cameraComboBox.setItem(picture.getMetadata().getCamera());
			lensComboBox.setItems(Metadata.schema.getCategoryValues("lens"));
			lensComboBox.setItem(picture.getMetadata().getLens());
			filtersComboBox.setItems(Metadata.schema.getCategoryValues("filters"));
			filtersComboBox.setItem(picture.getMetadata().getFilters());
			filmComboBox.setItems(Metadata.schema.getCategoryValues("film"));
			filmComboBox.setItem(picture.getMetadata().getFilm());
			chromeComboBox.setItems(Metadata.schema.getCategoryValues("chrome"));
			chromeComboBox.setItem(picture.getMetadata().getChrome());
			formatComboBox.setItems(Metadata.schema.getCategoryValues("format"));
			formatComboBox.setItem(picture.getMetadata().getFormat());
			yearComboBox.setItems(Metadata.schema.getCategoryValues("year"));
			yearComboBox.setItem(picture.getMetadata().getYear());
			monthComboBox.setItems(Metadata.schema.getCategoryValues("month"));
			monthComboBox.setItem(picture.getMetadata().getMonth());
			directionComboBox.setItems(Metadata.schema.getCategoryValues("direction"));
			directionComboBox.setItem(picture.getMetadata().getDirection());
			ratingComboBox.setItems(Metadata.schema.getCategoryValues("rating"));
			ratingComboBox.setItem(picture.getMetadata().getRating());
			isNewComboBox.setItems(Metadata.schema.getCategoryValues("isNew"));
			isNewComboBox.setItem(picture.getMetadata().getIsNew());
			isFavoriteComboBox.setItems(Metadata.schema.getCategoryValues("isFavorite"));
			isFavoriteComboBox.setItem(picture.getMetadata().getIsFavorite());
			doNotShowComboBox.setItems(Metadata.schema.getCategoryValues("doNotShow"));
			doNotShowComboBox.setItem(picture.getMetadata().getDoNotShow());
			isDiscardedComboBox.setItems(Metadata.schema.getCategoryValues("isDiscarded"));
			isDiscardedComboBox.setItem(picture.getMetadata().getIsDiscarded());
			isInFeedComboBox.setItems(Metadata.schema.getCategoryValues("isInFeed"));
			isInFeedComboBox.setItem(picture.getMetadata().getIsInFeed());
			folderComboBox.setItems(Repository.instance().getFolderNames().toArray(new String[Repository.instance().getFolderNames().size()]));
			folderComboBox.setItem(Repository.instance().getFolderNameForPicture(picture));
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean enabled = false;
		if (editDelegate.hasPrevious()) {
			enabled = true;
		}
		previous.setEnabled(enabled);
		enabled = false;
		if (editDelegate.hasNext()) {
			enabled = true;
		}
		next.setEnabled(enabled);
		shell.layout();
		shell.pack();
	}
	public void createPictureUserInterface(Composite parent) {
		pictureGroup = new Group(parent, SWT.NONE);
		GridLayout gLayout = new GridLayout();
		gLayout.numColumns = 2;
		pictureGroup.setLayout(gLayout);
		
		previewUI = new PreviewUserInterface(pictureGroup, picture, picture.getRepositoryThumbFilePath());
		previewUI.setClickObserver(new PreviewUserInterface.ClickObserver() {
			@Override
			public void click(MouseEvent mouseEvent, Picture pic) {
				Shell shell = new Shell(Application.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				shell.setLayout(new GridLayout());
				shell.setText("View Image");
				new PreviewUserInterface(shell, pic, pic.getRepositoryFilePath());
				shell.layout();
				shell.pack();
				shell.open();
			}
		});
		
		uberGroup = new Group(pictureGroup, SWT.NONE);
		uberGroup.setLayout(new RowLayout(SWT.VERTICAL));
		filePathLabel = new Label(uberGroup, SWT.SHADOW_OUT);
		filePathLabel.setText(picture.getRepositoryFilePath());
		
		textGroup = new Group(uberGroup, SWT.NONE);
		textGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		titleTextField = new PictureTextField(textGroup, "Title:", picture.getMetadata().getTitle(), 30);
		descriptionTextField = new PictureTextField(textGroup, "Description:", picture.getMetadata().getDescription(), 30);

		metadataGroup = new Group(uberGroup, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		metadataGroup.setLayout(gridLayout);
		
		try {
			subjectComboBox = new PictureComboBox(metadataGroup, "Subject:", picture.getMetadata().getSubject(),
					Metadata.schema.getCategoryValues("subject"));
			orientationComboBox = new PictureComboBox(metadataGroup, "Orientation:", picture.getMetadata().getOrientation(),
					Metadata.schema.getCategoryValues("orientation"));
			seasonComboBox = new PictureComboBox(metadataGroup, "Season:", picture.getMetadata().getSeason(),
					Metadata.schema.getCategoryValues("season"));
			cameraComboBox = new PictureComboBox(metadataGroup, "Camera:", picture.getMetadata().getCamera(),
					Metadata.schema.getCategoryValues("camera"));
			lensComboBox = new PictureComboBox(metadataGroup, "Lens:", picture.getMetadata().getLens(),
					Metadata.schema.getCategoryValues("lens"));
			filtersComboBox = new PictureComboBox(metadataGroup, "Filters:", picture.getMetadata().getFilters(),
					Metadata.schema.getCategoryValues("filters"));
			filmComboBox = new PictureComboBox(metadataGroup, "Film:", picture.getMetadata().getFilm(),
					Metadata.schema.getCategoryValues("film"));
			chromeComboBox = new PictureComboBox(metadataGroup, "Chrome:", picture.getMetadata().getChrome(),
					Metadata.schema.getCategoryValues("chrome"));
			formatComboBox = new PictureComboBox(metadataGroup, "Format:", picture.getMetadata().getFormat(),
					Metadata.schema.getCategoryValues("format"));
			timeTextField = new PictureTextField(metadataGroup, "Time:", picture.getMetadata().getTime(), 10);
			dateTextField = new PictureTextField(metadataGroup, "Date:", picture.getMetadata().getDate(), 10);
			yearComboBox = new PictureComboBox(metadataGroup, "Year:", picture.getMetadata().getYear(),
					Metadata.schema.getCategoryValues("year"));
			monthComboBox = new PictureComboBox(metadataGroup, "Month:", picture.getMetadata().getMonth(),
					Metadata.schema.getCategoryValues("month"));
			directionComboBox = new PictureComboBox(metadataGroup, "Direction:", picture.getMetadata().getDirection(),
					Metadata.schema.getCategoryValues("direction"));
			ratingComboBox = new PictureComboBox(metadataGroup, "Rating:", picture.getMetadata().getRating(),
					Metadata.schema.getCategoryValues("rating"));
			isNewComboBox = new PictureComboBox(metadataGroup, "Is new:", picture.getMetadata().getIsNew(),
					Metadata.schema.getCategoryValues("isNew"));
			isFavoriteComboBox = new PictureComboBox(metadataGroup, "Is Favorite:", picture.getMetadata().getIsFavorite(),
					Metadata.schema.getCategoryValues("isFavorite"));
			doNotShowComboBox = new PictureComboBox(metadataGroup, "Do Not Show:", picture.getMetadata().getDoNotShow(),
					Metadata.schema.getCategoryValues("doNotShow"));
			isDiscardedComboBox = new PictureComboBox(metadataGroup, "Is Discarded:", picture.getMetadata().getIsDiscarded(),
					Metadata.schema.getCategoryValues("isDiscarded"));
			isInFeedComboBox = new PictureComboBox(metadataGroup, "Is in Feed:", picture.getMetadata().getIsInFeed(),
					Metadata.schema.getCategoryValues("isInFeed"));
			folderComboBox = new PictureComboBox(metadataGroup, "Repository:", Repository.instance().getFolderNameForPicture(picture),
					Repository.instance().getFolderNames().toArray(new String[Repository.instance().getFolderNames().size()]), SWT.READ_ONLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		metadataGroup.layout();
		pictureGroup.layout();
	}
}
