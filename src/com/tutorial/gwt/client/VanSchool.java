package com.tutorial.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.tutorial.gwt.client.UserDataService;
import com.tutorial.gwt.client.UserDataServiceAsync;
import com.tutorial.gwt.client.LoginService;
import com.tutorial.gwt.client.LoginServiceAsync;
import com.tutorial.gwt.client.NotLoggedInException;
import com.tutorial.gwt.client.LoginInfo;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.List;

public class VanSchool implements EntryPoint, ValueChangeHandler {

	private final ConnectionManagerAsync connectionManager = GWT.create(ConnectionManager.class);
	private List<SchoolClient> schoolClients;
	private final MapManager mapManager = new MapManager();
	// private SchoolManagerJorge schoolManager;
	private List<SchoolClient> schoolList = new ArrayList<SchoolClient>();

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	private TextBox newInfoTextBox = new TextBox();
	private TextBox lastNameTextBox = new TextBox();
	private TextBox ageTextBox = new TextBox();
	private TextBox genderTextBox = new TextBox();
	private Button addInfoButton = new Button("Save");

	private ArrayList<String> userdatas = new ArrayList<String>();
	private ArrayList<String> lastNames = new ArrayList<String>();
	private ArrayList<Integer> ages = new ArrayList<Integer>();
	private ArrayList<String> genders = new ArrayList<String>();
	private final UserDataServiceAsync userDataService = GWT.create(UserDataService.class);

	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadVanSchool();
				} else {
					loadLogin();
				}
			}
		});
	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("core").add(loginPanel);
	}

	public void loadVanSchool() {

		// Set up sign out hyperlink.
		signOutLink.setHref(loginInfo.getLogoutUrl());

		// loadUserInfos();
		loadUserDatas();
		loadLastNames();
		loadAges();
		loadGenders();

		// schoolManager = new SchoolManagerJorge();
		// fetchSchoolData();
		// getSchools();
		schoolClients = new ArrayList<SchoolClient>();
		connectionManager.getSchools(new AsyncCallback<String[][]>() {
			public void onFailure(Throwable error) {
				System.out.println("!!!!!!GOT TO FAILURE!!!!");
				error.printStackTrace();
			}

			public void onSuccess(String[][] schools) {

				for (int i = 0; i < schools.length; i++) {
					SchoolClient schoolClient = new SchoolClient(new Long(schools[i][0]), schools[i][1], schools[i][2],
							schools[i][3], parseBoolean(schools[i][4]), parseBoolean(schools[i][5]),
							parseBoolean(schools[i][6]), parseBoolean(schools[i][7]), parseBoolean(schools[i][8]),
							parseBoolean(schools[i][9]), parseBoolean(schools[i][10]), parseBoolean(schools[i][11]),
							parseBoolean(schools[i][12]), parseBoolean(schools[i][13]),
							new Location(Double.parseDouble(schools[i][14]), Double.parseDouble(schools[i][15])));
					schoolClients.add(schoolClient);
					schoolList.add(schoolClient);
				}

				// testing
				for (SchoolClient s : schoolClients) {
					System.out.println("School Name: " + s.getName());
					System.out.println("Has Coop Program" + s.isHasCoopProg());
				}
				for (SchoolClient s : schoolList) {
					System.out.println("School List Name: " + s.getName());
					System.out.println("Has Coop Program" + s.isHasCoopProg());
				}
				System.out.println("Schools retrieved Successfully!!");
			}
		});

		if (schoolList.isEmpty()) {
			System.out.println("!!!Empty school list!!!");
		}
		if (schoolClients.isEmpty()) {
			System.out.println("!!!Empty school clients list!!!");
		}
		for (SchoolClient s : schoolList) {
			System.out.println("!!!" + s.getName() + "!!!");
		}
		createNav();
		History.addValueChangeHandler(this);
		// when there is no token, the "home" token is set, else changePage() is
		// called.
		// this is useful if a user has bookmarked a site other than the
		// homepage.
		if (History.getToken().isEmpty()) {
			History.newItem("home");
		} else {
			changePage(History.getToken());
		}
		Button buttonGetSchools = new Button("Get Schools");
		RootPanel.get().add(buttonGetSchools);
		buttonGetSchools.isVisible();
		buttonGetSchools.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// handle the click event
				System.out.println("Getting Schools...");
				// getSchools();
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
	}
	/*
	 * private void getSchools() { schoolClients = new
	 * ArrayList<SchoolClient>(); connectionManager.getSchools(new
	 * AsyncCallback<String[][]>() { public void onFailure(Throwable error) {
	 * System.out.println("!!!!!!GOT TO FAILURE!!!!"); error.printStackTrace();
	 * }
	 * 
	 * public void onSuccess(String[][] schools) {
	 * 
	 * for (int i = 0; i < schools.length; i++) { SchoolClient schoolClient =
	 * new SchoolClient( new Long(schools[i][0]), schools[i][1], schools[i][2],
	 * schools[i][3], parseBoolean(schools[i][4]),
	 * parseBoolean(schools[i][5]),parseBoolean(schools[i][6]),
	 * parseBoolean(schools[i][7]),parseBoolean(schools[i][8]),
	 * parseBoolean(schools[i][9]),parseBoolean(schools[i][10]),
	 * parseBoolean(schools[i][11]),parseBoolean(schools[i][12]),
	 * parseBoolean(schools[i][13]), new
	 * Location(Double.parseDouble(schools[i][14]),Double.parseDouble(schools[i]
	 * [15]))); schoolClients.add(schoolClient); schoolList.add(schoolClient); }
	 * 
	 * //testing for(SchoolClient s : schoolClients){ System.out.println(
	 * "School Name: " + s.getName()); System.out.println("Has Coop Program" +
	 * s.isHasCoopProg()); } System.out.println(
	 * "Schools retrieved Successfully!!"); } }); }
	 */

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

	@Override
	public void onValueChange(ValueChangeEvent valueChangeEvent) {
		changePage(History.getToken());
	}

	private void changePage(String token) {
		if (token.equals("search")) {
			RootPanel.get("core").clear();
			renderListOfSchools();
			return;
		}
		if (tokenIsASchool(token)) {
			renderSchoolPage(token);
		} else {
			renderHomePage();
		}
	}

	private void createNav() {
		Hyperlink homeButton = new Hyperlink("Home", "home");
		RootPanel.get("homeButton").add(homeButton);
		Hyperlink searchButton = new Hyperlink("Search Schools!", "search");
		RootPanel.get("searchButton").add(searchButton);
		Hyperlink profileButton = new Hyperlink("My profile", "userProfile");
		RootPanel.get("profileButton").add(profileButton);
		Hyperlink signInOrOutButton = new Hyperlink("Sign in", "userSign");
		RootPanel.get("signInOrOutButton").add(signInOrOutButton);
		Hyperlink createProfileButton = new Hyperlink("Sign up", "userCreate");
		RootPanel.get("createProfileButton").add(createProfileButton);
	}

	private void renderHomePage() {

		// Assemble UserInfo panel
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(12);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		layout.setHTML(0, 0, "Enter Your Info:");
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		layout.setHTML(1, 0, "First Name:");
		layout.setWidget(1, 1, newInfoTextBox);
		layout.setHTML(2, 0, "Last Name:");
		layout.setWidget(2, 1, lastNameTextBox);
		layout.setHTML(3, 0, "Age:");
		layout.setWidget(3, 1, ageTextBox);
		layout.setHTML(4, 0, "Gender:");
		layout.setWidget(4, 1, genderTextBox);
		layout.setWidget(4, 2, addInfoButton);

		DecoratorPanel decPanel = new DecoratorPanel();
		decPanel.setWidget(layout);

		console("here1");
		RootPanel.get("core").clear();
		HorizontalPanel panel = new HorizontalPanel();

		HTML info = new HTML("App info will go here");

		panel.add(signOutLink);
		panel.add(decPanel);
		panel.add(info);

		HTMLPanel mapPanel = new HTMLPanel(""); // automatically creates a div.
		panel.add(mapPanel);
		info.getElement().getParentElement().getStyle().setProperty("width", 50, Style.Unit.PCT);
		RootPanel.get("core").add(panel);
		mapManager.startMapOnPanel(mapPanel, getSchoolList());

		// Move cursor focus to the input box.
		newInfoTextBox.setFocus(true);

		// Listen for mouse events on the Add Info button.
		addInfoButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addUserData();
			}
		});

		// Listen for keyboard events in the input Info box.
		newInfoTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addUserData();
				}
			}
		});
	}

	private void addUserData() {

		final String uid = newInfoTextBox.getText().trim();
		final String lastName = lastNameTextBox.getText().trim();
		final Integer age = Integer.parseInt(ageTextBox.getText().trim());
		final String gender = genderTextBox.getText().trim();
		newInfoTextBox.setFocus(true);

		// Stock code must be between 1 and 10 chars that are numbers, letters,
		// or dots.
		if (!uid.matches("^[0-9a-zA-Z\\.]{1,10}$")) {
			Window.alert("'" + uid + "' is not a valid first name.");
			newInfoTextBox.selectAll();
			return;
		}

		if (!lastName.matches("^[0-9a-zA-Z\\.]{1,10}$")) {
			Window.alert("'" + lastName + "' is not a valid last name.");
			lastNameTextBox.selectAll();
			return;
		}

		if (!String.valueOf(age).matches("[0-9]+")) {
			Window.alert("'" + age + "' is not a valid age.");
			ageTextBox.selectAll();
			return;
		}

		if (!gender.matches("^[0-9a-zA-Z\\.]{1,10}$")) {
			Window.alert("'" + gender + "' is not a valid last gender.");
			genderTextBox.selectAll();
			return;
		}

		newInfoTextBox.setText("");

		// // Don't add the stock if it's already in the table.
		// if (userdatas.contains(uid))
		// return;

		addUserData(uid, lastName, age, gender);

	}

	private void addUserData(final String uid, final String lastName, final Integer age, final String gender) {
		userDataService.addUserData(uid, lastName, age, gender, new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(Void ignore) {
				displayUserData(uid);
				displayLastName(lastName);
				displayAge(age);
				displayGender(gender);
			}
		});
	}

	private void displayUserData(final String uid) {
		// Add the uid to the table.
		userdatas.add(uid);
		newInfoTextBox.setText(uid);

	}

	private void displayLastName(final String lastName) {
		// Add the uid to the table.
		lastNames.add(lastName);
		lastNameTextBox.setText(lastName);

	}

	private void displayAge(final Integer age) {
		// Add the uid to the table.
		ages.add(age);
		ageTextBox.setText(String.valueOf(age));

	}

	private void displayGender(final String gender) {
		// Add the uid to the table.
		genders.add(gender);
		genderTextBox.setText(gender);

	}

	private void loadUserDatas() {
		userDataService.getUserDatas(new AsyncCallback<String[]>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(String[] uids) {
				displayUserDatas(uids);
			}
		});
	}

	private void loadLastNames() {
		userDataService.getLastNames(new AsyncCallback<String[]>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(String[] lastNames) {
				displayLastNames(lastNames);
			}
		});
	}

	private void loadAges() {
		userDataService.getAges(new AsyncCallback<Integer[]>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(Integer[] ages) {
				displayAges(ages);
			}
		});
	}

	private void loadGenders() {
		userDataService.getGenders(new AsyncCallback<String[]>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(String[] genders) {
				displayGenders(genders);
			}
		});
	}

	private void displayUserDatas(String[] uids) {
		for (String uid : uids) {
			displayUserData(uid);
		}

	}

	private void displayLastNames(String[] lastNames) {
		for (String lastName : lastNames) {
			displayLastName(lastName);
		}

	}

	private void displayAges(Integer[] ages) {
		for (Integer age : ages) {
			displayAge(age);
		}

	}

	private void displayGenders(String[] genders) {
		for (String gender : genders) {
			displayGender(gender);
		}

	}

	// Debugging purposes, writes to javascript console
	public static native void console(String text)
	/*-{
		console.log(text);
	}-*/;

	/// PO ADDED FROM SCHOOL MANAGER
	public boolean tokenIsASchool(String token) {
		for (SchoolClient school : schoolList) {
			if (SchoolClient.hasEqualCode(token, school)) {
				return true;
			}
		}
		return false;
	}

	public void renderSchoolPage(String token) {
		RootPanel.get("core").clear();
		SchoolClient school = findSchoolByToken(token);
		VerticalPanel panel = new VerticalPanel();
		if (school != null) {
			HTML schoolName = new HTML("school name: " + school.getName());
			panel.add(schoolName);
			HTML schoolAddress = new HTML("school name: " + school.getAddress());
			panel.add(schoolAddress);
			HTML schoolUrl = new HTML(
					"school URL: <a  target=\"_blank\"  href=" + school.getUrl() + " >" + school.getUrl() + "</a>");
			panel.add(schoolUrl);
		} else {
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

		Location l = new Location(1.33, 2.54);

		SchoolClient school1 = new SchoolClient(new Long(3939012), "Point Grey Secondary", "5350 Eest Boulevard",
				"http://www.vsb.bc.ca/schools/point-grey", false, true, false, true, false, false, true, false, true,
				false, new Location(49.2379, -123.1534));
		schoolList.add(school1);
		SchoolClient school2 = new SchoolClient(new Long(3939074), "Maple Grove Elementary", "6199 Cypress St",
				"http://www.vsb.bc.ca/schools/maple-grove", false, true, false, true, false, false, false, false, false,
				false, new Location(49.2294, -123.1515));
		schoolList.add(school2);
		SchoolClient school3 = new SchoolClient(new Long(3939008), "Lord Byng Secondary", "3939 W 16th Av",
				"http://www.vsb.bc.ca/schools/lord-byng", false, true, false, true, false, false, true, false, true,
				true, new Location(49.2596, -123.1925));
		schoolList.add(school3);
		SchoolClient school4 = new SchoolClient(new Long(3939084), "Trafalgar Elementary", "4170 Trafalgar St",
				"http://www.vsb.bc.ca/schools/trafalgar", false, true, false, true, false, false, false, false, false,
				false, new Location(49.2502, -123.1646));
		schoolList.add(school4);
		SchoolClient school5 = new SchoolClient(new Long(3939010), "Templeton Secondary", "727 Templeton Drive",
				"http://www.vsb.bc.ca/schools/templeton", false, true, false, true, false, false, true, false, true,
				false, new Location(49.2784, -123.0607));
		schoolList.add(school5);

		schoolList = new ArrayList<SchoolClient>();

		schoolList.add(school1);
		schoolList.add(school2);
		schoolList.add(school3);
		schoolList.add(school4);
		schoolList.add(school5);

		for (SchoolClient s : schoolList) {
			if (schoolsFlexTable.getRowCount() == schoolList.size() + 1)
				return;
			else {

				int row = schoolsFlexTable.getRowCount();
				schools.add(s.getName());
				Hyperlink hyper = new Hyperlink(s.getName(), "schoos/" + s.getNumber().toString());
				schoolsFlexTable.setWidget(row, 0, hyper);
				schoolsFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
				schoolsFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
				schoolsFlexTable.setText(row, 1, s.getAddress());
				Anchor anchor = new Anchor(s.getUrl(), s.getUrl());
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
				System.out.println("!!!Fetched..." + schools.length + "schools!!!");
				schoolList = new ArrayList<SchoolClient>();
				for (int i = 0; i < schools.length; i++) {
					SchoolClient schoolClient = new SchoolClient(new Long(schools[i][0]), schools[i][1], schools[i][2],
							schools[i][3], parseBoolean(schools[i][4]), parseBoolean(schools[i][5]),
							parseBoolean(schools[i][6]), parseBoolean(schools[i][7]), parseBoolean(schools[i][8]),
							parseBoolean(schools[i][9]), parseBoolean(schools[i][10]), parseBoolean(schools[i][11]),
							parseBoolean(schools[i][12]), parseBoolean(schools[i][13]),
							new Location(Double.parseDouble(schools[i][14]), Double.parseDouble(schools[i][15])));
					schoolList.add(schoolClient);
				}

				// testing
				System.out.println("Schools retrieved Successfully!!");
			}
		});
		return schoolList;
	}

	private List<SchoolClient> createTestSchoolData() {
		// eventually will get the data from mySQLConnector, for now just create
		// one
		List<SchoolClient> schoolList = new ArrayList<SchoolClient>();

		SchoolClient school1 = new SchoolClient(new Long(3939012), "Point Grey Secondary", "5350 Eest Boulevard",
				"http://www.vsb.bc.ca/schools/point-grey", false, true, false, true, false, false, true, false, true,
				false, new Location(49.2379, -123.1534));
		schoolList.add(school1);
		SchoolClient school2 = new SchoolClient(new Long(3939074), "Maple Grove Elementary", "6199 Cypress St",
				"http://www.vsb.bc.ca/schools/maple-grove", false, true, false, true, false, false, false, false, false,
				false, new Location(49.2294, -123.1515));
		schoolList.add(school2);
		SchoolClient school3 = new SchoolClient(new Long(3939008), "Lord Byng Secondary", "3939 W 16th Av",
				"http://www.vsb.bc.ca/schools/lord-byng", false, true, false, true, false, false, true, false, true,
				true, new Location(49.2596, -123.1925));
		schoolList.add(school3);
		SchoolClient school4 = new SchoolClient(new Long(3939084), "Trafalgar Elementary", "4170 Trafalgar St",
				"http://www.vsb.bc.ca/schools/trafalgar", false, true, false, true, false, false, false, false, false,
				false, new Location(49.2502, -123.1646));
		schoolList.add(school4);
		SchoolClient school5 = new SchoolClient(new Long(3939010), "Templeton Secondary", "727 Templeton Drive",
				"http://www.vsb.bc.ca/schools/templeton", false, true, false, true, false, false, true, false, true,
				false, new Location(49.2784, -123.0607));
		schoolList.add(school5);

		return schoolList;
	}

	private SchoolClient findSchoolByToken(String token) {
		for (SchoolClient school : schoolList) {
			if (SchoolClient.hasEqualCode(token, school)) {
				return school;
			}
		}
		return null;
	}

	public List<SchoolClient> getSchoolList() {
		return schoolList;
	}

	private boolean parseBoolean(String s) {
		if (s.toUpperCase().trim() == "TRUE") {
			return true;
		} else {
			return false;
		}
	}

	private void handleError(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(loginInfo.getLogoutUrl());
		}
	}

}
