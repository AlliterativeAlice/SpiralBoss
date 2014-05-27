package com.spiralboss;
import org.json.simple.JSONObject;


public abstract class SearchResponse {
	
	/**
	 * The number of results skipped before results were selected for return.
	 */
	public int Start;
	
	/**
	 * The number of results returned.
	 */
	public int Count;
	
	/**
	 * The total number of results that match the query.
	 */
	public int TotalResults;

	SearchResponse(JSONObject jsonObject) {
		this.Start = Integer.parseInt((String)jsonObject.get("start"));
		this.Count = Integer.parseInt((String)jsonObject.get("count"));
		this.TotalResults = Integer.parseInt((String)jsonObject.get("totalresults"));
	}

}
