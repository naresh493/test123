package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.ObjectSpyConfigDao;
import com.infotree.qliktest.admin.entity.configs.ObjectSpy;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class ObjectspyConfigServiceImpl extends AbstractQTAdminService<ObjectSpy>
		implements ObjectspyConfigService {
	private static final Logger LOG = LoggerFactory.getLogger(ObjectspyConfigServiceImpl.class);
	@Autowired
	private ObjectSpyConfigDao objectspyDao;
	
	@Override
	public ObjectSpy save(ObjectSpy t) {
		try {
			return objectspyDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(ObjectSpy t) {
		return null;
	}

	@Override
	public List<ObjectSpy> list() {
		try {
			return objectspyDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<ObjectSpy> getTDao() {
		try {
			return objectspyDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ObjectSpy findByConfigName(String name) {
		try {
			return objectspyDao.findByConfigName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<ObjectSpy> getObjectspyByCreated(String id){
		try {
			return objectspyDao.getObjectspyByCreated(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getObjectspyById(String id){
		try {
			return objectspyDao.getObjectspyById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ObjectSpy getById(Serializable id){
		try {
			return objectspyDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ObjectSpy findByNameAndIdNotIn(String name, Integer id) {
		try {
			return objectspyDao.findByNameAndIdNot(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateObjspyConfiguration(ObjectSpy spy) {
		try {
			return objectspyDao.updateObjectspyConfiguration(spy);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public List<ObjectSpy> findByOrder() {
		return objectspyDao.findByOrder();
	}
}
