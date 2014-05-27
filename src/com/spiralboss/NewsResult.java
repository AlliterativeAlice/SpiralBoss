package com.spiralboss;
import java.util.Date;

import org.json.simple.JSONObject;

public class NewsResult {
	
	/**
     * The title of the news article.
     */ 
	public String Title;
	
	/**
     * A short preview extracted from the news article.
     */ 
	public String Abstract;
	
	/**
     * URL of the news story.
     */ 
	public String URL;
	
	/**
     * The article's source publication.
     */ 
	public String Source;
	
	/**
     * URL of the website of the article's source publication.
     */ 
	public String SourceURL;
	
	/**
     * The article's publication date.
     */ 
	public Date Date;
	
	/**
     * UNIX timestamp of the article's publication date.
     */ 
	public long Timestamp;
	
	/**
     * The article's language.
     */ 
	public String Language;
	
	//ClickURL not included in JSON results
	//public String ClickURL;

	NewsResult(JSONObject jsonObject) {
		this.Title = (String)jsonObject.get("title");
		this.Abstract = (String)jsonObject.get("abstract");
		this.URL = (String)jsonObject.get("url");
		this.Source = (String)jsonObject.get("source");
		this.SourceURL = (String)jsonObject.get("sourceurl");
		this.Language = (String)jsonObject.get("language");
		//this.ClickURL = (String)jsonObject.get("clickurl");
		
		String strTimeStamp = (String)jsonObject.get("date");
		if (strTimeStamp == null || strTimeStamp.length() == 0) {
			this.Timestamp = 0;
			this.Date = null;
		}
		else {
			this.Timestamp = Long.parseLong(strTimeStamp);
			this.Date = new Date(this.Timestamp * 1000);
		}
	}

}
