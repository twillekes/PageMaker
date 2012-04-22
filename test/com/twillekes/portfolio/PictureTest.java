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
	public void testPicture() {
		Picture m = new Picture();
		assertEquals("undefined", m.getFileName());
		assertEquals(m.getFileName(), "undefined");
	}

	@Test
	public void testToString() {
		Picture m = new Picture();
		m.setFileName("testFileName");
		assertEquals("title: testName, fileName: testFileName, thumbFileName: testThumbFileName, description: testDescription", m.toString());
	}
}
