package com.infotree.qliktest.admin.common.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;


/**
 * Provides a converter from String to OrganisationBrand to be used by the Spring conversion API.
 * The String value must be in the format <orgId>:<brandId> as required by the method OrganisationBrand.fromId(). 
 */
public class ProjectConverter implements Converter<String, Project> {

	@Autowired
	ProjectService projectService;
	
	public Project convert(String source) {
		return projectService.findByName(source);
	}
}