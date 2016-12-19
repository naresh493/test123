package com.infotree.qliktest.admin.common.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.service.referencedata.TeamService;


/**
 * Provides a converter from String to OrganisationBrand to be used by the Spring conversion API.
 * The String value must be in the format <orgId>:<brandId> as required by the method OrganisationBrand.fromId(). 
 */
public class TeamConverter implements Converter<String, Team> {

	@Autowired
	TeamService teamService;
	
	public Team convert(String source) {
		return teamService.findByName(source);
	}
}
