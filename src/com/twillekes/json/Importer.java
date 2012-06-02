package com.twillekes.json;

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
import com.twillekes.portfolio.Folder;
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
		public void handleJsonObject(JsonObject obj) throws Exception;
	}
	
	private class LocationDeserializer implements Deserializer {
		public List<MetadataRecord> metadataRecords;
		public LocationDeserializer() {
			this.metadataRecords = new ArrayList<MetadataRecord>();
		}
		public String getFileName() {
			return Repository.instance().getOriginalBasePath() + "locations.json";
		}
		public void handleJsonObject(JsonObject obj) throws Exception {
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
			return Repository.instance().getFileName();
		}
		public void handleJsonObject(JsonObject obj) throws Exception {
			String account = null;
			String path = null;
			PortfolioDeserializer portfolioDeserializer = null;
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				final String key = entry.getKey();
				if (key.equals("account")) {
					account = entry.getValue().getAsString();
					path = null;
					portfolioDeserializer = null;
				} else if (key.equals("path")) {
					path = entry.getValue().getAsString();
					if (account != null && path != null) {
						Folder folder = Repository.instance().createFolder(account, path);
						portfolioDeserializer = new PortfolioDeserializer(this.portfolio, PortfolioType.PICTURES);
						portfolioDeserializer.folder = folder;
					} else {
						throw new Exception("Control flow error, missing account/path");
					}
				} else if (key.equals("pictures")) {
					if (portfolioDeserializer != null && entry.getValue().isJsonArray()) {
						importJsonRecords(entry.getValue().getAsJsonArray(), portfolioDeserializer);
					} else {
						throw new ParseException("Unable to parse, not an array", 0);
					}
				} else if (key.equals("trash")) {
					if (portfolioDeserializer != null) {
						portfolioDeserializer.type = PortfolioType.TRASH;
						importJsonRecords(entry.getValue().getAsJsonArray(), portfolioDeserializer);
					} else {
						throw new ParseException("Missing portfolio deserializer", 0);
					}
				} else if (key.equals("words")) {
					if (portfolioDeserializer != null) {
						portfolioDeserializer.type = PortfolioType.WORDS;
						importJsonRecords(entry.getValue().getAsJsonArray(), portfolioDeserializer);
					} else {
						throw new ParseException("Missing portfolio deserializer", 0);
					}
				}
			}
		}
	}
	public enum PortfolioType {
		METADATA_WORDS, METADATA_PICTURES,
		WORDS, PICTURES, TRASH
	}
	private class PortfolioDeserializer implements Deserializer {
		public Folder folder;
		public Portfolio portfolio;
		public PortfolioType type;
		private long minuteIncrement = 0;
		public PortfolioDeserializer(Portfolio portfolio, PortfolioType type) {
			this.portfolio = portfolio;
			this.type = type;
		}
		public void handleJsonObject(JsonObject obj) throws Exception {
			if (this.type == PortfolioType.WORDS || this.type == PortfolioType.METADATA_WORDS) {
				handleJsonWordsObject(obj);
			} else {
				Picture picture = new Picture();
				Metadata metadata = new Metadata();
				picture.setMetadata(metadata);
				if (this.type == PortfolioType.PICTURES || this.type == PortfolioType.METADATA_PICTURES) {
					handleJsonPictureObject(obj, picture);
				} else {
					handleJsonTrashObject(obj, picture);
				}
			}
		}
		public void handleJsonWordsObject(JsonObject obj) throws Exception {
			Words words = new Words();
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				final String key = entry.getKey();
				final String val = entry.getValue().getAsString();
				try {
					if (key.equals("filename")) {
						words.setFilePath(this.folder.getRelativePath() + val);
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
			this.folder.addWords(words);
		}
		public void handleJsonPictureObject(JsonObject obj, Picture picture) throws Exception {
			this.folder.add(picture);
			iterateThroughPictureJsonObject(obj, picture);
			this.portfolio.addPicture(picture);
			//System.out.println("Added picture: " + picture.toString());
		}
		public void handleJsonTrashObject(JsonObject obj, Picture picture) throws Exception {
			this.folder.addToTrash(picture);
			iterateThroughPictureJsonObject(obj, picture);
			//System.out.println("Added picture: " + picture.toString());
		}
		private void iterateThroughPictureJsonObject(JsonObject obj, Picture picture) {
			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				if (entry.getValue().isJsonObject()) {
					for (final Entry<String, JsonElement> entr : ((JsonObject)entry.getValue()).entrySet()) {
						this.setPictureProperty(entr, picture);
					}
				} else {
					this.setPictureProperty(entry, picture);
				}
			}
		}
		public void setPictureProperty(Entry<String, JsonElement> entry, Picture picture) {
			final String key = entry.getKey();
			final String val = entry.getValue().getAsString();
			Metadata metadata = picture.getMetadata();
			try {
				if (key.equals("filename")) { // Original metadata used "filename"
					picture.setFileName(val);
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
				} else if (key.equals("time")) {
					metadata.setTime(val);
				} else if (key.equals("date")) {
					if (metadata.getTime() == null) {
						// When reading from original metadata, just pad some times to ensure sortedSet behaves.
						// I.e. Comparable in metadata behaves.
						metadata.setTime(minuteIncrement / 60, minuteIncrement % 60, "AM");
						minuteIncrement++;
					}
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
					// If initializing from the original metadata format, assume
					// all new pictures should be in the RSS feed
					if (this.type == PortfolioType.METADATA_PICTURES) {
						metadata.setIsInFeed(val);
					}
					metadata.setIsNew(val);
				} else if (key.equals("isInFeed")) {
					metadata.setIsInFeed(val);
				} else if (key.equals("isFavorite")) {
					metadata.setIsFavorite(val);
				} else {
					throw new Exception("Unknown JSON key ('" + key + "') in items array");
				}
			} catch (Exception e) {
				System.out.println("Encounted exception in file " + this.getFileName() + " error: " + e.getMessage());
			}
		}
		public String getFileName() {
			return this.folder.getOriginalPath() + "/metadata.json";
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
		Repository.instance().setIsDirty(false);
//		System.out.println("After loading the portfolio is: " + repositoryDeserializer.portfolio.toString());
		return repositoryDeserializer.portfolio;
	}
	public Portfolio createPortfolioFromMetadata() {
		portfolio = new Portfolio();
		LocationDeserializer locationDeserializer = new LocationDeserializer();
		this.importJson(locationDeserializer);
		System.out.println("There are " + locationDeserializer.metadataRecords.size() + " metadata records");
		
		PortfolioDeserializer portfolioDeserializer = new PortfolioDeserializer(portfolio, PortfolioType.PICTURES);
		final Iterator<MetadataRecord> it = locationDeserializer.metadataRecords.iterator();
		while(it.hasNext()) {
			final MetadataRecord rec = it.next();
			if (rec.type == RecordType.REMOTE){
				continue;
			}
			Folder folder = null;
			portfolioDeserializer.type = PortfolioType.METADATA_PICTURES;
			if (rec.path.equals("images")) {
				folder = Repository.instance().createFolder("tjwillekes", "images");
			} else if (rec.path.equals("images_2")) {
				folder = Repository.instance().createFolder("tomjwillekes", "images_2");
			} else if (rec.path.equals("newImages")) {
				folder = Repository.instance().createFolder("twillekes", "newImages");
			} else if (rec.path.equals("words")) {
				folder = Repository.instance().createFolder("twillekes", "words");
				portfolioDeserializer.type = PortfolioType.METADATA_WORDS;
			}
			portfolioDeserializer.folder = folder;
			this.importJson(portfolioDeserializer);
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
				throw new Exception("Unhandled JSON data type");
			}
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	public static void importJsonRecords(JsonObject jsonObject, Deserializer deserializer) throws Exception {
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
			throw new Exception("Could not find items list in JSON");
		}
		final JsonArray jsonArray = itemsEntry.getValue().getAsJsonArray();
		importJsonRecords(jsonArray, deserializer);
	}
	public static void importJsonRecords(JsonArray jsonArray, Deserializer deserializer) throws Exception {
		final Iterator<JsonElement> it = jsonArray.iterator();
		while (it.hasNext()) {
			JsonObject obj = it.next().getAsJsonObject();
			deserializer.handleJsonObject(obj);
		}
	}
}
