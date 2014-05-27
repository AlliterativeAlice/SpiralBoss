package com.spiralboss;

public enum BlogRegionLanguage {
	HongKongChineseSimplified("zh-hant-hk"),
	KoreaKorean("ko-kr"),
	SpainSpanish("es-es"),
	TaiwanChineseTraditional("zh-hant-tw"),
	UnitedStatesEnglish("en-us"),
	VietnamVietnamese("vi-vn");
	
	private final String code;
	BlogRegionLanguage(String code) { this.code = code; }
    public String getCode() { return this.code; }
}
