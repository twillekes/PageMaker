package com.twillekes.jsonImporter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twillekes.portfolio.Portfolio;

public class Importer {
	@SuppressWarnings("unused")
	private static Portfolio portfolio;
	private static Importer importer;
	private enum RecordType {
		LOCAL, REMOTE
	}
	private class MetadataRecord {
		public RecordType type;
		public String path;
		public MetadataRecord(RecordType type, String path) {
			this.type = type;
			this.path = path;
		}
	}
	private List<MetadataRecord> metadataRecords;
	public static void main(String[] args) {
		portfolio = new Portfolio();
		importer = new Importer();
		importer.importJson("locations.json");
		
		System.out.println("Created portfolio");
	}
	
	public Importer() {
		this.metadataRecords = new ArrayList<MetadataRecord>();
	}
	/**
	 * @param jsonFileName
	 */
	public void importJson(String jsonFileName) {
		final JsonParser parser = new JsonParser();
		final FileReader fileReader;
		try {
			fileReader = new FileReader(jsonFileName);
		} catch (FileNotFoundException err) {
			System.out.println(err.getMessage());
			return;
		}
		final JsonElement jsonElement = parser.parse(fileReader);
		final JsonObject jsonObject = jsonElement.getAsJsonObject();
		this.importLocationJson(jsonObject);
	
//		for (final Entry<String, JsonElement> entry : jsonObject.entrySet()) {
//		   final String key = entry.getKey();
//		   final JsonElement value = entry.getValue();
//		   System.out.println("Key is "+key+" value is "+value+"");
//		}
	}
	
	public void importLocationJson(JsonObject jsonObject) {
		final Entry<String, JsonElement> itemsEntry = jsonObject.entrySet().iterator().next();
		final JsonArray jsonArray = itemsEntry.getValue().getAsJsonArray();
		final Iterator<JsonElement> it = jsonArray.iterator();
		while (it.hasNext()) {
			final JsonObject value = it.next().getAsJsonObject();
			for (final Entry<String, JsonElement> entry : value.entrySet()) {
				final String key = entry.getKey();
				final String path = entry.getValue().getAsString();
				RecordType type = RecordType.LOCAL;
				if (key == "remote") {
					type = RecordType.REMOTE;
				}
				this.metadataRecords.add(new MetadataRecord(type,path));
				System.out.println("Adding type "+key+" value "+path);
			}
		}
	}
}
