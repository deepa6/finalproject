package org.sixdimensions.finalproject.core.servlets;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.oak.commons.json.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.io.JSONWriter;
import org.sixdimensions.finalproject.core.models.TwitterTweetsModel;

import org.slf4j.LoggerFactory;


@SuppressWarnings("unused")
@SlingServlet(paths="/bin/TwitterTweetsServlet", name = "org.myorg.slingcurd.core.servlets.TwitterTweetsServlet", methods = "GET", metatype = true)

public class TwitterTweetsServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {

		private static final long serialVersionUID = 1L;
		
		//private static final Logger LOG = (Logger) LoggerFactory.getLogger(TwitterTweetsServlet.class);
	    
		 private String hitCount; 
		 
		public void init() 
		  { 
		     // Reset hit counter.
		    hitCount = null;
		  } 

		
		
		@Override
	    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
	  
			String search = request.getParameter("search");
			String count = request.getParameter("count");
			String ss = null;
			int cnt = Integer.parseInt(count);
			
			ServletContext context=getServletContext();  
			
			ServletContext context1=getServletContext();  
			
			
			    response.setCharacterEncoding("UTF-8");

			    response.setContentType("application/json");

			    response.setHeader("Content-Type", "application/json; charset=UTF-8");
			    JSONObject jsonObj = null;
			 	
				try {
					String jsonStr = (String) TwitterTweetsModel.fetchTimelineTweet(search,cnt);
									
					   
					    
					    
					    ss =(String) context1.getAttribute("searchArr");
					   
					    ss +=","+search;
					    
					    hitCount +=","+search;
					    context1.setAttribute("searchArr",ss);
					    
					   
					    
					    context.setAttribute("jsonStr",jsonStr); 
					    
					    response.getWriter().write(jsonStr+"~~~~~"+(String) context1.getAttribute("searchArr")+"~~~~"+hitCount);
					    
					    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//response.getWriter().write("ERROR");
					
					response.getWriter().write((String) context.getAttribute("jsonStr"));
					
				}
				
				
			
			//return jsonStr;
		}
		
}