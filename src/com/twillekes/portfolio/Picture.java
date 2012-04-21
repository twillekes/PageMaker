package com.twillekes.portfolio;

import java.util.List;

// Portfolio has albums (defined by tags?)
// Albums have pictures
// Pictures have tags and metadata and an image and preview
// Metadata is fixed information that won't change (e.g. focal length)
// Tags are information added later

public class Picture {
	// Fields
	private String title;
	private String fileName;
	private String thumbFileName;
	private String description;
	private float rating;
	private Metadata metadata;
	private List<String> tags;
	
	// Constructor
	public Picture() {
		fileName = "undefined";
		thumbFileName = "undefined";
		title = "Untitled";
		description = "none";
	}
	
	public String toString() {
		return "title: " + this.getTitle() +
			   ", fileName: " + this.getFileName() +
			   ", thumbFileName: " + this.getThumbFileName() +
			   ", description: " + this.getDescription();
	}

	// Methods
	public String getThumbFileName() {
		return thumbFileName;
	}

	public String getTitle() {
		return title;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setThumbFileName(String thumbFileName) {
		this.thumbFileName = thumbFileName;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	public void setRating(String rating) {
		
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
}
