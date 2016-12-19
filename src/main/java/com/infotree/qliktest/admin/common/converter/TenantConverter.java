package com.infotree.qliktest.admin.common.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.service.referencedata.TenantService;


/**
 * Provides a converter from String to OrganisationBrand to be used by the Spring conversion API.
 * The String value must be in the format <orgId>:<brandId> as required by the method OrganisationBrand.fromId(). 
 */
public class TenantConverter implements Converter<String, Tenant> {

	@Autowired
	TenantService tenantService;
	
	public Tenant convert(String source) {
		return tenantService.findByName(source);
	}
}