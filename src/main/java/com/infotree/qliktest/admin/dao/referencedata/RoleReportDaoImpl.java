/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.RoleReport;


@Repository
public class RoleReportDaoImpl  extends AbstractQTAdminDao<RoleReport> implements RoleReportDao {

	private static final Logger LOG = LoggerFactory.getLogger(RoleReportDaoImpl.class);
	@Override
	public List<RoleReport> findByIds(Integer roleId, Integer reportId) {
		try {
			Criterion lhs = Restrictions.eq("roleReportComp.roleId", roleId);
			Criterion rhs = Restrictions.eq("roleReportComp.reportId", reportId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<RoleReport> roleList = findMany(crit);
			if(roleList == null || roleList.size() == 0){
				return null;
			}else{
				return roleList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<RoleReport> getByReportId(Integer reportId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("roleReportComp.reportId", reportId));
			List<RoleReport> roleList = findMany(crit);
			if(roleList == null || roleList.size() == 0){
				return null;
			}else{
				return roleList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RoleReport> getByRoleId(Integer roleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(Role.class);
			subCrit.add(Restrictions.eq("id", roleId));
			subCrit.setProjection(Projections.property("id"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("roleReportComp.roleId").in(subCrit));
			List<RoleReport> roleList = findMany(crit);
			if(roleList == null || roleList.size() == 0){
				return null;
			}else{
				return roleList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public RoleReport save(RoleReport entity) {
		// TODO Auto-generated method stub
		return super.save(entity);
		
		
	}

	@Override
	public int insertNewRoleReportEntity(RoleReport roleReport) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public int deleteByRoleId(Integer roleId) {
		try {
			List<RoleReport> list = getByRoleId(roleId);
			Session session = getSession();
			if(list != null){
				Iterator<RoleReport> iterator = list.iterator();
				while(iterator.hasNext()){
					RoleReport roleReport = iterator.next();
					session.delete(roleReport);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}


}
