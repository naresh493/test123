package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.RoleDao;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class RoleServiceImpl extends AbstractQTAdminService<Role> implements RoleService{

	private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	private RoleDao roleDao;

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	public List<Role> list() {
		try {
			return roleDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public QTAdminDao<Role> getTDao() {
		try {
			return roleDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public Role findByName(String name) {
		try {
			return roleDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	public Role getById(String id){
		try {
			return roleDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public String getNameById(Integer id){
		try {
			return roleDao.getNameById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Role findByNameAndIdNot(String name, Integer id) {
		try {
			return roleDao.findByNameAndIdNot(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateRole(Role role) {
		try {
			return roleDao.updateRole(role);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Role> searchByPattern(String name) {
		try {
			return roleDao.searchByPattern(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Role> getRolesByModuleId(Integer moduleId) {
		try {
			return roleDao.getRolesByModuleId( moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Role> getRolesByModuleId1(Integer moduleId) {
		try {
			return roleDao.getRolesByModuleId1( moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Role> getByModuleId(Integer moduleId) {
		try {
			return roleDao.getByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Role> getByModuleNotInId(Integer moduleId) {
		try {
			return roleDao.getByModuleNotInId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Role> findByPermId(Integer permissionId) {
		try {
			return roleDao.findByPermId(permissionId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Role> getByRoleIdAndPermId(Integer roleId, Integer permId) {
		try {
			return roleDao.getByRoleIdAndPermId(roleId, permId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Role> findByTeamId(Integer teamId) {
		try {
			return roleDao.findByTeamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Role> getRolesNotAssignedToTeam(Integer teamId) {
		try {
			return roleDao.getRolesNotAssignedToTeam(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Role> getRolesByUser(Integer userId) {
		try {
			return roleDao.getRolesByUser(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Role> getRolesNotAssignedToUser(Integer userId) {
		try {
			return roleDao.getRolesNotAssignedToUser(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Role> findByOrder() {
		return roleDao.findByOrder();
	}
}
