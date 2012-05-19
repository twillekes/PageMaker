package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Now there is filePath in JS, which is the whole path to the file
// Change to "baseUrl", which is SITE_URL + account name and
// "relativeUrl", which is the path (e.g. "newImages") + file name/extension
// Then for the local case, just set baseUrl to ""

public class Repository {
	public static final String BASE_PATH = "../Page/My-personal-web-page/page/";
	public static final String FILE_NAME = "repository.json";
	private static final String SITE_URL = "http://members.shaw.ca/";
	private String account;
	private String path;
	private List<Picture> pictures;
	private static List<Repository> repositories = null;
	public Repository(String account, String path) {
		this.account = account;
		this.path = path;
		this.pictures = new ArrayList<Picture>();
		
		if (repositories == null) {
			repositories = new ArrayList<Repository>();
		}
		repositories.add(this);
	}
	public String getBaseUrl() {
		return SITE_URL + this.account + "/";
	}
//	public String getUrl() {
//		return BASE_URL + this.account + "/" + this.path + "/";
//	}
	public String getRelativeUrl() {
		return this.path + "/";
	}
	public String getPath() {
		return BASE_PATH + this.getRelativeUrl();
	}
	public void add(Picture picture) {
		this.pictures.add(picture);
	}
	public List<Picture> getPictures() {
		return this.pictures;
	}
	public static List<Repository> get() {
		return repositories;
	}
	public static List<String> getRepositoryNames() {
		Iterator<Repository> it = repositories.iterator();
		List<String> names = new ArrayList<String>();
		while(it.hasNext()) {
			Repository repo = it.next();
			names.add(repo.getRelativeUrl());
		}
		return names;
	}
	public static Repository get(String path) {
		Iterator<Repository> it = repositories.iterator();
		while(it.hasNext()) {
			Repository rep = it.next();
			if (rep.getPath().equals(path)) {
				return rep;
			}
		}
		return null;
	}
}
