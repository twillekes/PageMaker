package com.twillekes.portfolio;

import java.util.Date;

public class Metadata {
	// orientation
	public enum Orientation {
		PORTRAIT, LANDSCAPE
	}
	private Orientation orientation;
	private String subject;
	public enum Season {
		WINTER, SPRING, SUMMER, AUTUMN
	}
	private Season season;
	public enum Camera {
		EBONY, NIKON_F3, HASSELBLAD_500CM, HASSELBLAD_SWCM, HASSELBLAD_501CW, MAMIYA_7II
	}
	private Camera camera;
	public enum Lens {
		_17_35,
		_47, _58, _75, _90, _135, _180, _270,
		_38, _40, _50, _60, _80, _100, _120, _150, _250,
		_43, _65
	}
	private Lens lens;
	public enum Film {
		VELVIA50, VELVIA100, PROVIA100F, ILFORD_DELTA100
	}
	private Film film;
	private Boolean isColor;
	public enum Format {
		_35MM, _6x45, _6x6, _6x7, _6x9, _4x5
	}
	private Date date;
	public enum Direction {
		NORTH, WEST, EAST, SOUTH, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST
	}
	private Direction direction;
	
	public Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	public void setOrientation(String orientation) throws Exception {
		this.orientation = Orientation.LANDSCAPE;
		if (orientation.equals("portrait")) {
			this.orientation = Orientation.PORTRAIT;
		} else if (orientation.equals("landscape")) {
			this.orientation = Orientation.LANDSCAPE;
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
		if (lens.equals("90mm")) {
			this.lens = Lens._90;
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
	public Boolean getIsColor() {
		return isColor;
	}
	public void setIsColor(Boolean isColor) {
		this.isColor = isColor;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
