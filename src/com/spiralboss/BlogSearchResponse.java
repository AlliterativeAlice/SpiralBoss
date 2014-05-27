package com.spiralboss;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class BlogSearchResponse extends SearchResponse {

	/**
	 * The results of the blog post search.
	 */
	List<BlogResult> Results = new ArrayList<BlogResult>();

	BlogSearchResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		if (this.Count > 0) {
			JSONArray results = (JSONArray) jsonObject.get("results");
			@SuppressWarnings("unchecked") Iterator<JSONObject> iterator = results.iterator();
			while (iterator.hasNext()) {
				Results.add(new BlogResult(iterator.next()));
			}
		}
	}

}
