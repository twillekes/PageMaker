package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Portfolio {
	public class CategoryRecord {
		public String categoryValue;
		public List<Integer> imageIndexes;
		public CategoryRecord(String catValue) {
			this.categoryValue = catValue;
			this.imageIndexes = new ArrayList<Integer>();
		}
	}

	private SortedSet<Picture> pictures; // The complete list of pictures in the portfolio
	private SortedSet<Words> words;
	private Map<String, List<CategoryRecord>> categoryDictionary;
	private String[] categories = {"subject", "orientation", "season", "camera", "lens", "film", "chrome",
            "format", "year", "month", "direction", "rating"};
	
	public Portfolio() {
		pictures = new TreeSet<Picture>();
		words = new TreeSet<Words>();
	}
	public String toString() {
		return "There are " + pictures.size() + " images";
	}
	public SortedSet<Picture> getPictures() {
		return pictures;
	}
	public void addPicture(Picture p) {
		pictures.add(p);
	}
	// Top level is a map of string (category name: subject, season, etc.) to...
	// Next level is a map of string (category value: houses, winter, etc.) = 'categoryValue' to...
	// List<Integer> that are the picture indexes  = 'imageIndexes'
	public void findAllCategories() {
		categoryDictionary = new HashMap<String, List<CategoryRecord>>();
		CategoryRecord newCatRecord = new CategoryRecord("New");
		CategoryRecord favCatRecord = new CategoryRecord("Favorites");
		Boolean firstTime = true;
		for (int i = 0; i < categories.length; i++) {
			List<CategoryRecord> categoryList = new ArrayList<CategoryRecord>();
			categoryList.add(newCatRecord);
			categoryList.add(favCatRecord);
			Iterator<Picture> it = pictures.iterator();
			Integer picIndex = -1;
			while(it.hasNext()) {
				picIndex++;
				
				Picture pic = it.next();
				// Go through every picture and associate it's value for that category with
				// an existing or new list
				try {
					String catValue = pic.getMetadata().getCategoryValue(categories[i]);
					
					if (pic.getMetadata().getIsDiscarded() != null) {
						continue;
					}
					
					if (pic.getMetadata().getIsNew() != null) {
						if (firstTime) {
							newCatRecord.imageIndexes.add(picIndex);
						}
						continue;
					}
					
					// See if we have this cat value yet...
					CategoryRecord catRecord = null;
					Iterator<CategoryRecord> cit = categoryList.iterator();
					while(cit.hasNext()) {
						CategoryRecord rec = cit.next();
						if (rec.categoryValue.equals(catValue)) {
							catRecord = rec;
							break;
						}
					}
					if (catRecord == null) {
						catRecord = new CategoryRecord(catValue);
						categoryList.add(catRecord);
					}
					catRecord.imageIndexes.add(picIndex);
					
					if (pic.getMetadata().getIsFavorite() != null) {
						if (firstTime) {
							favCatRecord.imageIndexes.add(picIndex);
						}
					}
				} catch (Exception e) {
					System.out.println("Exception finding all categories: " + e.getMessage());
				}
			}
			categoryDictionary.put(categories[i], categoryList);
			firstTime = false;
		}
	}	
	public Map<String, List<CategoryRecord>> getCategoryDictionary() {
		return categoryDictionary;
	}
	public SortedSet<Words> getWords() {
		return words;
	}
	public void addWords(Words words) {
		this.words.add(words);
	}
}
