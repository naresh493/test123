package com.infotree.qliktest.admin.service.referencedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.ModuleDao;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class ModuleServiceImpl extends AbstractQTAdminService<Module>  	
									implements ModuleService{

	private static final Logger LOG = LoggerFactory.getLogger(ModuleServiceImpl.class);
	@Autowired
	private ModuleDao moduleDao;

	public Module save(Module t) {
		try {
			return moduleDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public String delete(Module t) {
		try {
			return moduleDao.delete(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Module> getModuleList(){
		try {
			return moduleDao.getModuleList();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public Module findByName(String name) {
		try {
			return moduleDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public void save(List<Module> testingTypeList) {
		try {
			moduleDao.save(testingTypeList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}

	public int updateModule(Module t) {
		try {
			return moduleDao.updateModule(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	protected QTAdminDao<Module> getTDao() {
		return moduleDao;
	}
	
	@Override
	public List<Module> getModulesByUserId(Integer id){
		try {
			return moduleDao.getModulesByUserId(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<Module> getActiveModules() {
		try {
			return moduleDao.getActiveModules();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, String> getByIds(){
		Map<Integer, String> result;
		try {
			result = new HashMap<Integer, String>();
			List<Module> mdlList = moduleDao.getActiveModules();
			if(mdlList != null && mdlList.size()>0){
				for (Module module: mdlList) {
					result.put(module.getId(), module.getName());
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public Module getModuleByNameAndId(String name, Integer id) {
		try {
			return moduleDao.getModuleByNameAndId(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Module> getOrderBydesc() {
		try {
			return moduleDao.getOrderBydesc();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Module> getModulesByProjId(String projid) {
		try {
			return moduleDao.getModulesByProjId(projid);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Module> getByModuleId(Integer projectid) {
		try {
			return moduleDao.getByModuleId(projectid);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Module> getByNotInModuleId(Integer projectid) {
		try {
			return moduleDao.getByNotInModuleId(projectid);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getNumberOfModules(Integer projectId) {
		return moduleDao.getNumberOfModules(projectId);
	}
	
	@Override
	public List<Module> getByProjectId(Integer projectId) {
		return moduleDao.getByProjectId(projectId);
	}
	
	@Override
	public List<Module> getByRoleId(Integer roleId) {
		try {
			return moduleDao.getByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Module> executeQuery(String query) {
		return moduleDao.executeQuery(query);
	}

	@Override
	public List<Module> findByModuleIdAndPermissionId(Integer moduleId,
			Integer permissionId) {
		try{
			return moduleDao.findByModuleIdAndPermissionId(moduleId, permissionId);
		}catch(Exception e){
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Module> findByPermissionId(Integer permId) {
		try{
			return moduleDao.findByPermissionId(permId);
		}catch(Exception e){
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Module> findByModuleId(Integer moduleId) {
		try{
			return moduleDao.findByModuleId(moduleId);
		}catch(Exception e){
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int getModuleCount() {
		try {
			return moduleDao.getModuleCount();
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public List<Module> getModulesByPageAndCount(int page, int limitsperpage) {
		try {
			return moduleDao.getModulesByPageAndCount(page, limitsperpage);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Module> getByPage(Long page) {
		try {
			return moduleDao.getByPage(page);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Module> executeQueryPerPage(String query, int pageNumber,
			int pageLimit) {
		return moduleDao.executeQueryPerPage(query, pageNumber, pageLimit);
	}

	@Override
	public int executeQueryCount(String query) {
		return moduleDao.executeQueryCount(query);
	}

}
