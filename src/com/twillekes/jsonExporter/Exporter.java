package com.twillekes.jsonExporter;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Portfolio;

public class Exporter {
	public static void main(String[] args) {
		Portfolio portfolio = new Portfolio();
		Importer importer = new Importer();
		importer.populate(portfolio);
		Exporter exporter = new Exporter();
		exporter.export(portfolio);
		
		System.out.println("Created portfolio");
	}
	
	public Exporter() {
		
	}
	public void export(Portfolio portfolio) {
		Gson gson = new Gson();
		String json = gson.toJson(portfolio.getPictures());
		json = "var imageList=" + json;
		System.out.println(json);
		
		try {
			FileWriter fstream = new FileWriter("imageList.js");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(json);
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
