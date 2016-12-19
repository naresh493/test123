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
import com.infotree.qliktest.admin.entity.referencedata.TeamRole;


@Repository
public class TeamRoleDaoImpl extends AbstractQTAdminDao<TeamRole> implements
		TeamRoleDao {

	private static final Logger LOG = LoggerFactory.getLogger(TeamRoleDaoImpl.class);
	@Override
	public TeamRole getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamRole save(TeamRole t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public String delete(TeamRole t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamRole> list() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int deleteByTeamId(Integer teamId) {
		try {
			Session session = getSession();
			List<TeamRole> list = findByTeamId(teamId);
			if(list != null){
				Iterator<TeamRole> iterator = list.iterator();
				while(iterator.hasNext()){
					TeamRole teamRole = iterator.next();
					session.delete(teamRole);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List<TeamRole> findByTeamId(Integer teamId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("teamRoleComp.teamId", teamId));
			List<TeamRole> list = findMany(crit);
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

	
}
