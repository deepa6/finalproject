package org.sixdimensions.finalproject.core.servlets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.oak.commons.json.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.io.JSONWriter;
import org.sixdimensions.finalproject.core.models.NewRunTweetsModel;
import org.sixdimensions.finalproject.core.models.NewSaveTweetsModel;
import org.sixdimensions.finalproject.core.models.NewTwitterTweetsModel;
import org.sixdimensions.finalproject.core.models.TwitterTweetsModel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
@SlingServlet(paths="/bin/NewTwitterTweetsServlet", name = "org.myorg.slingcurd.core.servlets.NewTwitterTweetsServlet", methods = "GET", metatype = true)

public class NewTwitterTweetsServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {// implements ServletContextListener {

		private static final long serialVersionUID = 1L;
		private static final Logger LOG = LoggerFactory.getLogger(NewTwitterTweetsServlet.class);
		 private String collectSearchValues; 
		 private ScheduledExecutorService scheduler;
		private int startJobcnt = 0; 
		public void init() 
		  { 
			startJobcnt = 1;
		  } 

		
		@Override
	    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
	  
			String search = request.getParameter("search");
			String count = request.getParameter("count");
			//String ss = null;
			NewSaveTweetsModel ns = new NewSaveTweetsModel();
			int cnt = Integer.parseInt(count);
			
			ServletContext context=getServletContext();  
			
			ServletContext context1=getServletContext();  
			
			 LOG.debug("logging test" );
			 
			 String queryStr = null; 
		     
	          // Session session = request.getResourceResolver().adaptTo(Session.class);
	           ResourceResolver rr =request.getResourceResolver();
	           Resource res =rr.getResource("/etc/twitter");
	           Node root = res.adaptTo(Node.class);
	        
			
			
				NodeIterator nodes = null;
				try {
					nodes = root.getNodes();
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
			    //queryStr = "orange,blue";
			/*scheduler = Executors.newSingleThreadScheduledExecutor();
			scheduler.scheduleAtFixedRate(new NewRunTweetsModel(queryStr,request), 0, 1, TimeUnit.MINUTES);*/
			    
	
			 
			    response.setCharacterEncoding("UTF-8");

			    response.setContentType("application/json");

			    response.setHeader("Content-Type", "application/json; charset=UTF-8");
			    JSONObject jsonObj = null;
			    String jsonS = null;
			    String jsonStr =null;
			   
			    
			   
			     //js = nr.updateTweets();
			    
				
					//jsonStr = (String) ns.readTweets(request,search);
					
					 //nr.activate();
				
			 	
				//jsonS = (String) NewTwitterTweetsModel.fetchTimelineTweet(request,search,cnt);
				try {
					//jsonStr = (String) ns.readTweets(request,search);
					jsonStr = (String) ns.loadFetchTweets(request,search,cnt);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				   
				  //ss =(String) context1.getAttribute("searchArr");
				   
				    //ss +=","+search;
				    
				   //// collectSearchValues +=","+search;
				   // context1.setAttribute("searchArr",ss);
				    
				   
				    
				    //context.setAttribute("jsonStr",jsonStr); 
				    
				   //response.getWriter().write(jsonStr);
				 LOG.debug("tweetNode queryStr:::: {}", queryStr.toString());	
				 //LOG.debug("tweetNode request::::: ", request);	
				 
				 if (startJobcnt == 1){
				 NewRunTweetsModel nr = new NewRunTweetsModel(queryStr,request);
				 startJobcnt = startJobcnt + 1;
				 LOG.debug("startJobcnt value {}", startJobcnt);
				 }
				 response.getWriter().write(jsonStr);
				  
				
				////List<String> myList = new ArrayList<String>(Arrays.asList(collectSearchValues.split(",")));
				
				////request.setAttribute("searchList",myList);
				
				//doPost(request,response);
			//return jsonStr;
		}
		
		
		  public void destroy() {
			 startJobcnt = 0;
			  }

		
		
		
		
		
}