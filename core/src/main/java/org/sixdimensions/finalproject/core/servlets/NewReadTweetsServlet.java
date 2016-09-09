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
import org.sixdimensions.finalproject.core.models.NewSaveTweetsModel;
//import org.sixdimensions.finalproject.core.models.TwitterTweetsModel;
import org.sixdimensions.finalproject.core.models.TwitterTweetsModel;




@SuppressWarnings("unused")
@SlingServlet(paths="/bin/NewReadTweetsServlet", name = "org.myorg.slingcurd.core.servlets.NewReadTweetsServlet", methods = "GET", metatype = true)

public class NewReadTweetsServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {

		private static final long serialVersionUID = 1L;
		
		@Override
	    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
	  
			String search = request.getParameter("search");
			String count = request.getParameter("count");
			//String ss = null;
			int cnt = Integer.parseInt(count);
			
			ServletContext context=getServletContext();  
			
			//ServletContext context1=getServletContext();  
			
			
			    response.setCharacterEncoding("UTF-8");

			    response.setContentType("application/json");

			    response.setHeader("Content-Type", "application/json; charset=UTF-8");
			    //JSONObject jsonObj = null;
			    String jsonStr = null;
			  //  String jsonS = null;
			 	
				try {
					jsonStr = (String) TwitterTweetsModel.fetchTimelineTweet(search,cnt);
					//jsonStr = (String) NewSaveTweetsModel.readTweets(search);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					 response.getWriter().write("Error while reading");
				}
							
				    
				   response.getWriter().write(jsonStr);
				
				
			
			
		}
		
}