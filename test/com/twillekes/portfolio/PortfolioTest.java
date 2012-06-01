package com.twillekes.portfolio;

import org.junit.Test;

import junit.framework.TestCase;

public class PortfolioTest extends TestCase {

	@Test
	public void testPortfolio() {
		Portfolio p = new Portfolio();
		assertEquals(p.getPictures().size(), 0);
//		assertEquals(p.getAlbums().size(), 0);
	}

	@Test
	public void testAddAlbum() {
//		Portfolio p = new Portfolio();
//		Album a = new Album("testAlbumName");
//		p.addAlbum(a);
//		assertEquals(p.getAlbums().size(), 1);
	}
	
	@Test
	public void testAddPicture() {
		Portfolio p = new Portfolio();
		Picture pic = new Picture();
		try {
			p.addPicture(pic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(p.getPictures().size(), 1);
	}
}
