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
import com.infotree.qliktest.admin.dao.referencedata.RoleModuleDao;
import com.infotree.qliktest.admin.entity.referencedata.RoleModule;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class RoleModuleServiceImpl extends AbstractQTAdminService<RoleModule>
		implements RoleModuleService {
	private static final Logger LOG = LoggerFactory.getLogger(RoleModuleServiceImpl.class);
	
	@Autowired
	private RoleModuleDao roleModuleDao;
	
	@Override
	public RoleModule getById(Serializable id) {
		// TODO Auto-generated method stub
		try {
			return roleModuleDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RoleModule save(RoleModule t) {
		// TODO Auto-generated method stub
		try {
			return roleModuleDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(RoleModule t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleModule> list() {
		// TODO Auto-generated method stub
		try {
			return roleModuleDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<RoleModule> getTDao() {
		// TODO Auto-generated method stub
		try {
			return roleModuleDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RoleModule> findByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return roleModuleDao.findByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public int getRoleCountByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return roleModuleDao.getRoleCountByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	

	@Override
	public int deleteByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return roleModuleDao.deleteByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	

}
