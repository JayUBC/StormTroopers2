package com.tutorial.gwt.client;


import com.tutorial.gwt.client.Location;
import com.tutorial.gwt.client.SchoolClient;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import java.util.List;
public class SchoolManagerJorge {

	private List<SchoolClient> schoolList = new ArrayList<SchoolClient>();
	private List<SchoolClient> schoolClients = new ArrayList<SchoolClient>(); //PO
    private final ConnectionManagerAsync connectionManager = GWT
    		.create(ConnectionManager.class);

    public void setSchoolList(List<SchoolClient> schoolList) {
		this.schoolList = schoolList;
	}

	public SchoolManagerJorge(){
		System.out.println("SchoolManagerJorge executing...");
        addSchools();
        getSchools();
        schoolClients = fetchSchoolData();
        schoolList = fetchSchoolData();
    }

    public boolean tokenIsASchool(String token) {
        for (SchoolClient school : schoolList){
            if(SchoolClient.hasEqualCode(token,school)){
                return true;
            }
        }
        return false;
    }

    public void renderSchoolPage(String token) {
        RootPanel.get("core").clear();
        SchoolClient school = findSchoolByToken(token);
        VerticalPanel panel = new VerticalPanel();
        if (school != null){
            HTML schoolName = new HTML("school name: "+ school.getName());
            panel.add(schoolName);
            HTML schoolAddress = new HTML("school name: "+ school.getAddress());
            panel.add(schoolAddress);
            HTML schoolUrl = new HTML("school URL: <a  target=\"_blank\"  href=" + school.getUrl() + " >" + school.getUrl() + "</a>");
            panel.add(schoolUrl);
        }
        else{
            HTML noSchoolFound = new HTML("<b>No school found!</b>");
            panel.add(noSchoolFound);
        }

        RootPanel.get("core").add(panel);
    }



   public void renderListOfSchools() {
    	RootPanel.get("core").clear();
        VerticalPanel panel = new VerticalPanel();
        FlexTable schoolsFlexTable = new FlexTable();
 	    ArrayList<String> schools = new ArrayList<String>();
 	    // Create table for school data.
	    schoolsFlexTable.setText(0, 0, "School Name");
	    schoolsFlexTable.setText(0, 1, "Address");
	    schoolsFlexTable.setText(0, 2, "Website");
	    

	    
	    // Add styles to elements in the stock list table.
	    schoolsFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
	    schoolsFlexTable.addStyleName("watchList");
	    schoolsFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	    schoolsFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");

	
	    // Assemble Add School panel.
	    panel.add(schoolsFlexTable);
	    
	    RootPanel.get("core").add(panel);
	    
	    Location l = new Location(1.33,2.54);
	    
	    SchoolClient school1 = new SchoolClient(new Long(3939012), "Point Grey Secondary", "5350 Eest Boulevard", "http://www.vsb.bc.ca/schools/point-grey", false, true, false, true, false, false, true, false, true, false, new Location(49.2379, -123.1534));
       schoolList.add(school1);
       SchoolClient school2 = new SchoolClient(new Long(3939074), "Maple Grove Elementary", "6199 Cypress St", "http://www.vsb.bc.ca/schools/maple-grove", false, true, false, true, false, false, false, false, false, false, new Location(49.2294, -123.1515));
       schoolList.add(school2);
       SchoolClient school3 = new SchoolClient(new Long(3939008), "Lord Byng Secondary", "3939 W 16th Av", "http://www.vsb.bc.ca/schools/lord-byng", false, true, false, true, false, false, true, false, true, true, new Location(49.2596, -123.1925));
       schoolList.add(school3);
       SchoolClient school4 = new SchoolClient(new Long(3939084), "Trafalgar Elementary","4170 Trafalgar St", "http://www.vsb.bc.ca/schools/trafalgar", false, true, false, true, false, false, false, false, false, false, new Location(49.2502, -123.1646));
       schoolList.add(school4);
       SchoolClient school5 = new SchoolClient(new Long(3939010), "Templeton Secondary","727 Templeton Drive","http://www.vsb.bc.ca/schools/templeton",false,true,false,true,false,false,true,false,true,false, new Location(49.2784,-123.0607));
       schoolList.add(school5);
	    
	   
	    schoolList = new ArrayList<SchoolClient>();
	  
	    schoolList.add(school1);
	    schoolList.add(school2);
	    schoolList.add(school3);
	    schoolList.add(school4);
	    schoolList.add(school5);

	  
	    
     for(SchoolClient s: schoolList){
   	  if (schoolsFlexTable.getRowCount()==schoolList.size()+1)
   		  return;
   	  else{
   	  
	    	int row = schoolsFlexTable.getRowCount();
	        schools.add(s.getName());
	        Hyperlink hyper = new Hyperlink(s.getName(),"schoos/"+s.getNumber().toString());
	        schoolsFlexTable.setWidget(row, 0, hyper);
	        schoolsFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
	        schoolsFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
	        schoolsFlexTable.setText(row, 1, s.getAddress());
	        Anchor anchor = new Anchor(s.getUrl(),s.getUrl());
	        schoolsFlexTable.setWidget(row, 2, anchor);
   	  }
	    
	    
     }
	  
    }

