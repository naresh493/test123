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
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;


@Repository
public class TeamProjectDaoImpl extends AbstractQTAdminDao<TeamProject> implements
		TeamProjectDao {
	private static final Logger LOG = LoggerFactory.getLogger(TeamProjectDaoImpl.class);
	@Override
	public TeamProject getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamProject save(TeamProject t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public String delete(TeamProject t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamProject> list() {
		// TODO Auto-generated method stub
		return super.list();
	}

	@Override
	public List<TeamProject> getProjectsByTeamId(Integer teamId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("teamProjId.teamId", teamId));
			List<TeamProject> list = findMany(crit);
			if(list == null || list.size() == 0){
				return null;
			}else{
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	

	@Override
	public int deleteByTeamId(Integer teamId) {
		try {
			List<TeamProject> list = getProjectsByTeamId(teamId);
			Session session = getSession();
			if(list != null){
				Iterator<TeamProject> iterator = list.iterator();
				while(iterator.hasNext()){
					TeamProject teamProject = iterator.next();
					session.delete(teamProject);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

}
