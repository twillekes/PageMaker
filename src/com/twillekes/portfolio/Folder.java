package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

// Change to "baseUrl", which is SITE_URL + account name and
// "relativeUrl", which is the path (e.g. "newImages/")
// Then for the local case, just set baseUrl to ""

public class Folder extends Observable implements Observer {
	private String account;
	private String path;
	private List<Picture> pictures;
	private List<Picture> trash;
	public Folder(String account, String path) {
		this.account = account;
		this.path = path;
		this.pictures = new ArrayList<Picture>();
		this.trash = new ArrayList<Picture>();
	}
	public String getAccount() {
		return this.account;
	}
	public String getBaseUrl() {
		return Repository.instance().getSiteUrl() + this.account + "/";
	}
	public String getRelativeUrl() {
		return this.path + "/";
	}
	public String getUrl() {
		return getBaseUrl() + getRelativeUrl();
	}
	public String getRelativePath() {
		return Repository.instance().getBasePath() + this.getRelativeUrl();
	}
	public String getOriginalPath() {
		return Repository.instance().getOriginalBasePath() + this.getRelativeUrl();
	}
	public void add(Picture picture) {
		this.pictures.add(picture);
		picture.addObserver(this);
		this.setChanged();
		this.notifyObservers();
	}
	public void remove(Picture picture) throws Exception {
		if (!this.pictures.remove(picture)) {
			throw new Exception("Could not remove picture " + picture.getRepositoryFilePath() + " from repository " + this.getPath());
		}
		this.setChanged();
		this.notifyObservers();
	}
	public List<Picture> getPictures() {
		return this.pictures;
	}
	public String getPath() {
		return this.path;
	}
	public boolean containsPicture(Picture picture) {
		Iterator<Picture> it = this.pictures.iterator();
		while(it.hasNext()) {
			Picture pic = it.next();
			if (picture.equals(pic)) {
				return true;
			}
		}
		return false;
	}
	public void moveToTrash(Picture picture) throws Exception {
		if (!pictures.remove(picture)) {
			throw new Exception("Could not find picture " + picture.getRepositoryFilePath() + " in repository " + this.getPath());
		}
		trash.add(picture);
		this.setChanged();
		this.notifyObservers();
	}
	public List<Picture> getTrash() {
		return trash;
	}
	public void addToTrash(Picture picture) {
		trash.add(picture);
		this.setChanged();
		this.notifyObservers();
	}
	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
	}
	public void replace(Picture orig, Picture clon) throws Exception {
		if (!pictures.remove(orig)) {
			throw new Exception("Unable to replace picture " + orig.getRepositoryFilePath());
		}
		if (!pictures.add(clon)) {
			throw new Exception("Unable to add clone picture " + clon.getRepositoryFilePath());
		}
		update(null, null);
	}
}
