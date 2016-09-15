package org.sixdimensions.finalproject.core.models;
//import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.felix.scr.annotations.Component;
//import org.apache.felix.scr.annotations.Property;
//import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Reference;
//import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;

import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.sixdimensions.finalproject.core.models.NewSaveTweetsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import com.day.cq.wcm.api.Page;
 import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
@Component(immediate=true)

public class NewRunTweetsModel implements java.lang.Runnable, ReadService {

	private static final Logger LOG = LoggerFactory.getLogger(NewRunTweetsModel.class);
	
    @Reference
    private Scheduler scheduler ; 
     
    @Reference
	private ResourceResolverFactory resolverFactory;
    

    
    private String Search = null;

     
	public static String tstr;
	public static SlingHttpServletRequest reqObj;
	//private String query;
	NewSaveTweetsModel ns = new NewSaveTweetsModel();
	public NewRunTweetsModel(){};
	public NewRunTweetsModel(String tweets,SlingHttpServletRequest req){
		 LOG.debug("this is from parameterized tweets {}", tweets);
		 LOG.debug("this is from parameterized req {}", req);
		this.tstr = tweets;
		this.reqObj = req;
		
		 
		
		 
	}
     
	@Override
	public void run() {}
	

public String updateTweets(ComponentContext componentContext){
	
	
	  String queryStr = null;
	  Map<String,Object> paramMap = new HashMap<String,Object>();
      //Mention the subServiceName you had used in the User Mapping
      paramMap.put(ResourceResolverFactory.SUBSERVICE, "readService");
      LOG.debug("After the param");
      ResourceResolver rr = null;
 
      try{
      rr = resolverFactory.getServiceResourceResolver(paramMap);
  
      LOG.debug("UserId : " + rr.getUserID());
      Resource res1 = rr.getResource("/etc/twitter");
      Node root1 = res1.adaptTo(Node.class);
      
		
		
		NodeIterator nodes = null;
		try {
			nodes = root1.getNodes();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   
	    while (nodes.hasNext()) {
	      Node node = nodes.nextNode();
	      try {
	    	  queryStr = queryStr +","+ node.getName();
	    	  LOG.debug("logging node.getName() {}", node.getName());
			//out.write("path=" + node.getName() + "\n");
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	

	    
	    
	    //queryStr.substring(0,queryStr.length()-1);
	    queryStr =  queryStr.substring(5);
	    
	    queryStr = queryStr.toString();
	    this.tstr= queryStr;
      }
      
      catch(Exception e){
    	  LOG.debug(e.getMessage());
      }
      
	 LOG.debug("inside updatetweets reqObj {}" , this.reqObj);	

		String [] items = this.tstr.split(",");
		 LOG.debug("tweetNode items: {}", items);	
		List<String> container = Arrays.asList(items);	
		String str = null;
		String tweetNode = null;
		NewSaveTweetsModel ns1 = new NewSaveTweetsModel();
		for (int i = 0; i < container.size(); i++) {
			tweetNode = container.get(i);
			try {
				  LOG.debug("tweetNode are {}", tweetNode);	
				  Search = tweetNode;
				//str = ns1.scheduleTweets(this.reqObj,tweetNode,100);
				
				  //str =  (String) NewTwitterTweetsModel.fetchTimelineTweet1(this.reqObj,tweetNode,100);
				  
				  
				  try{
			            rr = resolverFactory.getServiceResourceResolver(paramMap);
			        	Session session = rr.adaptTo(Session.class);
			            LOG.debug("UserId : " + rr.getUserID());
			            Resource res = rr.getResource("/etc/twitter");
			            //SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss"); 
			            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			            Date date = new Date();
			            LOG.debug("dateFormat.format(date) are {}", dateFormat.format(date));	
				 // Resource PageResource = resourceResolver.getResource("/etc/twitter"); 
					
				   Node root = res.adaptTo(Node.class);
					
				  

		  			    
					  if (root.hasNode(tweetNode+"/tweet")){
						  Node n = root.getNode(tweetNode+"/tweet/");
				      LOG.debug("get path value" + n.getPath());
				      
				      Property references = n.getProperty("time");  
				      Value values = references.getValue();
				      String myVal = values.getString();

				      LOG.debug("time value:" + myVal);
				      
				      String currentTime = dateFormat.format(date);
		  			   
			  			 Date d1 = null;
			  			Date d2 = null;
			  			
			  			 d1 = dateFormat.parse(myVal);
			  		    d2 = dateFormat.parse(currentTime);
			  			   
			  			long diff = d2.getTime() - d1.getTime();
			  			LOG.debug("Time value d1:" + d2.getTime());
			  			LOG.debug("Time value d2:" + d1.getTime());
			  			
			  			LOG.debug("Time difference:" + diff);	
			  			
			  			 long diffMinutes = diff / (60 * 1000);  
			  			 
			  			LOG.debug("difference in time in minutes d2:" + diffMinutes);
			  			 
			  			if(diffMinutes >= 15){ 
			  			LOG.debug("Time difference is 15 mins");	
			  			str =  (String) NewTwitterTweetsModel.fetchTimelineTweet1(this.reqObj,tweetNode,100);
			  			//LOG.debug("fetchTimelineTweet1 value:" + str);
			  			n.setProperty("message",str); 
			  			n.setProperty("time",dateFormat.format(date));
			  		  
			  			 }
							
			  			 else
			  			 {
			  				  Property refMessage = n.getProperty("message");  
						      Value mval = refMessage.getValue();
						      String tweets = mval.getString();

						     // LOG.debug("tweets value:" + tweets);  
						      str = tweets;
						      n.setProperty("message",str); 
						  	  n.setProperty("time",myVal);
						      
			  			 }
					        
					  
					
						
						//String getVal = NewSaveTweetsModel.readTweetsTime(this.reqObj,tweetNode);
					  LOG.debug("Node Exist");	
					  }
					  
					  else{
				      Node Twitter = root.addNode(tweetNode); 
					  Node tweets = Twitter.addNode("tweet"); 
					  tweets.setProperty("message",str);
					  tweets.setProperty("time",dateFormat.format(date)); 
					  LOG.debug("Node not Exist");	
					   }
				  //NewSaveTweetsModel.readTweets(this.reqObj,tweetNode);
					  session.save(); 
				  
				
             
				  }
		
			catch(Exception e){
		    	  LOG.debug(e.getMessage());
		      }
			}
			catch(Exception e){
		    	  LOG.debug(e.getMessage());
		      }
		}
		return str;
}

@SuppressWarnings("deprecation")
public void activate(final ComponentContext componentContext) {
	// TODO Auto-generated method stub
	
    LOG.debug("test activate finalproject");
  
   
    
final  String schedulingExpression = "0 0/1 * * * ?"; 
Map<String, Serializable> config1 = new HashMap<String, Serializable>();
boolean runConcurrently = true;
			    final Runnable job = new Runnable() {
			    
			        public void run() {
			        	LOG.debug("logrunnable");
			     	 String strTweets =  updateTweets(componentContext); 
			        }
			    };

try {
	
this.scheduler.addJob("myJob", job, config1, schedulingExpression, runConcurrently);
	
} catch (Exception e) {
job.run();
}

	
}
@Override
@Activate
public void listTitles() {

}

		
}
		
    
		
		
