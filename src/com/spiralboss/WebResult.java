package com.spiralboss;
import java.util.Date;

import org.json.simple.JSONObject;


public class WebResult {
	
	/**
	 * Title of web page. Contains HTML to bold query keywords found in the title.
	 */
	public String Title;
	
	/**
	 * A short preview extracted from the web page.
	 */
	public String Abstract;
	
	/**
	 * URL of the web page.
	 */
	public String URL;
	
	/**
	 * The URL of the web page containing HTML to bold query keywords found in the URL.
	 */
	public String DisplayURL;
	
	/**
	 * Date that the web page was last crawled.
	 */
	public Date Date;
	
	/**
	 * UNIX timestamp of the date the web page was last crawled. 
	 */
	public long Timestamp;
	
	/**
	 * URL for a clickable link to the web page.
	 */
	public String ClickURL;

	WebResult(JSONObject jsonObject) {
		this.Title = (String)jsonObject.get("title");
		this.Abstract = (String)jsonObject.get("abstract");
		this.URL = (String)jsonObject.get("url");
		this.DisplayURL = (String)jsonObject.get("dispurl");
		this.ClickURL = (String)jsonObject.get("clickurl");
		
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
