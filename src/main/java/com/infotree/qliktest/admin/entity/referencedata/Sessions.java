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

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;

@Entity
@Table(name="sessions")
public class Sessions implements BaseQTAdminEntity,Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "userid", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	//@NotNull 
	private Integer userid;

	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCreatedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getCreatedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getModifiedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getModifiedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
