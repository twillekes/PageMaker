package com.twillekes.json;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Observable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.portfolio.Repository;

public class Exporter {
    public class RepositoryExclusionStrategy implements ExclusionStrategy {
        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }
        public boolean shouldSkipField(FieldAttributes f) {
            return (f.getDeclaringClass() == Observable.class && f.getName().equals("obs")) ||
            	   (f.getDeclaringClass() == Observable.class && f.getName().equals("changed"));
        }
    }
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
		Gson gson = new GsonBuilder().setExclusionStrategies(new RepositoryExclusionStrategy()).create();
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
		Gson gson = new GsonBuilder().setExclusionStrategies(new RepositoryExclusionStrategy()).create();
		String json = gson.toJson(Repository.instance().getFolders());
		try {
			FileWriter fstream = new FileWriter(Repository.instance().getFileName());
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(json);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Repository.instance().setIsDirty(false);
	}
	public void exportToJS(Portfolio portfolio) {
		Gson gson = new GsonBuilder().setExclusionStrategies(new RepositoryExclusionStrategy()).create();
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
			FileWriter fstream = new FileWriter(Repository.instance().getPagePath() + "imageList.js");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(json);
			out.write(cats);
			out.write(words);
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
