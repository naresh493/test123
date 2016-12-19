/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Notifications;

@Repository
public class NotificationsDaoImpl extends AbstractQTAdminDao<Notifications> implements NotificationsDao{

	private static final Logger LOG = LoggerFactory.getLogger(NotificationsDaoImpl.class);
	@Override
	public List<Notifications> getAllNotifications() {
		try {
			Criteria crit = createBaseCriteria();
			List<Notifications> list = findMany(crit);
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
	public List<Notifications> getNotificationsById(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("assignedTo", userId+"");
			Criterion rhs = Restrictions.eq("status", "UnRead");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			List<Notifications> list = findMany(crit);
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
	public int updateStatusById(Serializable id) {
		try {
			Notifications notifications = getById(id);
			notifications.setStatus("Read");
			Session session = getSession();
			session.save(notifications);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Notifications saveNotification(Notifications notification) {
		try {
			Session session = getSession();
			session.save(notification);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return new Notifications();
	}

	@Override
	public int getMaxId() {
		Criteria crit=null;
		try {
			crit = createBaseCriteria().setProjection(Projections.max("id"));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return (Integer)crit.uniqueResult();
	}
	
}
