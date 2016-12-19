package com.infotree.qliktest.admin.entity.system;

import java.io.Serializable;



/**
 * Stores details about changes to entities or entity properties that must be audited.
 * Instance of this class store information about a single change to an entity or property.
 */
/*@Entity
@Table(name = "AUDIT_RECORD")*/
public class AuditRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;

	@Column(name = "ID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ENTITY_CLASS", length = 250, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String entityClass;
	
	@Column(name = "ENTITY_ID", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String entityId;

	@Column(name = "EVENT_TYPE")
	@Basic(fetch = FetchType.EAGER)
	private Integer eventType;
	
	@Column(name = "PROPERTY_NAME", length = 50, nullable = true)
	@Basic(fetch = FetchType.EAGER)
	private String propertyName;
	
	@Column(name = "USER_ID", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String userId;
	
	
	@Column(name = "IPORIGIN",length = 50,nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String iporigin;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EVENT_TIMESTAMP")
	@Basic(fetch = FetchType.EAGER)
	private Date eventTimestamp;


	*//**
	 * @return the id
	 *//*
	public Integer getId() {
		return id;
	}


	*//**
	 * @param id the id to set
	 *//*
	public void setId(Integer id) {
		this.id = id;
	}


	*//**
	 * @return the entityClass
	 *//*
	public String getEntityClass() {
		return entityClass;
	}


	*//**
	 * @param entityClass the entityClass to set
	 *//*
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}


	*//**
	 * @return the entityId
	 *//*
	public String getEntityId() {
		return entityId;
	}


	*//**
	 * @param entityId the entityId to set
	 *//*
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


	*//**
	 * @return the eventType
	 *//*
	public Integer getEventType() {
		return eventType;
	}


	*//**
	 * @param eventType the eventType to set
	 *//*
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}


	*//**
	 * @return the propertyName
	 *//*
	public String getPropertyName() {
		return propertyName;
	}


	*//**
	 * @param propertyName the propertyName to set
	 *//*
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}


	*//**
	 * @return the userId
	 *//*
	public String getUserId() {
		return userId;
	}


	*//**
	 * @param userId the userId to set
	 *//*
	public void setUserId(String userId) {
		this.userId = userId;
	}


	*//**
	 * @return the iporigin
	 *//*
	public String getIporigin() {
		return iporigin;
	}


	*//**
	 * @param iporigin the iporigin to set
	 *//*
	public void setIporigin(String iporigin) {
		this.iporigin = iporigin;
	}


	*//**
	 * @return the eventTimestamp
	 *//*
	public Date getEventTimestamp() {
		return eventTimestamp;
	}


	*//**
	 * @param eventTimestamp the eventTimestamp to set
	 *//*
	public void setEventTimestamp(Date eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}
	
	
	*/
}
