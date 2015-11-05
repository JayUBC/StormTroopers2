package com.tutorial.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tutorial.gwt.server.School;

@RemoteServiceRelativePath("file")
public interface FileConnectionManager extends RemoteService {
	
	public List<School> parseData() ;
	

}
