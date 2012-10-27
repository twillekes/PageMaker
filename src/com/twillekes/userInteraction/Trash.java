package com.twillekes.userInteraction;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.apache.commons.io.FileUtils;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Folder;
import com.twillekes.portfolio.Repository;
import com.twillekes.userInterface.Application;

public class Trash extends Observable {
	private static Trash theInstance = null;
	public static Trash instance() {
		if (theInstance == null) {
			theInstance = new Trash();
		}
		return theInstance;
	}
	public void moveToTrash(List<Picture> pictures) throws Exception {
		Iterator<Picture> it = pictures.iterator();
		while(it.hasNext()) {
			Picture picture = it.next();
			Repository.instance().getFolderForPicture(picture).moveToTrash(picture);
			Application.getPortfolio().remove(picture);
		}
		instance().setChanged();
		instance().notifyObservers(instance().collect().size());
	}
	public List<Picture> collect() {
		List<Picture> trash = new ArrayList<Picture>();
		Iterator<Folder> it = Repository.instance().getFolders().iterator();
		while(it.hasNext()) {
			Folder repo = it.next();
			trash.addAll(repo.getTrash());
		}
		return trash;
	}
	public void empty() {
		Iterator<Folder> it = Repository.instance().getFolders().iterator();
		while(it.hasNext()) {
			Folder repo = it.next();
			List<Picture> trash = repo.getTrash();
			Iterator<Picture> trashIt = trash.iterator();
			while(trashIt.hasNext()) {
				Picture picture = trashIt.next();
				deletePictureFiles(picture);
				trashIt.remove();
			}
		}
		this.setChanged();
		this.notifyObservers(0);
	}
	private static void deletePictureFiles(Picture picture) {
		FileUtils.deleteQuietly(new File(picture.getDiscardedFilePath() + ".json"));
		FileUtils.deleteQuietly(new File(picture.getDiscardedFilePath()));
		FileUtils.deleteQuietly(new File(picture.getDiscardedThumbFilePath()));
	}
}
