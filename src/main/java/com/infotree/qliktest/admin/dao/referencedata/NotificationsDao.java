package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Notifications;


public interface NotificationsDao {

	List<Notifications> getAllNotifications();
	
	List<Notifications> getNotificationsById(Integer userId);
	
	int updateStatusById(Serializable id);
	
	Notifications saveNotification(Notifications notification);
	
	int getMaxId();
}
