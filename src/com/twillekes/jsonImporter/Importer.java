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
import com.twillekes.portfolio.Repository;
import com.twillekes.portfolio.Words;

public class Importer {
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
	
	private interface Deserializer {
		public String getFileName();
		public void handleJsonObject(JsonObject obj) throws ParseException;
	}
	
	private class LocationDeserializer implements Deserializer {
		public List<MetadataRecord> metadataRecords;
		public LocationDeserializer() {
			this.metadataRecords = new ArrayList<MetadataRecord>();
		}
		public String getFileName() {
			return Repository.BASE_PATH + "locations.json";
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
	private class RepositoryDeserializer implements Deserializer {
		public Portfolio portfolio;
		public RepositoryDeserializer() {
			this.portfolio = new Portfolio();
		}
		public String getFileName() {
			return Repository.FILE_NAME;
		}
		public void handleJsonObject(JsonObject obj) throws ParseException {
			String account = null;
			String path = null;
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				if (entry.getValue().isJsonArray()) {
					if (account != null && path != null) {
						Repository repository = new Repository(account, path);
						PortfolioDeserializer portfolioDeserializer = new PortfolioDeserializer(this.portfolio);
						portfolioDeserializer.repository = repository;
						importJsonRecords(entry.getValue().getAsJsonArray(), portfolioDeserializer);
					} else {
						throw new ParseException("Unable to parse, missing account/path", 0);
					}
				} else {
					final String key = entry.getKey();
					final String val = entry.getValue().getAsString();
					if (key.equals("account")) {
						account = val;
					} else if (key.equals("path")) {
						path = val;
					} else {
						throw new ParseException("Unknown key (" + key + ")", 0);
					}
				}
			}
		}
	}
	private class PortfolioDeserializer implements Deserializer {
		public Repository repository;
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
						words.setFilePath(this.repository.getPath() + val);
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
			picture.setMetadata(metadata);
			this.repository.add(picture);
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				if (entry.getValue().isJsonObject()) {
					for (final Entry<String, JsonElement> entr : ((JsonObject)entry.getValue()).entrySet()) {
						this.setPictureProperty(entr, picture);
					}
				} else {
					this.setPictureProperty(entry, picture);
				}
			}
			this.portfolio.addPicture(picture);
			//System.out.println("Added picture: " + picture.toString());
		}
		public void setPictureProperty(Entry<String, JsonElement> entry, Picture picture) {
			final String key = entry.getKey();
			final String val = entry.getValue().getAsString();
			Metadata metadata = picture.getMetadata();
			try {
				if (key.equals("filename")) { // Original metadata used "filename"
					picture.setFilePath(this.repository.getBaseUrl() + this.repository.getRelativeUrl() + val);
					picture.setLocalFilePath(this.repository.getRelativeUrl() + val);
				} else if (key.equals("filePath")) {
					picture.setFilePath(val);
				} else if (key.equals("localFilePath")) {
					picture.setLocalFilePath(val);
				} else if (key.equals("title")) {
					metadata.setTitle(val);
				} else if (key.equals("caption")) { // Original metadata used "caption"
					metadata.setDescription(val);
				} else if (key.equals("description")) { // Javascript uses "description"
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
				} else if (key.equals("realDate")) {
					// This is derived from date in the metadata class
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
		public String getFileName() {
			return this.repository.getPath() + "/metadata.json";
		}
	}
	
	public static void main(String[] args) {
		importer = new Importer();
		portfolio = importer.createPortfolioFromMetadata();
//		portfolio = importer.createPortfolioFromRepository();
		System.out.println(portfolio.toString());
	}
	
	public Importer() {
	}
	public Portfolio createPortfolioFromRepository() {
		RepositoryDeserializer repositoryDeserializer = new RepositoryDeserializer();
		this.importJson(repositoryDeserializer);
//		System.out.println("After loading the portfolio is: " + repositoryDeserializer.portfolio.toString());
		return repositoryDeserializer.portfolio;
	}
	public Portfolio createPortfolioFromMetadata() {
		portfolio = new Portfolio();
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
			Repository repository = null;
			//portfolioDeserializer.path = rec.path;
			//portfolioDeserializer.url = rec.path;
			if (rec.path.equals("images")) {
				repository = new Repository("tjwillekes", "images");
				//portfolioDeserializer.url = "http://members.shaw.ca/tjwillekes/images";
			} else if (rec.path.equals("images_2")) {
				repository = new Repository("tomjwillekes", "images_2");
				//portfolioDeserializer.url = "http://members.shaw.ca/tomjwillekes/images_2";
			} else if (rec.path.equals("newImages")) {
				repository = new Repository("twillekes", "newImages");
			} else if (rec.path.equals("words")) {
				continue;
//				portfolioDeserializer.isWords = true;
			}
			portfolioDeserializer.repository = repository;
			this.importJson(portfolioDeserializer);
//			System.out.println("After loading " + rec.path + " the portfolio is: " + portfolio.toString());
		}
		return portfolio;
	}
	public void importJson(Deserializer deserializer) {
		final JsonParser parser = new JsonParser();
		final FileReader fileReader;
		try {
			fileReader = new FileReader(deserializer.getFileName());
		} catch (FileNotFoundException err) {
			err.printStackTrace();
			return;
		}
		try {
			final JsonElement jsonElement = parser.parse(fileReader);
			if (jsonElement.isJsonObject()) {
				final JsonObject jsonObject = jsonElement.getAsJsonObject();
				importJsonRecords(jsonObject, deserializer);
			} else if (jsonElement.isJsonArray()) {
				final JsonArray jsonArray = jsonElement.getAsJsonArray();
				importJsonRecords(jsonArray, deserializer);
			} else {
				throw new ParseException("Unhandled JSON data type", 0);
			}
		} catch(ParseException e) {
			e.printStackTrace();
			return;
		}
	}
	public static void importJsonRecords(JsonObject jsonObject, Deserializer deserializer) throws ParseException {
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
		importJsonRecords(jsonArray, deserializer);
	}
	public static void importJsonRecords(JsonArray jsonArray, Deserializer deserializer) throws ParseException {
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
