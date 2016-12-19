package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Module;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface ModuleDao extends QTAdminDao<Module> {

	Module findByName(String moduleName);
	
	List<Module> getModuleList();
	
	void save(List<Module> moduleList);
	
	int updateModule(Module t);
	
	String delete(Module t);
	
	List<Module> getActiveModules();
	
	List<Module> getModulesByUserId(Integer id);
	
	Module getModuleByNameAndId(String name,Integer id);
	
	List<Module> getOrderBydesc();
	
	List<Module> getModulesByProjId(String projid);

	List<Module> getByModuleId(Integer projectid);

	List<Module> getByNotInModuleId(Integer projectid);
	
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
	
	List<Module> executeQueryPerPage(String query,int pageNumber,int pageLimit);
	
	int executeQueryCount(String query);
}
