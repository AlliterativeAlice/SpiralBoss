package com.spiralboss;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NewsSearchResponse extends SearchResponse {
	
	/**
	 * The results of the news search.
	 */
	List<NewsResult> Results = new ArrayList<NewsResult>();

	NewsSearchResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		if (this.Count > 0) {
			JSONArray results = (JSONArray) jsonObject.get("results");
			@SuppressWarnings("unchecked") Iterator<JSONObject> iterator = results.iterator();
			while (iterator.hasNext()) {
				Results.add(new NewsResult(iterator.next()));
			}
		}
	}

}
