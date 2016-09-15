package org.sixdimensions.finalproject.core.models;

import java.io.BufferedReader;
//import org.json.JSONArray;
//import com.ibm.json.java.JSONObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
//import java.util.Base64.Encoder;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;

import org.apache.jackrabbit.oak.spi.security.authentication.Authentication;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.settings.SlingSettingsService;
//import org.myorg.slingcurd.core.models.GetRepository; 
import org.sixdimensions.finalproject.core.servlets.NewTwitterTweetsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
@Model(adaptables=Resource.class)
public class NewTwitterTweetsModel {

	private static final Logger LOG = LoggerFactory.getLogger(NewTwitterTweetsModel.class);
	
 @Inject
  private SlingSettingsService settings;

  @Inject @Named("sling:resourceType") @Default(values="No resourceType")
  protected String resourceType;

  private static String message;
 
    @PostConstruct
   protected void init() {

  	message = "\tencodevalue: "+encodeKeys("EgknuxHqgDqEfZkwILnEVLH0e","Tl77LKkncgNGKNdsUZzDnw7IXf1T0Odr3z6Eb34lykIkzaWE1e");
  	
  	try {
	   		message= "\tfetchtimelinetweet: "+fetchTimelineTweet(null,"orange",4);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			message += "\tfetchtimelinetweet:"+e;
			
		} catch (JSONException e) {
			message += "\tfetchtimelinetweet:error"+e;
			// TODO Auto-generated catch block
		
		}
  }

  public String getMessage() {
      return message;
  }  
  
  
  
// Encodes the consumer key and secret to create the basic authorization key
  private static String encodeKeys(String consumerKey, String consumerSecret) {
  	 	
  	try {
  		String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
  		String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
  		
  		String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
  		byte[] encodedBytes = Base64.getEncoder().encode(fullKey.getBytes());
  		return new String(encodedBytes);  
  	}
  	catch (UnsupportedEncodingException e) {
      		return new String();
  	}
  }
  

  
// Constructs the request for requesting a bearer token and returns that token as a string
  
 private static String requestBearerToken(String endPointUrl) throws IOException, JSONException {
	    HttpURLConnection connection = null;
	    
	    String encodedCredentials = encodeKeys("EgknuxHqgDqEfZkwILnEVLH0e","Tl77LKkncgNGKNdsUZzDnw7IXf1T0Odr3z6Eb34lykIkzaWE1e");

	    try {
	        URL url = new URL(endPointUrl); 
	        connection =  (HttpURLConnection) url.openConnection();           
  		connection.setDoOutput(true);
  		connection.setDoInput(true); 
  		connection.setRequestMethod("POST"); 
  		connection.setRequestProperty("Host", "api.twitter.com");
  		connection.setRequestProperty("User-Agent", "labs.6dglobal");
  		connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
  		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
  		connection.setRequestProperty("Content-Length", "29");
  		connection.setUseCaches(false);

	        //System.out.println("Request: " + writeRequest(connection, "grant_type=client_credentials"));
	        writeRequest(connection, "grant_type=client_credentials");
	        String jsonResponse = null;
      
	        try {
	        	  jsonResponse = readResponse(connection);
	        } finally {
	            System.out.println("readResponse Exception");
	       
	        }
	        
      JSONObject obj = new JSONObject(jsonResponse); 
	       if (obj != null) {
	    
	        	String tokenType = (String)obj.get("token_type");
  			String token = (String)obj.get("access_token");
		
  			return ((tokenType.equals("bearer")) && (token != null)) ? token : "";
	        }
	        return new String();
	    }
	    
	        
	   
	    catch (MalformedURLException e) {
	        throw new IOException("Invalid endpoint URL specified.", e);
	    }
	    finally {
	        if (connection != null) {
	            connection.disconnect();
	        }
	    }
	}
 
 
 
