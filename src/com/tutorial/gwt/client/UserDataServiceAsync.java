package com.tutorial.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserDataServiceAsync {

	void addUserData(String uid, String lastName, Integer age, String gender, AsyncCallback<Void> async);

	void getUserDatas(AsyncCallback<String[]> async);

	void removeUserData(String uid, AsyncCallback<Void> async);
	
	void getLastNames(AsyncCallback<String[]> async);
	
	void getAges(AsyncCallback<Integer[]> async);
	
	void getGenders(AsyncCallback<String[]> async);

}
