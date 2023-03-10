import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Api {
	public String url;
	public String Json;
	
	public Api() {
		url = "";
		Json = "";
	}
	
	/*
	 * get to the url and return Json as string
	 */
    public static String readJson(String Url) throws Exception{

    	URL url = new URL(Url);
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("GET");
    	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	StringBuilder result = new StringBuilder();
    	String content;
    	while((content = rd.readLine()) != null) {
    		result.append(content);
    	}
    	return result.toString();

    }
}
