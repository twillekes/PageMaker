package com.twillekes.portfolio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Metadata extends Observable implements Cloneable, Observer {
	// Fields
	// WARNING: These must appear as expected by the page's Javascript as they
	// are serialized directly into JSON from here!
	private String title;
	private String description;
	private String rating;
	private String orientation;
	private String subject;
	private String season;
	private String camera;
	private String lens;
	private String film;
	private String chrome;
	private String format;
	private String date;
	private Date realDate;
	private String year;
	private String month;
	private String direction;
	private String filters;
	private String doNotShow;
	private String isNew;
	private String isDiscarded;
	private String isFavorite;
	private String isInFeed;
	public static MetadataSchema schema = null;
	
	public Metadata() {
		if (schema == null) {
			schema = new MetadataSchema();
			schema.addObserver(this);
		}
	}
	public Metadata clone() throws CloneNotSupportedException {
	    Metadata clone = (Metadata)super.clone();
	    if (realDate != null) {
	    	clone.realDate = (Date)realDate.clone();
	    }
	    return clone;
	}
	
	public class MetadataSchema extends Observable {
		public List<String> orientations, subjects, seasons, cameras, lenses, films,
			chromes, formats, directions, filterss, doNotShows, isNews, isDiscardeds,
			isFavorites, ratings, years, months, isInFeeds;
		public MetadataSchema() {
			orientations = new ArrayList<String>();
			subjects = new ArrayList<String>();
			seasons = new ArrayList<String>();
			cameras = new ArrayList<String>();
			lenses = new ArrayList<String>();
			films = new ArrayList<String>();
			chromes = new ArrayList<String>();
			formats = new ArrayList<String>();
			directions = new ArrayList<String>();
			filterss = new ArrayList<String>();
			doNotShows = new ArrayList<String>();
			isNews = new ArrayList<String>();
			isDiscardeds = new ArrayList<String>();
			isFavorites = new ArrayList<String>();
			ratings = new ArrayList<String>();
			years = new ArrayList<String>();
			months = new ArrayList<String>();
			isInFeeds = new ArrayList<String>();
		}
		public void update(String newEntry, List<String> arr) {
			boolean addIt = true;
			Iterator<String> it = arr.iterator();
			while(it.hasNext()) {
				String item = it.next();
				if (item.equals(newEntry)) {
					addIt = false;
					break;
				}
			}
			if (addIt) {
				arr.add(newEntry);
			}
			this.setChanged();
			this.notifyObservers();
		}
		public List<String> getCategoryValues(String categoryName) throws Exception {
			if (categoryName.equals("subject")) {
				return this.subjects;
			} else if (categoryName.equals("orientation")) {
				return this.orientations;
			} else if (categoryName.equals("season")) {
				return this.seasons;
			} else if (categoryName.equals("camera")) {
				return this.cameras;
			} else if (categoryName.equals("lens")) {
				return this.lenses;
			} else if (categoryName.equals("film")) {
				return this.films;
			} else if (categoryName.equals("chrome")) {
				return this.chromes;
			} else if (categoryName.equals("format")) {
				return this.formats;
			} else if (categoryName.equals("year")) {
				return this.years;
			} else if (categoryName.equals("month")) {
				return this.months;
			} else if (categoryName.equals("direction")) {
				return this.directions;
			} else if (categoryName.equals("rating")) {
				return this.ratings;
			} else if (categoryName.equals("filters")) {
				return this.filterss;
			} else if (categoryName.equals("doNotShow")) {
				return this.doNotShows;
			} else if (categoryName.equals("isNew")) {
				return this.isNews;
			} else if (categoryName.equals("isDiscarded")) {
				return this.isDiscardeds;
			} else if (categoryName.equals("isFavorite")) {
				return this.isFavorites;
			} else if (categoryName.equals("isInFeed")) {
				return this.isInFeeds;
			} else {
				throw new Exception("Unrecognized category name: " + categoryName);
			}
		}
	}
	
	public static List<String> getCategories() {
		return Arrays.asList(
			"subject", "orientation","season", "camera", "lens", "film", "chrome",
			"format", "year", "month", "direction", "rating");
	}
	
	public static List<String> getFlagCategories() {
		List<String> categories = Arrays.asList("doNotShow", "isNew", "isDiscarded", "isFavorite", "isInFeed");
		List<String> populated = new ArrayList<String>();
		Iterator<String> it = categories.iterator();
		while(it.hasNext()) {
			String cat = it.next();
			try {
				if (Metadata.schema.getCategoryValues(cat).size() > 0) {
					populated.add(cat);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return populated;
	}
	
	public static List<String> getFreeformCategories() {
		return Arrays.asList("filters", "title", "description");
	}
	
	public String getCategoryValue(String categoryName) throws Exception {
		if (categoryName.equals("subject")) {
			return this.subject;
		} else if (categoryName.equals("orientation")) {
			return this.orientation;
		} else if (categoryName.equals("season")) {
			return this.season;
		} else if (categoryName.equals("camera")) {
			return this.camera;
		} else if (categoryName.equals("lens")) {
			return this.lens;
		} else if (categoryName.equals("film")) {
			return this.film;
		} else if (categoryName.equals("chrome")) {
			return this.chrome;
		} else if (categoryName.equals("format")) {
			return this.format;
		} else if (categoryName.equals("year")) {
			return this.year;
		} else if (categoryName.equals("month")) {
			return this.month;
		} else if (categoryName.equals("direction")) {
			return this.direction;
		} else if (categoryName.equals("rating")) {
			return this.rating;
		} else if (categoryName.equals("filters")) {
			return this.filters;
		} else if (categoryName.equals("doNotShow")) {
			return this.doNotShow;
		} else if (categoryName.equals("isNew")) {
			return this.isNew;
		} else if (categoryName.equals("isDiscarded")) {
			return this.isDiscarded;
		} else if (categoryName.equals("isFavorite")) {
			return this.isFavorite;
		} else if (categoryName.equals("isInFeed")) {
			return this.isInFeed;
		} else {
			throw new Exception("Unrecognized category name: " + categoryName);
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
		schema.update(rating, schema.ratings);
	}
	
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
		schema.update(orientation, schema.orientations);
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
		schema.update(subject, schema.subjects);
	}

	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
		schema.update(season, schema.seasons);
	}
	
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
		schema.update(camera, schema.cameras);
	}
	
	public String getLens() {
		return lens;
	}
	public void setLens(String lens) {
		this.lens = lens;
		schema.update(lens, schema.lenses);
	}
	
	public String getFilm() {
		return film;
	}
	public void setFilm(String film) {
		this.film = film;
		schema.update(film, schema.films);
	}

	public String getChrome() {
		return chrome;
	}
	public void setChrome(String chrome) {
		this.chrome = chrome;
		schema.update(chrome, schema.chromes);
	}

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
		schema.update(format, schema.formats);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
		
		try {
			this.realDate = this.validateDate(date);
		} catch (Exception e) {
			this.realDate = null;
		}
		this.setChanged();
		this.notifyObservers();
	}
	public Date validateDate(String date) throws Exception {
		Date dat = null;
		if (date.equals("Unknown")) {
			throw new Exception("No date provided");
		}
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyyy");
		try {
			dat = (Date)dateFormat.parse(date);
		} catch (ParseException e) {
			this.date = null;
		}
		if (dat != null) {
			return dat;
		}
		dateFormat = new SimpleDateFormat("MMM, yyyy");
		try {
			dat = (Date)dateFormat.parse(date);
		} catch (ParseException e) {
			System.out.println("Date/month parse exception: " + e.getMessage());
		}
		return dat;
	}
	public Date getRealDate() {
		return this.realDate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
		schema.update(year, schema.years);
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
		schema.update(month, schema.months);
	}

	public enum Direction {
		NORTH, WEST, EAST, SOUTH, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST, NONE
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
		schema.update(direction, schema.directions);
	}

	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
		schema.update(filters, schema.filterss);
	}

	public String getDoNotShow() {
		return doNotShow;
	}
	public void setDoNotShow(String doNotShow) {
		if (doNotShow.equals("")) {
			this.doNotShow = null;
		} else {
			this.doNotShow = doNotShow;
			schema.update(doNotShow, schema.doNotShows);
		}
	}

	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		if (isNew.equals("")) {
			isNew = null;
		} else {
			this.isNew = isNew;
			schema.update(isNew, schema.isNews);
		}
	}

	public String getIsDiscarded() {
		return isDiscarded;
	}
	public void setIsDiscarded(String isDiscarded) {
		if (isDiscarded.equals("")) {
			this.isDiscarded = null;
		} else {
			this.isDiscarded = isDiscarded;
			schema.update(isDiscarded, schema.isDiscardeds);
		}
	}
	public String getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(String isFavorite) {
		if (isFavorite.equals("")) {
			this.isFavorite = null;
		} else {
			this.isFavorite = isFavorite;
			schema.update(isFavorite, schema.isFavorites);
		}
	}
	
	public String toString() {
		return "Title: " + this.title +
			   " Camera: " + this.camera +
			   " Film: " + this.film +
			   " Orientation: " + this.orientation +
			   " Direction: " + this.direction +
			   " Filters: " + this.filters +
			   " Format: " + this.format +
			   " Date: " + this.date + 
			   " Season: " + this.season +
			   " Subject: " + this.subject +
			   " Rating: " + this.rating +
			   " isNew: " + this.isNew;
	}
	public String getIsInFeed() {
		return isInFeed;
	}
	public void setIsInFeed(String isInFeed) {
		if (isInFeed.equals("")) {
			this.isInFeed = null;
		} else {
			this.isInFeed = isInFeed;
			schema.update(isInFeed, schema.isInFeeds);
		}
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		this.setChanged();
		this.notifyObservers();
	}
}
