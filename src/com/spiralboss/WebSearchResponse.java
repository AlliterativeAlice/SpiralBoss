package com.spiralboss;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class WebSearchResponse extends SearchResponse {
	
	/**
	 * The results of the web search.
	 */
	List<WebResult> Results = new ArrayList<WebResult>();

	WebSearchResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		if (this.Count > 0) {
			JSONArray results = (JSONArray) jsonObject.get("results");
			@SuppressWarnings("unchecked") Iterator<JSONObject> iterator = results.iterator();
			while (iterator.hasNext()) {
				Results.add(new WebResult(iterator.next()));
			}
		}
	}

}
