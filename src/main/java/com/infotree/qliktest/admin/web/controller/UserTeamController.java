/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infotree.qliktest.admin.dao.referencedata.NotificationsDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Notifications;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
import com.infotree.qliktest.admin.entity.referencedata.UserTeamComp;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.UserProjectPojo;
import com.infotree.qliktest.admin.helperpojo.UserTeamPojo;
import com.infotree.qliktest.admin.mails.UserTeamMail;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.TeamService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserTeamService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/userteam")
public class UserTeamController extends AbstractQTAdminController<UserTeam>{
	private static final Logger LOG = LoggerFactory.getLogger(UserTeamController.class);
	@Autowired
	private UserTeamService userTeamService;
	
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private NotificationsDao notificationsDao;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AuditLogRecordService auditRecordService;
	
	@Autowired
	private TeamService teamService;
	
	/**
	 * This method returns the UI to assign the users to the team
	 */
	@RequestMapping(value="/assignuserstoteam", method=RequestMethod.GET)
	public String assignUserToTeam(Model model,HttpServletRequest request,HttpServletResponse response)
	{
		
			try {
				HttpSession session=request.getSession();
				Integer id=(Integer)session.getAttribute("userid");
				model.addAttribute("assignusertoteam","assignusertoteam");
				model.addAttribute("crudObj",new UserTeamPojo());
				model.addAttribute("team_list",teamService.getTeamsById(id+""));
			} catch (Exception e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		
		return "home";
	}
	
	/**
	 * This method is ajax request method which will give the users data by taking the teamid as the parameter
	 */
	@RequestMapping(value="/getusersdata",method=RequestMethod.GET)
	public void getusersdata(Model model,@RequestParam("teamid") String teamId,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			int temid = Integer.parseInt(teamId);
			List<User> userList=userTeamService.getUsersByTeamId(temid);
			String trUsers = "";
			if(userList.contains(null)){
				trUsers += "<option label='No Users Available' value='No Users Available' disabled='true'</option>";
				try{
					PrintWriter out=response.getWriter();
					out.print(trUsers);
					out.flush();
				}
				catch(IOException e){
					
				}
			}
			else if(userList !=null && userList.size() >0){
				for (User user : userList) {
					trUsers += "<option label='"+user.getFirstName()+"' value='"+user.getId()+"'>"+user.getFirstName()+"</option>";
				}
				try {
					PrintWriter out = response.getWriter();
					out.println(trUsers);
					out.flush();
				} catch (IOException e) {
					
				}
			}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to remove the users from the team
	 */
	
	@RequestMapping(value="/removeuserfromteam", method=RequestMethod.GET)
	public String removeUserFromTeam(Model model,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			
			model.addAttribute("removeuserfromteam","removeuserfromteam");
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("team_list",teamService.getTeamsById(id+""));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is written for the assigning the users to the team
	 */
	@RequestMapping(value = "/assign", method = RequestMethod.POST)
    public String assignUsers(@Valid UserTeamPojo entity,BindingResult bindingResult, Model model,HttpServletRequest request) {
		
		String displayMessage = null;
		HttpSession session=request.getSession();
		Integer id=(Integer)session.getAttribute("userid");
		List<Integer> newUserIds = new ArrayList<Integer>();
		try {
			List<Integer> beforeAssignedUserList = (List<Integer>) request.getSession().getAttribute("userIds");
			List<Integer> newlyAssignedUserList = entity.getUserId();
			int beforeAssignedUserSize = beforeAssignedUserList.size();
			int newlyAssignedUserSize = 0;
			if(newlyAssignedUserList != null)
				newlyAssignedUserSize = newlyAssignedUserList.size();

			if (beforeAssignedUserSize == 0 && newlyAssignedUserSize == 0) {
				displayMessage = "Please assign atleast one User to the Team";
				model.addAttribute("displayMessage", displayMessage);
				model.addAttribute("assignusertoteam","assignusertoteam");
				model.addAttribute("crudObj",new UserTeamPojo());
				model.addAttribute("team_list",teamService.getTeamsById(id+""));
				return "home";
			} else if (beforeAssignedUserSize > 0 && newlyAssignedUserSize == 0) {
				if (beforeAssignedUserSize == 1)
					displayMessage = "1 user is removed";
				else
					displayMessage = beforeAssignedUserSize + " users are removed ";
			} else if(beforeAssignedUserSize == 0 && newlyAssignedUserSize > 0){
					newUserIds.addAll(newlyAssignedUserList);
					if(newlyAssignedUserSize == 1)
						displayMessage=" 1 user is assigned";
					else
						displayMessage = newlyAssignedUserSize+" users are assigned ";
			}
			else{
				int sameUserAssigned = 0;
				boolean flag ;
				if(newlyAssignedUserList != null){
					for(Integer userId : newlyAssignedUserList){
						flag = false;
						if( beforeAssignedUserList != null){
							for(Integer uid : beforeAssignedUserList){
								if(userId.intValue() == uid.intValue()){
									sameUserAssigned++;
									flag = true;
									break;
								}
							}
						}
						if(flag == false){
							newUserIds.add(userId);
						}
					}
				}
				int removedUser = beforeAssignedUserSize - sameUserAssigned;
				if(removedUser == 0)
					displayMessage = "";
				else if(removedUser == 1)
					displayMessage = " and 1 user is removed";
				else
					displayMessage = " and "+removedUser+" users are removed";
					
					int assignedUser = 	newlyAssignedUserSize - sameUserAssigned;
					
					if(assignedUser == 0 && beforeAssignedUserSize == newlyAssignedUserSize){
						displayMessage = "No changes are made";
						model.addAttribute("displayMessage", displayMessage);
						model.addAttribute("assignusertoteam","assignusertoteam");
						model.addAttribute("crudObj",new UserTeamPojo());
						model.addAttribute("team_list",teamService.getTeamsById(id+""));
						return "home";
					}
					else if(assignedUser == 0){
							if(removedUser == 0)
								displayMessage = "";
							else if(removedUser == 1)
								displayMessage = " 1 user is removed";
							else
								displayMessage = removedUser+" users are removed";
					}
					else if(assignedUser == 1)
						displayMessage = "1 user is assigned"+displayMessage;
					else
						displayMessage = assignedUser+" projects are assigned"+displayMessage;
				
			}
			model.addAttribute("displayMessage", displayMessage);
			model.addAttribute("assignusertoteam","assignusertoteam");

			Integer teamId = entity.getTeamId();
			userTeamService.deleteByTeamId(teamId);
			List<Integer> users = entity.getUserId();
			/*DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();*/
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			User tenant = userService.getById((Integer)request.getSession().getAttribute("userid"));
			Team team = teamService.getById(entity.getTeamId());
			
			if(users != null){
				Iterator<Integer> iterator = users.iterator();
				while(iterator.hasNext()){
					Integer userId = iterator.next(); 
					UserTeam userTeam=new UserTeam();
					UserTeamComp userTeamComp = new UserTeamComp();
					userTeamComp.setUserId(userId);
					userTeamComp.setTeamId(teamId);
					userTeam.setUserTeamComp(userTeamComp);
					userTeam.setCreatedBy(entity.getCreatedBy());
					userTeam.setCreatedDate(dateInIndia);
					userTeam.setModifiedBy(entity.getModifiedBy());
					userTeam.setModifiedDate(dateInIndia);
					userTeamService.save(userTeam);
					/*
					 * send the mail when the user assigned for a Team
					 */
					User u = userService.getById(userId);
					/*User tenant = userService.getById((Integer)request.getSession().getAttribute("userid"));
					Team team = teamService.getById(entity.getTeamId());*/
					/**
					 * Prepare the notification when the user assigns to the team
					 */
					Notifications notifications = new Notifications();
					notifications.setId(notificationsDao.getMaxId()+1);
					notifications.setAssignedBy((Integer)request.getSession().getAttribute("userid")+"");
					notifications.setAssignedTo(u.getId()+"");
					notifications.setAssignedData("You have been Assigned to "+team.getName()+" Team");
					notifications.setStatus("UnRead");
					notifications.setCreatedBy(entity.getCreatedBy());
					notifications.setModifiedBy(entity.getModifiedBy());
					notifications.setCreatedDate(dateInIndia);
					notifications.setModifiedDate(dateInIndia);
					notificationsDao.saveNotification(notifications);
					/*UserTeamMail mail = new UserTeamMail();
					mail.setUserName(u.getUserName());
					mail.setTeamName(team.getName());
					mail.setTenantName(tenant.getUserName());
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("entity", mail);
					try {
						mailService.sendMail("dev@infotreesolutions.com", u.getEmailAddress(), "You Have Assigned for a Team", map);
					} catch (Exception e) {
						e.printStackTrace();
					}*/
					//userTeamService.save(userTeam);
				}
				if(newUserIds.size() > 0){
					for(Integer userId : newUserIds){
						UserTeamMail mail = new UserTeamMail();
						User u = userService.getById(userId);
						mail.setUserName(u.getUserName());
						mail.setTeamName(team.getName());
						mail.setTenantName(tenant.getUserName());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("entity", mail);
						try {
							mailService.sendMail("dev@infotreesolutions.com", u.getEmailAddress(), "You Have Assigned for a Team", map);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}	
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionData("Team name is "+team.getName());
				record.setActionType(AuditType.ASSIGN);
				record.setActionPerformed("Users Assigned");
				record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
				/*try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					record.setIpOrigin(inetAddress.getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}*/
				record.setUserId((Integer)request.getSession().getAttribute("userid"));
				record.setUserName(request.getSession().getAttribute("username").toString());
				auditRecordService.saveRecord(record);
				model.addAttribute("usersassigned","User(s) Assigned to Team.");
			}else{
				model.addAttribute("usersassigned","User(s) Assigned to Team.'");
			}
			
			//Integer id=(Integer)request.getSession().getAttribute("userid");
			model.addAttribute("assignusertoteam","assignusertoteam");
			model.addAttribute("crudObj",new UserTeamPojo());
//			/model.addAttribute("user_list",userService.getUsersBasedOnCreatedBy(id));
			model.addAttribute("team_list",teamService.getTeamsById(id+""));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is used to remove the selected users from the selected team
	 */
	@RequestMapping(value="/removeselectedusersfromteam", method=RequestMethod.POST)
	public String removeSelectedUsersFromTeam( @Valid UserTeamPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			/*DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();*/
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			model.addAttribute("crudObj",entity);
			model.addAttribute("removeuserfromteam","removeuserfromteam");
			model.addAttribute("team_list",teamService.getActiveTeams());
			List<Integer> l=entity.getUserId();
			Iterator<Integer> itr=l.iterator();
			String userId="";
			while(itr.hasNext()){
				if(userId==""){
					userId=userId+itr.next();
				}
				else{
				userId=userId+","+itr.next();
				}
			}
			
			Iterator<Integer> iterator = l.iterator();
			while(iterator.hasNext()){
				User u = userProfileService.getById(iterator.next());
				Team team = teamService.getById(entity.getTeamId());
				Notifications notifications = new Notifications();
				notifications.setId(notificationsDao.getMaxId()+1);
				notifications.setAssignedBy((Integer)request.getSession().getAttribute("userid")+"");
				notifications.setAssignedTo(u.getId()+"");
				notifications.setAssignedData("You have been Assigned to "+team.getName()+" Team");
				notifications.setStatus("UnRead");
				notifications.setCreatedBy(entity.getCreatedBy());
				notifications.setModifiedBy(entity.getModifiedBy());
				notifications.setCreatedDate(dateInIndia);
				notifications.setModifiedDate(dateInIndia);
			}
			userTeamService.deleteMultUsersFromTeam(entity.getTeamId(), userId);
			
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			
			Team team = teamService.getById(entity.getTeamId());
			
			record.setActionData("Team name is "+team.getName());
			record.setActionType(AuditType.REMOVE);
			record.setActionPerformed("Users Removed from team "+team.getName());
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			/*try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}*/
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			auditRecordService.saveRecord(record);
			model.addAttribute("usersremoved", "'" + l.size() + "'users removed." );
			Integer id=(Integer)request.getSession().getAttribute("userid");
			model.addAttribute("removeuserfromteam","removeuserfromteam");
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("team_list",teamService.getTeamsById(id+""));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	/**
	 * This is a ajax request based request which return the assignd users to the team by taking the teamid as the parameter
	 */
	@RequestMapping(value="/getassignedteamusers",method=RequestMethod.GET)
	public void getAssignedUsers(Model model,@RequestParam("teamId") String teamId,HttpServletRequest request,HttpServletResponse response)
	{
		if(teamId == null || teamId == ""){
			return;
		}
		
		try {
			Integer temId = Integer.parseInt(teamId);
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			String trtAsUsers = "";
			List<User> userList = userService.getUsersByTeamAndCreatedBy(temId, userId);
			List<Integer> userIds = new ArrayList<Integer>();
			if(userList != null){
				for(User list : userList){
					trtAsUsers += "<option label='"+list.getUserName()+"' value='"+list.getId()+"'>"+list.getUserName()+"</option>";
					userIds.add(list.getId());
				}
			}
			request.getSession().setAttribute("userIds", userIds);
				try{
					PrintWriter out = response.getWriter();
					out.println(trtAsUsers);
					out.flush();
				}catch(Exception e){
					e.printStackTrace();
				}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method fetches the available users for a team by taking the teamid and userid as parameters.
	 * userid for finding the team which was created by the particular user
	 */
	@RequestMapping(value="/getavailableteamusers",method=RequestMethod.GET)
	public void getAvailableUsers(Model model,@RequestParam("teamId") String teamId,HttpServletRequest request,HttpServletResponse response)
	{
		if(teamId == null || teamId == ""){
			return;
		}
		try {
			Integer temId = Integer.parseInt(teamId);
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			String trtaUsers = "";
			List<User> userList = userService.getUsersNotByTeamAndCreatedBy(temId, userId);
			if(userList != null){
				for(User list : userList){
					trtaUsers += "<option label='"+list.getUserName()+"' value='"+list.getId()+"'>"+list.getUserName()+"</option>";
				}
			}
				try{
					PrintWriter out = response.getWriter();
					out.println(trtaUsers);
					out.flush();
				}catch(Exception e){
					e.printStackTrace();
				}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public QTAdminService<UserTeam> getTService() {
		return userTeamService;
	}
	@Override
	protected Validator getValidator() {
		return validator;
	}
}
