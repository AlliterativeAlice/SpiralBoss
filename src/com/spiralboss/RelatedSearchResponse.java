package com.spiralboss;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class RelatedSearchResponse extends SearchResponse {
	
	/**
	 * The results of the related search.
	 */
	List<String> Results = new ArrayList<String>();

	RelatedSearchResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		if (this.Count > 0) {
			JSONArray results = (JSONArray)jsonObject.get("results");
			@SuppressWarnings("unchecked") Iterator<JSONObject> iterator = results.iterator();
			while (iterator.hasNext()) {
				JSONObject suggestionObject = (JSONObject)iterator.next();
				Results.add((String)suggestionObject.get("suggestion"));
			}
		}
	}

}
