package com.twillekes.repoExporter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Iterator;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Repository;

public class SiteExporter {
	private static final String SERVER = "ftp.shaw.ca";
	private class FtpExporter {
		private String password;
		private FTPClient client;
		public FtpExporter(String password) {
			this.password = password;
			client = new FTPClient();
		}
		public void initiate(String account, String destDir) throws Exception {
			System.out.println("Initiating with account "+account);
			client.connect(SERVER);
			int reply = client.getReplyCode();
			//System.out.print(client.getReplyString());
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				throw new Exception("Server refused connection");
			}
			if (!client.login(account, password)) {
				throw new Exception("Unable to log in");
			}
			if (!client.changeWorkingDirectory(destDir)) {
				client.makeDirectory(destDir);
				if (!client.changeWorkingDirectory(destDir)) {
					throw new Exception("Could not create destination directory " + destDir);
				}
			}
		}
		public void terminate() {
			try {
				client.logout();
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void upload(String filePath) throws Exception {
			// Don't forget thumbnails.
			String destFilePath = Picture.getFileName(filePath);
	        String thumbPath = Picture.getThumbName(filePath);
	        String destThumbFilePath = Picture.getFileName(thumbPath);

	        System.out.println("Uploading from "+filePath+" to "+destFilePath+" and "+thumbPath+" to "+destThumbFilePath);
			InputStream local = new FileInputStream(filePath);
			if (!client.storeFile(destFilePath, local)) {
				throw new Exception("Unable to upload "+filePath);
			}
			local = new FileInputStream(thumbPath);
			if (!client.storeFile(destThumbFilePath, local)) {
				throw new Exception("Unable to upload "+thumbPath);
			}
		}
	}
	public void export(String password) {
		FtpExporter ftpExporter = new FtpExporter(password);
		Iterator<Repository> it = Repository.get().iterator();
		while(it.hasNext()) {
			Repository repo = it.next();
			String repoDir = repo.getPath();
			try {
				ftpExporter.initiate(repo.getAccount(), repoDir);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			Iterator<Picture> picIt = repo.getPictures().iterator();
			while(picIt.hasNext()) {
				Picture picture = picIt.next();
				try {
					ftpExporter.upload(picture.getRepositoryFilePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ftpExporter.terminate();
		}
	}
	public void test(String pw) {
		FtpExporter ftpExporter = new FtpExporter(pw);
		try {
			ftpExporter.initiate("photonwrangler", "testUpload/");
			ftpExporter.upload("repository/TESTIMAGE.jpg");
			ftpExporter.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
