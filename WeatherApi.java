import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherApi extends Api{

	public double temp = 0;
	
	public WeatherApi(){
		super();
	}
	
	public WeatherApi(String Input) {
		
    	String myAPIurl;
    	
    	
    	//check if input is zipcode or name of place
    	if(Character.isDigit(Input.charAt(0))) {
    		myAPIurl = "https://api.openweathermap.org/data/2.5/weather?zip=";
    	}else {
    		myAPIurl = "http://api.openweathermap.org/data/2.5/weather?q=";}
    	String myApiToken ="&appid=8b5fd6f2758e05f6c539ea7ec00c0f66";
    	
    	url = myAPIurl + Input + myApiToken; 
    	Json = getJson();
	}
	
	public String getJson() {
		try{
    		return readJson(url);//use the read Json function if the abstract Api class
    	}catch(Exception e) {
    		return "error getting weather JSON";
    	}
	}
	
	public double getTemp() {
		temp = parseWeatherJson(Json);
		return temp;
	}
	
	protected double parseWeatherJson(String json) {
    	
		if(json.contains("error")) {
			return -1;
		}
		
	    JsonObject object = new JsonParser().parse(json).getAsJsonObject();

	    JsonObject main = object.getAsJsonObject("main");
	    double temp = main.get("temp").getAsDouble();
	    return temp; 
    }
}
