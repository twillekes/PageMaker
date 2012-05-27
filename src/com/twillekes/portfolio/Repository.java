package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Change to "baseUrl", which is SITE_URL + account name and
// "relativeUrl", which is the path (e.g. "newImages/")
// Then for the local case, just set baseUrl to ""

public class Repository {
	private static final String ORIG_PATH = "../Page/My-personal-web-page/page/";
	private static final String FROM_ORIG_PATH = "../repository/";
	private static final String BASE_PATH = "repository/";
	private static final String PAGE_PATH = "page/";
	private static final String FILE_NAME = BASE_PATH + "repository.json";
	private static final String SITE_URL = "http://members.shaw.ca/";
	private String account;
	private String path;
	private List<Picture> pictures;
	private List<Picture> trash;
	private static List<Repository> repositories = null;
	public Repository(String account, String path) {
		this.account = account;
		this.path = path;
		this.pictures = new ArrayList<Picture>();
		this.trash = new ArrayList<Picture>();
		
		if (repositories == null) {
			repositories = new ArrayList<Repository>();
		}
		repositories.add(this);
	}
	public String getAccount() {
		return this.account;
	}
	public static String getFileName() {
		return FILE_NAME;
	}
	public String getBaseUrl() {
		return SITE_URL + this.account + "/";
	}
	public String getRelativeUrl() {
		return this.path + "/";
	}
	public String getUrl() {
		return getBaseUrl() + getRelativeUrl();
	}
	public String getRelativePath() {
		return BASE_PATH + this.getRelativeUrl();
	}
	public String getOriginalPath() {
		return ORIG_PATH + this.getRelativeUrl();
	}
	public void add(Picture picture) {
		this.pictures.add(picture);
	}
	public void remove(Picture picture) throws Exception {
		if (!this.pictures.remove(picture)) {
			throw new Exception("Could not remove picture " + picture.getRepositoryFilePath() + " from repository " + this.getPath());
		}
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
			names.add(repo.getPath());
		}
		return names;
	}
	public static Repository get(String path) throws Exception {
		Iterator<Repository> it = repositories.iterator();
		while(it.hasNext()) {
			Repository rep = it.next();
			if (rep.getPath().equals(path)) {
				return rep;
			}
		}
		throw new Exception("Unable to find repository with path " + path);
	}
	public String getPath() {
		return this.path;
	}
	public static Repository getRepositoryForPicture(Picture picture) throws Exception {
		Iterator<Repository> it = repositories.iterator();
		while(it.hasNext()) {
			Repository repo = it.next();
			if (repo.containsPicture(picture)) {
				return repo;
			}
		}
		throw new Exception("Could not find repository for picture " + picture.getRepositoryFilePath());
	}
	public static String getRepositoryNameForPicture(Picture picture) throws Exception {
		return getRepositoryForPicture(picture).getPath();
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
	public static void movePictureToRepository(Picture picture, String repoName) {
		// TODO
	}
	public static String getOriginalBasePath() {
		return ORIG_PATH;
	}
	public static String getBasePath() {
		return BASE_PATH;
	}
	public static String getJavascriptPath() {
		return getOriginalBasePath();
	}
	public static String getPathFromJavascript() {
		return FROM_ORIG_PATH;
	}
	public static String getPagePath() {
		return PAGE_PATH;
	}
	public void moveToTrash(Picture picture) throws Exception {
		if (!pictures.remove(picture)) {
			throw new Exception("Could not find picture " + picture.getRepositoryFilePath() + " in repository " + this.getPath());
		}
		trash.add(picture);
	}
	public List<Picture> getTrash() {
		return trash;
	}
	public void addToTrash(Picture picture) {
		trash.add(picture);
	}
}
