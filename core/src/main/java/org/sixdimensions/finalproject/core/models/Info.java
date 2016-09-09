package org.sixdimensions.finalproject.core.models;

import com.adobe.cq.sightly.WCMUse;
import java.security.SecureRandom;

public class Info extends WCMUse {
    private String lowerCaseTitle;
    private String lowerCaseDescription;
    private String subStr;
    private String reverseText;
    private String  AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private String sbb; 
    static SecureRandom rnd = new SecureRandom();
    
    @Override
    public void activate() throws Exception {
        lowerCaseTitle = getProperties().get("jcr:text", "this is title").toLowerCase();
        lowerCaseDescription = getProperties().get("description", "This is description").toLowerCase();
        String text = get("texti", String.class);
        subStr = text.substring(text.lastIndexOf("/") + 1);
        
        sbb = get("sbb", String.class);
        
        String txt = get("text", String.class);
        reverseText = new StringBuffer(txt).reverse().toString();

    }
  
    public String getLowerCaseTitle() {
        return lowerCaseTitle;
    }
  
    public String getLowerCaseDescription() {
        return lowerCaseDescription;
    }
    

	public String getSubStr() {
		return subStr;
	}
	public String getReverseText()
    {

   return reverseText; 
    }
	public String getSbb(){
		StringBuilder sb = new StringBuilder( 100 );
        for( int i = 0; i < 100; i++ ) 
           sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        sbb = sb.toString();
		return sbb;
	}
}
