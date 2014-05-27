package com.spiralboss;

public enum SearchLanguage {
	Catalan("cs"),
	ChineseSimplified("zh-hans"),
	ChineseTraditional("zh-hant"),
	Czech("cs"),
	Danish("da"),
	Dutch("nl"),
	English("en"),
	French("fr"),
	Finnish("fi"),
	German("de"),
	Hebrew("he"),
	Hungarian("hu"),
	Italian("it"),
	Indonesian("id"),
	Japanese("ja"),
	Korean("ko"),
	Malay("ms"),
	Norwegian("no"),
	Portuguese("pt"),
	Romanian("ro"),
	Russian("ru"),
	Spanish("es"),
	Swedish("sv"),
	Tagalog("tl"),
	Thai("th"),
	Turkish("tr"),
	Vietnamese("vi"),
	Unknown("unknown");
	
	private final String code;
	SearchLanguage(String code) { this.code = code; }
    public String getCode() { return this.code; }
}
