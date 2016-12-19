package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;
import com.infotree.qliktest.admin.entity.system.AuditType;


@Document(collection = "auditrecord")
public class AuditLogRecord implements BaseQTAdminEntity,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Field
	private Integer userId;
	
	@Field
	private String userName;
	
	@Field
	private AuditType actionType;
	
	@Field
	private String actionPerformed;
	
	@Field
	private String ipOrigin;
	
	@Field
	private String projectName;
	
	@Field
	private Date actionDate;
	
	@Field
	private String actionData;
	
	@Field
	private String createdBy;
	
	@Field
	private Date createdDate;
	
	@Field
	private String modifiedBy;
	
	@Field
	private Date modifiedDate;
	
	//private String userName;
	
	private Integer projectId;
	
	private Date startDate;
	
	private Date endDate;
	
	private String actionDateandTime;
	
	private String startingDate;
	
	private String endingDate;
	
	public String toString(){
		String msg = "id: "+id+",UserId: "+userId+", UserName : "+userName+",ActionType : "+actionType+",Action Performed : "+actionPerformed
						+"IpOrigin: "+ipOrigin+",projectName: "+projectName+", actionDate: "+actionDate+",Action Data :"+actionData+", Created By :"
						+createdBy+" , CreatedDate: "+createdDate;
		return msg;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the actionType
	 */
	public AuditType getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(AuditType actionType) {
		this.actionType = actionType;
	}
	/**
	 * @return the actionPerformed
	 */
	public String getActionPerformed() {
		return actionPerformed;
	}
	/**
	 * @param actionPerformed the actionPerformed to set
	 */
	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}
	/**
	 * @return the ipOrigin
	 */
	public String getIpOrigin() {
		return ipOrigin;
	}
	/**
	 * @param ipOrigin the ipOrigin to set
	 */
	public void setIpOrigin(String ipOrigin) {
		this.ipOrigin = ipOrigin;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the actionDate
	 */
	public Date getActionDate() {
		return actionDate;
	}
	/**
	 * @param actionDate the actionDate to set
	 */
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	/**
	 * @return the actionData
	 */
	public String getActionData() {
		return actionData;
	}
	/**
	 * @param actionData the actionData to set
	 */
	public void setActionData(String actionData) {
		this.actionData = actionData;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	/**
	 * @return the projectId
	 */
	public Integer getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the actionDateandTime
	 */
	public String getActionDateandTime() {
		return actionDateandTime;
	}
	/**
	 * @param actionDateandTime the actionDateandTime to set
	 */
	public void setActionDateandTime(String actionDateandTime) {
		this.actionDateandTime = actionDateandTime;
	}
	/**
	 * @return the startingDate
	 */
	public String getStartingDate() {
		return startingDate;
	}
	/**
	 * @param startingDate the startingDate to set
	 */
	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}
	/**
	 * @return the endingDate
	 */
	public String getEndingDate() {
		return endingDate;
	}
	/**
	 * @param endingDate the endingDate to set
	 */
	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}

	
	
}
