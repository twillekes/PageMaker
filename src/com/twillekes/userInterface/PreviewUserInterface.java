package com.twillekes.userInterface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.twillekes.jsonImporter.Importer;

public class PreviewUserInterface extends Component {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private static int WIDTH = 75;
	private static int HEIGHT = 100;
	
	public PreviewUserInterface(String filePath) {
		int dotPos = filePath.lastIndexOf(".");
		String thumbExt = filePath.substring(dotPos);
		String thumbName = filePath.substring(0, dotPos) + "_thumb" + thumbExt;
		image = null;
        try {
        	image = ImageIO.read(new File(Importer.PATH_TO_IMAGES + thumbName));
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
	}
	public void paint(Graphics g) {
		float xOffset = (WIDTH - image.getWidth(null)) / 2;
		if (xOffset < 0) {
			xOffset = 0;
		}
		float yOffset = (HEIGHT - image.getHeight(null)) / 2;
		if (yOffset < 0) {
			yOffset = 0;
		}
        g.drawImage(image, (int)xOffset, (int)yOffset, null);
    }
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH,HEIGHT);
    }
}
