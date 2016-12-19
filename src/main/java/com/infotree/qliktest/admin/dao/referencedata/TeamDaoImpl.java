package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
import com.infotree.qliktest.admin.entity.referencedata.UserTeamComp;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class TeamDaoImpl extends AbstractQTAdminDao<Team> implements TeamDao {

	private static final Logger LOG = LoggerFactory.getLogger(TeamDaoImpl.class);
	/**
	 * Gets the contacts for a given organisation.
	 * @param organisation - the organisation to find contacts for.
	 * @return a list of users of this system who are contacts for this organisation
	 */
	public List<Team> getTeamList() {
		return super.list();
	}
	
	public List<Team> getTeamsById(String id){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("createdBy", id));
			List<Team> list = findMany(crit);
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
	

	public User getById(String userId) {
		return null;
	}
	
	
	
	public int deleteUsersByTeamId(Integer teamId){
		try {
			UserTeamComp comp = new UserTeamComp();
			comp.setTeamId(teamId);
			UserTeam ut = new UserTeam();
			Session session=getSession();
			session.delete(ut);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	public Team findByName(String teamName) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", teamName));
			List<Team> teamList = findMany(crit);
			if(teamList == null || teamList.size() == 0){
				return null;
			}else{
				return teamList.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> executeSQLQuery(String query) {
		SQLQuery sqlquery=null;
		try {
			Session session=getSession();
			sqlquery = session.createSQLQuery(query);
			sqlquery.addEntity(Team.class);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return sqlquery.list();
	}

	@Override
	public Team findByNameAndIdNot(String name, Integer id) {
		try {
			Criterion lhs = Restrictions.eq("name", name);
			Criterion rhs = Restrictions.not(Restrictions.in("id", new Integer[]{id}));
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			Team team = (Team)crit.uniqueResult();
			if(team != null){
				return team;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateTeam(Team team) {
		try {
			Session session = getSession();
			Team t = getById(team.getId());
			t.setName(team.getName());
			t.setId(team.getId());
			t.setModifiedBy(team.getModifiedBy());
			t.setModifiedDate(team.getModifiedDate());
			session.save(t);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		
		return 1;
	}

	@Override
	public List<Team> getAssignedTeamsForUser(Integer userId, Integer createdBy) {
		try{
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserTeam.class);
			subCrit.add(Restrictions.eq("userTeamComp.userId", userId));
			subCrit.setProjection(Property.forName("userTeamComp.teamId"));
			Criterion lhs = Property.forName("id").in(subCrit);
			Criterion rhs = Property.forName("createdBy").eq(createdBy+"");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<Team> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Team> getAvailableTeamsForUser(Integer userId, Integer createdBy) {
		try{
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserTeam.class);
			subCrit.add(Restrictions.eq("userTeamComp.userId", userId));
			subCrit.setProjection(Property.forName("userTeamComp.teamId"));
			Criterion lhs = Property.forName("id").notIn(subCrit);
			Criterion rhs = Property.forName("createdBy").eq(createdBy+"");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<Team> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
