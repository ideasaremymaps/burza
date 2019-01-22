package com.app.burza.client;

import com.app.burza.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Burza implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Timer timer = new Timer() {

			@Override
			public void run() {
				// Create the popup dialog box
				final DialogBox dialogBox = new DialogBox();
				dialogBox.setText("Remote Procedure Call");
				dialogBox.setAnimationEnabled(true);
				final Button closeButton = new Button("Close");
				// We can set the id of a widget by accessing its Element
				closeButton.getElement().setId("closeButton");
				final Label textToServerLabel = new Label();
				final HTML serverResponseLabel = new HTML();
				VerticalPanel dialogVPanel = new VerticalPanel();
				dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
				dialogVPanel.add(serverResponseLabel);
				dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
				dialogVPanel.add(closeButton);
				dialogBox.setWidget(dialogVPanel);

				// Add a handler to close the DialogBox
				closeButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						dialogBox.hide();
					}
				});

				// Create a handler for the sendButton and nameField
				class MyHandler implements ClickHandler{
					private void sendNameToServer() {

						serverResponseLabel.setText("");
						greetingService.greetServer("čai",
								new AsyncCallback<String>() {
									public void onFailure(Throwable caught) {
										// Show the RPC error message to the user
										dialogBox
												.setText("Remote Procedure Call - Failure");
										serverResponseLabel
												.addStyleName("serverResponseLabelError");
										serverResponseLabel.setHTML(SERVER_ERROR);
										dialogBox.center();
										closeButton.setFocus(true);
									}

									public void onSuccess(String result) {
										dialogBox.setText("Remote Procedure Call");
										serverResponseLabel
												.removeStyleName("serverResponseLabelError");
										serverResponseLabel.setHTML(result);
										dialogBox.center();
										closeButton.setFocus(true);
									}
								});
					}

					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						
					}
				}

				MyHandler handler = new MyHandler();
				handler.sendNameToServer();
			}	
		};
		timer.scheduleRepeating(1000*20);
		
		
	}
}
