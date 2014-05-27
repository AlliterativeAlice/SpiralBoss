package com.spiralboss;
import org.json.simple.JSONObject;

public class ImageResult {
	
	
	/**
     * The title of the image.
     */ 
	public String Title;
	
	/**
     * URL of the image itself.
     */ 
	public String URL;
	
	/**
     * Link to page on which image was found.
     */ 
	public String RefererURL;
	
	/**
     * URL for clickable link to page on which image was found.
     */ 
	public String RefererClickURL;
	
	/**
     * File format of image.
     */ 
	public ImageFormat Format;
	
	/**
	 * Size of image file (in bytes)
	 */
	public float Size;
	
	/**
     * Height of image (in pixels)
     */
	public int Height;
	
	/**
     * Width of image (in pixels)
     */
	public int Width;
	
	/**
     * Height of thumbnail (in pixels)
     */
	public int ThumbnailHeight;
	
	/**
     * Width of thumbnail (in pixels)
     */
	public int ThumbnailWidth;
	
	/**
     * URL of thumbnail of the image.
     */
	public String ThumbnailURL;
	
	/**
     * URL for clickable link to the image.
     */
	public String ClickURL;

	ImageResult(JSONObject jsonObject) {
		this.Title = (String)jsonObject.get("title");
		this.URL = (String)jsonObject.get("url");
		this.RefererURL = (String)jsonObject.get("url");
		this.RefererClickURL = (String)jsonObject.get("url");
		
		//Determine format
		String formatString = (String)jsonObject.get("format");
		if (formatString == "jpeg") this.Format = ImageFormat.JPEG;
		else if (formatString == "gif") this.Format = ImageFormat.GIF;
		else if (formatString == "png") this.Format = ImageFormat.PNG;
		else if (formatString == "svg") this.Format = ImageFormat.SVG;
		else this.Format = ImageFormat.UNKNOWN;
		
		//Determine size
		String sizeString = (String)jsonObject.get("size");
		if (sizeString == null || sizeString.length() == 0) this.Size = 0;
		else {
			this.Size = Math.round(Float.parseFloat(sizeString.substring(0, sizeString.length() - 3)) * 1024);
		}
		
		String widthString = (String)jsonObject.get("width");
		String heightString = (String)jsonObject.get("height");
		String thumbnailWidthString = (String)jsonObject.get("thumbnailwidth");
		String thumbnailHeightString = (String)jsonObject.get("thumbnailheight");
		
		if (widthString == null || widthString.length() == 0) this.Width = 0;
		else this.Width = Integer.parseInt(widthString);
		
		if (heightString == null || heightString.length() == 0) this.Height = 0;
		else this.Height = Integer.parseInt(heightString);
		
		if (thumbnailWidthString == null || thumbnailWidthString.length() == 0) this.ThumbnailWidth = 0;
		else this.ThumbnailWidth = Integer.parseInt(thumbnailWidthString);
		
		if (thumbnailHeightString == null || thumbnailHeightString.length() == 0) this.ThumbnailHeight = 0;
		else this.ThumbnailHeight = Integer.parseInt(thumbnailHeightString);

		this.ClickURL = (String)jsonObject.get("clickurl");
		
	}

}
