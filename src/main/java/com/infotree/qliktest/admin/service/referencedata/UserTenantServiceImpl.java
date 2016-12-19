/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.service.referencedata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.UserTenantDao;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class UserTenantServiceImpl extends AbstractQTAdminService<UserTenant> implements UserTenantService{
	private static final Logger LOG = LoggerFactory.getLogger(UserTenantServiceImpl.class);

	@Autowired
	private UserTenantDao userTenantDao;

	public UserTenant save(UserTenant t) {
		try {
			return super.save(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public String delete(UserTenant t) {
		try {
			return super.delete(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public int deleteByUserIdAndTenantId(Integer userId, Integer tenantId) {
		try {
			return userTenantDao.deleteByUserIdAndTenantId(userId, tenantId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	protected QTAdminDao<UserTenant> getTDao() {
		try {
			return userTenantDao;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public int deleteByTenantId(Integer tenantId) {
		try {
			return userTenantDao.deleteByTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
			}
	}

	public UserProject save(UserProject t) {
		return null;
	}

	public String delete(UserProject t) {
		return null;
	}

	public int deleteByUserIdAndProjectId(Integer userId, Integer projectId) {
		return 0;
	}

	public int deleteByProjectId(Integer projectId) {
		return 0;
	}

	@Override
	public UserTenant findByUserId(Integer userId) {
		// TODO Auto-generated method stub
		try {
			return userTenantDao.findByUserId(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserTenant findByTenantId(Integer tenantId) {
		// TODO Auto-generated method stub
		try {
			return userTenantDao.findByTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserTenant findByTenantIdAndCreatedBy(Integer tenantId,
			Integer createdBy) {
		// TODO Auto-generated method stub
		try {
			return userTenantDao.findByTenantIdAndCreatedBy(tenantId, createdBy);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
}
