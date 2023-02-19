import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WikipediaApi extends Api{
	
	public String[] wikiInfo;
	
	public WikipediaApi() {
		super();
	}
	
	public WikipediaApi(String Input) {
		
		url = "https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch="
    			+ Input + "&utf8=&format=json";
		Json = getJson(url);
		//System.out.print(Json);
	}

	public String[] getInfo() {
		
		wikiInfo = parseWiki(Json);
		return wikiInfo;
	}
	
	public String getJson(String url) {
		try {
			return readJson(url);//use the read Json function if the abstract Api class
		}catch(Exception e) {
			return "error reading Wikipedia JSON";
		}
	}
	
    protected String[] parseWiki(String json) {
    	
    	if(json.contains("error")) {
    		return null;
    	}
    	
    	JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
    	JsonObject query = obj.getAsJsonObject("query");
    	JsonArray search = query.getAsJsonArray("search");
    	
    	int t = search.size();
    	String[] wikiInfo = new String[t*2];//using a string array to store titles and contents of the search
    	for(int i = 0; i < t; i++) {
    		
    		wikiInfo[i*2] = search.get(i).getAsJsonObject().get("title").getAsString();
    		String s = search.get(i).getAsJsonObject().get("snippet").getAsString();
    		s = s.replaceAll("<span class=\"searchmatch\">", "");
    		s = s.replaceAll("&quot;","");
    		wikiInfo[i*2+1] = s.replaceAll("</span>", "");

    	}
    		
    	return wikiInfo;
    }
}
