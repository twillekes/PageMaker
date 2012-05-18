package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.List;

// Portfolio has albums (defined by tags?)
// Albums have pictures
// Pictures have tags and metadata and an image and preview
// Metadata is fixed information that won't change (e.g. focal length)
// Tags are information added later

public class Picture implements Comparable<Picture>, Cloneable {
	// Fields
	private String filePath;
	private String localFilePath;
	private Metadata metadata;
	private List<String> tags;
	
	// Constructor
	public Picture() {
		filePath = "undefined";
		this.metadata = new Metadata();
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
	public void setFilePath(String fileName) {
		this.filePath = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getThumbFilePath() {
		return Picture.getThumbName(this.getLocalFilePath());
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getLocalFilePath() {
		return localFilePath;
	}
	public void setLocalFilePath(String filePath) {
		this.localFilePath = filePath;
	}
	public String toString() {
		return "File name: " + this.filePath + " File Path: " + this.localFilePath + " Metadata: " + this.metadata.toString();
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
}
