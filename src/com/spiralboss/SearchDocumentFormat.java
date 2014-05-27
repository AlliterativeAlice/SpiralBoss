package com.spiralboss;

public enum SearchDocumentFormat {
	HTML("html"), PDF("pdf"), Text("text"), Excel("xl"), MSWord("msword"), PowerPoint("ppt");
	
	private final String code;
	SearchDocumentFormat(String code) { this.code = code; }
    public String getCode() { return this.code; }
}
