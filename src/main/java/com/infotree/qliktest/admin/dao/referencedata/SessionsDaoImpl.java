/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

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
import com.infotree.qliktest.admin.entity.referencedata.Sessions;
@Repository
public class SessionsDaoImpl extends AbstractQTAdminDao<Sessions> implements SessionsDao {

	private static final Logger LOG = LoggerFactory.getLogger(SessionsDaoImpl.class);
	@Override
	public Sessions insertUser(Sessions sessions) {
		try {
			Session session = getSession();
			session.save(sessions);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return new Sessions();
	}

	@Override
	public Sessions deleteUser(Integer userId) {
		try {
			Session session = getSession();
			List<Sessions> list = findByUserId(userId);
			if(list != null) {
				Iterator<Sessions> iterator = list.iterator();
				while(iterator.hasNext()) {
					session.delete(iterator.next());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage().toString());
		}
		return new Sessions();
	}

	@Override
	public List<Sessions> getAllUsers() {
		try {
			Criteria crit = createBaseCriteria();
			List<Sessions> list = findMany(crit);
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
	public List<Sessions> findByUserId(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userid", userId));
			List<Sessions> list = findMany(crit);
			if(list != null) {
				return list;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			return null;
		}
	}
}
