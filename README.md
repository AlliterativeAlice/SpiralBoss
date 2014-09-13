# SpiralBoss SDK - A Java SDK for the Yahoo! BOSS Search API

Binary Downloads: https://github.com/AlliterativeAlice/SpiralBoss/releases

## Introduction

The SpiralBoss SDK is a Java SDK for querying the [Yahoo! BOSS Search API](https://developer.yahoo.com/boss/search/)

```java
String consumerKey = "key";
String consumerSecret = "secret";
		
BossContext ctx = new BossContext(consumerKey, consumerSecret);
		
WebSearchResponse webResponse = ctx.searchWeb("test");
System.out.println(webResponse.Results.get(0).Title);
```
