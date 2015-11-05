package com.tutorial.gwt.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class SchoolManager{ //implements EntryPoint {

	private List<SchoolClient> schoolClients;
	private final ConnectionManagerAsync connectionManager = GWT
			.create(ConnectionManager.class);

	/*@Override
	public void onModuleLoad() {
		Button buttonGetSchools = new Button("Get Schools");
		RootPanel.get().add(buttonGetSchools);
		buttonGetSchools.isVisible();
		buttonGetSchools.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// handle the click event
				System.out.println("Getting Schools...");
				getSchools();
			}
		});

		Button buttonImportSchools = new Button("Import Schools");
		RootPanel.get().add(buttonImportSchools);
		buttonImportSchools.isVisible();
		buttonImportSchools.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// handle the click event
				System.out.println("Adding Schools...");
				addSchools();
			}
		});
		
		Button buttonTruncateSchools = new Button("Truncate Schools");
		RootPanel.get().add(buttonTruncateSchools);
		buttonTruncateSchools.isVisible();
		buttonTruncateSchools.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// handle the click event
				System.out.println("Truncating Schools...");
				truncateSchools();
			}
		});
	}*/

	private void getSchools() {
		connectionManager.getSchools(new AsyncCallback<String[][]>() {
			public void onFailure(Throwable error) {
				error.printStackTrace();
			}

			public void onSuccess(String[][] schools) {
				schoolClients = new ArrayList<SchoolClient>();
				for (int i = 0; i < schools.length; i++) {			
					SchoolClient schoolClient = new SchoolClient(
							new Long(schools[i][0]), schools[i][1],
							 schools[i][2], schools[i][3], parseBoolean(schools[i][4]), 
							 parseBoolean(schools[i][5]),parseBoolean(schools[i][6]),
							 parseBoolean(schools[i][7]),parseBoolean(schools[i][8]),
							 parseBoolean(schools[i][9]),parseBoolean(schools[i][10]),
							 parseBoolean(schools[i][11]),parseBoolean(schools[i][12]),
							 parseBoolean(schools[i][13]),
							 new Location(Double.parseDouble(schools[i][14]),Double.parseDouble(schools[i][15])));
					schoolClients.add(schoolClient);		
				}
				
				//testing
				for(SchoolClient s : schoolClients){
					System.out.println("School Name: " + s.getName());
					System.out.println("Has Coop Program" + s.isHasCoopProg());
				}
				System.out.println("Schools retrieved Successfully!!");
			}
		});
	}

	private void addSchool(Long number, String name, String address,
			String url, boolean isPrivate, boolean hasEngLangLearnerProg,
			boolean hasFrenchImmersionProg, boolean hasAborigSuppServices,
			boolean hasContinuingEdProgram, boolean hasDistributedLearnProg,
			boolean hasCareerPrepProg, boolean hasCoopProg,
			boolean hasApprenticeshipProg, boolean hasCareerTechnicalProg,
			double latitude, double longitude) {
		connectionManager.addSchool(number, name, address, url, isPrivate,
				hasEngLangLearnerProg, hasFrenchImmersionProg,
				hasAborigSuppServices, hasContinuingEdProgram,
				hasDistributedLearnProg, hasCareerPrepProg, hasCoopProg,
				hasApprenticeshipProg, hasCareerTechnicalProg, latitude,
				longitude, new AsyncCallback<Void>() {
					public void onFailure(Throwable error) {
						error.printStackTrace();
					}

					public void onSuccess(Void ignore) {
						System.out.println("School Added Successfully!!");
					}
				});
	}

	private void addSchools() {
		connectionManager.addSchools(new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				error.printStackTrace();
			}

			public void onSuccess(Void ignore) {
				System.out.println("Schools Added Successfully!!");
			}
		});
	}
	
	private void truncateSchools() {
		connectionManager.truncateSchools(new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				error.printStackTrace();
			}

			public void onSuccess(Void ignore) {
				System.out.println("Schools Truncated Successfully!!");
			}
		});
	}
	
	private boolean parseBoolean (String s){
		if(s.toUpperCase().trim() =="TRUE"){
			return true;
		}
		else{
			return false;
		}
	}
	
	

}
