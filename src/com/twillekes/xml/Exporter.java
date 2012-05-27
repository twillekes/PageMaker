package com.twillekes.xml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

//import com.twillekes.json.Importer;
import com.twillekes.portfolio.Picture;
import com.twillekes.portfolio.Portfolio;
import com.twillekes.portfolio.Repository;

public class Exporter {
//	public static void main(String[] args) {
//		Importer imp = new Importer();
//		Portfolio portfolio = imp.createPortfolioFromRepository();
//		Exporter exp = new Exporter();
//		exp.export(portfolio);
//	}
	public void export(Portfolio portfolio) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		Document doc = docBuilder.newDocument();
		
		buildRSS(portfolio, doc);
		
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		String xmlString = escape(result.getWriter().toString());
		System.out.println(xmlString);
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(Repository.getPagePath() + "feed.xml"));
			out.write(xmlString);
			out.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	public void buildRSS(Portfolio portfolio, Document doc) {
		Node rss = doc.createElement("rss");
		Attr rssVer = doc.createAttribute("version");
		rssVer.setValue("2.0");
		rss.getAttributes().setNamedItem(rssVer);
		doc.appendChild(rss);
		
		Node channel = doc.createElement("channel");
		rss.appendChild(channel);
		
		Node title = doc.createElement("title");
		title.setTextContent("eMulsions: The Photography of Tom Willekes");
		channel.appendChild(title);
		
		Node descr = doc.createElement("description");
		descr.setTextContent("RSS Feed for eMulsions: The Photography of Tom Willekes");
		channel.appendChild(descr);
		
		Node link = doc.createElement("link");
		link.setTextContent("http://members.shaw.ca/twillekes/");
		channel.appendChild(link);

		Iterator<Picture> it = portfolio.getPictures("isInFeed", "1").iterator();
		while(it.hasNext()) {
			Picture pic = it.next();
			Node item = doc.createElement("item");
			
			Node title_ = doc.createElement("title");
			title_.setTextContent(pic.getMetadata().getTitle());
			item.appendChild(title_);
			
			Node descrip = doc.createElement("description");
			descrip.setTextContent(pic.getMetadata().getDescription());
			item.appendChild(descrip);
			
			Node link_ = doc.createElement("link");
			link_.setTextContent(pic.getFilePath());
			item.appendChild(link_);
			
			channel.appendChild(item);
		}
	}
	public String escape(String in) {
		if (in == null) {
			return null;
		}
		String esc = in;
		esc = esc.replaceAll("&", "&amp;");
		esc = esc.replaceAll("'", "&apos;");
		//esc = esc.replaceAll("<", "&lt;");
		//esc = esc.replaceAll(">", "&gt;");
		return esc;
	}
}
