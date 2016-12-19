package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;


@Entity
@Table(name="notifications")
public class Notifications implements BaseQTAdminEntity, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	
	@Column(name = "assignedby", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String assignedBy;
	
	@Column(name = "assignedto", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String assignedTo;
	
	@Column(name = "assigneddata", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String assignedData;
	
	
	@Column(name = "status", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String status;
	
	@Column(name = "created_by", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date createdDate;
	
	@Column(name = "modified_by", length = 50)
	@Basic(fetch = FetchType.EAGER)
	private String modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	@Basic(fetch = FetchType.EAGER)
	private Date modifiedDate;
	
	
	/**
	 * @return the assignedBy
	 */
	public String getAssignedBy() {
		return assignedBy;
	}

	/**
	 * @param assignedBy the assignedBy to set
	 */
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	/**
	 * @return the assignedTo
	 */
	public String getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @return the assignedData
	 */
	public String getAssignedData() {
		return assignedData;
	}

	/**
	 * @param assignedData the assignedData to set
	 */
	public void setAssignedData(String assignedData) {
		this.assignedData = assignedData;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		// TODO Auto-generated method stub
		this.createdBy = createdBy;
	}

	@Override
	public String getCreatedBy() {
		// TODO Auto-generated method stub
		return createdBy;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		// TODO Auto-generated method stub
		this.createdDate = createdDate;
	}

	@Override
	public Date getCreatedDate() {
		// TODO Auto-generated method stub
		return createdDate;
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		// TODO Auto-generated method stub
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String getModifiedBy() {
		// TODO Auto-generated method stub
		return modifiedBy;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		// TODO Auto-generated method stub
		this.modifiedDate = modifiedDate;
	}

	@Override
	public Date getModifiedDate() {
		// TODO Auto-generated method stub
		return modifiedDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	

}
