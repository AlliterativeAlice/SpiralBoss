package com.spiralboss;
import oauth.signpost.*;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A context associated with a specific consumerKey/consumerSecret pair. Provides methods for calling the Yahoo! BOSS API.
 */
public class BossContext {
	
	private OAuthConsumer consumer;
	private JSONParser parser = new JSONParser();

	/**
	 * Constructor for creating a new context from a consumerKey and consumerSecret pair.
	 * @param  consumerKey    The consumer key provided by Yahoo!
	 * @param  consumerSecret The consumer secret value provided by Yahoo!
	 */
	public BossContext(String consumerKey, String consumerSecret) {
		this.consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
	}
	
	// Region NewsAPI
	
	/**
	 * Calls the Yahoo! BOSS News Search API
	 *
	 * @param  query         The query to search.
	 * @param  start         The number of results to skip before selecting results to return. Must be greater than 0 and less than 1000. Defaults to 0.
	 * @param  count         The number of results to return. Must be greater than 0 and no more than 50. Defaults to 50.
	 * @param  market        The region/language whose news articles should be searched. Defaults to United States English.
	 * @param  includedSites If specified, search will be limited to sites in the array. By default all sites are included.
	 * @param  maxAge        If specified, news articles older than the specified age will be excluded from the search. If a range is specified, articles with ages outside that range will be excluded. Valid values include 12h, 2d, 2w, 5d-8d, etc. The maximum value is 30d.
	 * @param  title         Limits search to news sources with the specified value in the title.
	 * @param  url           Limits search to news sources with the specified value in the url.
	 */
	public NewsSearchResponse searchNews(String query, int start, int count, NewsRegionLanguage market, String[] includedSites, String maxAge, String title, String url) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		if (count < 50) params.put("count", Integer.toString(count));
		if (start > 0) params.put("start", Integer.toString(start));
		if (market != null) params.put("market", market.getCode());
		if (!stringIsNullOrEmpty(maxAge)) params.put("age", maxAge);
		if (!stringIsNullOrEmpty(title)) params.put("title", title);
		if (!stringIsNullOrEmpty(url)) params.put("url", url);
		
		if ((includedSites != null && includedSites.length > 0)) {
			StringBuffer sitesBuffer = new StringBuffer();
			for (String site : includedSites) sitesBuffer.append(site + ",");
			params.put("sites", trimStr(sitesBuffer.toString(), ','));
		}
		
