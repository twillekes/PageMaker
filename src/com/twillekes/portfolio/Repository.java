package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Repository extends Observable implements Observer {
	private final String ORIG_PATH = "../Page/My-personal-web-page/page/";
	private final String FROM_ORIG_PATH = "../repository/";
	private final String BASE_PATH = "repository/";
	private final String PAGE_PATH = "page/";
	private final String FILE_NAME = BASE_PATH + "repository.json";
	private final String SITE_URL = "http://members.shaw.ca/";
	private List<Folder> folders = null;
	private static Repository theInstance = null;
	private boolean isDirty = false;
	public Repository() {
		folders = new ArrayList<Folder>();
	}
	public static Repository instance() {
		if (theInstance == null) {
			theInstance = new Repository();
		}
		return theInstance;
	}
	public Folder createFolder(String account, String path) {
		Folder folder = new Folder(account, path);
		folder.addObserver(this);
		folders.add(folder);
		return folder;
	}
	public String getFileName() {
		return FILE_NAME;
	}
	public List<Folder> getFolders() {
		return folders;
	}
	public List<String> getFolderNames() {
		Iterator<Folder> it = folders.iterator();
		List<String> names = new ArrayList<String>();
		while(it.hasNext()) {
			Folder repo = it.next();
			names.add(repo.getPath());
		}
		return names;
	}
	public Folder getFolder(String path) throws Exception {
		Iterator<Folder> it = folders.iterator();
		while(it.hasNext()) {
			Folder rep = it.next();
			if (rep.getPath().equals(path)) {
				return rep;
			}
		}
		//throw new Exception("Unable to find folder with path " + path);
		return null;
	}
	public Folder getFolderForPicture(Picture picture) throws Exception {
		Iterator<Folder> it = folders.iterator();
		while(it.hasNext()) {
			Folder folder = it.next();
			if (folder.containsPicture(picture)) {
				return folder;
			}
		}
		throw new Exception("Could not find folder for picture " + picture.getRepositoryFilePath());
	}
	public String getFolderNameForPicture(Picture picture) throws Exception {
		return getFolderForPicture(picture).getPath();
	}
	public void movePictureToFolder(Picture picture, String folderName) throws Exception {
		Folder currentFolder = getFolderForPicture(picture);
		if (!currentFolder.getPath().equals(folderName)) {
			currentFolder.remove(picture);
			Folder newFolder = getFolder(folderName);
			if (newFolder == null) {
				newFolder = createFolder("photonwrangler", folderName);
			}
			newFolder.add(picture);
		}
	}
	public String getOriginalBasePath() {
		return ORIG_PATH;
	}
	public String getBasePath() {
		return BASE_PATH;
	}
	public String getJavascriptPath() {
		return getOriginalBasePath();
	}
	public String getPathFromJavascript() {
		return FROM_ORIG_PATH;
	}
	public String getPagePath() {
		return PAGE_PATH;
	}
	public String getSiteUrl() {
		return SITE_URL;
	}
	@Override
	public void update(Observable o, Object arg) {
		if (!isDirty) {
			isDirty = true;
			this.setChanged();
			this.notifyObservers(true);
		}
	}
	public void setIsDirty(boolean isDirty) {
		this.isDirty = isDirty;
		this.setChanged();
		this.notifyObservers(isDirty);
	}
	public boolean getIsDirty() {
		return this.isDirty;
	}
	public void replace(Picture orig, Picture clon) throws Exception {
		Folder folder = getFolderForPicture(orig);
		folder.replace(orig, clon);
	}
}
