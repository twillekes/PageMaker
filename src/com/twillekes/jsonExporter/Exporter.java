package com.twillekes.jsonExporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;

import com.google.gson.Gson;
import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.portfolio.Repository;

public class Exporter {
	public static void main(String[] args) {
		Importer importer = new Importer();
		Portfolio portfolio = importer.createPortfolioFromMetadata();
		Exporter exporter = new Exporter();
		exporter.export();
		//exporter.export(portfolio);
		exporter.exportToJS(portfolio);
		
		System.out.println("Created portfolio");
	}
	
	public Exporter() {
		
	}
	public void export(Portfolio portfolio) {
		Gson gson = new Gson();
		String json = gson.toJson(portfolio.getPictures());
		try {
			FileWriter fstream = new FileWriter("metadata.json");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(json);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void export() {
		Gson gson = new Gson();
		String json = gson.toJson(Repository.get());
		try {
			FileWriter fstream = new FileWriter(Repository.getFileName());
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(json);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void exportToJS(Portfolio portfolio) {
		Gson gson = new Gson();
		String json = gson.toJson(portfolio.getPictures());
		json = "var imageList=" + json + ";";
//		System.out.println(json);
		portfolio.findAllCategories();
		String cats = gson.toJson(portfolio.getCategoryDictionary());
		cats = "var categoryDictionary=" + cats + ";";
//		System.out.println(cats);
		String words = gson.toJson(portfolio.getWords());
		words = "var articleList=" + words + ";";
//		System.out.println(words);
		
		try {
			FileWriter fstream = new FileWriter(Repository.getJavascriptPath() + "imageList.js");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(json);
			out.write(cats);
			out.write(words);
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void exportRepositoryToFileSystem(String toPath) throws Exception {
		Iterator<Repository> it = Repository.get().iterator();
		while(it.hasNext()) {
			Repository repo = it.next();
			String repoPath = repo.getRelativeUrl();
			Iterator<Picture> picIt = repo.getPictures().iterator();
			while(picIt.hasNext()) {
				Picture picture = picIt.next();
				Importer.copyFiles(picture.getRepositoryFilePath(), toPath + "/" + repoPath);
			}
		}
	}
}
