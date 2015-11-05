package com.tutorial.gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tutorial.gwt.client.ConnectionManager;
import com.tutorial.gwt.client.FileConnectionManager;
import com.tutorial.gwt.client.FileConnectionManagerAsync;
import com.tutorial.gwt.client.SchoolClient;

public class ConnectionManagerImpl extends RemoteServiceServlet implements
		ConnectionManager {
	// JDBC driver name and database URL
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	public void addSchools() {
		truncateSchools();
		FileConnectionManagerImpl fcm = new FileConnectionManagerImpl();
		List<School> schools = fcm.parseData();

		for (School s : schools) {

			addSchool(s.getNumber(), s.getName(), s.getAddress(), s.getUrl(),
					s.isPrivate(), s.isHasEngLangLearnerProg(),
					s.isHasFrenchImmersionProg(), s.isHasAborigSuppServices(),
					s.isHasContinuingEdProgram(),
					s.isHasDistributedLearnProg(), s.isHasCareerPrepProg(),
					s.isHasCoopProg(), s.isHasApprenticeshipProg(),
					s.isHasCareerTechnicalProg(), s.getLatitude(),
					s.getLongitude());
		}

	}

	public void addSchool(Long number, String name, String address, String url,
			boolean isPrivate, boolean hasEngLangLearnerProg,
			boolean hasFrenchImmersionProg, boolean hasAborigSuppServices,
			boolean hasContinuingEdProgram, boolean hasDistributedLearnProg,
			boolean hasCareerPrepProg, boolean hasCoopProg,
			boolean hasApprenticeshipProg, boolean hasCareerTechnicalProg,
			double latitude, double longitude) {

		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new School(number, name, address, url, isPrivate,
					hasEngLangLearnerProg, hasFrenchImmersionProg,
					hasAborigSuppServices, hasContinuingEdProgram,
					hasDistributedLearnProg, hasCareerPrepProg, hasCoopProg,
					hasApprenticeshipProg, hasCareerTechnicalProg, latitude,
					longitude));
		} finally {
			pm.close();
		}
	}

	public String[][] getSchools() {
		PersistenceManager pm = getPersistenceManager();
		String[][] returnSchools;
		try {
			Query q = pm.newQuery(School.class); // query grabbing a stock for
													// each user
			// q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("name"); // orders by the date
			List<School> schools = (List<School>) q.execute(getUser());
			returnSchools = new String[schools.size()][16];
			for (int i = 0; i < schools.size(); i++) {
				School school = schools.get(i);
				returnSchools[i][0] = school.getNumber().toString();
				returnSchools[i][1] = school.getName();
				returnSchools[i][2] = school.getAddress();
				returnSchools[i][3] = school.getUrl();
				returnSchools[i][4] = boolToString(school.isPrivate());
				returnSchools[i][5] = boolToString(school
						.isHasEngLangLearnerProg());
				returnSchools[i][6] = boolToString(school
						.isHasFrenchImmersionProg());
				returnSchools[i][7] = boolToString(school
						.isHasAborigSuppServices());
				returnSchools[i][8] = boolToString(school
						.isHasContinuingEdProgram());
				returnSchools[i][9] = boolToString(school
						.isHasDistributedLearnProg());
				returnSchools[i][10] = boolToString(school
						.isHasCareerPrepProg());
				returnSchools[i][11] = boolToString(school.isHasCoopProg());
				returnSchools[i][12] = boolToString(school
						.isHasApprenticeshipProg());
				returnSchools[i][13] = boolToString(school
						.isHasCareerTechnicalProg());
				returnSchools[i][14] = "" + school.getLatitude();
				returnSchools[i][15] = "" + school.getLongitude();
				// symbols.add(school.getSymbol());
			}
		} finally {
			pm.close();
		}
		return returnSchools;
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private String boolToString(Boolean b) {
		if (b) {
			return "true";
		} else {
			return "false";
		}
	}

	public void truncateSchools() {
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(School.class);
			// q.declareParameters("com.google.appengine.api.users.User u");
			List<School> schools = (List<School>) q.execute(getUser());

			System.out.println("Retrieved " + schools.size()
					+ " schools to be deleted");
			for (School school : schools) {
				deleteCount++;
				pm.deletePersistent(school);
			}
			System.out.println("Deleted " + deleteCount + " number of schools");
		} finally {
			pm.close();
		}
	}
}
