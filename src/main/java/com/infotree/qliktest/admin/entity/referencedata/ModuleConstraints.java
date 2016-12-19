package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;

@Entity
@Table(name="module_constraints")
public class ModuleConstraints implements BaseQTAdminEntity, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ModuleConstraintComp moduleConstraintComp;
	
	@Column(name = "value", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer value;
	
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

	
	@Transient
	private String constraintId;
	
	@Transient
	private String constValue;
	
	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
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

	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the moduleConstraintComp
	 */
	public ModuleConstraintComp getModuleConstraintComp() {
		return moduleConstraintComp;
	}

	/**
	 * @param moduleConstraintComp the moduleConstraintComp to set
	 */
	public void setModuleConstraintComp(ModuleConstraintComp moduleConstraintComp) {
		this.moduleConstraintComp = moduleConstraintComp;
	}

	/**
	 * @return the constraintId
	 */
	public String getConstraintId() {
		return constraintId;
	}

	/**
	 * @param constraintId the constraintId to set
	 */
	public void setConstraintId(String constraintId) {
		this.constraintId = constraintId;
	}

	/**
	 * @return the constValue
	 */
	public String getConstValue() {
		return constValue;
	}

	/**
	 * @param constValue the constValue to set
	 */
	public void setConstValue(String constValue) {
		this.constValue = constValue;
	}

}
