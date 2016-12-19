package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

public class ModuleConstraintComp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="module_id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	private Integer moduleId;
	
	

	@Column(name = "constraint_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private Integer constraintId;



	/**
	 * @return the moduleId
	 */
	public Integer getModuleId() {
		return moduleId;
	}



	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}



	/**
	 * @return the constraintId
	 */
	public Integer getConstraintId() {
		return constraintId;
	}



	/**
	 * @param constraintId the constraintId to set
	 */
	public void setConstraintId(Integer constraintId) {
		this.constraintId = constraintId;
	}
	
}
