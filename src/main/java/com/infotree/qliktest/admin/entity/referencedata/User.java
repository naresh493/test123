package com.infotree.qliktest.admin.entity.referencedata;	//NOPMD

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.springframework.security.core.context.SecurityContextHolder;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;
import com.infotree.qliktest.admin.security.authentication.UserDetailsImpl;


/**
 */
@Entity
@Table(name = "user")
public class User implements BaseQTAdminEntity, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	
	@Column(name = "user_name", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String userName;

	@Column(name = "first_name", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String firstName;

	@Column(name = "sur_name", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String surName; 

	@Column(name = "email_address", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String emailAddress;
	

	@Column(name = "landline", nullable = true)
	@Basic(fetch = FetchType.EAGER)
	//@Max(50)
	private String landline;
	

	@Column(name = "mobile", nullable = true)
	@Basic(fetch = FetchType.EAGER)
	//@Max(50)
	private String mobile;

	@Column(name = "password", length = 15, nullable = true)
	@Basic(fetch = FetchType.EAGER)
	private String password;
	
	@Column(name = "is_disabled", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Byte disabled;

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
	private Date modifiedDate;

	@Column(name = "is_password_change_required")
	@Basic(fetch = FetchType.EAGER)
	private Byte isPasswordChangeRequired;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "password_change_date", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date passwordChangedDate;
	
	
	@Column(name = "imported_from_ldap")
	@Basic(fetch = FetchType.EAGER)
	private Byte importedFromLdap;
	
	@Column(name = "attempts")
	@Basic(fetch = FetchType.EAGER)
	private Integer attempts;
	
	@Column(name = "nda")
	@Basic(fetch = FetchType.EAGER)
	private Byte nda;
	
	@Column(name = "domain", length = 100)
	@Basic(fetch = FetchType.EAGER)
	private String domain;
	
	@Column(name = "experience", length = 10)
	@Basic(fetch = FetchType.EAGER)
	private String experience;
	
	@Column(name = "no_of_projects_worked")
	@Basic(fetch = FetchType.EAGER)
	private Integer noOfProjectsWorked;
	
	
	@Transient
	private String conformPassword;
	
	@Transient
	private Integer roleId;
	
	@Transient
	private Integer projectId;
	
	@Transient 
	private Integer teamId;
	
	@Transient
	private Integer tenantId;
	
	@Transient
	private String tenantName;
	
	@Transient
	private Integer projectCount;
	
	@Transient
	private String projectName;
	
	@Transient
	private String name;
	
	@Transient
	private String companyName;
	
	@Transient
	private String displayName;
	
	@Transient
	private Integer instances;
	
	@Transient
	private List<Integer> teamsList;
	
	@Transient
	private List<Integer> projectsList;
	
	@Transient
	private List<Integer> rolesList;
	
	@Transient
	private List<Integer> roles;
	
	@Transient
	private List<Integer> projects;
	
	@Transient
	private List<Integer> teams;
	
	@Transient
	private String createdName;
	
	@Transient
	private Date modificationDate;
	
	
	/**
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName;
	}

	/**
	 * @param tenantName the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	/**
	 * @return the conformPassword
	 */
	public String getConformPassword() {
		return conformPassword;
	}

	/**
	 * @param conformPassword the conformPassword to set
	 */
	public void setConformPassword(String conformPassword) {
		this.conformPassword = conformPassword;
	}

	

	/**
	 */
	public User() {
	}
	
   public static User getLoginUser(){
		return ((UserDetailsImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUser();
    }
   
	/**
	 */
	public void setDisabled(Byte disabled) {
		this.disabled = disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = (byte)(disabled ? 1 : 0);
	}

	/**
	 */
	public byte getDisabled() {
		return this.disabled;
	}
	
	public boolean isDisabled() {
		return (disabled != null && disabled > 0);
	}

		/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

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
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the teamId
	 */
	public Integer getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
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
	 * @return the isPasswordChangeRequired
	 */
	public Byte getIsPasswordChangeRequired() {
		return isPasswordChangeRequired;
	}

	/**
	 * @param isPasswordChangeRequired the isPasswordChangeRequired to set
	 */
	public void setIsPasswordChangeRequired(Byte isPasswordChangeRequired) {
		this.isPasswordChangeRequired = isPasswordChangeRequired;
	}
	
	/**
	 * @return the importedFromLdap
	 */
	public Byte getImportedFromLdap() {
		return importedFromLdap;
	}

	/**
	 * @param importedFromLdap the importedFromLdap to set
	 */
	public void setImportedFromLdap(Byte importedFromLdap) {
		this.importedFromLdap = importedFromLdap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((id == null) ? 0 : id.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (!(obj instanceof User)) {
			return false;
		}
		User equalCheck = (User) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null)) {
			return false;
		} else if (id != null && !id.equals(equalCheck.id)) {
			return false;
		}
		return true;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("<id>"+this.id+"<id>");
		sb.append("<first_name>"+this.firstName+"<first_name>");
		sb.append("<sur_name>"+this.surName+"<sur_name>");
		sb.append("<email_address>"+this.emailAddress+"<email_address>");
		sb.append("<landline>"+this.landline+"<landline>");
		sb.append("<mobile>"+this.mobile+"<mobile>");
		sb.append("<password>"+this.password+"<password>");
		sb.append("<is_disabled>"+this.isDisabled()+"<is_disabled>");
		sb.append("<is_password_change_required>"+this.isPasswordChangeRequired+"<is_password_change_required>");
		sb.append("<created_by>"+this.createdBy+"<created_by>");
		sb.append("<created_date>"+this.createdDate+"<created_date>");
		sb.append("<modified_by>"+this.modifiedBy+"<modified_by>");
		sb.append("<modified_date>"+this.modifiedDate+"<modified_date>");
		
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

	/**
	 * @return the tenantId
	 */
	public Integer getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the instances
	 */
	public Integer getInstances() {
		return instances;
	}

	/**
	 * @param instances the instances to set
	 */
	public void setInstances(Integer instances) {
		this.instances = instances;
	}

	/**
	 * @return the projectCount
	 */
	public Integer getProjectCount() {
		return projectCount;
	}

	/**
	 * @param projectCount the projectCount to set
	 */
	public void setProjectCount(Integer projectCount) {
		this.projectCount = projectCount;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the teamsList
	 */
	public List<Integer> getTeamsList() {
		return teamsList;
	}

	/**
	 * @param teamsList the teamsList to set
	 */
	public void setTeamsList(List<Integer> teamsList) {
		this.teamsList = teamsList;
	}

	/**
	 * @return the projectsList
	 */
	public List<Integer> getProjectsList() {
		return projectsList;
	}

	/**
	 * @param projectsList the projectsList to set
	 */
	public void setProjectsList(List<Integer> projectsList) {
		this.projectsList = projectsList;
	}

	/**
	 * @return the rolesList
	 */
	public List<Integer> getRolesList() {
		return rolesList;
	}

	/**
	 * @param rolesList the rolesList to set
	 */
	public void setRolesList(List<Integer> rolesList) {
		this.rolesList = rolesList;
	}

	/**
	 * @return the createdName
	 */
	public String getCreatedName() {
		return createdName;
	}

	/**
	 * @param createdName the createdName to set
	 */
	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	/**
	 * @return the modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * @param modificationDate the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * @return the passwordChangedDate
	 */
	public Date getPasswordChangedDate() {
		return passwordChangedDate;
	}

	/**
	 * @param passwordChangedDate the passwordChangedDate to set
	 */
	public void setPasswordChangedDate(Date passwordChangedDate) {
		this.passwordChangedDate = passwordChangedDate;
	}

	/**
	 * @return the attempts
	 */
	public Integer getAttempts() {
		return attempts;
	}

	/**
	 * @param attempts the attempts to set
	 */
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	/**
	 * @return the roles
	 */
	public List<Integer> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	/**
	 * @return the projects
	 */
	public List<Integer> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Integer> projects) {
		this.projects = projects;
	}

	/**
	 * @return the teams
	 */
	public List<Integer> getTeams() {
		return teams;
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<Integer> teams) {
		this.teams = teams;
	}

	/**
	 * @return the nda
	 */
	public Byte getNda() {
		return nda;
	}

	/**
	 * @param nda the nda to set
	 */
	public void setNda(Byte nda) {
		this.nda = nda;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the experience
	 */
	public String getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(String experience) {
		this.experience = experience;
	}

	/**
	 * @return the noOfProjectsWorked
	 */
	public Integer getNoOfProjectsWorked() {
		return noOfProjectsWorked;
	}

	/**
	 * @param noOfProjectsWorked the noOfProjectsWorked to set
	 */
	public void setNoOfProjectsWorked(Integer noOfProjectsWorked) {
		this.noOfProjectsWorked = noOfProjectsWorked;
	}

	

}
