package com.tutorial.gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.tutorial.gwt.client.NotLoggedInException;
import com.tutorial.gwt.client.UserDataService;
import com.tutorial.gwt.client.UserInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserDataServiceImpl extends RemoteServiceServlet implements UserDataService {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(UserDataServiceImpl.class.getName());
	private static final PersistenceManagerFactory IPMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional2");

	public void addUserData(String uid, String lastName, Integer age, String gender) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new UserData(getUser(), uid, lastName, age, gender));
		} finally {
			pm.close();
		}
	}

	public void removeUserData(String uid) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(UserData.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			List<UserData> userdatas = (List<UserData>) q.execute(getUser());
			for (UserData userdata : userdatas) {
				if (uid.equals(userdata.getUid())) {
					deleteCount++;
					pm.deletePersistent(userdata);
				}
			}
			if (deleteCount != 1) {
				LOG.log(Level.WARNING, "removeUserData deleted " + deleteCount + " UserDatas");
			}
		} finally {
			pm.close();
		}
	}

	public String[] getUserDatas() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<String> uids = new ArrayList<String>();
		try {
			Query q = pm.newQuery(UserData.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			List<UserData> userdatas = (List<UserData>) q.execute(getUser());
			for (UserData userdata : userdatas) {
				uids.add(userdata.getUid());
			}
		} finally {
			pm.close();
		}
		return (String[]) uids.toArray(new String[0]);
	}

	public String[] getLastNames() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<String> lastNames = new ArrayList<String>();
		try {
			Query q = pm.newQuery(UserData.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			List<UserData> userdatas = (List<UserData>) q.execute(getUser());
			for (UserData userdata : userdatas) {
				lastNames.add(userdata.getLastName());
			}
		} finally {
			pm.close();
		}
		return (String[]) lastNames.toArray(new String[0]);
	}
	
	public Integer[] getAges() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<Integer> ages = new ArrayList<Integer>();
		try {
			Query q = pm.newQuery(UserData.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			List<UserData> userdatas = (List<UserData>) q.execute(getUser());
			for (UserData userdata : userdatas) {
				ages.add(userdata.getAge());
			}
		} finally {
			pm.close();
		}
		return (Integer[]) ages.toArray(new Integer[0]);
	}
	
	public String[] getGenders() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<String> genders = new ArrayList<String>();
		try {
			Query q = pm.newQuery(UserData.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			List<UserData> userdatas = (List<UserData>) q.execute(getUser());
			for (UserData userdata : userdatas) {
				genders.add(userdata.getGender());
			}
		} finally {
			pm.close();
		}
		return (String[]) genders.toArray(new String[0]);
	}

	private void checkLoggedIn() throws NotLoggedInException {
		if (getUser() == null) {
			throw new NotLoggedInException("Not logged in.");
		}
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private PersistenceManager getPersistenceManager() {
		return IPMF.getPersistenceManager();
	}
}
