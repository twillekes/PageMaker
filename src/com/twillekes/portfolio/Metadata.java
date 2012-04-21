package com.twillekes.portfolio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Metadata {
	// orientation
	public enum Orientation {
		PORTRAIT, LANDSCAPE, SQUARE, PANORAMA
	}
	private Orientation orientation;
	private String subject;
	public enum Season {
		WINTER, SPRING, SUMMER, AUTUMN
	}
	private Season season;
	public enum Camera {
		EBONY, NIKON_F3, NIKON_F6, NIKON_FM3A, HASSELBLAD_500CM, HASSELBLAD_SWCM, HASSELBLAD_503CW,
		MAMIYA_7II, MAMIYA_645_PRO_TL, MAMIYA_6, LEICA_M3, LEICA_M2, ROLLEIFLEX_MX_EVS
	}
	private Camera camera;
	public enum Lens {
		_17_35, _35, _70_300, _28_70,
		_47, _58, _75, _90, _135, _180, _270,
		_38, _40, _50, _60, _80, _100, _120, _150, _250,
		_43, _65, _45, _55
	}
	private Lens lens;
	public enum Film {
		VELVIA50, VELVIA100, PROVIA100F, ILFORD_DELTA100, ILFORD_DELTA400, ILFORD_PANF, KODAK_TRIX
	}
	private Film film;
	private Boolean isColor;
	public enum Format {
		_35MM, _6x45, _6x6, _6x7, _6x9, _6x12, _4x5
	}
	private Format format;
	private Date date;
	public enum Direction {
		NORTH, WEST, EAST, SOUTH, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST, NONE
	}
	private Direction direction;
	private String filters;
	private Boolean doNotShow;
	
	public Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	public void setOrientation(String orientation) throws Exception {
		this.orientation = Orientation.LANDSCAPE;
		if (orientation.equals("Portrait")) {
			this.orientation = Orientation.PORTRAIT;
		} else if (orientation.equals("Landscape")) {
			this.orientation = Orientation.LANDSCAPE;
		} else if (orientation.equals("Square")) {
			this.orientation = Orientation.SQUARE;
		} else if (orientation.equals("Panorama")) {
			this.orientation = Orientation.PANORAMA;
		} else {
			throw new Exception("Unrecognized orientation: "+orientation);
		}
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public void setSeason(String season) throws Exception {
		this.season = Season.SUMMER;
		if (season.equals("Winter")) {
			this.season = Season.WINTER;
		} else if (season.equals("Spring")) {
			this.season = Season.SPRING;
		} else if (season.equals("Autumn")) {
			this.season = Season.AUTUMN;
		} else if (season.equals("Summer")) {
			this.season = Season.SUMMER;
		} else {
			throw new Exception("Unrecognized season: " + season);
		}
	}
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public void setCamera(String camera) throws Exception {
		if (camera.equals("Ebony 45S")) {
			this.camera = Camera.EBONY;
		} else if (camera.equals("Mamiya 7ii")) {
			this.camera = Camera.MAMIYA_7II;
		} else if (camera.equals("Hasselblad SWC/M")) {
			this.camera = Camera.HASSELBLAD_SWCM;
		} else if (camera.equals("Hasselblad 501CM")) {
			this.camera = Camera.HASSELBLAD_500CM;
		} else if (camera.equals("Hasselblad 503CW")) {
			this.camera = Camera.HASSELBLAD_503CW;
		} else if (camera.equals("Nikon F3")) {
			this.camera = Camera.NIKON_F3;
		} else if (camera.equals("Nikon F6")) {
			this.camera = Camera.NIKON_F6;
		} else if (camera.equals("Nikon FM3a")) {
			this.camera = Camera.NIKON_FM3A;
		} else if (camera.equals("Mamiya 645 Pro-TL")) {
			this.camera = Camera.MAMIYA_645_PRO_TL;
		} else if (camera.equals("Mamiya 6")) {
			this.camera = Camera.MAMIYA_6;
		} else if (camera.equals("Leica M3")) {
			this.camera = Camera.LEICA_M3;
		} else if (camera.equals("Leica M2")) {
			this.camera = Camera.LEICA_M2;
		} else if (camera.equals("Rolleiflex mx-evs")) {
			this.camera = Camera.ROLLEIFLEX_MX_EVS;
		} else {
			throw new Exception("Unrecognized camera: " + camera);
		}
	}
	public Lens getLens() {
		return lens;
	}
	public void setLens(Lens lens) {
		this.lens = lens;
	}
	public void setLens(String lens) throws Exception {
		if (lens.equals("47mm")) {
			this.lens = Lens._47;
		} else if (lens.equals("58mm")) {
			this.lens = Lens._58;
		} else if (lens.equals("75mm")) {
			this.lens = Lens._75;
		} else if (lens.equals("90mm")) {
			this.lens = Lens._90;
		} else if (lens.equals("135mm")) {
			this.lens = Lens._135;
		} else if (lens.equals("180mm")) {
			this.lens = Lens._180;
		} else if (lens.equals("270mm")) {
			this.lens = Lens._270;
		} else if (lens.equals("43mm")) {
			this.lens = Lens._43;
		} else if (lens.equals("65mm")) {
			this.lens = Lens._65;
		} else if (lens.equals("150mm")) {
			this.lens = Lens._150;
		} else if (lens.equals("17-35mm")) {
			this.lens = Lens._17_35;
		} else if (lens.equals("28-70mm")) {
			this.lens = Lens._28_70;
		} else if (lens.equals("70-300mm")) {
			this.lens = Lens._70_300;
		} else if (lens.equals("35mm")) {
			this.lens = Lens._35;
		} else if (lens.equals("38mm")) {
			this.lens = Lens._38;
		} else if (lens.equals("40mm")) {
			this.lens = Lens._40;
		} else if (lens.equals("50mm")) {
			this.lens = Lens._50;
		} else if (lens.equals("60mm")) {
			this.lens = Lens._60;
		} else if (lens.equals("80mm")) {
			this.lens = Lens._80;
		} else if (lens.equals("100mm")) {
			this.lens = Lens._100;
		} else if (lens.equals("120mm")) {
			this.lens = Lens._120;
		} else if (lens.equals("250mm")) {
			this.lens = Lens._250;
		} else if (lens.equals("45mm")) {
			this.lens = Lens._45;
		} else if (lens.equals("55mm")) {
			this.lens = Lens._55;
		} else {
			throw new Exception("Unrecognized lens: " + lens);
		}
	}
	public Film getFilm() {
		return film;
	}
	public void setFilm(Film film) {
		this.film = film;
	}
	public void setFilm(String film) throws Exception {
		if (film.equals("Fuji Provia 100F")) {
			this.film = Film.PROVIA100F;
		} else if (film.equals("Fuji Velvia 50")) {
			this.film = Film.VELVIA50;
		} else if (film.equals("Fuji Velvia 100")) {
			this.film = Film.VELVIA100;
		} else if (film.equals("Ilford Delta 100")) {
			this.film = Film.ILFORD_DELTA100;
		} else if (film.equals("Ilford Delta 400")) {
			this.film = Film.ILFORD_DELTA400;
		} else if (film.equals("Ilford Pan-F")) {
			this.film = Film.ILFORD_PANF;
		} else if (film.equals("Kodak Tri-X")) {
			this.film = Film.KODAK_TRIX;
		} else {
			throw new Exception("Unrecognized film: " + film);
		}
	}
	public Boolean getIsColor() {
		return isColor;
	}
	public void setIsColor(Boolean isColor) {
		this.isColor = isColor;
	}
	public void setIsColor(String isColor) throws Exception {
		if (isColor.equals("Monochrome")) {
			this.isColor = false;
		} else if (isColor.equals("Polychrome")) {
			this.isColor = true;
		} else {
			throw new Exception("Unknown isColor: " + isColor);
		}
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setDate(String date) throws Exception {
		this.date = null;
		if (date.equals("Unknown")) {
			return;
		}
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyyy");
		try {
			this.date = (Date)dateFormat.parse(date);
		} catch (ParseException e) {
			this.date = null;
			//System.out.println("Date parse exception: " + e.getMessage());
			//throw e;
		}
		if (this.date != null) {
			return;
		}
		dateFormat = new SimpleDateFormat("MMM, yyyy");
		try {
			this.date = (Date)dateFormat.parse(date);
		} catch (ParseException e) {
			System.out.println("Date/month parse exception: " + e.getMessage());
		}
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public void setDirection(String direction) throws Exception {
		if (direction.equals("North")) {
			this.direction = Direction.NORTH;
		} else if (direction.equals("Northeast")) {
			this.direction = Direction.NORTH_EAST;
		} else if (direction.equals("East")) {
			this.direction = Direction.EAST;
		} else if (direction.equals("Southeast")) {
			this.direction = Direction.SOUTH_EAST;
		} else if (direction.equals("South")) {
			this.direction = Direction.SOUTH;
		} else if (direction.equals("Southwest") || direction.equals("SouthWest")) {
			this.direction = Direction.SOUTH_WEST;
		} else if (direction.equals("West")) {
			this.direction = Direction.WEST;
		} else if (direction.equals("Northwest")) {
			this.direction = Direction.NORTH_WEST;
		} else if (direction.equals("None")) {
			this.direction = Direction.NONE;
		} else {
			throw new Exception("Unrecognized direction: " + direction);
		}
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public void setFormat(String format) throws Exception {
		if (format.equals("4x5")) {
			this.format = Format._4x5;
		} else if (format.equals("6x4.5")) {
			this.format = Format._6x45;
		} else if (format.equals("6x6")) {
			this.format = Format._6x6;
		} else if (format.equals("6x7")) {
			this.format = Format._6x7;
		} else if (format.equals("6x9")) {
			this.format = Format._6x9;
		} else if (format.equals("6x12")) {
			this.format = Format._6x12;
		} else if (format.equals("35mm")) {
			this.format = Format._35MM;
		} else {
			throw new Exception("Unrecognized format: " + format);
		}
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public Boolean getDoNotShow() {
		return doNotShow;
	}
	public void setDoNotShow(Boolean doNotShow) {
		this.doNotShow = doNotShow;
	}
	public void setDoNotShow(String doNotShow) throws Exception {
		if (doNotShow.equals("1")) {
			this.doNotShow = true;
		} else if (doNotShow.equals("0")) {
			this.doNotShow = false;
		} else {
			throw new Exception("Unrecognized doNotShow: " + doNotShow);
		}
	}
}
