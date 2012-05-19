package com.twillekes.repoExporter;

import java.util.Iterator;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;

public class SiteExporter {
	private class FtpExporter {
		private String password;
		private String account;
		public FtpExporter(String password) {
			this.password = password;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public void upload(String filePath, String destFilePath) {
			// Don't forget thumbnails.
			System.out.println("Would upload from "+filePath+" to "+destFilePath+" with account "+account+" and pw "+password);
		}
	}
	public void export(String password) {
		FtpExporter ftpExporter = new FtpExporter(password);
		Iterator<Repository> it = Repository.get().iterator();
		while(it.hasNext()) {
			Repository repo = it.next();
			String repoPath = repo.getRelativeUrl();
			ftpExporter.setAccount(repo.getAccount());
			Iterator<Picture> picIt = repo.getPictures().iterator();
			while(picIt.hasNext()) {
				Picture picture = picIt.next();
				ftpExporter.upload(picture.getRepositoryFilePath(), repoPath + Picture.getFileName(picture.getFilePath()));
			}
		}
	}
}
