package com.twillekes.portfolio;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

	private List<Album> albums;  // Built up from tags in the pictures' metadata
	private List<Picture> pictures; // The complete list of pictures in the portfolio
	
	public Portfolio() {
		pictures = new ArrayList<Picture>();
		albums = new ArrayList<Album>();
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void addAlbum(Album album) {
		albums.add(album);
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void addPicture(Picture p) {
		pictures.add(p);
	}

}
