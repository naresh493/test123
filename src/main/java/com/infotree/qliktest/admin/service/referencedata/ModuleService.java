package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;
import java.util.Map;

import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface ModuleService extends QTAdminService<Module>{

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	Module findByName(String name);
	
	void save(List<Module> moduleList);
	
	int updateModule(Module t);
	
	String delete(Module t);

	List<Module> getActiveModules();
	
	Map<Integer, String> getByIds();
	
	List<Module> getModulesByUserId(Integer id);
	
	List<Module> getModuleList();
	
	Module getModuleByNameAndId(String name,Integer id);
	
	List<Module> getOrderBydesc();
	
	List<Module> getModulesByProjId(String projid);

	List<Module> getByModuleId(Integer moduleId);

	List<Module> getByNotInModuleId(Integer moduleId);
	
	int getNumberOfModules(Integer projectId);
	
	List<Module> getByProjectId(Integer projectId);
	
	List<Module> getByRoleId(Integer roleId);
	
	List<Module> executeQuery(String query);
	
	List<Module> findByModuleIdAndPermissionId(Integer moduleId,Integer permissionId);
	
	List<Module> findByPermissionId(Integer permId);
	
	List<Module> findByModuleId(Integer moduleId);
	
	int getModuleCount();
	
	List<Module> getModulesByPageAndCount(int page, int limitsperpage);
	
	List<Module> getByPage(Long page);
	
	List<Module> executeQueryPerPage(String query, int pageNumber, int pageLimit);
	
	int executeQueryCount(String query);
}
