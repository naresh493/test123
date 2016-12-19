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
import com.infotree.qliktest.admin.dao.referencedata.ModuleConstraintsDao;
import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraints;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class ModuleConstraintsServiceImpl extends
		AbstractQTAdminService<ModuleConstraints> implements
		ModuleConstraintsService {
	private static final Logger LOG = LoggerFactory.getLogger(ModuleConstraintsServiceImpl.class);
	@Autowired
	private ModuleConstraintsDao moduleConstraintsDao;
	
	@Override
	public ModuleConstraints getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModuleConstraints save(ModuleConstraints t) {
		// TODO Auto-generated method stub
		try {
			return moduleConstraintsDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(ModuleConstraints t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModuleConstraints> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected QTAdminDao<ModuleConstraints> getTDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCountOfPackages(Integer constraintId) {
		// TODO Auto-generated method stub
		try {
			return moduleConstraintsDao.getCountOfPackages(constraintId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ModuleConstraints> findByConstraintId(Integer constraintId) {
		// TODO Auto-generated method stub
		try {
			return moduleConstraintsDao.findByConstraintId(constraintId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getCountOfConstraints(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return moduleConstraintsDao.getCountOfConstraints(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ModuleConstraints> findByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return moduleConstraintsDao.findByModuleId(moduleId);
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
			return moduleConstraintsDao.deleteByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
		
	}

}
