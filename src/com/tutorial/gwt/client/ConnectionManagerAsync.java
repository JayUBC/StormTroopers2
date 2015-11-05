package com.tutorial.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ConnectionManagerAsync {
	//public void pushSchools(AsyncCallback<Void> async) ;
	public void addSchool(Long number, String name, String address,
			String url, boolean isPrivate, boolean hasEngLangLearnerProg,
			boolean hasFrenchImmersionProg, boolean hasAborigSuppServices,
			boolean hasContinuingEdProgram, boolean hasDistributedLearnProg,
			boolean hasCareerPrepProg, boolean hasCoopProg,
			boolean hasApprenticeshipProg, boolean hasCareerTechnicalProg, 
			double latitude, double longitude, AsyncCallback<Void> async);

	public void getSchools(AsyncCallback <String[][]> async);
	
	public void addSchools(AsyncCallback<Void> async);
	
	public void truncateSchools(AsyncCallback<Void> async);
}