		JSONObject bossResponse = getJson("news", params);
		return new NewsSearchResponse((JSONObject) bossResponse.get("news") );
	}
	
	/**
	 * @see #searchNews(String,int,int,NewsRegionLanguage,String[],String,String,String)
	 */
	public NewsSearchResponse searchNews(String query) { return searchNews(query, 0, 50, null, null, null, null, null); }
	/**
	 * @see #searchNews(String,int,int,NewsRegionLanguage,String[],String,String,String)
	 */
	public NewsSearchResponse searchNews(String query, String age) { return searchNews(query, 0, 50, null, null, age, null, null); }
	/**
	 * @see #searchNews(String,int,int,NewsRegionLanguage,String[],String,String,String)
	 */
	public NewsSearchResponse searchNews(String query, String age, String title) { return searchNews(query, 0, 50, null, null, age, title, null); }
	/**
	 * @see #searchNews(String,int,int,NewsRegionLanguage,String[],String,String,String)
	 */
	public NewsSearchResponse searchNews(String query, int start) { return searchNews(query, start, 50, null, null, null, null, null); }
	/**
	 * @see #searchNews(String,int,int,NewsRegionLanguage,String[],String,String,String)
	 */
	public NewsSearchResponse searchNews(String query, int start, int count) { return searchNews(query, start, count, null, null, null, null, null); }
	/**
	 * @see #searchNews(String,int,int,NewsRegionLanguage,String[],String,String,String)
	 */
	public NewsSearchResponse searchNews(String query, NewsRegionLanguage market) { return searchNews(query, 0, 50, market, null, null, null, null); }
	/**
	 * @see #searchNews(String,int,int,NewsRegionLanguage,String[],String,String,String)
	 */
	public NewsSearchResponse searchNews(String query, int start, int count, NewsRegionLanguage market) { return searchNews(query, start, count, market, null, null, null, null); }
	
	// EndRegion NewsAPI
	
	// Region BlogAPI
	
	/**
	 * Calls the Yahoo! BOSS Blog Search API
	 *
	 * @param  query         The query to search.
	 * @param  start         The number of results to skip before selecting results to return. Must be greater than 0 and less than 1000. Defaults to 0.
	 * @param  count         The number of results to return. Must be greater than 0 and no more than 20. Defaults to 20.
	 * @param  market        The region/language whose blog posts should be searched. Defaults to United States English.
	 * @param  includedSites If specified, search will be limited to sites in the array. By default all sites are included.
	 * @param  maxAge        If specified, blog posts older than the specified age will be excluded from the search. If a range is specified, articles with ages outside that range will be excluded. Valid values include 12h, 2d, 2w, 5d-8d, etc. The maximum value is 30d.
	 * @param  title         Limits search to blog sources with the specified value in the title.
	 */
	public BlogSearchResponse searchBlogs(String query, int start, int count, BlogRegionLanguage market, String[] includedSites, String maxAge, String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		if (count < 20 && count > 0) params.put("count", Integer.toString(count));
		if (start > 0 && start < 1000) params.put("start", Integer.toString(start));
		if (market != null) params.put("market", market.getCode());
		if (!stringIsNullOrEmpty(maxAge)) params.put("age", maxAge);
		if (!stringIsNullOrEmpty(title)) params.put("title", title);
		
		if ((includedSites != null && includedSites.length > 0)) {
			StringBuffer sitesBuffer = new StringBuffer();
			for (String site : includedSites) sitesBuffer.append(site + ",");
			params.put("sites", trimStr(sitesBuffer.toString(), ','));
		}
		
		JSONObject bossResponse = getJson("blogs", params);
		return new BlogSearchResponse((JSONObject) bossResponse.get("blogs") );
	}
	
	/**
	 * @see #searchBlogs(String,int,int,BlogRegionLanguage,String[],String,String)
	 */
	public BlogSearchResponse searchBlogs(String query) { return searchBlogs(query, 0, 20, null, null, null, null); }
	/**
	 * @see #searchBlogs(String,int,int,BlogRegionLanguage,String[],String,String)
	 */
	public BlogSearchResponse searchBlogs(String query, int start) { return searchBlogs(query, start, 20, null, null, null, null); }
	/**
	 * @see #searchBlogs(String,int,int,BlogRegionLanguage,String[],String,String)
	 */
	public BlogSearchResponse searchBlogs(String query, int start, String age) { return searchBlogs(query, start, 20, null, null, age, null); }
	/**
	 * @see #searchBlogs(String,int,int,BlogRegionLanguage,String[],String,String)
	 */
	public BlogSearchResponse searchBlogs(String query, String age) { return searchBlogs(query, 0, 20, null, null, age, null); }
	/**
	 * @see #searchBlogs(String,int,int,BlogRegionLanguage,String[],String,String)
	 */
	public BlogSearchResponse searchBlogs(String query, String age, String title) { return searchBlogs(query, 0, 20, null, null, age, title); }
	/**
	 * @see #searchBlogs(String,int,int,BlogRegionLanguage,String[],String,String)
	 */
	public BlogSearchResponse searchBlogs(String query, int start, int count) { return searchBlogs(query, start, count, null, null, null, null); }
	/**
	 * @see #searchBlogs(String,int,int,BlogRegionLanguage,String[],String,String)
	 */
	public BlogSearchResponse searchBlogs(String query, int start, int count, BlogRegionLanguage market) { return searchBlogs(query, start, count, market, null, null, null); }
	
	// EndRegion BlogAPI
	
	// Region WebAPI
	
	/**
	 * Calls the Yahoo! BOSS Web Search API
	 *
	 * @param  query           The query to search.
	 * @param  start           The number of results to skip before selecting results to return. Must be greater than 0 and less than 1000. Defaults to 0.
	 * @param  count           The number of results to return. Must be greater than 0 and no more than 50. Defaults to 50.
	 * @param  market          The region/language whose web pages should be searched. Defaults to United States English.
	 * @param  includedSites   An array of sites that should be included in the search. By default all sites are included.
	 * @param  excludedSites   An array of sites that should be excluded from the search. By default no sites are excluded.
	 * @param  excludePorn     Whether or not known pornographic sites should be excluded from the search. Defaults to false.
	 * @param  longAbstracts   If set to true, abstract length will be expanded to 300 characters.
	 * @param  stripHtml       If set to true, HTML tags will be stripped from the abstract.
	 * @param  includedFormats An array of document formats to include in the search. By default all formats are included.
	 * @param  excludedFormats An array of document formats to exclude from the search. By default no formats are excluded.
	 * @param  languages       An array of languages to include in the search. 
	 * @param  title           Limits search to web pages with the specified value in the title.
	 * @param  url             Limits search to web pages with the specified value in the url.
	 */
	public WebSearchResponse searchWeb(String query, int start, int count, WebRegionLanguage market, String[] includedSites, String[] excludedSites, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats, SearchLanguage[] languages, String title, String url) {
		Map<String, String> params = generateWebSearchParams(query, start, count, includedSites, excludedSites, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, languages, title, url);
		if (market != null) params.put("market", market.getCode());
		
		JSONObject bossResponse = getJson("web", params);
		return new WebSearchResponse((JSONObject) bossResponse.get("web") );
	}
	
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query) { return searchWeb(query, 0, 0, null, null, null, false, false, false, null, null, null, null, null); }
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query, boolean excludePorn) { return searchWeb(query, 0, 0, null, null, null, excludePorn, false, false, null, null, null, null, null); }
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query, boolean excludePorn, boolean longAbstracts) { return searchWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, false, null, null, null, null, null); }
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml) { return searchWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, null, null, null, null, null); }
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats) { return searchWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, null, null, null, null); }
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats) { return searchWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, null, null, null); }
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats, SearchLanguage[] languages) { return searchWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, languages, null, null); }
	/**
	 * @see #searchWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats, SearchLanguage[] languages, String title) { return searchWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, languages, title, null); }
	
	// EndRegion WebAPI
	
	// Region LimitedWebAPI
	
	/**
	 * Calls the Yahoo! BOSS Limited Web Search API. Limited web search is cheaper but only searches a limited index and has a slower refresh rate (~3 days.)
	 *
	 * @param  query           The query to search.
	 * @param  start           The number of results to skip before selecting results to return. Must be greater than 0 and less than 1000. Defaults to 0.
	 * @param  count           The number of results to return. Must be greater than 0 and no more than 50. Defaults to 50.
	 * @param  market          The region/language whose web pages should be searched. Defaults to United States English.
	 * @param  includedSites   An array of sites that should be included in the search. By default all sites are included.
	 * @param  excludedSites   An array of sites that should be excluded from the search. By default no sites are excluded.
	 * @param  excludePorn     Whether or not known pornographic sites should be excluded from the search. Defaults to false.
	 * @param  longAbstracts   If set to true, abstract length will be expanded to 300 characters.
	 * @param  stripHtml       If set to true, HTML tags will be stripped from the abstract.
	 * @param  includedFormats An array of document formats to include in the search. By default all formats are included.
	 * @param  excludedFormats An array of document formats to exclude from the search. By default no formats are excluded.
	 * @param  languages       An array of languages to include in the search. 
	 * @param  title           Limits search to web pages with the specified value in the title.
	 * @param  url             Limits search to web pages with the specified value in the url.
	 */
	public WebSearchResponse searchLimitedWeb(String query, int start, int count, LimitedWebRegionLanguage market, String[] includedSites, String[] excludedSites, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats, SearchLanguage[] languages, String title, String url) {
		Map<String, String> params = generateWebSearchParams(query, start, count, includedSites, excludedSites, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, languages, title, url);
		if (market != null) params.put("market", market.getCode());
		
		JSONObject bossResponse = getJson("limitedweb", params);
		return new WebSearchResponse((JSONObject) bossResponse.get("limitedweb") );
	}
	
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query) { return searchLimitedWeb(query, 0, 0, null, null, null, false, false, false, null, null, null, null, null); }
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query, boolean excludePorn) { return searchLimitedWeb(query, 0, 0, null, null, null, excludePorn, false, false, null, null, null, null, null); }
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query, boolean excludePorn, boolean longAbstracts) { return searchLimitedWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, false, null, null, null, null, null); }
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml) { return searchLimitedWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, null, null, null, null, null); }
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats) { return searchLimitedWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, null, null, null, null); }
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats) { return searchLimitedWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, null, null, null); }
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats, SearchLanguage[] languages) { return searchLimitedWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, languages, null, null); }
	/**
	 * @see #searchLimitedWeb(String,int,int,WebRegionLanguage,String[],String[],boolean,boolean,boolean,SearchDocumentFormat[],SearchDocumentFormat[],SearchLanguage[],String,String)
	 */
	public WebSearchResponse searchLimitedWeb(String query, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats, SearchLanguage[] languages, String title) { return searchLimitedWeb(query, 0, 0, null, null, null, excludePorn, longAbstracts, stripHtml, includedFormats, excludedFormats, languages, title, null); }
	
	// EndRegion LimitedWebAPI
	
	// Region ImageAPI
	
	/**
	 * Calls the Yahoo! BOSS Image Search API.
	 *
	 * @param  query                  The query to search.
	 * @param  start                  The number of results to skip before selecting results to return. Must be greater than 0 and less than 1000. Defaults to 0.
	 * @param  count                  The number of results to return. Must be greater than 0 and no more than 35. Defaults to 35.
	 * @param  market                 The region/language whose images should be searched. Defaults to United States English.
	 * @param  site                   A site to limit search to/exclude from the search. Site can be excluded by prepending it with a '-' (e.g. -example.com). Image search currently only allows a single site to be specified.
	 * @param  exludeOffensiveContent Whether or not offensive images should be excluded. Defaults to true.
	 * @param  queryFilters           Array of content categories that should be filtered out of the results.
	 * @param  dimensions             Array of image dimensions that should be included in the search. By default all dimensions are included.
	 * @param  refererUrl             Restricts search to images with the specified referer URL.
	 * @param  url                    Limits search to images with the specified URL.
	 */
	public ImageSearchResponse searchImages(String query, int start, int count, ImageRegionLanguage market, String site, boolean exludeOffensiveContent, ImageQueryFilter[] queryFilters, ImageDimension[] dimensions, String refererUrl, String url) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		if (count < 35 && count > 0) params.put("count", Integer.toString(count));
		if (start > 0 && start < 1000) params.put("start", Integer.toString(start));
		if (market != null) params.put("market", market.getCode());
		if (!stringIsNullOrEmpty(site)) params.put("sites", site);
		if (!stringIsNullOrEmpty(refererUrl)) params.put("refererurl", refererUrl);
		if (!stringIsNullOrEmpty(url)) params.put("url", url);
		if (!exludeOffensiveContent) params.put("filter", "no");
		
		if (queryFilters != null && queryFilters.length > 0 && !arrayContains(queryFilters,ImageQueryFilter.None)) {
			StringBuffer queryFilter = new StringBuffer();
			for (ImageQueryFilter filter : queryFilters) queryFilter.append("-" + filter.getCode() + ",");
			params.put("queryfilter", trimStr(queryFilter.toString(), ','));
		}

		if (dimensions != null && dimensions.length > 0 && !arrayContains(dimensions, ImageDimension.All)) {
			StringBuffer dimensionFilter = new StringBuffer();
			for (ImageDimension dimension : dimensions) dimensionFilter.append("-" + dimension.getCode() + ",");
			params.put("dimensions", trimStr(dimensionFilter.toString(), ','));
		}
		
		JSONObject bossResponse = getJson("images", params);
		return new ImageSearchResponse((JSONObject) bossResponse.get("images") );
	}
	
	/**
	 * @see #searchImages(String,int,int,ImageRegionLanguage,String,boolean,ImageQueryFilter[],ImageDimension[],String,String)
	 */
	public ImageSearchResponse searchImages(String query) { return searchImages(query, 0, 0, null, null, true, null, null, null, null); }
	/**
	 * @see #searchImages(String,int,int,ImageRegionLanguage,String,boolean,ImageQueryFilter[],ImageDimension[],String,String)
	 */
	public ImageSearchResponse searchImages(String query, boolean exludeOffensiveContent) { return searchImages(query, 0, 0, null, null, exludeOffensiveContent, null, null, null, null); }
	/**
	 * @see #searchImages(String,int,int,ImageRegionLanguage,String,boolean,ImageQueryFilter[],ImageDimension[],String,String)
	 */
	public ImageSearchResponse searchImages(String query, boolean exludeOffensiveContent, ImageQueryFilter[] queryFilters) { return searchImages(query, 0, 0, null, null, exludeOffensiveContent, queryFilters, null, null, null); }
	/**
	 * @see #searchImages(String,int,int,ImageRegionLanguage,String,boolean,ImageQueryFilter[],ImageDimension[],String,String)
	 */
	public ImageSearchResponse searchImages(String query, boolean exludeOffensiveContent, ImageQueryFilter[] queryFilters, ImageDimension[] dimensions) { return searchImages(query, 0, 0, null, null, exludeOffensiveContent, queryFilters, dimensions, null, null); }
	/**
	 * @see #searchImages(String,int,int,ImageRegionLanguage,String,boolean,ImageQueryFilter[],ImageDimension[],String,String)
	 */
	public ImageSearchResponse searchImages(String query, boolean exludeOffensiveContent, ImageQueryFilter[] queryFilters, ImageDimension[] dimensions, String refererUrl) { return searchImages(query, 0, 0, null, null, exludeOffensiveContent, queryFilters, dimensions, refererUrl, null); }
	
	// EndRegion ImageAPI
	
	// Region RelatedAPI
	
	/**
	 * Calls the Yahoo! BOSS Related Search API.
	 *
	 * @param  query  The query to search.
	 * @param  count  The number of results to return. Must be greater than 0 and no more than 10. Defaults to 10.
	 * @param  market The region/language to search. Defaults to United States English.
	 */
	public RelatedSearchResponse searchRelated(String query, int count, RelatedRegionLanguage market) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		if (count > 0 && count < 10) params.put("count", Integer.toString(count));
		if (market != null) params.put("market", market.getCode());
		
		JSONObject bossResponse = getJson("related", params);
		return new RelatedSearchResponse((JSONObject) bossResponse.get("related") );
	}
	
	/**
	 * @see #searchRelated(String,int,RelatedRegionLanguage)
	 */
	public RelatedSearchResponse searchRelated(String query) { return searchRelated(query, 0, null); }
	/**
	 * @see #searchRelated(String,int,RelatedRegionLanguage)
	 */
	public RelatedSearchResponse searchRelated(String query, int count) { return searchRelated(query, count, null); }
	/**
	 * @see #searchRelated(String,int,RelatedRegionLanguage)
	 */
	public RelatedSearchResponse searchRelated(String query, RelatedRegionLanguage market) { return searchRelated(query, 0, market); }
	
	// EndRegion RelatedAPI
	
	// Region SpellingAPI
	
	/**
	 * Calls the Yahoo! BOSS Spelling Suggestion API. Spelling suggestion API always returns either 1 result, or 0 results.
	 *
	 * @param  wordToCheck  The word to search for a corrected spelling for.
	 * @param  market       The region/language to search. Defaults to United States English.
	 * @return The spelling suggestion if the API returned one. Null if the API has no suggestion.
	 */
	public String getSpellingSuggestions(String wordToCheck, SpellingRegionLanguage market) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", wordToCheck);
		if (market != null) params.put("market", market.getCode());
		
		JSONObject bossResponse = getJson("spelling", params);
		JSONObject spellingResponse = (JSONObject)bossResponse.get("spelling");
		int count = Integer.parseInt((String)spellingResponse.get("count"));
		if (count > 0) {
			JSONArray arr = (JSONArray)spellingResponse.get("results");
			JSONObject suggestionObject = (JSONObject)arr.get(0);
			return (String) suggestionObject.get("suggestion");
		}
		return null;
	}
	
	/**
	 * @see #getSpellingSuggestions(String,SpellingRegionLanguage)
	 */
	public String getSpellingSuggestions(String wordToCheck) { return getSpellingSuggestions(wordToCheck, null); }
	
	// EndRegion SpellingAPI
	
	// Region RequestManagement
	
	private JSONObject getJson(String endpoint, Map<String, String> params) {
		try {
			String responseString = sendRequest(endpoint, params);
			JSONObject jsonObject = (JSONObject) parser.parse(responseString);
			
			JSONObject bossResponse = (JSONObject) jsonObject.get("bossresponse");
			
			String responseCode = (String) bossResponse.get("responsecode");
			
			if (responseCode.equals("200")) {
				return bossResponse;
			}
			else throw new BossException("The search request returned a response code of " + responseCode);
		}
		 catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new BossException("Unspecified error");
	}
	
	private String sendRequest(String endpoint, Map<String, String> params) {
		try {
			StringBuffer paramBuffer = new StringBuffer();
			Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> pair = it.next();
				paramBuffer.append(URLEncoder.encode(pair.getKey(), "UTF-8") + "=" + URLEncoder.encode(pair.getValue(), "UTF-8"));
				if (it.hasNext()) paramBuffer.append("&");
			}
			String urlString = "http://yboss.yahooapis.com/ysearch/" + endpoint + "?" + paramBuffer;
			URL url = new URL(urlString);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			consumer.sign(request);
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(request.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			return response.toString();
		}
		catch (MalformedURLException ex) {
			//Should never happen
		} catch (IOException e) {
			throw new BossException("IOException: " + e.getMessage());
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new BossException("Unspecified error");
	}
	
	// EndRegion RequestManagement
	
	// Region UtilityFunctions
	
	private <T> boolean arrayContains( final T[] array, final T v ) {
	    for ( final T e : array )
	        if ( e == v || v != null && v.equals( e ) )
	            return true;
	    
	    return false;
	}
	
	private boolean stringIsNullOrEmpty(String str) {
		return (str == null || str.length() == 0);
	}
	
	private String trimStr(String str,char ch) {
		if(str == null) return null;
		str = str.trim();
		int count = str.length();
		int len = str.length();
		int st = 0;

		char[] val = str.toCharArray();   

		while ((st < len) && (val[st] == ch)) {
			st++;
		}
		while ((st < len) && (val[len - 1] == ch)) {
			len--;
		}
		return ((st > 0) || (len < count)) ? str.substring(st, len) : str;
	}
	
	private Map<String, String> generateWebSearchParams(String query, int start, int count, String[] includedSites, String[] excludedSites, boolean excludePorn, boolean longAbstracts, boolean stripHtml, SearchDocumentFormat[] includedFormats, SearchDocumentFormat[] excludedFormats, SearchLanguage[] languages, String title, String url) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		if (start > 0 && start < 1000) params.put("start", Integer.toString(start));
		if (count > 0 && count < 50) params.put("count", Integer.toString(start));
		if (excludePorn) params.put("filter", "-porn");
		if (longAbstracts) params.put("abstract", "long");
		if (stripHtml) params.put("style", "raw");
		if (!stringIsNullOrEmpty(title)) params.put("title", title);
		if (!stringIsNullOrEmpty(url)) params.put("url", url);
		
		if ((includedFormats != null && includedFormats.length > 0) || (excludedFormats != null && excludedFormats.length > 0)) {
			StringBuffer typeBuffer = new StringBuffer();
			if (includedFormats != null && includedFormats.length > 0) {
				for (SearchDocumentFormat format : includedFormats) {
					typeBuffer.append(format.getCode() + ",");
				}
			}
			if (excludedFormats != null && excludedFormats.length > 0) {
				for (SearchDocumentFormat format : excludedFormats) {
					typeBuffer.append("-" + format.getCode() + ",");
				}
			}
			params.put("type", trimStr(typeBuffer.toString(), ','));
		}
		
		if ((includedSites != null && includedSites.length > 0) || (excludedSites != null && excludedSites.length > 0)) {
			StringBuffer sitesBuffer = new StringBuffer();
			if (includedSites != null && includedSites.length > 0) {
				for (String site : includedSites) sitesBuffer.append(site + ",");
			}
			if (excludedSites != null && excludedSites.length > 0) {
				for (String site : excludedSites) sitesBuffer.append("-" + site + ",");
			}
			params.put("sites", trimStr(sitesBuffer.toString(), ','));
		}
		
		if (languages != null && languages.length > 0) {
			StringBuffer viewBuffer = new StringBuffer();
			for (SearchLanguage language : languages) {
				viewBuffer.append(language.getCode() + ",");
			}
			params.put("view", trimStr(viewBuffer.toString(), ','));
		}
		
		return params;
	}
	
	// EndRegion UtilityFunctions
	

}
