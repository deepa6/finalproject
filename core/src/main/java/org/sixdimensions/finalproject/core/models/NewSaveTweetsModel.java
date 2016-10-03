package org.sixdimensions.finalproject.core.models;
import javax.jcr.Repository; 
import javax.jcr.Session; 
import javax.jcr.SimpleCredentials;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.felix.scr.annotations.Reference;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.jackrabbit.core.TransientRepository; 
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
    
@SuppressWarnings("unused")
public class NewSaveTweetsModel implements  ReadService {
  
	private static final Logger LOG = LoggerFactory.getLogger(NewSaveTweetsModel.class);
	@Reference
	private ResourceResolverFactory resolverFactory;
	   
	public String saveTweets(SlingHttpServletRequest req,String tw,String search) throws Exception { 
		  LOG.debug("inside saveTweets");
		 // LOG.debug("inside SlingHttpServletRequest {}",req);
		 // LOG.debug("inside tweets {}",tw);
		 // LOG.debug("inside search {}",search);
		 
		try { 
 	  
	       Session session = req.getResourceResolver().adaptTo(Session.class);
           ResourceResolver rr =req.getResourceResolver();
           Resource res =rr.getResource("/etc/twitter");
		   Node root = res.adaptTo(Node.class);
		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           Date date = new Date();
           LOG.debug("dateFormat : " + dateFormat.format(date));
			
							         
			
	  if (root.hasNode(search+"/tweet")){
	  System.out.println("4444~~~~NodeExist"); 
      Node n = root.getNode(search+"/tweet/");
	  n.setProperty("message",tw); 
	  n.setProperty("time",dateFormat.format(date)); 
	  }
	  
	  else{
      System.out.println("4444~~~~NodeNotExist"); 
	  Node Twitter = root.addNode(search); 
	  Node tweets = Twitter.addNode("tweet"); 
	  tweets.setProperty("message",tw); 
	  tweets.setProperty("time",dateFormat.format(date)); 
	   }
	  
	  session.save(); 
	//  session.logout();
	  return "saveTweetsSuccessfully" ;

	  
	} 
	
			
	 catch(Exception e){
		 
		 StringWriter errors = new StringWriter();
		 e.printStackTrace(new PrintWriter(errors));
		 return errors.toString();

		  }
	}
	
	public static String readTweets(SlingHttpServletRequest req,String search) throws Exception { 
	
		try{
							         
		       Session session = req.getResourceResolver().adaptTo(Session.class);
		       Node root = session.getRootNode(); 
	           Node node = root.getNode("etc/twitter/"+search+"/tweet"); 
  			   return node.getProperty("message").getString(); 
	
		}
		
		 catch(Exception e){
			 
			 StringWriter errors = new StringWriter();
			 e.printStackTrace(new PrintWriter(errors));
			 LOG.debug("inside saveTweets error {}", errors.toString());
			 return errors.toString();

			  }	
		
	}
	
	/*public static String readTweetsTime(SlingHttpServletRequest req,String search) throws Exception { 
		
		try{
							         
		       Session session = req.getResourceResolver().adaptTo(Session.class);
		       Node root = session.getRootNode(); 
	           Node node = root.getNode("etc/twitter/"+search+"/tweet"); 
  			   return node.getProperty("time").getString(); 
	
		}
		
		 catch(Exception e){
			 
			 StringWriter errors = new StringWriter();
			 e.printStackTrace(new PrintWriter(errors));
			 LOG.debug("inside saveTweets error {}", errors.toString());
			 return errors.toString();

			  }	
		
	}  */
	
	
	public String loadFetchTweets(SlingHttpServletRequest req,String search,int count) throws Exception {
		//return search;
		
		
		try { 
		 	  
		       Session session = req.getResourceResolver().adaptTo(Session.class);
	           ResourceResolver rr =req.getResourceResolver();
	           Resource res =rr.getResource("/etc/twitter");
			   Node root = res.adaptTo(Node.class);
			   String tweStr = null;
								         
				
		  if (root.hasNode(search+"/tweet")){
			  tweStr = readTweets(req,search);
			  return tweStr;
		  }
		  
		  else {
			
			      NewTwitterTweetsModel.fetchTimelineTweet(req,search,count);
			      tweStr = readTweets(req,search);
			      return tweStr;
			    
		  }
		}
  
			 catch(Exception e){
			 StringWriter errors = new StringWriter();
			 e.printStackTrace(new PrintWriter(errors));
			 return errors.toString();

			  }	
		
		
	}
	
	
	/*public String scheduleTweets(SlingHttpServletRequest req,String search,int count) throws Exception {
		//return search;
		 String tweStr = null;
		 
		// LOG.debug("this is from NewSaveTweets search {}", search);
		 //LOG.debug("this is from NewSaveTweets req {}", req);
		
		try { 
		 	  
		       //Session session = req.getResourceResolver().adaptTo(Session.class);
           
	           ResourceResolver rr =req.getResourceResolver();
	           Resource res =rr.getResource("/etc/twitter");
			   Node root = res.adaptTo(Node.class);
			  
								         
				
		  if (root.hasNode(search+"/tweet")){
			  LOG.debug("inside hasNode");
			    NewTwitterTweetsModel.fetchTimelineTweet(req,search,count);
			    tweStr = readTweets(req,search);
			    return tweStr;
			    
		  }
		}
  
			 catch(Exception e){
			 
			 StringWriter errors = new StringWriter();
			 e.printStackTrace(new PrintWriter(errors));
			 LOG.debug("inside hasNode {}", errors.toString() );
			 return errors.toString();
			 

			  }
		return tweStr;	
		
		
	}*/

	@Override
	public void listTitles() {
		// TODO Auto-generated method stub
		
	}
	
}