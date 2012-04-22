package com.twillekes.portfolio;

import java.util.List;

// Portfolio has albums (defined by tags?)
// Albums have pictures
// Pictures have tags and metadata and an image and preview
// Metadata is fixed information that won't change (e.g. focal length)
// Tags are information added later

public class Picture {
	// Fields
	private String fileName;
	private String remoteFileName;
	private Metadata metadata;
	private List<String> tags;
	
	// Constructor
	public Picture() {
		fileName = "undefined";
	}
	
	// Methods
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
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

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}
	
	public String toString() {
		return "File name: " + this.fileName + " Remote file name: " + this.remoteFileName + " Metadata: " + this.metadata.toString();
	}
}
