package com.tutorial.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tutorial.gwt.server.School;

public interface FileConnectionManagerAsync {
	
	public void parseData(AsyncCallback<List<School>> callback) ;

}
