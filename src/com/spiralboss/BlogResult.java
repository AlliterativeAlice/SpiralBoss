package com.spiralboss;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;


public class BlogResult {
	
	/**
	 * Title of the blog post. Contains HTML to bold query keywords found in the title.
	 */
	public String Title;
	
	/**
	 * A short preview extracted from the blog post. Contains HTML to bold query keywords found in the title.
	 */
	public String Abstract;
	
	/**
	 * The blog provider the blog is hosted by.
	 */
	public String Provider;
	
	/**
	 * The URL of the blog post containing HTML to bold query keywords found in the URL.
	 */
	public String DisplayURL;
	
	/**
	 * The date of the blog post.
	 */
	public Date Date;
	
	/**
	 * UNIX timestamp of the date of the blog post.
	 */
	public long Timestamp;
	
	/**
	 * Relevancy of the blog post to the query.
	 */
	public float Score;
	
	/**
	 * Words that match the query.
	 */
	public String KeyTerms;
	
	/**
	 * The name of the blog post's author.
	 */
	public String Author;
	
	/**
	 * URL of an associated image that may be part of the blog.
	 */
	public String IconURL;
	
	/**
	 * URL for clickable link to the blog post.
	 */
	public String ClickURL;

	BlogResult(JSONObject jsonObject) {
		this.Title = (String)jsonObject.get("title");
		this.Abstract = (String)jsonObject.get("abstract");
		this.Provider = (String)jsonObject.get("provider");
		this.DisplayURL = (String)jsonObject.get("dispurl");
		this.KeyTerms = (String)jsonObject.get("keyterms");
		this.Author = (String)jsonObject.get("author");
		this.IconURL = (String)jsonObject.get("icon");
		this.ClickURL = (String)jsonObject.get("clickurl");
		
		String dateString = (String)jsonObject.get("date");
		if (dateString == null || dateString.length() == 0) {
			this.Timestamp = 0;
			this.Date = null;
		}
		else {
			String[] splitDate = dateString.split("\\/");
			int year = Integer.parseInt(splitDate[0]);
			int month = Integer.parseInt(splitDate[1]);
			int day = Integer.parseInt(splitDate[2]);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day);
			this.Date = cal.getTime();
			this.Timestamp = Math.round(this.Date.getTime() / 1000);
		}
		
		String scoreString = (String)jsonObject.get("score");
		if (scoreString == null || scoreString.length() == 0) this.Score = 0;
		else this.Score = Float.parseFloat(scoreString);
	}

}
