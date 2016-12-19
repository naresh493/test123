package com.infotree.qliktest.admin.entity.referencedata.enums;


/**
 * Refers to a function within this system that is access controlled.
 * Each entry is commented with the requirement that describes the
 * authorisation rules for that function. 
 */
public enum Function implements EnumEntity {
	
    //Functions for organisation reference data
    //a user can see any organisation related data but can edit only if
    //edit access has been provided(provided other rules are satisfied)
    ;

    
	private final Integer id;
	private final String name;
	private final Class<?> entityClass;


	/**
	 * Constructor. Initialises a global system function that is not related to an entity instance.
	 */
	private Function(int id, String name) {
		this(id, name, null);
	}

	/**
	 * Constructor. Sets all properties to their final values.
	 */
	private Function(int id, String name, Class<?> entityClass) {
		this.id = id;
		this.name = name;
		this.entityClass = entityClass;
	}

	/**
	 * Gets the id that will be stored in the database to reference this instance.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the name/description of this system function.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the class of the entity type this operation is performed on,
	 * or null if the operation is not performed on an existing entity instance.
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}
}

