package com.twillekes.userInteraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInterface.PreviewUserInterface;

public class Selection extends Observable {
	private static Selection theInstance = null;
	List<PreviewUserInterface> selected;
	public Selection() {
		theInstance = this;
		selected = new ArrayList<PreviewUserInterface>();
	}
	public static Selection instance() {
		if (theInstance == null) {
			theInstance = new Selection();
		}
		return theInstance;
	}
	public void add(PreviewUserInterface previewUserInterface) {
		selected.add(previewUserInterface);
		this.setChanged();
		this.notifyObservers(selected.size());
	}
	public void remove(PreviewUserInterface previewUserInterface) {
		selected.remove(previewUserInterface);
		this.setChanged();
		this.notifyObservers(selected.size());
	}
	public void removeAll() {
		selected.removeAll(null);
		this.setChanged();
		this.notifyObservers(selected.size());
	}
	public List<PreviewUserInterface> get() {
		return selected;
	}
	public List<Picture> getPictures() {
		Iterator<PreviewUserInterface> it = selected.iterator();
		List<Picture> pics = new ArrayList<Picture>();
		while(it.hasNext()) {
			PreviewUserInterface pUi = it.next();
			pics.add(pUi.getPicture());
		}
		return pics;
	}
}
