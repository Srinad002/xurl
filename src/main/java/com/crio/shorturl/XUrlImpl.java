package com.crio.shorturl;

import java.util.*;

public class XUrlImpl implements XUrl {

    Map<String, String> longToShortMapping = new HashMap<>();
    Map<String, String> shortToLongMapping = new HashMap<>();
    Map<String, Integer> hitCount = new HashMap<>();

    private String alphaNumericStr =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvwxyz";

        @Override
    public String registerNewUrl(String longUrl) {
        if (!longToShortMapping.containsKey(longUrl)) {
            StringBuilder unique = new StringBuilder();

            for (int i = 0; i < 9; i++) {
                int idx = (int) (alphaNumericStr.length() * Math.random());
                unique.append(alphaNumericStr.charAt(idx));
            }

            String shortUrl = "http://short.url/" + unique.toString();

            longToShortMapping.put(longUrl, shortUrl);
            shortToLongMapping.put(shortUrl, longUrl);
        }
        return longToShortMapping.get(longUrl);
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (!shortToLongMapping.containsKey(shortUrl)) {
            longToShortMapping.put(longUrl, shortUrl);
            shortToLongMapping.put(shortUrl, longUrl);
            return shortUrl;
        }
        return null;
    }

    @Override
    public String getUrl(String shortUrl) {
        String longUrl = shortToLongMapping.getOrDefault(shortUrl, null);
        
        if(longUrl!=null){
            hitCount.put(longUrl, hitCount.getOrDefault(longUrl, 0)+1);
        }

        return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        return hitCount.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl) {
        String shortUrl = longToShortMapping.getOrDefault(longUrl, null);
        longToShortMapping.remove(longUrl);
        if (shortUrl != null){
            shortToLongMapping.remove(shortUrl);
        }
        return null;
    }
    
}