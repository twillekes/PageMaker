package com.twillekes.userInteraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.twillekes.portfolio.Picture;
import com.twillekes.userInterface.PreviewUserInterface;

public class Selection {
	public static Selection instance = null;
	List<PreviewUserInterface> selected;
	public Selection() {
		instance = this;
		selected = new ArrayList<PreviewUserInterface>();
	}
	public void add(PreviewUserInterface previewUserInterface) {
		selected.add(previewUserInterface);
	}
	public void remove(PreviewUserInterface previewUserInterface) {
		selected.remove(previewUserInterface);
	}
	public void removeAll() {
		selected.removeAll(null);
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
