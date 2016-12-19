package com.infotree.qliktest.admin.dwr;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.beans.factory.annotation.Autowired;

import com.infotree.qliktest.admin.dao.referencedata.NotificationsDao;
import com.infotree.qliktest.admin.dao.referencedata.SessionsDao;
import com.infotree.qliktest.admin.entity.referencedata.Notifications;
import com.infotree.qliktest.admin.entity.referencedata.Sessions;


@RemoteProxy(name="dwrService")
public class DWRService {
	
	public static boolean stopthrd = true;
	
	public Integer userId;
	
	public DWRService() { }
	
	@Autowired
	private NotificationsDao notificationsDao;
	
	@Autowired
	private SessionsDao sessionsDao;
	
	Util utiAll;
	
	Notification notification;
	
	@RemoteMethod
	public Notification getAllNotifications(){
		Notification notification = new Notification();
		try {
			List<Notifications> list = notificationsDao.getAllNotifications();
			Iterator<Notifications> itr = list.iterator();
			
			while(itr.hasNext()){
				Notifications notifications = itr.next();
				notification.setMessage(notifications.getAssignedData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notification;
	}
	
	@RemoteMethod
	public Notification sendNotifications(HttpServletRequest request) throws InterruptedException{
		notification = new Notification();
		try {
			ServletContext application = request.getSession().getServletContext();
			if(application.getAttribute("isNotificationstarted") == null){
			application.setAttribute("isNotificationstarted", "TRUE");
			WebContext context = WebContextFactory.get();
			String currentPage = context.getCurrentPage();
			Collection session = context.getScriptSessionsByPage(currentPage);
			utiAll = new Util(session);
			
			/**
			 * Writing the method level inner class for running a thread
			 */
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(stopthrd){
					
					/**
					 * notifications setting for the users who are available in session
					 */
					try {
						Thread.sleep(10000);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					List<Sessions> sessions = sessionsDao.getAllUsers();
					Iterator<Sessions> itr = sessions.iterator();
					while(itr.hasNext()) {
						Sessions userSession = itr.next();
						List<Notifications> notificationsList = notificationsDao.getNotificationsById(userSession.getUserid());
						Iterator<Notifications> notificationItr = notificationsList.iterator();
						while(notificationItr.hasNext()) {
							
							Notifications notifications = notificationItr.next();
							notification.setMessage(notifications.getAssignedData());
							utiAll.setValue("message", notification);
							utiAll.addFunctionCall("reversajaxtest",notification.getMessage());
							notificationsDao.updateStatusById(notifications.getId());
						}
					}
				}
				}
			});
			Thread.interrupted();
			thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return notification;
	}
}