    private List<SchoolClient> fetchSchoolData() {
    	System.out.println("Fetching School List");
        connectionManager.getSchools(new AsyncCallback<String[][]>() {
            public void onFailure(Throwable error) {
                error.printStackTrace();
            }

            public void onSuccess(String[][] schools) {
            	System.out.println("Fetching School Success...");
                schoolList = new ArrayList<SchoolClient>();
                for (int i = 0; i < schools.length; i++) {
                    SchoolClient schoolClient = new SchoolClient(
                            new Long(schools[i][0]), schools[i][1],
                            schools[i][2], schools[i][3], parseBoolean(schools[i][4]),
                            parseBoolean(schools[i][5]), parseBoolean(schools[i][6]),
                            parseBoolean(schools[i][7]), parseBoolean(schools[i][8]),
                            parseBoolean(schools[i][9]), parseBoolean(schools[i][10]),
                            parseBoolean(schools[i][11]), parseBoolean(schools[i][12]),
                            parseBoolean(schools[i][13]),
                            new Location(Double.parseDouble(schools[i][14]), Double.parseDouble(schools[i][15])));
                    schoolList.add(schoolClient);
                }

                //testing
                System.out.println("Schools retrieved Successfully!!");
            }
        });
        return schoolList;
    }
    public void getSchools() {
    	schoolClients = new ArrayList<SchoolClient>();
		connectionManager.getSchools(new AsyncCallback<String[][]>() {
			public void onFailure(Throwable error) {
				System.out.println("!!!!!!GOT TO FAILURE!!!!");
				error.printStackTrace();
			}

			public void onSuccess(String[][] schools) {
				
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
					schoolList.add(schoolClient);
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

    private List<SchoolClient> createTestSchoolData(){
        //eventually will get the data from mySQLConnector, for now just create one
        List<SchoolClient> schoolList = new ArrayList<SchoolClient>();

        SchoolClient school1 = new SchoolClient(new Long(3939012), "Point Grey Secondary", "5350 Eest Boulevard", "http://www.vsb.bc.ca/schools/point-grey", false, true, false, true, false, false, true, false, true, false, new Location(49.2379, -123.1534));
        schoolList.add(school1);
        SchoolClient school2 = new SchoolClient(new Long(3939074), "Maple Grove Elementary", "6199 Cypress St", "http://www.vsb.bc.ca/schools/maple-grove", false, true, false, true, false, false, false, false, false, false, new Location(49.2294, -123.1515));
        schoolList.add(school2);
        SchoolClient school3 = new SchoolClient(new Long(3939008), "Lord Byng Secondary", "3939 W 16th Av", "http://www.vsb.bc.ca/schools/lord-byng", false, true, false, true, false, false, true, false, true, true, new Location(49.2596, -123.1925));
        schoolList.add(school3);
        SchoolClient school4 = new SchoolClient(new Long(3939084), "Trafalgar Elementary","4170 Trafalgar St", "http://www.vsb.bc.ca/schools/trafalgar", false, true, false, true, false, false, false, false, false, false, new Location(49.2502, -123.1646));
        schoolList.add(school4);
        SchoolClient school5 = new SchoolClient(new Long(3939010), "Templeton Secondary","727 Templeton Drive","http://www.vsb.bc.ca/schools/templeton",false,true,false,true,false,false,true,false,true,false, new Location(49.2784,-123.0607));
        schoolList.add(school5);

        return schoolList;
    }

    private SchoolClient findSchoolByToken(String token){
        for (SchoolClient school : schoolList){
            if (SchoolClient.hasEqualCode(token, school)){
                return school;
            }
        }
        return null;
    }

    public List<SchoolClient> getSchoolList() {
        return schoolList;
    }

    private boolean parseBoolean (String s){
        if(s.toUpperCase().trim() =="TRUE"){
            return true;
        }
        else{
            return false;
        }
    }

    //Debugging purposes, writes to javascript console
    public static native void console(String text)
/*-{
    console.log(text);
}-*/;
}
