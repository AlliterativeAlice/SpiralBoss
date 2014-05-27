package com.spiralboss;

public enum SpellingRegionLanguage {
	AregentinaSpanish("es-ar"),
	AustriaGerman("de-at"),
	AustraliaEnglish("en-au"),
	BrazilPortugese("pt-br"),
	CanadaEnglish("en-ca"),
	CanadaFrench("fr-ca"),
	ChileSpanish("es-cl"),
	ColombiaSpanish("es-co"),
	FinlandFinnish("fl-fl"),
	FranceFrench("fr-fr"),
	GermanyGerman("de-de"),
	HongKongChineseSimplified("zh-hant-hk"),
	IndiaEnglish("en-in"),
	IrelandEnglish("en-ie"),
	ItalyItalian("it-it"),
	JapanJapanese("ja-jp"),
	KoreaKorean("ko-kr"),
	MexicoSpanish("es-mx"),
	NewZealandEnglish("en-nz"),
	PeruSpanish("es-pe"),
	SingaporeEnglish("en-sg"),
	SpainSpanish("es-es"),
	SwitzerlandFrench("fr-ch"),
	SwitzerlandGerman("de-ch"),
	SwitzerlandItalian("it-ch"),
	TaiwanChineseTraditional("zh-hant-tw"),
	UnitedKingdomEnglish("en-gb"),
	UnitedStatesEnglish("en-us"),
	UnitedStatesSpanish("es-us"),
	VenezuelaSpanish("es-ve"),
	VietnamVietnamese("vi-vn");
	
	private final String code;
	SpellingRegionLanguage(String code) { this.code = code; }
    public String getCode() { return this.code; }
}
