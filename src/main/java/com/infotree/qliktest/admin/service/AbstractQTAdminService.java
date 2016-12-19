package com.infotree.qliktest.admin.service;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;
import com.infotree.qliktest.admin.entity.accesscontrolled.AccessControlled;

@SuppressWarnings({"unused","unchecked"})
public abstract class AbstractQTAdminService<T extends BaseQTAdminEntity> {

	//private AccessControlHandler accessControlHandler;
	
	protected final Class<T> entityClass;

	
	protected AbstractQTAdminService(){	
		//derive what type T is (for this instance) using reflection
		ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
		this.entityClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
	}
	
	
	/*@Autowired
	public void setAccessControlHandler(AccessControlHandler accessControlHandler) {
		this.accessControlHandler = accessControlHandler;
	}*/
	
	protected abstract QTAdminDao<T> getTDao();
	
	
	public T getById(Serializable id){
		
		T t = getTDao().getById(id);	
		return t;
	}
	
	public T save(T t){

		if (AccessControlled.class.isAssignableFrom(entityClass)){
			String functionPrefix = null;
			if(t.getId() == null){
				functionPrefix = "CREATE";
			}
			else{
				functionPrefix = "EDIT";
			}
			
			/*Function function = FunctionManager.getFunctionForUserActionAndClass(functionPrefix, entityClass);
			if( accessControlHandler.hasPermission(null, t, function) ){
				throw new AccessDeniedException("User not authorised to invoke action");
			}*/
		}
		return getTDao().save(t);
	}
	
	public String delete(T t){
		
		if (AccessControlled.class.isAssignableFrom(entityClass)){
			String functionPrefix = "DELETE";

			/*Function function = FunctionManager.getFunctionForUserActionAndClass(functionPrefix, entityClass);
			if( accessControlHandler.hasPermission(null, t, function) ){
				throw new AccessDeniedException("User not authorised to invoke action");
			}*/
		}

		
		return getTDao().delete(t);
	}
	
	public List<T> list(){
		return getTDao().list();
	}
}
