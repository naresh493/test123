package com.infotree.qliktest.admin.common.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.service.referencedata.RoleService;


/**
 * Provides a converter from String to OrganisationBrand to be used by the Spring conversion API.
 * The String value must be in the format <orgId>:<brandId> as required by the method OrganisationBrand.fromId(). 
 */
public class RoleConverter implements Converter<String, Role> {

	@Autowired
	RoleService roleService;
	
	public Role convert(String source) {
		return roleService.findByName(source);
	}
}
