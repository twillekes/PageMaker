package com.twillekes.repoExporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Folder;
import com.twillekes.portfolio.Repository;
import com.twillekes.userInterface.Application;

public class SiteExporter {
	private static final String SERVER = "ftp.shaw.ca";
	public static boolean forReal = true;
	public interface Logger {
		public void log(String message);
	}
	public interface CompletionObserver {
		public void complete();
	}
	private class LoggerProxy implements Logger {
		Logger logger;
		public LoggerProxy(Logger logger) {
			this.logger = logger;
		}
		@Override
		public void log(final String message) {
			Application.getDisplay().asyncExec(new Runnable(){
				@Override
				public void run() {
					logger.log(message);
				}
			});
		}
	}
	private class FtpExporter {
		private String password;
		private Logger logger;
		private FTPClient client;
		private FTPFile[] files;
		private static final long FILE_SIZE_TOLERANCE = 0;
		public FtpExporter(String password, Logger logger) {
			this.password = password;
			this.logger = logger;
			client = new FTPClient();
		}
		public void initiate(String account, String destDir) throws Exception {
			logger.log("Initiating with account " + account);
			client.connect(SERVER);
			int reply = client.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				logger.log("  ***ERROR*** Server refused connection, reply: "+reply);
				throw new Exception("Server refused connection");
			}
			if (!client.login(account, password)) {
				logger.log("  ***ERROR*** Unable to log in");
				throw new Exception("Unable to log in");
			}
			if (destDir.length() > 0 && !client.changeWorkingDirectory(destDir)) {
				client.makeDirectory(destDir);
				if (!client.changeWorkingDirectory(destDir)) {
					logger.log("  ***ERROR*** Could not create destination directory " + destDir);
					throw new Exception("Could not create destination directory " + destDir);
				}
			}
			client.setUseEPSVwithIPv4(true);
			files = client.listFiles();
			logger.log("  Account (" + account + ") has " + files.length + " files");
		}
		private boolean shouldUpload(String filePath) {
			String fileName = Picture.getFileName(filePath);
			File theFile = new File(filePath);
			boolean found = false;
			for (int i = 0; i < this.files.length ; i++) {
				if (this.files[i].getName().equals(fileName)) {
//					System.out.println("Remote file size is "+this.files[i].getSize()+" local size is "+FileUtils.sizeOf(theFile));
					if (Math.abs(this.files[i].getSize() - FileUtils.sizeOf(theFile)) > FILE_SIZE_TOLERANCE) {
						return true;
					}
//					Date date = new Date(theFile.lastModified());
//					System.out.println("Remote file time stamp is "+this.files[i].getTimestamp().getTime()+" Local date is "+date);
					if (FileUtils.isFileNewer(theFile, this.files[i].getTimestamp().getTime())) {
						return true;
					}
					found = true;
					break;
				}
			}
			return !found;
		}
		public boolean needsUpload(String filePath) {
			if (shouldUpload(filePath)) {
				return true;
			}
			if (shouldUpload(Picture.getThumbName(filePath))) {
				return true;
			}
			//logger.log("    File " + Picture.getFileName(filePath) + " does not need upload");
			return false;
		}
		public void terminate() {
			try {
				client.logout();
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void uploadPicture(String filePath) throws Exception {
			client.setFileType(FTP.BINARY_FILE_TYPE);
	        String thumbPath = Picture.getThumbName(filePath);
	        upload(filePath);
	        upload(thumbPath);
		}
		public void upload(String filePath) throws Exception {
	        logger.log("    Uploading "+filePath);
	        String destFilePath = Picture.getFileName(filePath);
			InputStream local = new FileInputStream(filePath);
			boolean success = true;
			if (forReal) {
				success = client.storeFile(destFilePath, local);
			}
			local.close();
			if (!success) {
				logger.log("    ***ERROR*** Unable to upload "+filePath+" reply: "+client.getReplyCode());
				throw new Exception("Unable to upload "+filePath);
			}
		}
		public void purge(List<String> filesToKeep) {
			logger.log("  Starting purge...");
			for (int i = 0; i < this.files.length ; i++) {
				String thumbName = Picture.getThumbName(this.files[i].getName());
				if (filesToKeep.contains(this.files[i].getName())) {
					//logger.log("    Keeping file "+this.files[i].getName());
					continue;
				}
				logger.log("    Deleting files "+this.files[i].getName()+" and "+thumbName);
				try {
					if (forReal && !client.deleteFile(this.files[i].getName())) {
						logger.log("    ***ERROR*** Unable to delete "+this.files[i].getName());
					}
					if (forReal && !client.deleteFile(thumbName)) {
						logger.log("    ***ERROR*** Unable to delete "+thumbName);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void export(final String password, final Logger logger, final CompletionObserver completionObserver) {
		final LoggerProxy loggerProxy = new LoggerProxy(logger);
		new Thread() {
			public void run() {
				performExport(password, loggerProxy);
				Application.getDisplay().asyncExec(new Runnable(){
					@Override
					public void run() {
						completionObserver.complete();
					}
				});
			}
		}.start();
	}
	private void performExport(String password, Logger logger) {
		FtpExporter ftpExporter = new FtpExporter(password, logger);
		Iterator<Folder> it = Repository.instance().getFolders().iterator();
		while(it.hasNext()) {
			Folder repo = it.next();
			String repoDir = repo.getPath();
			try {
				ftpExporter.initiate(repo.getAccount(), repoDir);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			Iterator<Picture> picIt = repo.getPictures().iterator();
			List<String> filesToKeep = new ArrayList<String>();
			while(picIt.hasNext()) {
				Picture picture = picIt.next();
				String repoFilePath = picture.getRepositoryFilePath();
				filesToKeep.add(Picture.getFileName(repoFilePath));
				filesToKeep.add(Picture.getThumbName(Picture.getFileName(repoFilePath)));
				if (!ftpExporter.needsUpload(repoFilePath)) {
					continue;
				}
				try {
					ftpExporter.uploadPicture(repoFilePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ftpExporter.purge(filesToKeep);
			ftpExporter.terminate();
		}
		try {
			ftpExporter.initiate("twillekes", "");
			ftpExporter.upload(Repository.instance().getPagePath() + "feed.xml");
			ftpExporter.upload(Repository.instance().getPagePath() + "imageList.js");
			ftpExporter.upload(Repository.instance().getPagePath() + "scripts.js");
			ftpExporter.terminate();
		} catch (Exception e) {
			logger.log("***ERROR*** Unable to upload feed and imageList files");
			e.printStackTrace();
			return;
		}
		logger.log("The site has been published successfully");
	}
	public void test(final String password, final Logger logger, final CompletionObserver completionObserver) {
		forReal = true;
		final LoggerProxy loggerProxy = new LoggerProxy(logger);
		new Thread() {
			public void run() {
				FtpExporter ftpExporter = new FtpExporter(password, loggerProxy);
				try {
					ftpExporter.initiate("photonwrangler", "testUpload/");
					if (ftpExporter.needsUpload("repository/TESTIMAGE.jpg")) {
						ftpExporter.upload("repository/TESTIMAGE.jpg");
					}
					List<String> files = new ArrayList<String>();
					files.add("TESTIMAGE.jpg");
					files.add("TESTIMAGE_thumb.jpg");
					ftpExporter.purge(files);
					ftpExporter.terminate();
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				loggerProxy.log("The test has completed successfully");
				Application.getDisplay().asyncExec(new Runnable(){
					@Override
					public void run() {
						completionObserver.complete();
					}
				});
			}
		}.start();
	}
}
