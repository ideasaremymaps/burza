package com.app.burza.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.app.burza.client.GreetingService;
import com.app.burza.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import twitter4j.*;
import twitter4j.auth.AccessToken;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		GreetingServiceImpl f1 = new GreetingServiceImpl();
		String brojNezaposlenih = f1.getHTML("http://burzarada.hzz.hr/statistika.aspx?mode=4","hzz");

		return "<br/>Broj nezaposlenih je: "+brojNezaposlenih+"!";
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	public String getHTML(String URL, String Filename) {
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
