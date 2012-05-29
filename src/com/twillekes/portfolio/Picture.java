package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

// Portfolio has albums (defined by tags?)
// Albums have pictures
// Pictures have tags and metadata and an image and preview
// Metadata is fixed information that won't change (e.g. focal length)
// Tags are information added later

public class Picture extends Observable implements Comparable<Picture>, Cloneable, Observer {
	// Fields
	// WARNING: These must appear as expected by the page's Javascript as they
	// are serialized directly into JSON from here!
	private String filePath;
	private String localFilePath;
	private Metadata metadata;
	private List<String> tags;
	
	// Constructor
	public Picture() {
		filePath = "undefined";
		localFilePath = "undefined";
		this.metadata = new Metadata();
		this.metadata.addObserver(this);
	}
	public Picture clone() throws CloneNotSupportedException {
	    Picture clone = (Picture)super.clone();
	    if (metadata != null) {
	    	clone.metadata = metadata.clone();
	    }
	    if (tags != null) {
	    	clone.tags = new ArrayList<String>(tags);;
	    }
	    return clone;
	}
	public void setFileName(String fileName) throws Exception {
		Folder repo = Repository.instance().getFolderForPicture(this);
		if (repo == null) {
			throw new Exception("Must add picture to repository before setting its name");
		}
		this.setFilePath(repo.getUrl() + fileName);
		this.setLocalFilePath(Repository.instance().getPathFromJavascript() + fileName);
		this.setChanged();
		this.notifyObservers();
	}
	// "File path" is the one used when the page is served from the web
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		this.setChanged();
		this.notifyObservers();
	}
	public String getFilePath() {
		return filePath;
	}
	// "Local file path" is the one used when the page is served from the local file system
	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
		this.setChanged();
		this.notifyObservers();
	}
	public String getLocalFilePath() {
		return localFilePath;
	}
	// "Repository file path" is used when reading files from within this PageMaker application
	public String getRepositoryFilePath() {
		return Repository.instance().getBasePath() + getFileName(this.getLocalFilePath());
	}
	public String getRepositoryThumbFilePath() {
		return Picture.getThumbName(this.getRepositoryFilePath());
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
		this.setChanged();
		this.notifyObservers();
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
		this.setChanged();
		this.notifyObservers();
	}
	public String toString() {
		return "File path: " + this.filePath + " Metadata: " + this.metadata.toString();
	}
	public int compareTo(Picture o) {
		if (!(o instanceof Picture)) {
			throw new ClassCastException("A picture object was expected");
		}
		if (this.metadata.getRealDate() == null || o.metadata.getRealDate() == null) {
			return 1;
		}
		int result = o.metadata.getRealDate().compareTo(this.metadata.getRealDate());
		if (result == 0) {
			return 1;
		}
		return result;
	}
	public static String getThumbName(String filePath) {
		int dotPos = filePath.lastIndexOf(".");
		String thumbExt = filePath.substring(dotPos);
		return filePath.substring(0, dotPos) + "_thumb" + thumbExt;
	}
	public static String getFileName(String filePath) {
		int dotPos = filePath.lastIndexOf("/");
		dotPos += 1;
		return filePath.substring(dotPos);
	}
	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
	}
}
