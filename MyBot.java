import org.jibble.pircbot.*;

public class MyBot extends PircBot {
    
    public MyBot() {
        this.setName("KK7Bot");
    }
    
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message){
    	
    	
    	/*-------------------------------------
    	 * Return greetings to users' greeting
    	 *///----------------------------------
    	if (message.contains("hello")||message.contains("Hello")) {
    		
            sendMessage(channel, "Hello, "+ sender +"! What can I help you?");
        }
        if(message.contains("hank") || message.contains("ppreciate")) {
        	sendMessage(channel, "You are so welcome, "+sender + "! I am glad to help you!");
        }
        
        
        /*-----------------------------------------------------------
         * Return current time to users
         *Explanation: I have to use else if statement to avoid
         *cases that my chat robot answer both weather or time question
         *and the Wikipedia searching
         *///--------------------------------------------------------
        if (message.contains("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }
        
        
        /*-----------------------------
         * If user asks  about weather
         * prompts to enter the name or zipcode of the place
         *///--------------------------
        else if(message.contains("eather") && (!((message.contains("in"))||message.contains("of")||message.matches("weather at")))){
        	
        	sendMessage(channel,  "What is the name or zip code of the place that you want know its weather?");
        	sendMessage(channel,  "Please type in \"wt + Name or Zip code of the Place(+,Country if needed) \"");
        	sendMessage(channel,  "For example, type in \"wt Dallas\", \"wt Dallas,us\" or \"wt 75070,uk\"");
        }
        
        
        /*-----------------------------
         * Process of returning weather
         *///--------------------------
        else if(message.matches("wt.*")|| message.contains("weather in")||message.contains("weather at")||message.contains("weather of")) {
        	
        	String Input;
        	if(!(message.matches("wt.*"))) {
        		Input = message.substring(message.indexOf("weather") + 11);
        	}else {
        		Input = message.substring(message.indexOf("wt") + 3);
        	}
        	WeatherApi weather = new WeatherApi(Input);//instantiate the weatherapi with inputting part of url

        	double temp = weather.getTemp();
        	if(temp >= 0) {
        		
        		double celsius = temp - 273.15;
            	double fahre = (temp-273.15)*((double)9/5)+32;
            	sendMessage(channel, "The Absolute Temperature is " + temp +"K");
            	sendMessage(channel, "The Celsius degree is " + Math.round(celsius) +"C°");
            	sendMessage(channel, "The Fahrenheit degree is " + Math.round(fahre) +"F°");
            	
        	}else{
        		sendMessage(channel, "Error, check your typing");
        	}
        	
        }
        
        
        /*--------------------------------------------------
         * Give user answers of their keyword from Wikipedia
         *///-----------------------------------------------
        else if(message.contains("ook up")||message.contains("hat is")
        		||message.contains("ho is")||message.contains("o you know")) {
        	
        	int index = 0;
        	
        	//finding different index of the message where the keyword for searching is
        	if(message.contains("ook up")){
        		index = message.indexOf("ook up") + 7;
        	}else if(message.contains("hat is")) {
        		index = message.indexOf("hat is") + 7;
        	}else if(message.contains("ho is")) {
        		index = message.indexOf("ho is") + 6;
        	}else{
        		index = message.indexOf("o you know") + 11;
        	}
        	String Input = message.substring(index).replaceAll(" ", "%20");//creating the middle part of url
        	WikipediaApi wiki = new WikipediaApi(Input);
        	
        	String[] wikiInfo = wiki.getInfo();
        	if(wikiInfo != null) {
        
        		sendMessage(channel,  "Below is what I found on Wikipedia: ");
        		for(int i = 0, j =i+1; i < wikiInfo.length; i+=2, j+=2) {
        			sendMessage(channel, "Title: "+ wikiInfo[i]);
        			sendMessage(channel,  "Content: "+ wikiInfo[j]);
        			sendMessage(channel, "...                               "
        					+ "                                             "
        					+ "                                             ");
        		}
        		String link = message.substring(index).replaceAll(" ", "_");
        		sendMessage(channel, "This is the link to Wikipedia page about your search: "
        							+ "https://en.wikipedia.org/wiki/"+link);//send the link to wikipedia to users
        		
        	}else{
        		sendMessage(channel, "Wiki Error, check your typing");
        	}
        }
    }
}

