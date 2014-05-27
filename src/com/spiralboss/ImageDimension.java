package com.spiralboss;

public enum ImageDimension {
	All("all"), Small("small"), Medium("medium"), Large("large"), Wallpaper("wallpaper"), WideWallpaper("widewallpaper");
	
	private final String code;
	ImageDimension(String code) { this.code = code; }
    public String getCode() { return this.code; }
}
