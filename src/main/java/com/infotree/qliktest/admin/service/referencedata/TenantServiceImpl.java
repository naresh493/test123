/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.TenantDao;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class TenantServiceImpl extends AbstractQTAdminService<Tenant> implements TenantService{

	private static final Logger LOG = LoggerFactory.getLogger(TenantServiceImpl.class);
	@Autowired
	private TenantDao tenantDao;

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	public List<Tenant> list() {
		try {
			return tenantDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public QTAdminDao<Tenant> getTDao() {
		try {
			return tenantDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public Tenant findByName(String name) {
		try {
			return tenantDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public void save(List<Tenant> tenantList){
		try {
			tenantDao.save(tenantList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}

	public List<Tenant> getActiveTenants() {
		try {
			return tenantDao.getActiveTenants();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public String delete(Tenant t){
		try {
			return tenantDao.delete(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public int updateTenant(Tenant t) {
		try {
			return tenantDao.updateTenant(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Tenant findByTenantId(Integer id) {
		// TODO Auto-generated method stub
		try {
			return tenantDao.findByTenantId(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Tenant findByNameAndNotId(String name, Integer id) {
		// TODO Auto-generated method stub
		try {
			return tenantDao.findByNameAndNotId(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Tenant findByTenantIdAndCreatedBy(Integer tenantId, Integer createdBy) {
		// TODO Auto-generated method stub
		try {
			return tenantDao.findByTenantIdAndCreatedBy(tenantId, createdBy);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Tenant findByProject(Integer projectId) {
		// TODO Auto-generated method stub
		try {
			return tenantDao.findByProject(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
}
