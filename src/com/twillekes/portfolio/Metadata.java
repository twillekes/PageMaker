package com.twillekes.portfolio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Metadata {
	private String title;
	private String description;
	private String rating;
	
	// orientation
	public enum Orientation {
		PORTRAIT, LANDSCAPE, SQUARE, PANORAMA
	}
	private String orientation;
	private String subject;
	public enum Season {
		WINTER, SPRING, SUMMER, AUTUMN
	}
	private String season;
	public enum Camera {
		EBONY, NIKON_F3, NIKON_F6, NIKON_FM3A, HASSELBLAD_500CM, HASSELBLAD_SWCM, HASSELBLAD_503CW,
		MAMIYA_7II, MAMIYA_645_PRO_TL, MAMIYA_6, LEICA_M3, LEICA_M2, ROLLEIFLEX_MX_EVS
	}
	private String camera;
	public enum Lens {
		_17_35, _35, _70_300, _28_70,
		_47, _58, _75, _90, _135, _180, _270,
		_38, _40, _50, _60, _80, _100, _120, _150, _250,
		_43, _65, _45, _55
	}
	private String lens;
	public enum Film {
		VELVIA50, VELVIA100, PROVIA100F, ILFORD_DELTA100, ILFORD_DELTA400, ILFORD_PANF, KODAK_TRIX
	}
	private String film;
	private String chrome;
	public enum Format {
		_35MM, _6x45, _6x6, _6x7, _6x9, _6x12, _4x5
	}
	private String format;
	private String date;
	private String year;
	private String month;
	public enum Direction {
		NORTH, WEST, EAST, SOUTH, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST, NONE
	}
	private String direction;
	private String filters;
	private String doNotShow;
	private String isNew;
	private String isDiscarded;
	private String isFavorite;
	
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
			   " Rating: " + this.rating;
	}
	
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public Orientation validateOrientation(String orientation) throws Exception {
		Orientation orient = Orientation.LANDSCAPE;
		if (orientation.equals("Portrait")) {
			orient = Orientation.PORTRAIT;
		} else if (orientation.equals("Landscape")) {
			orient = Orientation.LANDSCAPE;
		} else if (orientation.equals("Square")) {
			orient = Orientation.SQUARE;
		} else if (orientation.equals("Panorama")) {
			orient = Orientation.PANORAMA;
		} else {
			throw new Exception("Unrecognized orientation: "+orientation);
		}
		return orient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public Season validateSeason(String season) throws Exception {
		Season sea = Season.SUMMER;
		if (season.equals("Winter")) {
			sea = Season.WINTER;
		} else if (season.equals("Spring")) {
			sea = Season.SPRING;
		} else if (season.equals("Autumn")) {
			sea = Season.AUTUMN;
		} else if (season.equals("Summer")) {
			sea = Season.SUMMER;
		} else {
			throw new Exception("Unrecognized season: " + season);
		}
		return sea;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	public Camera validateCamera(String camera) throws Exception {
		Camera cam;
		if (camera.equals("Ebony 45S")) {
			cam = Camera.EBONY;
		} else if (camera.equals("Mamiya 7ii")) {
			cam = Camera.MAMIYA_7II;
		} else if (camera.equals("Hasselblad SWC/M")) {
			cam = Camera.HASSELBLAD_SWCM;
		} else if (camera.equals("Hasselblad 501CM")) {
			cam = Camera.HASSELBLAD_500CM;
		} else if (camera.equals("Hasselblad 503CW")) {
			cam = Camera.HASSELBLAD_503CW;
		} else if (camera.equals("Nikon F3")) {
			cam = Camera.NIKON_F3;
		} else if (camera.equals("Nikon F6")) {
			cam = Camera.NIKON_F6;
		} else if (camera.equals("Nikon FM3a")) {
			cam = Camera.NIKON_FM3A;
		} else if (camera.equals("Mamiya 645 Pro-TL")) {
			cam = Camera.MAMIYA_645_PRO_TL;
		} else if (camera.equals("Mamiya 6")) {
			cam = Camera.MAMIYA_6;
		} else if (camera.equals("Leica M3")) {
			cam = Camera.LEICA_M3;
		} else if (camera.equals("Leica M2")) {
			cam = Camera.LEICA_M2;
		} else if (camera.equals("Rolleiflex mx-evs")) {
			cam = Camera.ROLLEIFLEX_MX_EVS;
		} else {
			throw new Exception("Unrecognized camera: " + camera);
		}
		return cam;
	}
	public String getLens() {
		return lens;
	}
	public void setLens(String lens) {
		this.lens = lens;
	}
	public Lens validateLens(String lens) throws Exception {
		Lens len;
		if (lens.equals("47mm")) {
			len = Lens._47;
		} else if (lens.equals("58mm")) {
			len = Lens._58;
		} else if (lens.equals("75mm")) {
			len = Lens._75;
		} else if (lens.equals("90mm")) {
			len = Lens._90;
		} else if (lens.equals("135mm")) {
			len = Lens._135;
		} else if (lens.equals("180mm")) {
			len = Lens._180;
		} else if (lens.equals("270mm")) {
			len = Lens._270;
		} else if (lens.equals("43mm")) {
			len = Lens._43;
		} else if (lens.equals("65mm")) {
			len = Lens._65;
		} else if (lens.equals("150mm")) {
			len = Lens._150;
		} else if (lens.equals("17-35mm")) {
			len = Lens._17_35;
		} else if (lens.equals("28-70mm")) {
			len = Lens._28_70;
		} else if (lens.equals("70-300mm")) {
			len = Lens._70_300;
		} else if (lens.equals("35mm")) {
			len = Lens._35;
		} else if (lens.equals("38mm")) {
			len = Lens._38;
		} else if (lens.equals("40mm")) {
			len = Lens._40;
		} else if (lens.equals("50mm")) {
			len = Lens._50;
		} else if (lens.equals("60mm")) {
			len = Lens._60;
		} else if (lens.equals("80mm")) {
			len = Lens._80;
		} else if (lens.equals("100mm")) {
			len = Lens._100;
		} else if (lens.equals("120mm")) {
			len = Lens._120;
		} else if (lens.equals("250mm")) {
			len = Lens._250;
		} else if (lens.equals("45mm")) {
			len = Lens._45;
		} else if (lens.equals("55mm")) {
			len = Lens._55;
		} else {
			throw new Exception("Unrecognized lens: " + lens);
		}
		return len;
	}
	public String getFilm() {
		return film;
	}
	public void setFilm(String film) {
		this.film = film;
	}
	public Film validateFilm(String film) throws Exception {
		Film fil;
		if (film.equals("Fuji Provia 100F")) {
			fil = Film.PROVIA100F;
		} else if (film.equals("Fuji Velvia 50")) {
			fil = Film.VELVIA50;
		} else if (film.equals("Fuji Velvia 100")) {
			fil = Film.VELVIA100;
		} else if (film.equals("Ilford Delta 100")) {
			fil = Film.ILFORD_DELTA100;
		} else if (film.equals("Ilford Delta 400")) {
			fil = Film.ILFORD_DELTA400;
		} else if (film.equals("Ilford Pan-F")) {
			fil = Film.ILFORD_PANF;
		} else if (film.equals("Kodak Tri-X")) {
			fil = Film.KODAK_TRIX;
		} else {
			throw new Exception("Unrecognized film: " + film);
		}
		return fil;
	}
	public String getChrome() {
		return chrome;
	}
	public void setChrome(String chrome) {
		this.chrome = chrome;
	}
	public Boolean validateChrome(String chrome) throws Exception {
		Boolean is;
		if (chrome.equals("Monochrome")) {
			is = false;
		} else if (chrome.equals("Polychrome")) {
			is = true;
		} else {
			throw new Exception("Unknown isColor: " + chrome);
		}
		return is;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
			//System.out.println("Date parse exception: " + e.getMessage());
			//throw e;
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
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Direction validateDirection(String direction) throws Exception {
		Direction dir;
		if (direction.equals("North")) {
			dir = Direction.NORTH;
		} else if (direction.equals("Northeast")) {
			dir = Direction.NORTH_EAST;
		} else if (direction.equals("East")) {
			dir = Direction.EAST;
		} else if (direction.equals("Southeast")) {
			dir = Direction.SOUTH_EAST;
		} else if (direction.equals("South")) {
			dir = Direction.SOUTH;
		} else if (direction.equals("Southwest") || direction.equals("SouthWest")) {
			dir = Direction.SOUTH_WEST;
		} else if (direction.equals("West")) {
			dir = Direction.WEST;
		} else if (direction.equals("Northwest")) {
			dir = Direction.NORTH_WEST;
		} else if (direction.equals("None")) {
			dir = Direction.NONE;
		} else {
			throw new Exception("Unrecognized direction: " + direction);
		}
		return dir;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Format validateFormat(String format) throws Exception {
		Format form;
		if (format.equals("4x5")) {
			form = Format._4x5;
		} else if (format.equals("6x4.5")) {
			form = Format._6x45;
		} else if (format.equals("6x6")) {
			form = Format._6x6;
		} else if (format.equals("6x7")) {
			form = Format._6x7;
		} else if (format.equals("6x9")) {
			form = Format._6x9;
		} else if (format.equals("6x12")) {
			form = Format._6x12;
		} else if (format.equals("35mm")) {
			form = Format._35MM;
		} else {
			throw new Exception("Unrecognized format: " + format);
		}
		return form;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public String getDoNotShow() {
		return doNotShow;
	}
	public void setDoNotShow(String doNotShow) {
		this.doNotShow = doNotShow;
	}
	public Boolean validateDoNotShow(String doNotShow) throws Exception {
		Boolean doNot;
		if (doNotShow.equals("1")) {
			doNot = true;
		} else if (doNotShow.equals("0")) {
			doNot = false;
		} else {
			throw new Exception("Unrecognized doNotShow: " + doNotShow);
		}
		return doNot;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public Boolean validateIsNew(String isNew) throws Exception {
		Boolean is;
		if (isNew.equals("0")) {
			is = false;
		} else if (isNew.equals("1")) {
			is = true;
		} else {
			throw new Exception("Unrecognized isNew: " + isNew);
		}
		return is;
	}
	public String getIsDiscarded() {
		return isDiscarded;
	}
	public void setIsDiscarded(String isDiscarded) {
		this.isDiscarded = isDiscarded;
	}
	public Boolean validateIsDiscarded(String isDiscarded) throws Exception {
		Boolean is;
		if (isDiscarded.equals("0")) {
			is = false;
		} else if (isDiscarded.equals("1")) {
			is = true;
		} else {
			throw new Exception("Unrecognized isDiscarded: " + isDiscarded);
		}
		return is;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Float validateRating(String rating) {
		return java.lang.Float.valueOf(rating);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
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
		} else {
			throw new Exception("Unrecognized category name: " + categoryName);
		}
	}
}
