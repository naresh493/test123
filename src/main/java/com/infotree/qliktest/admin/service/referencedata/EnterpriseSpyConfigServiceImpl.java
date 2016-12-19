package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.EnterpriseSpyConfigDao;
import com.infotree.qliktest.admin.entity.configs.EnterpriseSpy;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class EnterpriseSpyConfigServiceImpl extends AbstractQTAdminService<EnterpriseSpy>
		implements EnterpriseSpyConfigService {

	private static final Logger LOG = LoggerFactory.getLogger(EnterpriseSpyConfigServiceImpl.class);
	@Autowired
	private EnterpriseSpyConfigDao entpspyDao;
	
	@Override
	public EnterpriseSpy save(EnterpriseSpy t) {
		return entpspyDao.save(t);
	}

	@Override
	public String delete(EnterpriseSpy t) {
		return null;
	}

	@Override
	public List<EnterpriseSpy> list() {
		try {
			return entpspyDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<EnterpriseSpy> getTDao() {
		// TODO Auto-generated method stub
		try {
			return entpspyDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public EnterpriseSpy findByConfigName(String name) {
		// TODO Auto-generated method stub
		try {
			return entpspyDao.findByConfigName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<EnterpriseSpy> getEnterpriseSpyByCreated(String id){
		try {
			return entpspyDao.getEnterpriseSpyByCreated(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getEnterpriseSpyById(String id){
		try {
			return entpspyDao.getEnterpriseSpyById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public EnterpriseSpy getById(Serializable id){
		try {
			return entpspyDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public EnterpriseSpy findByNameAndIdNotIn(String name, Integer id) {
		// TODO Auto-generated method stub
		try {
			return entpspyDao.findByNameAndIdNot(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateEnterpriseSpyConfiguration(EnterpriseSpy spy) {
		try {
			return entpspyDao.updateEnterpriseSpyConfiguration(spy);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<EnterpriseSpy> findByConfigType(String type) {
		try {
			return entpspyDao.findByConfigType(type);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<EnterpriseSpy> findByOrder() {
		return entpspyDao.findByOrder();
	}
}
