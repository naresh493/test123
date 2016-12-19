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
import com.infotree.qliktest.admin.dao.referencedata.RolePermissionsDao;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class RolePermissionsServiceImpl extends AbstractQTAdminService<RolePermissions>
		implements RolePermissionsService {
	
	private static final Logger LOG = LoggerFactory.getLogger(RolePermissionsServiceImpl.class);
	
	@Autowired
	private RolePermissionsDao rolePermissionsDao;
	
	@Override
	public RolePermissions getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolePermissions save(RolePermissions t) {
		// TODO Auto-generated method stub
		try {
			return rolePermissionsDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(RolePermissions t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RolePermissions> list() {
		// TODO Auto-generated method stub
		try {
			return rolePermissionsDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<RolePermissions> getTDao() {
		// TODO Auto-generated method stub
		try {
			return rolePermissionsDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RolePermissions> findByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return rolePermissionsDao.findByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Override
	public Integer getPermissionsCountByRole(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return rolePermissionsDao.getPermissionsCountByRole(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	

	@Override
	public List<RolePermissions> findByRoleIdAndPermissionId(Integer roleId,
			Integer permissionId) {
		// TODO Auto-generated method stub
		try {
			return rolePermissionsDao.findByRoleIdAndPermissionId(roleId, permissionId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return rolePermissionsDao.deleteByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	

	@Override
	public List<RolePermissions> findByPermissionId(Integer permissionId) {
		// TODO Auto-generated method stub
		
		try {
			return rolePermissionsDao.findByPermissionId(permissionId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
