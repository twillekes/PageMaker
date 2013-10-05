package com.twillekes.repoExporter;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Folder;
import com.twillekes.portfolio.Repository;

public class FileSystemExporter {
	public static void export(String toPath) throws Exception {
		Iterator<Folder> it = Repository.instance().getFolders().iterator();
		while(it.hasNext()) {
			Folder repo = it.next();
			String repoPath = repo.getRelativeUrl();
			Iterator<Picture> picIt = repo.getPictures().iterator();
			while(picIt.hasNext()) {
				Picture picture = picIt.next();
				copyOrMoveFiles(picture.getRepositoryFilePath(), toPath + "/" + repoPath, false);
			}
		}
	}
	public static void copyOrMoveFiles(String filePath, String toPath, boolean move) throws Exception {
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
        if (move) {
			FileUtils.moveFile(pictureFile, destPictureFile);
			FileUtils.moveFile(thumbFile, destThumbFile);
        } else {
			FileUtils.copyFile(pictureFile, destPictureFile);
			FileUtils.copyFile(thumbFile, destThumbFile);
        }
	}
	public static long getPercent(Folder repository) {
		long size = 0;
		try {
			Iterator<Picture> it = repository.getPictures().iterator();
			while(it.hasNext()) {
				Picture picture = it.next();
				File picFile = new File(picture.getRepositoryFilePath());
				size += FileUtils.sizeOf(picFile);
				File thumbFile = new File(picture.getRepositoryThumbFilePath());
				size += FileUtils.sizeOf(thumbFile);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return size * 100 / (20 * 1024 * 1024);
	}
}
