package com.tutorial.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userdata")
public interface UserDataService extends RemoteService {
	public void addUserData(String uid, String lastName, Integer age, String gender) throws NotLoggedInException;

	public void removeUserData(String uid) throws NotLoggedInException;

	public String[] getUserDatas() throws NotLoggedInException;
	
	public String[] getLastNames() throws NotLoggedInException;
	
	public Integer[] getAges() throws NotLoggedInException;
	
	public String[] getGenders() throws NotLoggedInException;
}
