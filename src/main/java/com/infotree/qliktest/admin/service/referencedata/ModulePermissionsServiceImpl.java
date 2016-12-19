/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.ModulePermissionsDao;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;
@Service
public class ModulePermissionsServiceImpl extends AbstractQTAdminService<ModulePermissions>
		implements ModulePermissionsService {

	private static final Logger LOG = LoggerFactory.getLogger(ModulePermissionsServiceImpl.class);
	@Autowired
	private ModulePermissionsDao modulePermissionsDao;
	
	@Override
	public ModulePermissions getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModulePermissions save(ModulePermissions t) {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(ModulePermissions t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModulePermissions> list() {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<ModulePermissions> getTDao() {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ModulePermissions findByModuleIdAndPermissionId(Integer moduleId,
			Integer permissionId) {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao.findByModuleIdAndPermissionId(moduleId, permissionId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModulePermissions> findByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao.findByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getPermissionsCountByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao.getPermissionsCountByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ModulePermissions> findByPermissionsId(Integer permId) {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao.findByPermissionsId(permId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	

	

	@Override
	public int deleteByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return modulePermissionsDao.deleteByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

}