 // Writes a request to a connection
 private static boolean writeRequest(HttpURLConnection connection, String textBody) {
	    try {
	        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
	        wr.write(textBody);
	        wr.flush();
	        wr.close();

	        return true;
	    }
	    catch (IOException e) { return false; }
	}


//Reads a response for a given connection and returns it as a string.
 private static String readResponse(HttpURLConnection connection) {
	   //System.out.println("connection string &&&&& "+connection);
 	try {
 		StringBuilder str = new StringBuilder();
 			
 		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 		String line = "";
 		while((line = br.readLine()) != null) {
 			str.append(line + System.getProperty("line.separator"));
   		}
 		 		return str.toString();
 	}
 	catch (IOException e) { return new String(); }
 }

 
/*

public static Object fetchTimelineTweet(String query, int count) throws IOException , JSONException{
  HttpsURLConnection connection = null;

  String baseURL = "https://api.twitter.com/1.1/search/tweets.json";
  String endPointUrl = baseURL + "?q=" + query + "&f=tweets&count=" + count;
  //System.out.println(endPointUrl);
  try {
      URL url = new URL(endPointUrl);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Host", "api.twitter.com");
      connection.setRequestProperty("User-Agent", "labs.6dglobal");
      connection.setRequestProperty("Authorization", "Bearer " + requestBearerToken("https://api.twitter.com/oauth2/token"));
      connection.setUseCaches(false);

      JSONObject obj = new JSONObject(readResponse(connection)); 
    
		if (obj != null) {
			
			String tweet = obj.get("statuses").toString();
			//System.out.println("tweet~~~~~~"+tweet);
			return (tweet != null) ? tweet : "";
		} 
	    
     //return response;
      
  } catch (MalformedURLException e) {
      throw new IOException("Invalid endpoint URL specified.", e);
  } finally {
      if (connection != null) {
          connection.disconnect();
      }
  }
	return endPointUrl;
}
 
 */

 
 
