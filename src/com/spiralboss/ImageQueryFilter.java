package com.spiralboss;

public enum ImageQueryFilter {
	Adult("adult"), Illegal("illegal"), None("");
	
	private final String code;
	ImageQueryFilter(String code) { this.code = code; }
    public String getCode() { return this.code; }
}
