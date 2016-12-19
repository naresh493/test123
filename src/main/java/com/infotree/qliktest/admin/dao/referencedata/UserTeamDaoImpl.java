package com.infotree.qliktest.admin.dao.referencedata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.asn1.isismtt.x509.Restriction;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
import com.infotree.qliktest.admin.entity.referencedata.UserTeamComp;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@SuppressWarnings({"unused","unchecked"})
@Repository
public class UserTeamDaoImpl extends AbstractQTAdminDao<UserTeam> implements UserTeamDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserTeamDaoImpl.class);
	
	public UserTeam getById(String userId) {
		return super.getById(userId);
	}

	public UserTeam save(UserTeam userTeam) {
		return super.save(userTeam);
	}

	public List<UserTeam> findByUserId(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userTeamComp.userId", userId));
			List<UserTeam> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public UserTeam findByteamId(Integer teamId) {
		return null;
	}
	
	
	public List<User>  getUsersByTeamId(Integer teamId){
		List<User> list=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userTeamComp.teamId", teamId));
			Session session=getSession();
			SQLQuery query = null;
			
			query=session.createSQLQuery("select user_id from user_team where team_id="+teamId);
			list = new ArrayList<User>();
			List<Integer> l = query.list();
			if(l.isEmpty()){
			}else{
				String userid="";
				Iterator<Integer> iterator=l.iterator();
				while(iterator.hasNext()){
					if(userid==""){
						userid=userid+iterator.next();
					}
					else{
					userid=userid+","+iterator.next();
					}
				}
				query=session.createSQLQuery("select * from user where id in("+userid+")");
				query.addEntity(User.class);
				
				list=query.list();
				
			}
			if(list.isEmpty()){
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return list;
		
			
	}
	
	public int deleteMultUsersFromTeam(Integer teamId,String users){
		try {
			String arr[] = users.split(",");
			Session session = getSession();
			for(int i=0;i<arr.length;i++){
				UserTeam list = findByIds(Integer.parseInt(arr[i]), teamId);
				session.delete(list);
			}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	

	public UserTeam findByIds(Integer userId, Integer teamId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("userTeamComp.userId", userId);
			Criterion rhs = Restrictions.eq("userTeamComp.teamId", teamId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			UserTeam ut = findOne(crit);
			if(ut != null){
				return ut;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<UserTeam> getByUserId(Integer userId) {
		return null;
	}

	public List<UserTeam> getByteamId(Integer teamId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userTeamComp.teamId", teamId));
			List<UserTeam> list = findMany(crit);
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

	public List<UserTeam> getByIds(Integer userId, Integer teamId) {
		return null;
	}
	
	public String delete(UserTeam t) {
		return super.delete(t);
	}

	public int deleteByUserId(Integer userId) {
		try {
			Session session = getSession();
			List<UserTeam> list = findByUserId(userId);
			if(list != null){
				Iterator<UserTeam> iterator = list.iterator();
				while(iterator.hasNext()){
					UserTeam ut = iterator.next();
					session.delete(ut);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	
	
	public int getNoOfUsers(Integer teamId)
	{
		int count = 0;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userTeamComp.teamId", teamId));
			crit.setProjection(Projections.max("userTeamComp.userId"));
			count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return count;
		
	}
	

	public int insertNewUserTeamEntity(UserTeam userTeam) {
		try {
			Session session = getSession();
			session.save(userTeam);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	

	@Override
	public int deleteByTeamId(Integer teamId) {
		// TODO Auto-generated method stub
		try {
			Session session = getSession();
			List<UserTeam> list = getByteamId(teamId);
			if(list != null){
				Iterator<UserTeam> iterator = list.iterator();
				while(iterator.hasNext()){
					UserTeam ut = iterator.next();
					session.delete(ut);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	

}
