package com.twillekes.portfolio;

public class Words {
	private String filename;
	private String title;
	private String doNotShow;
	public String getFilePath() {
		return filename;
	}
	public void setFilePath(String filePath) {
		this.filename = filePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDoNotShow() {
		return doNotShow;
	}
	public void setDoNotShow(String doNotShow) {
		this.doNotShow = doNotShow;
	}
}
