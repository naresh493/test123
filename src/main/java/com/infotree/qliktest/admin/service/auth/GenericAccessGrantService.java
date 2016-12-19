/*package com.infotree.qliktest.admin.service.auth;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.enums.Function;
import com.infotree.qliktest.admin.entity.security.GenericAccessGrant;
import com.infotree.qliktest.admin.service.QTAdminService;

*//**
 * 
 * @author FOX0SHN
 * This class facilitates granting access to other users.
 * However this class needs to be tightly controlled so that a user cannot grant access
 * for something he himselves doesn't have access
 *
 *//*
public interface GenericAccessGrantService extends QTAdminService<GenericAccessGrant>{

	public boolean isUserGrantedAccessToFunctionOnEntity(User user, BaseQTAdminEntity entity, Function function);
	
	public boolean isUserGrantedAccessToFunction(User user, Function function);
	
	public boolean doesEntitySupportAccessGrant (String className);
	
	public boolean doesFunctionBelongToAnEntityWhichSupportsAccessGrant(Function function);
	
	//other search methods come here
	
}
*/