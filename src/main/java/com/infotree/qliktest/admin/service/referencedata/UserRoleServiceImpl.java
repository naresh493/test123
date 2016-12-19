package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.UserRoleDao;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class UserRoleServiceImpl extends AbstractQTAdminService<UserRole> implements UserRoleService{

	private static final Logger LOG = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	public List<UserRole> list() {
		try {
			return userRoleDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public QTAdminDao<UserRole> getTDao() {
		try {
			return userRoleDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public UserRole findByUserId(Integer userId) {
		try {
			return userRoleDao.findByUserId(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public UserRole findByRoleId(Integer roleId) {
		return null;
	}

	public List<UserRole> findByIds(Integer userId, Integer roleId) {
		try {
			return userRoleDao.findByIds(userId, roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<UserRole> getByUserId(Integer userId) {
		try {
			return userRoleDao.getByUserId(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<UserRole> getByRoleId(Integer roleId) {
		return null;
	}

	public List<UserRole> getByIds(Integer userId, Integer roleId) {
		return null;
	}

	public int deleteByUserId(Integer userId) {
		try {
			return userRoleDao.deleteByUserId(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	public int insertNewUserRoleEntity(UserRole userRole) {
		return 0;
	}

	@Override
	public List<UserRole> getAllRecords() {
		// TODO Auto-generated method stub
		try {
			return userRoleDao.getAllRecords();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateByUserId(UserRole userRole) {
		// TODO Auto-generated method stub
		try {
			return userRoleDao.updateByUserId(userRole);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	

	@Override
	public UserRole getByUserAndRoleId(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return userRoleDao.getByUserAndRoleId(userId, roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteByUserAndRole(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return userRoleDao.deleteByUserAndRole(userId, roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

}
