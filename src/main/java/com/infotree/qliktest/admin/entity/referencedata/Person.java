package com.infotree.qliktest.admin.entity.referencedata;


public class Person {
   
	private String fullName;
	
	private String userName;

	private String firstName;
	
	private String surName;

	private String eMail;

	private String landline;
	
	private String mobile;
	
	private String password;
	
	private String description;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the surName
	 */
	public String getSurName() {
		return surName;
	}

	/**
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the landline
	 */
	public String getLandline() {
		return landline;
	}

	/**
	 * @param landline the landline to set
	 */
	public void setLandline(String landline) {
		this.landline = landline;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("<fullName>"+this.fullName+"</fullName>");
		sb.append("<firstName>"+this.firstName+"</firstName>");
		sb.append("<surName>"+this.surName+"</surName>");
		sb.append("<eMail>"+this.eMail+"</eMail>");
		sb.append("<landline>"+this.landline+"</landline>");
		sb.append("<mobile>"+this.mobile+"</mobile>");
		sb.append("<password>"+this.password+"</password>");
		sb.append("<description>"+this.description+"</description>");
		String result = sb.toString();
		sb = null;
		return result;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
