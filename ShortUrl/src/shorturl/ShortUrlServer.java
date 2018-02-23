package shorturl;


import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



/**
 * Use this URL in browser address: http://localhost:8000/applications/shortenurl?url=10
 * @author rajajosh
 */
public class ShortUrlServer {
	class MyHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			URI uri = t.getRequestURI();
			String query = uri.getQuery();
			String[] params = query.split(",");
			String facn="";
			String inputNum="";
			for(String p:params){
				inputNum=p.split("url=")[1];
				facn=processUrl(inputNum);
			}
			System.out.println("Received request for URL:"+inputNum);
			sendResponse(t,facn);
		}
		void sendResponse(HttpExchange t, String facn) throws IOException{
			String response = "Result Url-> "+facn;
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();			
		}
		String processUrl(String url) {
			// TODO Auto-generated method stub
			return ShortUrl2.getShortUrl(url);
		}
		private BigInteger factorial(int num) {
			BigInteger  res=new BigInteger(num--+"");
			while(num>1){
				res=res.multiply(new BigInteger(num--+""));
			}
			return res;
		}
	}
	class ExpandHandler extends MyHandler{
		@Override
		String processUrl(String url) {
			// TODO Auto-generated method stub
			return ShortUrl2.getOriginalUrl(url);
		}
		
		@Override
		void sendResponse(HttpExchange t, String result) throws IOException{
			try {
//			if(result.startsWith("www.")) result="http://"+result;
//			if(!result.startsWith("http://www.")) result="http://www."+result;
			System.out.println("Redirecting to "+result);			
			URLConnection huc = new URL(result).openConnection();
			huc.setDoOutput(true);
			OutputStream os = huc.getOutputStream();
			t.setStreams(huc.getInputStream(), os);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		HttpServer server;
		try {
			// if port 80 is used then no need to specify port number in browser address bar 
			server = HttpServer.create(new InetSocketAddress(8000),0);
			server.createContext("/applications/shorten", new ShortUrlServer().new MyHandler());
			server.createContext("/applications/expand", new ShortUrlServer().new ExpandHandler());
			server.setExecutor(null); // creates a default executor
			server.start();
			System.out.println("Use this URL to shorten: http://localhost:8000/applications/shorten?url=<URL HERE>");
			System.out.println("Use this URL to expand: http://localhost:8000/applications/exapand?url=<URL HERE>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
