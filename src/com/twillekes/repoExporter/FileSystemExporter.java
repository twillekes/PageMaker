package com.twillekes.repoExporter;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;

public class FileSystemExporter {
	public static void export(String toPath) throws Exception {
		Iterator<Repository> it = Repository.get().iterator();
		while(it.hasNext()) {
			Repository repo = it.next();
			String repoPath = repo.getRelativeUrl();
			Iterator<Picture> picIt = repo.getPictures().iterator();
			while(picIt.hasNext()) {
				Picture picture = picIt.next();
				copyFiles(picture.getRepositoryFilePath(), toPath + "/" + repoPath);
			}
		}
	}
	public static void copyFiles(String filePath, String toPath) throws Exception {
        String thumbPath = Picture.getThumbName(filePath);
        if (!(new File(thumbPath).isFile())) {
        	throw new Exception("Expected thumbnail (" + thumbPath + ")");
        }
        File pictureFile = new File(filePath);
        String fileName = pictureFile.getName();
        File destPictureFile = new File(toPath + fileName);
        File thumbFile = new File(thumbPath);
        String thumbFileName = thumbFile.getName();
        File destThumbFile = new File(toPath + thumbFileName);
		FileUtils.copyFile(pictureFile, destPictureFile);
		FileUtils.copyFile(thumbFile, destThumbFile);
	}
}
