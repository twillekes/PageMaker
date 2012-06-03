package com.twillekes.userInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
	private interface TextChangeHandler {
		void textChanged(String value);
	}
	private class TextModifyListener implements ModifyListener {
		Text text;
		Combo combo;
		TextChangeHandler handler;
		public TextModifyListener(Text textField, TextChangeHandler changeHandler) {
			text = textField;
			handler = changeHandler;
		}
		public TextModifyListener(Combo textField, TextChangeHandler changeHandler) {
			combo = textField;
			handler = changeHandler;
		}
		@Override
		public void modifyText(ModifyEvent e) {
			if (text != null) {
				handler.textChanged(text.getText());
			} else if (combo != null) {
				handler.textChanged(combo.getText());
			}
		}
	}
	private class PictureTextField {
		public Group group;
		private Label label;
		private Text text;
		public PictureTextField(Composite parent, String sLabel, String initialText, int textFieldSize, TextChangeHandler handler) {
			group = new Group(parent, SWT.SHADOW_NONE);
			group.setLayout(new RowLayout(SWT.HORIZONTAL));
			
			label = new Label(group, SWT.SHADOW_OUT);
			label.setText(sLabel);
			
			text = new Text(group, SWT.LEFT);
			setText(initialText);
			text.addModifyListener(new TextModifyListener(text, handler));
			
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
	}
	private class PictureComboBox {
		private Group group;
		private Label label;
		private Combo combo;
		private String[] items;
		public PictureComboBox(Composite parent, String sLabel, String initialText, String[] items, TextChangeHandler handler) throws Exception {
			create(parent, sLabel, initialText, items, handler, 0);
		}
		public PictureComboBox(Composite parent, String sLabel, String initialText, String[] items, TextChangeHandler handler, int styles) throws Exception {
			create(parent, sLabel, initialText, items, handler, styles);
		}
		public void create(Composite parent, String sLabel, String initialText, String[] items, TextChangeHandler handler, int styles) throws Exception {
			this.items = items;
			group = new Group(parent, SWT.SHADOW_NONE);
			group.setLayout(new RowLayout(SWT.HORIZONTAL));
			
			label = new Label(group, SWT.SHADOW_OUT);
			label.setText(sLabel);
			
			combo = new Combo(group, SWT.DROP_DOWN | styles);
			combo.setItems(items);
			
			setItem(initialText);
			combo.addModifyListener(new TextModifyListener(combo, handler));
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
	private PictureTextField titleTextField, descriptionTextField, timeTextField, dateTextField;
	private PictureComboBox subjectComboBox, orientationComboBox, seasonComboBox, cameraComboBox, lensComboBox,
							filtersComboBox, filmComboBox, chromeComboBox, formatComboBox, yearComboBox, 
							monthComboBox, directionComboBox, ratingComboBox, isNewComboBox, isFavoriteComboBox,
							doNotShowComboBox, isDiscardedComboBox, isInFeedComboBox, repositoryComboBox;
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
						editDelegate.done();
					}
				});
	    		shell.close();
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
		if (pRect.height > rect.height-HEIGHT_OFFSET) {
			GridData layoutData = new GridData();
			layoutData.heightHint = rect.height - HEIGHT_OFFSET;
			portfolioScroll.setLayoutData(layoutData);
			portfolioScroll.layout();
			shell.pack();
		}
		shell.open();
	}
	private void switchTo(Picture pic) {
		this.picture = pic;
		previewUI.changeImage(pic, pic.getRepositoryThumbFilePath());
		titleTextField.setText(picture.getMetadata().getTitle());
		descriptionTextField.setText(picture.getMetadata().getDescription());
		timeTextField.setText(picture.getMetadata().getTime());
		dateTextField.setText(picture.getMetadata().getDate());
		subjectComboBox.setItem(picture.getMetadata().getSubject());
		orientationComboBox.setItem(picture.getMetadata().getOrientation());
		seasonComboBox.setItem(picture.getMetadata().getSeason());
		cameraComboBox.setItem(picture.getMetadata().getCamera());
		lensComboBox.setItem(picture.getMetadata().getLens());
		filtersComboBox.setItem(picture.getMetadata().getFilters());
		filmComboBox.setItem(picture.getMetadata().getFilm());
		chromeComboBox.setItem(picture.getMetadata().getChrome());
		formatComboBox.setItem(picture.getMetadata().getFormat());
		yearComboBox.setItem(picture.getMetadata().getYear());
		monthComboBox.setItem(picture.getMetadata().getMonth());
		directionComboBox.setItem(picture.getMetadata().getDirection());
		ratingComboBox.setItem(picture.getMetadata().getRating());
		isNewComboBox.setItem(picture.getMetadata().getIsNew());
		isFavoriteComboBox.setItem(picture.getMetadata().getIsFavorite());
		doNotShowComboBox.setItem(picture.getMetadata().getDoNotShow());
		isDiscardedComboBox.setItem(picture.getMetadata().getIsDiscarded());
		isInFeedComboBox.setItem(picture.getMetadata().getIsInFeed());
		try {
			repositoryComboBox.setItem(Repository.instance().getFolderNameForPicture(picture));
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
		Label filePathLabel = new Label(uberGroup, SWT.SHADOW_OUT);
		filePathLabel.setText(picture.getRepositoryFilePath());
		
		textGroup = new Group(uberGroup, SWT.NONE);
		textGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		titleTextField = new PictureTextField(textGroup, "Title:", picture.getMetadata().getTitle(), 30, new TextChangeHandler(){
			@Override
			public void textChanged(String value) {
				picture.getMetadata().setTitle(value);
				uberGroup.layout();
			}
		});
		descriptionTextField = new PictureTextField(textGroup, "Description:", picture.getMetadata().getDescription(), 30, new TextChangeHandler(){
			@Override
			public void textChanged(String value) {
				picture.getMetadata().setDescription(value);
				uberGroup.layout();
			}
		});

		metadataGroup = new Group(uberGroup, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		metadataGroup.setLayout(gridLayout);
		
		try {
			subjectComboBox = new PictureComboBox(metadataGroup, "Subject:", picture.getMetadata().getSubject(),
					Metadata.schema.subjects.toArray(new String[Metadata.schema.subjects.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setSubject(value);
				}
			});
			orientationComboBox = new PictureComboBox(metadataGroup, "Orientation:", picture.getMetadata().getOrientation(),
					Metadata.schema.orientations.toArray(new String[Metadata.schema.orientations.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setOrientation(value);
				}
			});
			seasonComboBox = new PictureComboBox(metadataGroup, "Season:", picture.getMetadata().getSeason(),
					Metadata.schema.seasons.toArray(new String[Metadata.schema.seasons.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setSeason(value);
				}
			});
			cameraComboBox = new PictureComboBox(metadataGroup, "Camera:", picture.getMetadata().getCamera(),
					Metadata.schema.cameras.toArray(new String[Metadata.schema.cameras.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setCamera(value);
				}
			});
			lensComboBox = new PictureComboBox(metadataGroup, "Lens:", picture.getMetadata().getLens(),
					Metadata.schema.lenses.toArray(new String[Metadata.schema.lenses.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setLens(value);
				}
			});
			filtersComboBox = new PictureComboBox(metadataGroup, "Filters:", picture.getMetadata().getFilters(),
					Metadata.schema.filterss.toArray(new String[Metadata.schema.filterss.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setFilters(value);
				}
			});
			filmComboBox = new PictureComboBox(metadataGroup, "Film:", picture.getMetadata().getFilm(),
					Metadata.schema.films.toArray(new String[Metadata.schema.films.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setFilm(value);
				}
			});
			chromeComboBox = new PictureComboBox(metadataGroup, "Chrome:", picture.getMetadata().getChrome(),
					Metadata.schema.chromes.toArray(new String[Metadata.schema.chromes.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setChrome(value);
				}
			});
			formatComboBox = new PictureComboBox(metadataGroup, "Format:", picture.getMetadata().getFormat(),
					Metadata.schema.formats.toArray(new String[Metadata.schema.formats.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setFormat(value);
				}
			});
			timeTextField = new PictureTextField(metadataGroup, "Time:", picture.getMetadata().getTime(), 10, new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setTime(value);
					uberGroup.layout();
				}
			});
			dateTextField = new PictureTextField(metadataGroup, "Date:", picture.getMetadata().getDate(), 10, new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setDate(value);
					uberGroup.layout();
				}
			});
			yearComboBox = new PictureComboBox(metadataGroup, "Year:", picture.getMetadata().getYear(),
					Metadata.schema.years.toArray(new String[Metadata.schema.years.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setYear(value);
				}
			});
			monthComboBox = new PictureComboBox(metadataGroup, "Month:", picture.getMetadata().getMonth(),
					Metadata.schema.months.toArray(new String[Metadata.schema.months.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setMonth(value);
				}
			});
			directionComboBox = new PictureComboBox(metadataGroup, "Direction:", picture.getMetadata().getDirection(),
					Metadata.schema.directions.toArray(new String[Metadata.schema.directions.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setDirection(value);
				}
			});
			ratingComboBox = new PictureComboBox(metadataGroup, "Rating:", picture.getMetadata().getRating(),
					Metadata.schema.ratings.toArray(new String[Metadata.schema.ratings.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setRating(value);
				}
			});
			isNewComboBox = new PictureComboBox(metadataGroup, "Is new:", picture.getMetadata().getIsNew(),
					Metadata.schema.isNews.toArray(new String[Metadata.schema.isNews.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setIsNew(value);
				}
			});
			isFavoriteComboBox = new PictureComboBox(metadataGroup, "Is Favorite:", picture.getMetadata().getIsFavorite(),
					Metadata.schema.isFavorites.toArray(new String[Metadata.schema.isFavorites.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setIsFavorite(value);
				}
			});
			doNotShowComboBox = new PictureComboBox(metadataGroup, "Do Not Show:", picture.getMetadata().getDoNotShow(),
					Metadata.schema.doNotShows.toArray(new String[Metadata.schema.doNotShows.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setDoNotShow(value);
				}
			});
			isDiscardedComboBox = new PictureComboBox(metadataGroup, "Is Discarded:", picture.getMetadata().getIsDiscarded(),
					Metadata.schema.isDiscardeds.toArray(new String[Metadata.schema.isDiscardeds.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setIsDiscarded(value);
				}
			});
			isInFeedComboBox = new PictureComboBox(metadataGroup, "Is in Feed:", picture.getMetadata().getIsInFeed(),
					Metadata.schema.isInFeeds.toArray(new String[Metadata.schema.isInFeeds.size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					picture.getMetadata().setIsInFeed(value);
				}
			});
			repositoryComboBox = new PictureComboBox(metadataGroup, "Repository:", Repository.instance().getFolderNameForPicture(picture),
					Repository.instance().getFolderNames().toArray(new String[Repository.instance().getFolderNames().size()]), new TextChangeHandler(){
				@Override
				public void textChanged(String value) {
					Repository.instance().movePictureToRepository(picture, value);
				}
			}, SWT.READ_ONLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		metadataGroup.layout();
		pictureGroup.layout();
	}
}
