package com.spiralboss;

public enum RelatedRegionLanguage {
	AregentinaSpanish("es-ar"),
	AustraliaEnglish("en-au"),
	CanadaEnglish("en-ca"),
	CanadaFrench("fr-ca"),
	FranceFrench("fr-fr"),
	GermanyGerman("de-de"),
	HongKongChineseSimplified("zh-hant-hk"),
	IndonesiaEnglish("id-en"),
	IndonesiaIndonesian("id-id"),
	IndiaEnglish("en-in"),
	ItalyItalian("it-it"),
	MalaysiaMalaysian("ms-my"),
	MexicoSpanish("es-mx"),
	NewZealandEnglish("en-nz"),
	PhilippinesFilipino("tl-ph"),
	PhilippinesEnglish("en-ph"),
	SingaporeEnglish("en-sg"),
	SpainSpanish("es-es"),
	ThailandThai("th-th"),
	TaiwanChineseTraditional("zh-hant-tw"),
	UnitedKingdomEnglish("en-gb"),
	UnitedStatesEnglish("en-us"),
	UnitedStatesSpanish("es-us"),
	VietnamVietnamese("vi-vn");
	
	private final String code;
	RelatedRegionLanguage(String code) { this.code = code; }
    public String getCode() { return this.code; }
}
