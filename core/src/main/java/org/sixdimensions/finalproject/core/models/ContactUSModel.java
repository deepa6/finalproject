package org.sixdimensions.finalproject.core.models;
	
	import com.adobe.cq.sightly.WCMUse;


	public class ContactUSModel extends WCMUse {
	    private String lowerCaseTitle;
	    private String lowerCaseDescription;
	  
	    @Override
	    public void activate() throws Exception {
	        lowerCaseTitle = getProperties().get("jcr:title", "Configure Title").toLowerCase();
	        lowerCaseDescription = getProperties().get("jcr:description", "Configure Description ").toLowerCase();
	    }
	  
	    public String getLowerCaseTitle() {
	        return lowerCaseTitle;
	    }
	  
	    public String getLowerCaseDescription() {
	        return lowerCaseDescription;
	    }
	}

