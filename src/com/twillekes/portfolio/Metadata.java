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
	private String time;
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
	public boolean equals(Metadata metadata) {
		if (((title != null && title.equals(metadata.title)) || (title == null && metadata.title == null)) &&
			((description != null && description.equals(metadata.description)) || (description == null && metadata.description == null)) &&
			((rating != null && rating.equals(metadata.rating)) || (rating == null && metadata.rating == null)) &&
			((orientation != null && orientation.equals(metadata.orientation)) || (orientation == null && metadata.orientation == null)) &&
			((subject != null && subject.equals(metadata.subject)) || (subject == null && metadata.subject == null)) &&
			((season != null && season.equals(metadata.season)) || (season == null && metadata.season == null)) &&
			((camera != null && camera.equals(metadata.camera)) || (camera == null && metadata.camera == null)) &&
			((lens != null && lens.equals(metadata.lens)) || (lens == null && metadata.lens == null)) &&
			((film != null && film.equals(metadata.film)) || (film == null && metadata.film == null)) &&
			((chrome != null && chrome.equals(metadata.chrome)) || (chrome == null && metadata.chrome == null)) &&
			((format != null && format.equals(metadata.format)) || (format == null && metadata.format == null)) &&
			((date != null && date.equals(metadata.date)) || (date == null && metadata.date == null)) &&
			((time != null && time.equals(metadata.time)) || (time == null && metadata.time == null)) &&
			((realDate != null && realDate.equals(metadata.realDate)) || (realDate == null && metadata.realDate == null)) &&
			((year != null && year.equals(metadata.year)) || (year == null && metadata.year == null)) &&
			((month != null && month.equals(metadata.month)) || (month == null && metadata.month == null)) &&
			((direction != null && direction.equals(metadata.direction)) || (direction == null && metadata.direction == null)) &&
			((filters != null && filters.equals(metadata.filters)) || (filters == null && metadata.filters == null)) &&
			((doNotShow != null && doNotShow.equals(metadata.doNotShow)) || (doNotShow == null && metadata.doNotShow == null)) &&
			((isNew != null && isNew.equals(metadata.isNew)) || (isNew == null && metadata.isNew == null)) &&
			((isDiscarded != null && isDiscarded.equals(metadata.isDiscarded)) || (isDiscarded == null && metadata.isDiscarded == null)) &&
			((isFavorite != null && isFavorite.equals(metadata.isFavorite)) || (isFavorite == null && metadata.isFavorite == null)) &&
			((isInFeed != null && isInFeed.equals(metadata.isInFeed)) || (isInFeed == null && metadata.isInFeed == null))) {
			return true;
		}
		return false;
	}
	public Metadata clone() throws CloneNotSupportedException {
	    Metadata clone = (Metadata)super.clone();
	    if (realDate != null) {
	    	clone.realDate = (Date)realDate.clone();
	    }
	    return clone;
	}
	
	public class MetadataSchema extends Observable {
		private List<String> orientations, subjects, seasons, cameras, lenses, films,
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
		public String[] getCategoryValues(String categoryName) throws Exception {
			List<String> catValues = getCategoryValuesAsList(categoryName);
			return catValues.toArray(new String[catValues.size()]);
		}
		public List<String> getCategoryValuesAsList(String categoryName) throws Exception {
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
				if (Metadata.schema.getCategoryValuesAsList(cat).size() > 0) {
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
		if (this.title == null || !this.title.equals(title)) {
			this.title = title;
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if (this.description == null || !this.description.equals(description)) {
			this.description = description;
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		if (this.rating == null || !this.rating.equals(rating)) {
			this.rating = rating;
			schema.update(rating, schema.ratings);
		}
	}
	
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		if (this.orientation == null || !this.orientation.equals(orientation)) {
			this.orientation = orientation;
			schema.update(orientation, schema.orientations);
		}
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		if (this.subject == null || !this.subject.equals(subject)) {
			this.subject = subject;
			schema.update(subject, schema.subjects);
		}
	}

	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		if (this.season == null || !this.season.equals(season)) {
			this.season = season;
			schema.update(season, schema.seasons);
		}
	}
	
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		if (this.camera == null || !this.camera.equals(camera)) {
			this.camera = camera;
			schema.update(camera, schema.cameras);
		}
	}
	
	public String getLens() {
		return lens;
	}
	public void setLens(String lens) {
		if (this.lens == null || !this.lens.equals(lens)) {
			this.lens = lens;
			schema.update(lens, schema.lenses);
		}
	}
	
	public String getFilm() {
		return film;
	}
	public void setFilm(String film) {
		if (this.film == null || !this.film.equals(film)) {
			this.film = film;
			schema.update(film, schema.films);
		}
	}

	public String getChrome() {
		return chrome;
	}
	public void setChrome(String chrome) {
		if (this.chrome == null || !this.chrome.equals(chrome)) {
			this.chrome = chrome;
			schema.update(chrome, schema.chromes);
		}
	}

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		if (this.format == null || !this.format.equals(format)) {
			this.format = format;
			schema.update(format, schema.formats);
		}
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		if (this.time == null || !this.time.equals(time)) {
			this.time = time;
		}
	}
	public void setTime(long hour, long minute, String amPm) {
		this.time = String.valueOf(hour) + ":" + String.valueOf(minute) + " " + amPm;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		if (this.date == null || !this.date.equals(date)) {
			this.date = date;
			
			try {
				this.realDate = this.valiDate(this.date, this.time);
			} catch (Exception e) {
				this.realDate = null;
			}
			this.setChanged();
			this.notifyObservers();
		}
	}
	public Date valiDate(String date, String time) throws Exception {
		Date dat = null;
		if (date.equals("Unknown")) {
			throw new Exception("No date provided");
		}
		String hourFormat = "";
		String dateToParse = date;
		if (time != null) {
			hourFormat = "hh:mm aa ";
			dateToParse = time + " " + dateToParse;
		}
		DateFormat dateFormat = new SimpleDateFormat(hourFormat + "MMM dd, yyyyy");
		try {
			dat = (Date)dateFormat.parse(dateToParse);
		} catch (ParseException e) {
			dat = null;
		}
		if (dat != null) {
			return dat;
		}
		dateFormat = new SimpleDateFormat(hourFormat + "MMM, yyyy");
		try {
			dat = (Date)dateFormat.parse(dateToParse);
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
		if (this.year == null || !this.year.equals(year)) {
			this.year = year;
			schema.update(year, schema.years);
		}
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		if (this.month == null || !this.month.equals(month)) {
			this.month = month;
			schema.update(month, schema.months);
		}
	}

	public enum Direction {
		NORTH, WEST, EAST, SOUTH, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST, NONE
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		if (this.direction == null || !this.direction.equals(direction)) {
			this.direction = direction;
			schema.update(direction, schema.directions);
		}
	}

	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		if (filters.equals("")) {
			if (this.filters != null) {
				this.setChanged();
				this.notifyObservers();
			}
			this.filters = null;
		} else {
			if (this.filters == null || !this.filters.equals(filters)) {
				this.filters = filters;
				schema.update(filters, schema.filterss);
			}
		}
	}

	public String getDoNotShow() {
		return doNotShow;
	}
	public void setDoNotShow(String doNotShow) {
		if (doNotShow.equals("")) {
			this.doNotShow = null;
		} else {
			if (this.doNotShow == null || !this.doNotShow.equals(doNotShow)) {
				this.doNotShow = doNotShow;
				schema.update(doNotShow, schema.doNotShows);
			}
		}
	}

	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		if (isNew.equals("")) {
			isNew = null;
		} else {
			if (this.isNew == null || !this.isNew.equals(isNew)) {
				this.isNew = isNew;
				schema.update(isNew, schema.isNews);
			}
		}
	}

	public String getIsDiscarded() {
		return isDiscarded;
	}
	public void setIsDiscarded(String isDiscarded) {
		if (isDiscarded.equals("")) {
			this.isDiscarded = null;
		} else {
			if (this.isDiscarded == null || !this.isDiscarded.equals(isDiscarded)) {
				this.isDiscarded = isDiscarded;
				schema.update(isDiscarded, schema.isDiscardeds);
			}
		}
	}
	public String getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(String isFavorite) {
		if (isFavorite.equals("")) {
			this.isFavorite = null;
		} else {
			if (this.isFavorite == null || !this.isFavorite.equals(isFavorite)) {
				this.isFavorite = isFavorite;
				schema.update(isFavorite, schema.isFavorites);
			}
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
			if (this.isInFeed == null || !this.isInFeed.equals(isInFeed)) {
				this.isInFeed = isInFeed;
				schema.update(isInFeed, schema.isInFeeds);
			}
		}
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		this.setChanged();
		this.notifyObservers();
	}
}
