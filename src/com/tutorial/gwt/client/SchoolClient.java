package com.tutorial.gwt.client;

public class SchoolClient {
	private Long number;
	private String name;
	private String address;
	private String url;
	private boolean isPrivate;
	private boolean hasEngLangLearnerProg;
	private boolean hasFrenchImmersionProg;
	private boolean hasAborigSuppServices;
	private boolean hasContinuingEdProgram;
	private boolean hasDistributedLearnProg;
	private boolean hasCareerPrepProg;
	private boolean hasCoopProg;
	private boolean hasApprenticeshipProg;
	private boolean hasCareerTechnicalProg;
	private Location location;
	
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
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public SchoolClient(){
	}
	public SchoolClient(Long number, String name, String address,
			String url, boolean isPrivate, boolean hasEngLangLearnerProg,
			boolean hasFrenchImmersionProg, boolean hasAborigSuppServices,
			boolean hasContinuingEdProgram, boolean hasDistributedLearnProg,
			boolean hasCareerPrepProg, boolean hasCoopProg,
			boolean hasApprenticeshipProg, boolean hasCareerTechnicalProg, Location location) {
		super();
		this.name = name;
		this.number = number;
		this.address = address;
		this.location = location;
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
	
	public static boolean hasEqualCode(String token, SchoolClient school){
		return String.valueOf(school.getNumber()).equals(extractCodeFromToken(token));
	}

	private static String extractCodeFromToken(String token) {
		return token.substring(token.indexOf("/") + 1);
	}
}
