package com.app.burza.server;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.io.*;
import java.net.*;
import java.util.List;

import javax.servlet.http.HttpServlet;

public class TwitterServiceImpl extends HttpServlet { 
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	public TwitterServiceImpl() {
		String consumerKey="cJ4QARLd0mEZgslqCMauReNX0";
	    String consumerSecret = "TQ77zwcvxN2ad3QmGK256IodnWfRRRbJFb68VhxJy0Hvt4loG2";
	    String accessToken = "2684605706-7aGPCQFm3uvH8w1dpdQXC9jzSsyzSC1Pzi9HVpp";
	    String accessTokenSecret = "ITvSm2sCdrC8FJWkFnmlbImTC809fphY5zWYBh6SJqn4l";

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
		old= old.substring(31, 38);
		old=old.replace(".", "");
		String brojNezaposlenih = TwitterServiceImpl.getHTML("http://burzarada.hzz.hr/statistika.aspx?mode=4","hzz");
	    System.out.println(brojNezaposlenih);
	    current = brojNezaposlenih.replace(".", "");
	    oldI=Integer.parseInt(old);
	    currentI=Integer.parseInt(current);
	    difference=currentI-oldI;	
	    if (difference<0) sign=""; else sign="+";
		
	    StatusUpdate statusUpdate = new StatusUpdate(
                "Broj nezaposlenih u Hrvatskoj: "+brojNezaposlenih+" ("+sign+difference+")");
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
			int brojac=0, pogodak=0;
	        while ((inputLine = in.readLine()) != null) { 	
	        	brojac++;   
	        	if (brojac==(pogodak+2)){
	        		if(inputLine.length()>0) {
	        		hit=inputLine.substring(34,41);
	        		return hit;
	        		}
	        	}
	        	if (inputLine.contains("nezaposlenih")==true) pogodak=brojac;
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Filename;		
	}
}
