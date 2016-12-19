package com.infotree.qliktest.admin.helperpojo;

public class PasswordPojo {

	private String userName;
	private String currentPassword;
	private String newPassword;
	private String conformNewPassword;
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
	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the conformNewPassword
	 */
	public String getConformNewPassword() {
		return conformNewPassword;
	}
	/**
	 * @param conformNewPassword the conformNewPassword to set
	 */
	public void setConformNewPassword(String conformNewPassword) {
		this.conformNewPassword = conformNewPassword;
	}
}