 public static Object fetchTimelineTweet(SlingHttpServletRequest req,String query, int count) throws IOException , JSONException{
	    HttpsURLConnection connection = null;

	    LOG.debug("inside fetchTimelineTweet query {}", query );
	    LOG.debug("inside fetchTimelineTweet request {}", req );
	    LOG.debug("inside fetchTimelineTweet count {}", count );
	    
	    String baseURL = "https://api.twitter.com/1.1/search/tweets.json";
	    String endPointUrl = baseURL + "?q=" + query + "&f=tweets&count=" + count;
	    System.out.println("endpoint~~~~"+endPointUrl);
	    NewSaveTweetsModel objsaveTweet =  new NewSaveTweetsModel();
	    try {
	        URL url = new URL(endPointUrl);
	        connection = (HttpsURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Host", "api.twitter.com");
	        connection.setRequestProperty("User-Agent", "labs.6dglobal");
	        connection.setRequestProperty("Authorization", "Bearer " + requestBearerToken("https://api.twitter.com/oauth2/token"));
	        connection.setUseCaches(false);

	        JSONObject obj = new JSONObject(readResponse(connection)); 
	        
	        System.out.println("remainingSearch~~~~~~"+remainingSearch());
	        
	        String num = remainingSearch().toString();
	       
	        int remainingSerchCount = Integer.parseInt(num);
	      
	        if (remainingSerchCount < 1){
	        	 throw new JSONException("LimtExceeded");
	          
	        
	        }
			if (obj != null) {
				
				String tweet = obj.get("statuses").toString();
				System.out.println("tweet~~~~~~"+tweet);
				// LOG.debug("inside fetchTimelineTweet tweets {}", tweet );
				 try {
					 String tw = tweet;
					 objsaveTweet.saveTweets(req,tw,query);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 
				 
				return (tweet != null) ? tweet : "";
							 
				 
			} 
		    
	       //return response;
	        
	    } catch (MalformedURLException e) {
	        throw new IOException("Invalid endpoint URL specified.", e);
	    } catch (JSONException e) {
	    	
				throw new JSONException("Limit of Search Exceeded", e);
		
	    }finally {
	        if (connection != null) {
	            connection.disconnect();
	        }
	        
	        
	        
	    }
		return endPointUrl;
	}

public static Object remainingSearch() throws IOException , JSONException{
    HttpsURLConnection connection = null;
    
    String endPointUrl = "https://api.twitter.com/1.1/application/rate_limit_status.json?resources=search";

   // System.out.println("endpoint~~~~"+endPointUrl);
    try {
        URL url = new URL(endPointUrl);
        connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Host", "api.twitter.com");
        connection.setRequestProperty("User-Agent", "labs.6dglobal");
        connection.setRequestProperty("Authorization", "Bearer " + requestBearerToken("https://api.twitter.com/oauth2/token"));
        connection.setUseCaches(false);

        JSONObject obj = new JSONObject(readResponse(connection)); 
        
        System.out.println("obj~~~~~~"+obj);
        
        
      
		if (obj != null) {
			
			//String tweet = obj.get("statuses").toString();
			JSONObject resourceObj = (JSONObject) obj.get("resources");
			JSONObject searchObj = (JSONObject) resourceObj.get("search");
			JSONObject tweetObj = (JSONObject) searchObj.get("/search/tweets");
			String Remaining = tweetObj.get("remaining").toString();
			
			System.out.println("Remaining~~~~~~"+Remaining);
			return (Remaining != null) ? Remaining : "";
		} 
	    
       //return response;
        
    } catch (MalformedURLException e) {
        throw new IOException("Invalid endpoint URL specified.", e);
    } finally {
        if (connection != null) {
            connection.disconnect();
        }
    }
	return endPointUrl;
}
  

public static Object fetchTimelineTweet1(SlingHttpServletRequest req,String query, int count) throws IOException , JSONException{
    HttpsURLConnection connection = null;

    LOG.debug("inside fetchTimelineTweet query {}", query );
    LOG.debug("inside fetchTimelineTweet request {}", req );
    
    String baseURL = "https://api.twitter.com/1.1/search/tweets.json";
    String endPointUrl = baseURL + "?q=" + query + "&f=tweets&count=" + count;
    System.out.println("endpoint~~~~"+endPointUrl);
    NewSaveTweetsModel objsaveTweet =  new NewSaveTweetsModel();
    try {
        URL url = new URL(endPointUrl);
        connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Host", "api.twitter.com");
        connection.setRequestProperty("User-Agent", "labs.6dglobal");
        connection.setRequestProperty("Authorization", "Bearer " + requestBearerToken("https://api.twitter.com/oauth2/token"));
        connection.setUseCaches(false);

        JSONObject obj = new JSONObject(readResponse(connection)); 
        
        System.out.println("remainingSearch~~~~~~"+remainingSearch());
        
        String num = remainingSearch().toString();
       
        int remainingSerchCount = Integer.parseInt(num);
      
        if (remainingSerchCount < 1){
        	 throw new JSONException("LimtExceeded");
          
        
        }
		if (obj != null) {
			
			String tweet = obj.get("statuses").toString();
			System.out.println("tweet~~~~~~"+tweet);
			// LOG.debug("inside fetchTimelineTweet tweets {}", tweet );
			 try {
				 String tw = tweet;
				 //objsaveTweet.saveTweets(req,tw,query);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 
			 
			return (tweet != null) ? tweet : "";
						 
			 
		} 
	    
       //return response;
        
    } catch (MalformedURLException e) {
        throw new IOException("Invalid endpoint URL specified.", e);
    } catch (JSONException e) {
    	
			throw new JSONException("Limit of Search Exceeded", e);
	
    }finally {
        if (connection != null) {
            connection.disconnect();
        }
        
        
        
    }
	return endPointUrl;
}
  
}

