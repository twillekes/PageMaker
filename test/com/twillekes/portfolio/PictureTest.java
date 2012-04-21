package com.twillekes.portfolio;

import junit.framework.TestCase;

import org.junit.Test;

import com.twillekes.portfolio.Picture;

public class PictureTest extends TestCase {

	@Test
	public void testSetFileName() {
		Picture m = new Picture();
		m.setFileName("testFileName");
		assertEquals("testFileName", m.getFileName());
	}

	@Test
	public void testSetTitle() {
		Picture m = new Picture();
		m.setTitle("testName");
		assertEquals("testName", m.getTitle());
	}

	@Test
	public void testSetDescription() {
		Picture m = new Picture();
		m.setDescription("testDescription");
		assertEquals("testDescription", m.getDescription());
	}

	@Test
	public void testPicture() {
		Picture m = new Picture();
		assertEquals("Untitled", m.getTitle());
		assertEquals("undefined", m.getFileName());
		assertEquals("none", m.getDescription());
		assertEquals(m.getFileName(), "undefined");
		assertEquals(m.getThumbFileName(), "undefined");
		assertEquals(m.getTitle(), "Untitled");
	}

	@Test
	public void testToString() {
		Picture m = new Picture();
		m.setTitle("testName");
		m.setFileName("testFileName");
		m.setThumbFileName("testThumbFileName");
		m.setDescription("testDescription");
		assertEquals("title: testName, fileName: testFileName, thumbFileName: testThumbFileName, description: testDescription", m.toString());
	}
}
