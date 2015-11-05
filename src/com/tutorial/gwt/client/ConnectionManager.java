package com.tutorial.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tutorial.gwt.server.School;


@RemoteServiceRelativePath("school")
public interface ConnectionManager extends RemoteService {
	//public void pushSchools();

	public void addSchool(Long number, String name, String address,
			String url, boolean isPrivate, boolean hasEngLangLearnerProg,
			boolean hasFrenchImmersionProg, boolean hasAborigSuppServices,
			boolean hasContinuingEdProgram, boolean hasDistributedLearnProg,
			boolean hasCareerPrepProg, boolean hasCoopProg,
			boolean hasApprenticeshipProg, boolean hasCareerTechnicalProg, 
			double latitude, double longitude);
	
	public String[][] getSchools();
	
	public void addSchools();
	
	public void truncateSchools();

}
