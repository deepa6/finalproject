package org.sixdimensions.finalproject.core.servlets;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
    
    
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
  
import java.util.HashMap;
import java.util.Map;
    
import javax.jcr.Repository; 
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.jcr.Node; 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
     
import org.apache.jackrabbit.commons.JcrUtils;
    
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
    
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.sling.SlingServlet;

import javax.jcr.RepositoryException;
import org.apache.felix.scr.annotations.Reference;
import org.apache.jackrabbit.commons.JcrUtils;
    
import javax.jcr.Session;
import javax.jcr.Node; 
   
   
//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory ;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.io.JSONWriter;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource; 
    
  
//QUeryBuilder APIs
import com.day.cq.search.QueryBuilder; 
import com.day.cq.search.Query; 
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.result.SearchResult;
import com.day.cq.search.result.Hit; 
  


@SuppressWarnings("unused")
@SlingServlet(paths="/bin/SearchNewServiceString", name = "org.myorg.qbservletexample.core.servlets.SearchServiceString", methods = "POST", metatype = true)

public class SearchServiceString extends SlingAllMethodsServlet {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/** Default log. */
protected final Logger log = LoggerFactory.getLogger(this.getClass());
           
private Session session;
               
//Inject a Sling ResourceResolverFactory
@Reference
private ResourceResolverFactory resolverFactory;
           
@Reference
private QueryBuilder builder;
      
//@Override
protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

               
 //Invoke the adaptTo method to create a Session 
    //ResourceResolver resourceResolver = resolverFactory;
    ResourceResolver resourceResolver = request.getResourceResolver();
    session = resourceResolver.adaptTo(Session.class);
    String fulltextSearchTerm = request.getParameter("value1");      
   // String fulltextSearchTerm = "deepa";
   // String fulltextSearchTerm = ".jpg";
    
    response.setCharacterEncoding("UTF-8");

    response.setContentType("application/json");

    response.setHeader("Content-Type", "application/json; charset=UTF-8");

 // Get all of our filters
    String path = request.getParameter("path");
    
    System.out.println("This is path value:"+path);
    
    // create query description as hash map (simplest way, same as form post)
    Map<String, String> map = new HashMap<String, String>();
  
 // create query description as hash map (simplest way, same as form post)
                
    map.put("path", "/content/dam/finalproject");
    //map.put("type", "dam.Asset");
    map.put("type", "dam:Asset");
    map.put("property", "jcr:content/metadata/dc:format"); 
    map.put("property.value", "image/jpeg");
	map.put("group.p.and", "true"); // combine this group with OR
    map.put("group.1_fulltext", fulltextSearchTerm);
    
   // map.put("type", "jpeg");
   // map.put("group.p.or", "true"); // combine this group with OR
    //map.put("group.1_fulltext", fulltextSearchTerm);
    //map.put("group.1_fulltext.relPath", "jcr:content");
    //map.put("group.2_fulltext", fulltextSearchTerm);
    //map.put("group.2_fulltext.relPath", "jcr:content/@cq:tags");
    // can be done in map or with Query methods
    map.put("p.offset", "0"); // same as query.setStart(0) below
    map.put("p.limit", "100"); // same as query.setHitsPerPage(20) below 
    
   
    
                     
  /*  map.put("1_group.path", path);
    map.put("1_group.type", "cq:Page");
    map.put("1_group.property", "jcr:content/@sling:resourceType");
    map.put("1_group.property.operation", "Exists");
    map.put("p.offset", "0"); // same as query.setStart(0) below
    map.put("p.limit", "20");  */// same as query.setHitsPerPage(20) below */
   
    
    Query query = builder.createQuery(PredicateGroup.create(map), session);
                       
    query.setStart(0);
    query.setHitsPerPage(100);
               
    SearchResult result = query.getResult();
                   
    // paging metadata
    int hitsPerPage = result.getHits().size(); // 20 (set above) or lower
    long totalMatches = result.getTotalMatches();
    long offset = result.getStartIndex();
    long numberOfPages = totalMatches / 100;
    
  
try {

JSONWriter writer = new JSONWriter(response.getWriter());

query.setHitsPerPage(0);
//com.day.cq.wcm.foundation.Search.Result results = null;
SearchResult results = query.getResult();
if (results != null) {

writer.array();

for (com.day.cq.search.result.Hit hit : results.getHits()) {

String title = hit.getProperties().get("jcr:title", String.class);

String path1 = hit.getPath();

path1 = path1.substring(path1.indexOf('/',1)+1);

writer.object();

writer.key("value");

//writer.value("<a href="+path1+".html>"+path1+"</a>");

	
writer.value("<img src='/content/"+path1+"' height='100' width='100'><a href='/content/"+path1+"' target='_blank'>click view</a>");

String strPath = path1.substring(path1.lastIndexOf("/")+1, path1.length());

writer.key("text");

writer.value(strPath);

writer.endObject();

}
writer.endArray();}
}
catch (Exception e) {

throw new ServletException(e);
}
}
}    