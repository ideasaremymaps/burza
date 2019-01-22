package com.app.burza.server;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.io.*;
import java.net.*;
import java.util.List;
//import java.util.List;

import javax.servlet.http.HttpServlet;

public class TwitterSiServiceImpl extends HttpServlet { 
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	public TwitterSiServiceImpl() {
		String consumerKey="tn1Na0hTSscAiG4JZDpGEpTxc";
	    String consumerSecret = "ablMYzqQf89OudUX6aCWhJgBMEO9hCxsRYI0ex0vOkt66flzCR";
	    String accessToken = "2762454482-UK8s24B2ehbYzC8UWMJdLIislRZ6v0PcPPSxZwb";
	    String accessTokenSecret = "M4wsLNJjeCnMAFHjKii7nmEHKFg7nJjRLDzz92i85oWFS";

	    TwitterFactory twitterFactory = new TwitterFactory();
	    Twitter twitter = twitterFactory.getInstance();
	    twitter.setOAuthConsumer(consumerKey, consumerSecret);
	    twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
		
	    List<Status> statuses = null;
		try {
			statuses = twitter.getUserTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	    
		int oldI, currentI, difference;
		String sign="";
		Status statusOld = statuses.get(0);
		String old = statusOld.getText();
		String current = "";
		old= old.substring(34, 41);
		old=old.replace(".", "");
		String brojNezaposlenih = TwitterSiServiceImpl.getHTML("http://www.ess.gov.si/","ess");
		current = brojNezaposlenih.replace(".", "");
		current=current.trim();
		old=old.trim();
	    oldI=Integer.parseInt(old);
	    currentI=Integer.parseInt(current);
	    difference=currentI-oldI;	
	    if (difference<0) sign=""; else sign="+";
		
		StatusUpdate statusUpdate = new StatusUpdate(
                "Število nezaposlenih v Sloveniji: "+brojNezaposlenih+" ("+sign+difference+")");
	    try {
			Status status = twitter.updateStatus(statusUpdate);
			status.getUser();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	public static String getHTML(String URL, String Filename) {
		try {
			URL vijesti = new URL(URL);
			BufferedReader in = new BufferedReader(
			new InputStreamReader(vijesti.openStream()));
			String hit, inputLine;
			while ((inputLine = in.readLine()) != null) { 	
	        	if (inputLine.contains("iskalcev")){
	        		if(inputLine.length()>0) {
	        		hit=inputLine.substring(25,33);
	        		return hit;
	        		}
	        	}
	        	
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Filename;		
	}
}