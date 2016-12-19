package com.infotree.qliktest.admin.security.auth.rule;


public interface AccessControlRule<T> {
	
	//public Boolean invokeRule(T entity, Function function);
	
	public boolean supports(Object entity);

}
