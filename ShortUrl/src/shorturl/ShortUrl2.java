package shorturl;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShortUrl2 {

	public static void main(String[] args) {
		String url = "google.com";
		long id=24;
		String sstr=idToShortStr(24);
		System.out.println(id+":"+sstr);
		System.out.println(getOriginalUrl(baseUrl+sstr));
		String shortUrl = getShortUrl(url);
		System.out.println("short url:"+shortUrl);
		System.out.println("orig url:"+getOriginalUrl(shortUrl));
	}
	
	private static String baseUrl="http://www.rjurl.com/";
	private static char[] ALPHABET = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz").toCharArray();
	private static int ALPHABET_LENGTH=ALPHABET.length;
	private static Map<Long, String> urlMap = new HashMap<Long, String>();
	static Random random = new Random();
	static long MAX_NUM=(long)Math.pow(ALPHABET_LENGTH, 6);
	public static String getShortUrl(String url){
		long id = (long) ((random.nextDouble()+0.5)*MAX_NUM);
//		System.out.println("id for "+url+" is "+id);
		urlMap.put(id,url);
		return baseUrl+idToShortStr(id);
	}
	
	public static String getOriginalUrl(String shortUrl) {
		long id=0;
		char[] shortChars = shortUrl.substring(21).toCharArray();
		int i=0;
		for(char c:shortChars) {
			i=c-'0';
			if(i>9)i=c-'A'+10;
			if(i>35)i=c-'a'+36;
			id=id*ALPHABET_LENGTH+i;
		}
//		System.out.println("got back id "+id+ " from:"+new String(shortChars)+":");
		return urlMap.get(id);
	}	

	
	private static String idToShortStr(long id) {
		StringBuilder shortUrl = new StringBuilder();
		int i=0;
		while(id>0) {
			shortUrl.append(ALPHABET[(int)(id%ALPHABET_LENGTH)]);
			id=id/ALPHABET_LENGTH;
		}
		return shortUrl.reverse().toString();
	}

}
