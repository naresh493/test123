package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.LdapConfig;

@Repository
public class LdapConfigDaoImpl extends AbstractQTAdminDao<LdapConfig> implements
		LdapConfigDao {

	@Override
	public LdapConfig getById(Serializable id) {
		return super.getById(id);
	}

	@Override
	public LdapConfig save(LdapConfig t) {
		return super.save(t);
	}

	@Override
	public String delete(LdapConfig t) {
		return super.delete(t);
	}

	@Override
	public List<LdapConfig> list() {
		return super.list();
	}

	@Override
	public LdapConfig findByName(String name) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("name", name));
		LdapConfig config = (LdapConfig)crit.uniqueResult();
		if(config != null) {
			 return config;
		}else {
			return null;
		}
	}

	@Override
	public LdapConfig findByNameAndIdNot(Integer id, String name) {
		Criterion lhs = Restrictions.ne("id", id);
		Criterion rhs = Restrictions.eq("name", name);
		LogicalExpression exp = Restrictions.and(lhs, rhs);
		Criteria crit = createBaseCriteria();
		crit.add(exp);
		LdapConfig config = (LdapConfig)crit.uniqueResult();
		if(config != null) {
			return config;
		} else {
			return null;
		}
	}

	@Override
	public int updateLdapConfig(LdapConfig config) {
		try {
			Session session = getSession();
			LdapConfig ldap = getById(config.getId());
			ldap.setName(config.getName());
			ldap.setServerip(config.getServerip());
			ldap.setPort(config.getPort());
			ldap.setUsername(config.getUsername());
			ldap.setBase(config.getBase());
			ldap.setModifiedBy(config.getModifiedBy());
			ldap.setModifiedDate(config.getModifiedDate());
			session.save(ldap);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<LdapConfig> findByTenantId(Integer tenatId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("tenantId", tenatId));
			List<LdapConfig> list = findMany(crit);
			if(list != null) {
				return list;
			} else {
				return null;
			}
					
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
