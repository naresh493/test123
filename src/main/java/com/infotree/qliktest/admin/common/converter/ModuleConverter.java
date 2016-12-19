package com.infotree.qliktest.admin.common.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;


/**
 * Provides a converter from String to OrganisationBrand to be used by the Spring conversion API.
 * The String value must be in the format <orgId>:<brandId> as required by the method OrganisationBrand.fromId(). 
 */
public class ModuleConverter implements Converter<String, Module> {

	@Autowired
	ModuleService moduleService;
	
	public Module convert(String source) {
		return moduleService.findByName(source);
	}
}