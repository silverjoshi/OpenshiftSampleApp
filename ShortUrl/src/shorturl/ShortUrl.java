package shorturl;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShortUrl {

	public static void main(String[] args) {
		String url = "google.com";
		String shortUrl = getShortUrl(url);
		System.out.println("short url:"+shortUrl);
		System.out.println("orig url:"+getOriginalUrl(shortUrl));
	}
	
	private static String baseUrl="http://www.rjurl.com/";
	private static char[] ALPHABET = new String("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
	private static int ALPHABET_LENGTH=ALPHABET.length;
	private static Map<String, String> urlMap = new HashMap<String, String>();
	
	public static String getShortUrl(String url){
		String shortUrl = urlToShortUrl(url);
		urlMap.put(shortUrl,url);
		return baseUrl+shortUrl;
	}
	
	private static String urlToShortUrl(String url) {
		char[] urlChars = url.toCharArray();
		char[] shortUrlChars = new char[6];
		int i=0;
		for(char c:urlChars) {
			shortUrlChars[i]=ALPHABET[(shortUrlChars[i]+c)%ALPHABET_LENGTH];
			i++;
			if(i>5)i=0;
		}
		String shortUrl=new String(shortUrlChars);
		while(urlMap.containsKey(shortUrl)&&!urlMap.get(shortUrl).equals(url)) {
			char[] charArr =  shortUrl.toCharArray();
			charArr[charArr.length-1]++;
			shortUrl=new String(charArr);
		}
		return shortUrl;
	}

	public static String getOriginalUrl(String shortUrl) {
		shortUrl = shortUrl.substring(baseUrl.length()).toLowerCase();
		return urlMap.get(new String(shortUrl));
	}	


}
