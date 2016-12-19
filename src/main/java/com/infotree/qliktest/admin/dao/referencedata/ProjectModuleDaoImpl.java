/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;

@Repository
public class ProjectModuleDaoImpl extends AbstractQTAdminDao<ProjectModule> implements
		ProjectModuleDao {

	private static final Logger LOG = LoggerFactory.getLogger(ProjectModuleDaoImpl.class);
	@Override
	public ProjectModule getById(Serializable id) {
		// TODO Auto-generated method stub
		return super.getById(id);
	}

	@Override
	public ProjectModule save(ProjectModule t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public String delete(ProjectModule t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectModule> list() {
		// TODO Auto-generated method stub
		return super.list();
	}

	@Override
	public List<ProjectModule> findByProjectId(Integer projId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("projectModuleComp.projectId", projId));
			List<ProjectModule> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getModuleCountByProjectId(Integer projectId) {
		int count=0;
		try {
			Criteria criteria = createBaseCriteria();
			criteria.add(Restrictions.eq("projectModuleComp.projectId", projectId));
			criteria.setProjection(Projections.max("projectModuleComp.moduleId"));
			count = (int)criteria.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return count;
	}

	

	@Override
	public int deleteByProjectId(Integer projectId) {
		try {
			List<ProjectModule> list = findByProjectId(projectId);
			Session session = getSession();
			if(list != null){
				Iterator<ProjectModule> iterator = list.iterator();
				while(iterator.hasNext()){
					ProjectModule pm = iterator.next();
					session.delete(pm);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

}
