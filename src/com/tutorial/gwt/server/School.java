package com.tutorial.gwt.server;

import com.tutorial.gwt.client.Location;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class School {
	@Persistent
	private String name;
	
	/*@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private int id;*/
	@PrimaryKey	
	@Persistent
	private Long number;
	
	@Persistent
	private String address;
	
	@Persistent
	private double latitude;
	@Persistent
	private double longitude;
	@Persistent
	private String url;
	@Persistent
	private boolean isPrivate;
	@Persistent
	private boolean hasEngLangLearnerProg;
	@Persistent
	private boolean hasFrenchImmersionProg;
	@Persistent
	private boolean hasAborigSuppServices;
	@Persistent
	private boolean hasContinuingEdProgram;
	@Persistent
	private boolean hasDistributedLearnProg;
	@Persistent
	private boolean hasCareerPrepProg;
	@Persistent
	private boolean hasCoopProg;
	@Persistent
	private boolean hasApprenticeshipProg;
	@Persistent
	private boolean hasCareerTechnicalProg;

	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public boolean isHasEngLangLearnerProg() {
		return hasEngLangLearnerProg;
	}
	public void setHasEngLangLearnerProg(boolean hasEngLangLearnerProg) {
		this.hasEngLangLearnerProg = hasEngLangLearnerProg;
	}
	public boolean isHasFrenchImmersionProg() {
		return hasFrenchImmersionProg;
	}
	public void setHasFrenchImmersionProg(boolean hasFrenchImmersionProg) {
		this.hasFrenchImmersionProg = hasFrenchImmersionProg;
	}
	public boolean isHasAborigSuppServices() {
		return hasAborigSuppServices;
	}
	public void setHasAborigSuppServices(boolean hasAborigSuppServices) {
		this.hasAborigSuppServices = hasAborigSuppServices;
	}
	public boolean isHasContinuingEdProgram() {
		return hasContinuingEdProgram;
	}
	public void setHasContinuingEdProgram(boolean hasContinuingEdProgram) {
		this.hasContinuingEdProgram = hasContinuingEdProgram;
	}
	public boolean isHasDistributedLearnProg() {
		return hasDistributedLearnProg;
	}
	public void setHasDistributedLearnProg(boolean hasDistributedLearnProg) {
		this.hasDistributedLearnProg = hasDistributedLearnProg;
	}
	public boolean isHasCareerPrepProg() {
		return hasCareerPrepProg;
	}
	public void setHasCareerPrepProg(boolean hasCareerPrepProg) {
		this.hasCareerPrepProg = hasCareerPrepProg;
	}
	public boolean isHasCoopProg() {
		return hasCoopProg;
	}
	public void setHasCoopProg(boolean hasCoopProg) {
		this.hasCoopProg = hasCoopProg;
	}
	public boolean isHasApprenticeshipProg() {
		return hasApprenticeshipProg;
	}
	public void setHasApprenticeshipProg(boolean hasApprenticeshipProg) {
		this.hasApprenticeshipProg = hasApprenticeshipProg;
	}
	public boolean isHasCareerTechnicalProg() {
		return hasCareerTechnicalProg;
	}
	public void setHasCareerTechnicalProg(boolean hasCareerTechnicalProg) {
		this.hasCareerTechnicalProg = hasCareerTechnicalProg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public School(Long number, String name, String address,
			String url, boolean isPrivate, boolean hasEngLangLearnerProg,
			boolean hasFrenchImmersionProg, boolean hasAborigSuppServices,
			boolean hasContinuingEdProgram, boolean hasDistributedLearnProg,
			boolean hasCareerPrepProg, boolean hasCoopProg,
			boolean hasApprenticeshipProg, boolean hasCareerTechnicalProg, 
			double latitude, double longitude) {
		super();
		this.name = name;
		this.number = number;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.url = url;
		this.isPrivate = isPrivate;
		this.hasEngLangLearnerProg = hasEngLangLearnerProg;
		this.hasFrenchImmersionProg = hasFrenchImmersionProg;
		this.hasAborigSuppServices = hasAborigSuppServices;
		this.hasContinuingEdProgram = hasContinuingEdProgram;
		this.hasDistributedLearnProg = hasDistributedLearnProg;
		this.hasCareerPrepProg = hasCareerPrepProg;
		this.hasCoopProg = hasCoopProg;
		this.hasApprenticeshipProg = hasApprenticeshipProg;
		this.hasCareerTechnicalProg = hasCareerTechnicalProg;
	}
}

