package com.infotree.qliktest.admin.entity.system;

import com.infotree.qliktest.admin.entity.referencedata.enums.EnumEntity;


/**
 * Possible types of event that can be audited, e.g. entity creation of property modification.
 */
public enum AuditType implements EnumEntity {

	CREATE (1, "created"),		//create new entity
	MODIFY (2, "modified"),	//modify a property value of an existing entity 
	REMOVE (3, "removed"),		//delete an existing entity
	ASSIGN (4, "assigned"),    //assigned the users
	VIEW (5, "viewed"),		//viewd the data
	LOGIN (6,"login"),		//login event
	LOGOUT (7,"logout"),	//logout event
	IMPORT (8, "import");	//imported the users
	
	
	private final Integer id;
	private final String name;
	

	/**
	 * Constructor. Sets all properties to their final values.
	 */
	private AuditType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	
	/**
	 * Gets the id that will be stored in the database to reference this instance.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the user-friendly string representation of this enum entry
	 */
	public String getName() {
		return name;
	}
}
