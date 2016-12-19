package com.infotree.qliktest.admin.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.ViewTeamPojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.TeamService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserTeamService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/team")

public class TeamController extends AbstractQTAdminController<Team> {
	private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserTeamService userTeamService;
	
	@Autowired
	private RoleService roleService;
	
	
	

	public void setUserTeamService(UserTeamService userTeamService) {
		this.userTeamService = userTeamService;
	}

	/**
	 * This method returns the UI to create the team
	 */
	@RequestMapping(value="/createteam", method=RequestMethod.GET)
	public String createTeam(Model model)
	{
		try {
			model.addAttribute("crudObj", getEntityInstance());
			model.addAttribute("team_list", teamService.list());
			model.addAttribute("createteam","createteam");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is used to view the teams which are created by the particular app admin
	 */
	@RequestMapping(value="/viewteam", method=RequestMethod.GET)
	public String viewTeam(Model model,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			int count = 0;
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			String users="";
			model.addAttribute("crudObj",getEntityInstance());
			List<ViewTeamPojo> viewteams = new ArrayList<ViewTeamPojo>();
			List<Team> tlist = teamService.getTeamsById(id+"");
			
			Iterator<Team> iterator = tlist.iterator();
			while(iterator.hasNext()){
				Team team = iterator.next();
				ViewTeamPojo viewteampojo = new ViewTeamPojo();
				viewteampojo.setId(team.getId());
				String name = userService.getUsersById(team.getCreatedBy());
				viewteampojo.setCreatedBy(name);
				viewteampojo.setName(team.getName());
				count = userTeamService.getNoOfUsers(team.getId());
				if(count == 0){
					viewteampojo.setCount(new Integer(0));
					users="No Users Available";
				}else{
					viewteampojo.setCount(userTeamService.getNoOfUsers(team.getId()));
				}
				if(!(count==0)){
					List<User> listusers = userService.getUsersByTeamId(team.getId());
					if(listusers != null){
						Iterator<User> iter=listusers.iterator();
						while(iter.hasNext()){
							User u=iter.next();
							if(users=="")
								users=u.getFirstName();
							else
							users=users+" "+"<br>"+u.getFirstName();
						}
					}
					
				}
				
				viewteampojo.setUserName("<center><table width=70% border=1><tr><th><center>User Names</center></th></tr><tr><td><center>"+users+"</center></td></tr></table></center>");
				viewteams.add(viewteampojo);
				users = "";
			}
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			record.setActionType(AuditType.VIEW);
			record.setActionPerformed("Viewed the list of teams");
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			/*
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}*/
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			auditLogService.saveRecord(record);
			model.addAttribute("view_team",viewteams);
			model.addAttribute("viewteam","viewteam");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to edit the team with the list of teams which are created by the particular app admin
	 */
	@RequestMapping(value="/editteam", method=RequestMethod.GET)
	public String editTeam(Model model,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			List<Team> teams = new ArrayList<Team>();
			Integer id = (Integer)request.getSession().getAttribute("userid");
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("editteam","editteam");
			List<Team> teamList = teamService.getTeamsById(id+"");
			Iterator<Team> iterator = teamList.iterator();
			while(iterator.hasNext()){
				Team team = iterator.next();
				if(team.getModifiedBy() != null){
					User u = userProfileService.getById(Integer.parseInt(team.getModifiedBy()));
					team.setCreatedName(u.getUserName());
				}
				else{
					User u = userProfileService.getById(Integer.parseInt(team.getModifiedBy()));
					team.setCreatedName(u.getUserName());
				}
				teams.add(team);
			}
			
			
			model.addAttribute("team_list",teams);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to delete the team
	 */
	@RequestMapping(value="/deleteteam", method=RequestMethod.GET)
	public String deleteTeam(Model model,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			model.addAttribute("deleteteam","deleteteam");
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("team_list",teamService.getTeamsById(id+""));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is used to save the team details
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveTeam(@ModelAttribute("crudObj") @Valid Team entity, BindingResult bindingResult,Model model,HttpServletRequest request)
	{
		try {
			model.addAttribute("crudObj",getEntityInstance());
			if(teamService.findByName(entity.getName()) != null){
				model.addAttribute("createteam", "createteam");
				model.addAttribute("team_list", teamService.list());
				
				model.addAttribute("teamalreadyexists", "'" + entity.getName()+"' already exists. Pl. enter a different Name." );
			}else{
				Team team = new Team();
				team.setName(entity.getName());
				team.setDescription(entity.getDescription());
				team.setCreatedBy(entity.getCreatedBy());
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
				team.setCreatedDate(dateInIndia);
				team.setModifiedBy(entity.getModifiedBy());
				team.setModifiedDate(dateInIndia);
				Team t = null;
				String errorMsg = "";
				try {
					t = teamService.save(team);
				} catch (Exception e1) {
					e1.printStackTrace();
					errorMsg = e1.getMessage();
				}
				if(t == null){
					model.addAttribute("createteam", "createteam");
					model.addAttribute("project_list", teamService.list());
					model.addAttribute("teamnotselected", "Error in creating Module. Pl. contact Sys Admin." );
				}else{
					AuditLogRecord record = new AuditLogRecord();
					record.setActionDate(dateInIndia);
					record.setActionType(AuditType.CREATE);
					record.setActionPerformed("Team created with name "+entity.getName());
					record.setActionData("Team Name :"+entity.getName());
					record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
					if(errorMsg != ""){
						
					}
					/*try {
						java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
						record.setIpOrigin(inetAddress.getHostAddress());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}*/
					record.setUserId((Integer)request.getSession().getAttribute("userid"));
					record.setUserName(request.getSession().getAttribute("username").toString());
					auditLogService.saveRecord(record);
					model.addAttribute("createteam", "createteam");
					model.addAttribute("team_list", teamService.list());
					model.addAttribute("teamcreated", "'" + entity.getName() + "' created." );
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to edit the team which is selected by the user 
	 */
	@RequestMapping(value="/edit/{teamId}/update", method=RequestMethod.GET)
	public String updateTeam(@PathVariable Integer teamId, Model model) {
		try {
			Team entityToBeUpdated = teamService.getById(teamId);
			model.addAttribute("crudObj", entityToBeUpdated);
			model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
			model.addAttribute("updateteam", "updateteam");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	/**
	 * This method is used to update the team details which are submitted by the user
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTeam(@ModelAttribute("crudObj") @Valid Team entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
		Team t = null;
		try {
			try {
				t = teamService.findByNameAndIdNot(entity.getName(), entity.getId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			Integer id = (Integer)request.getSession().getAttribute("userid");
			if(t != null){
				model.addAttribute("editteam", "editteam");
				model.addAttribute("teamcreated", "<font color='red'>Team Already Exists. Choose Another Name</font>" );
			}else{
				Team team = new Team();
				team.setId(entity.getId());
				team.setName(entity.getName());
				team.setModifiedBy(entity.getModifiedBy());
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
				team.setModifiedDate(dateInIndia);
				teamService.updateTeam(team);
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionType(AuditType.MODIFY);
				record.setActionPerformed("Updated the team name");
				record.setActionData("Old name :"+team.getName()+"and new name is"+entity.getName());
				record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
				/*try {
					java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
					record.setIpOrigin(inetAddress.getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}*/
				record.setUserId((Integer)request.getSession().getAttribute("userid"));
				record.setUserName(request.getSession().getAttribute("username").toString());
				auditLogService.saveRecord(record);
				
				model.addAttribute("teamupdated", "'" + entity.getName() + "' updated." );
			}
			List<Team> teams = new ArrayList<Team>();
			List<Team> teamList = teamService.getTeamsById(id+"");
			Iterator<Team> iterator = teamList.iterator();
			while(iterator.hasNext()){
				Team team = iterator.next();
				if(team.getModifiedBy() != null){
					User u = userProfileService.getById(Integer.parseInt(team.getModifiedBy()));
					team.setCreatedName(u.getUserName());
				}
				else{
					User u = userProfileService.getById(Integer.parseInt(team.getModifiedBy()));
					team.setCreatedName(u.getUserName());
				}
				teams.add(team);
			}
			model.addAttribute("team_list",teams);
			model.addAttribute("editteam", "editteam");
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	/**
	 * This method is used to delete the team
	 */
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String deleteTeam(@ModelAttribute("crudObj") @Valid Team entity,BindingResult bindingResult,Model model)
	{
		try {
			model.addAttribute("crudObj", getEntityInstance());
			Team team = teamService.getById(entity.getId());
			if(team == null){
				model.addAttribute("deleteteam", "deleteteam");
				model.addAttribute("active_projects", teamService.getActiveTeams());
				model.addAttribute("teamdeleted", "Data cannot be deleted. Pl. try again." );
			}else{
				team.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
				team.setModifiedDate(new java.sql.Date(System.currentTimeMillis()));
				teamService.deleteUsersByTeamId(entity.getId());
				teamService.delete(team);
				model.addAttribute("crudObj", getEntityInstance());
				model.addAttribute("deleteteam", "deleteteam");
				model.addAttribute("team_list",teamService.getActiveTeams());
				model.addAttribute("teamdeleted", "'"+ entity.getName() +"' deleted.");
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@Override
	public QTAdminService<Team> getTService() {
		return teamService;
	}
	

	@Override
	protected Validator getValidator() {
		return validator;
	}
}
