package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.LdapConfigDao;
import com.infotree.qliktest.admin.entity.referencedata.LdapConfig;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class LdapConfigServiceImpl extends AbstractQTAdminService<LdapConfig> implements
		LdapConfigService {

	@Autowired
	private LdapConfigDao ldapConfigDao;
	
	@Override
	public LdapConfig getById(Serializable id) {
		return ldapConfigDao.getById(id);
	}

	@Override
	public LdapConfig save(LdapConfig t) {
		return ldapConfigDao.save(t);
	}

	@Override
	public String delete(LdapConfig t) {
		return ldapConfigDao.delete(t);
	}

	@Override
	public List<LdapConfig> list() {
		return ldapConfigDao.list();
	}

	@Override
	protected QTAdminDao<LdapConfig> getTDao() {
		return null;
	}

	@Override
	public LdapConfig findByName(String name) {
		try {
			return ldapConfigDao.findByName(name);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LdapConfig findByNameAndIdNot(Integer id, String name) {
		try {
			return ldapConfigDao.findByNameAndIdNot(id, name);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateLdapConfig(LdapConfig config) {
		try {
			return ldapConfigDao.updateLdapConfig(config);
		} catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public List<LdapConfig> findByTenantId(Integer tenatId) {
		try {
			return ldapConfigDao.findByTenantId(tenatId);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
