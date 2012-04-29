package com.twillekes.jsonImporter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twillekes.portfolio.Metadata;
import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.portfolio.Words;

public class Importer {
	public interface Deserializer {
		public String getFileName();
		public void handleJsonObject(JsonObject obj) throws ParseException;
	}
	
	public class LocationDeserializer implements Deserializer {
		public List<MetadataRecord> metadataRecords;
		public LocationDeserializer() {
			this.metadataRecords = new ArrayList<MetadataRecord>();
		}
		public String getFileName() {
			return "locations.json";
		}
		public void handleJsonObject(JsonObject obj) throws ParseException {
			RecordType type = RecordType.LOCAL;
			String path = "undefined";
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				final String key = entry.getKey();
				final String val = entry.getValue().getAsString();
				if (key.equals("type")) {
					if (val.equals("remote")) {
						type = RecordType.REMOTE;
					}
				} else if (key.equals("metadataPath")) {
					path = val;
				} else {
					throw new ParseException("Unknown JSON key ('" + key + "') in items array", 0);
				}
			}
			this.metadataRecords.add(new MetadataRecord(type,path));
			System.out.println("Adding type "+type+" value "+path);
		}
	}
	public class PortfolioDeserializer implements Deserializer {
		public String path;
		public String url;
		public Portfolio portfolio;
		public boolean isWords;
		public PortfolioDeserializer(Portfolio portfolio) {
			this.portfolio = portfolio;
		}
		public void handleJsonObject(JsonObject obj) throws ParseException {
			if (this.isWords) {
				handleJsonWordsObject(obj);
			} else {
				handleJsonPictureObject(obj);
			}
		}
		public void handleJsonWordsObject(JsonObject obj) throws ParseException {
			Words words = new Words();
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				final String key = entry.getKey();
				final String val = entry.getValue().getAsString();
				try {
					if (key.equals("filename")) {
						words.setFilePath(this.path + "/" + val);
					} else if (key.equals("title")) {
						words.setTitle(val);
					} else if (key.equals("doNotShow")) {
						words.setDoNotShow(val);
					}
				} catch (Exception e) {
					System.out.println("Encounted exception in file " + this.getFileName() + " error: " + e.getMessage());
				}
			}
			portfolio.addWords(words);
		}
		public void handleJsonPictureObject(JsonObject obj) throws ParseException {
			Picture picture = new Picture();
			Metadata metadata = new Metadata();
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				final String key = entry.getKey();
				final String val = entry.getValue().getAsString();
				try {
					if (key.equals("filename")) {
						picture.setFilePath(this.url + "/" + val);
						picture.setLocalFilePath(this.path + "/" + val);
					} else if (key.equals("title")) {
						metadata.setTitle(val);
					} else if (key.equals("caption")) {
						metadata.setDescription(val);
					} else if (key.equals("rating")) {
						metadata.setRating(val);
					} else if (key.equals("orientation")) {
						metadata.setOrientation(val);
					} else if (key.equals("subject")) {
						metadata.setSubject(val);
					} else if (key.equals("season")) {
						metadata.setSeason(val);
					} else if (key.equals("camera")) {
						metadata.setCamera(val);
					} else if (key.equals("lens")) {
						metadata.setLens(val);
					} else if (key.equals("film")) {
						metadata.setFilm(val);
					} else if (key.equals("chrome")) {
						metadata.setChrome(val);
					} else if (key.equals("format")) {
						metadata.setFormat(val);
					} else if (key.equals("year")) {
						metadata.setYear(val);;
					} else if (key.equals("month")) {
						metadata.setMonth(val);
					} else if (key.equals("date")) {
						metadata.setDate(val);
					} else if (key.equals("direction")) {
						metadata.setDirection(val);
					} else if (key.equals("filters")) {
						metadata.setFilters(val);
					} else if (key.equals("doNotShow")) {
						metadata.setDoNotShow(val);
					} else if (key.equals("isDiscarded")) {
						metadata.setIsDiscarded(val);
					} else if (key.equals("isNew")) {
						metadata.setIsNew(val);
					} else if (key.equals("isFavorite")) {
						metadata.setIsFavorite(val);
					} else {
						throw new ParseException("Unknown JSON key ('" + key + "') in items array", 0);
					}
				} catch (Exception e) {
					System.out.println("Encounted exception in file " + this.getFileName() + " error: " + e.getMessage());
				}
			}
			picture.setMetadata(metadata);
			this.portfolio.addPicture(picture);
			//System.out.println("Added picture: " + picture.toString());
		}
		public String getFileName() {
			return this.path + "/metadata.json";
		}
	}
	
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
	private String basePath;
	public static String PATH_TO_IMAGES = "../Page/My-personal-web-page/page/";
	
	public static void main(String[] args) {
		portfolio = new Portfolio();
		importer = new Importer();
		importer.populate(portfolio);
		
		System.out.println(portfolio.toString());
	}
	
	public Importer() {
		this.basePath = PATH_TO_IMAGES;
	}
	public void populate(Portfolio portfolio) {
		LocationDeserializer locationDeserializer = new LocationDeserializer();
		this.importJson(locationDeserializer);
		System.out.println("There are " + locationDeserializer.metadataRecords.size() + " metadata records");
		
		PortfolioDeserializer portfolioDeserializer = new PortfolioDeserializer(portfolio);
		final Iterator<MetadataRecord> it = locationDeserializer.metadataRecords.iterator();
		while(it.hasNext()) {
			final MetadataRecord rec = it.next();
			if (rec.type == RecordType.REMOTE){
				continue;
			}
			portfolioDeserializer.isWords = false;
			portfolioDeserializer.path = rec.path;
			portfolioDeserializer.url = rec.path;
			if (rec.path.equals("images")) {
				portfolioDeserializer.url = "http://members.shaw.ca/tjwillekes/images";
			} else if (rec.path.equals("images_2")) {
				portfolioDeserializer.url = "http://members.shaw.ca/tomjwillekes/images_2";
			} else if (rec.path.equals("words")) {
				portfolioDeserializer.isWords = true;
			}
			this.importJson(portfolioDeserializer);
			System.out.println("After loading " + rec.path + " the portfolio is: " + portfolio.toString());
		}
	}
	public void importJson(Deserializer deserializer) {
		final JsonParser parser = new JsonParser();
		final FileReader fileReader;
		try {
			fileReader = new FileReader(this.basePath + deserializer.getFileName());
		} catch (FileNotFoundException err) {
			System.out.println(err.getMessage());
			return;
		}
		final JsonElement jsonElement = parser.parse(fileReader);
		final JsonObject jsonObject = jsonElement.getAsJsonObject();
		try {
			this.importJsonRecords(jsonObject, deserializer);
		} catch(ParseException e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	public void importJsonRecords(JsonObject jsonObject, Deserializer deserializer) throws ParseException {
		final Iterator<Map.Entry<String,JsonElement>> iter = jsonObject.entrySet().iterator();
		Entry<String, JsonElement> itemsEntry = null;
		while(iter.hasNext()) {
			final Entry<String, JsonElement> entry = iter.next();
			if (entry.getKey().equals("items")) {
				itemsEntry = entry;
				break;
			}
		}
		if (itemsEntry == null) {
			throw new ParseException("Could not find items list in JSON", 0);
		}
		final JsonArray jsonArray = itemsEntry.getValue().getAsJsonArray();
		final Iterator<JsonElement> it = jsonArray.iterator();
		while (it.hasNext()) {
			JsonObject obj = it.next().getAsJsonObject();
			try {
				deserializer.handleJsonObject(obj);
			} catch (ParseException e) {
				System.out.println("Exception: " + e.getMessage());
			}
		}
	}
}
