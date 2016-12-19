/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;
import com.infotree.qliktest.admin.entity.referencedata.TeamProjectId;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.TeamProjectPojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.TeamProjectService;
import com.infotree.qliktest.admin.service.referencedata.TeamService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserTeamService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@RequestMapping("/teamproject")

@Controller
public class TeamProjectController extends AbstractQTAdminController<TeamProject> {
	private static final Logger LOG = LoggerFactory.getLogger(TeamProjectController.class);
	
	@Autowired
	private TeamProjectService teamProjectService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserTeamService userTeamService;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private AuditLogRecordService auditRecordService;
	
	@Autowired
	private DoNothingValidator validator;
	
	
	/**
	 * This method returns the UI to assign the projects to the team
	 */
	@RequestMapping(value = "/assignprojectstoteam",method = RequestMethod.GET)
	public String assignProjectsToTeam(Model model,HttpServletRequest request){
		try {
			model.addAttribute("assignprojectstoteam", "assignprojectstoteam");
			model.addAttribute("crudObj", new TeamProjectPojo());
			model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
			/*UserTenant ut = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
			model.addAttribute("project_list", projectService.getProjectsByUserId(ut.getUserTenantComp().getTenantId()));*/
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 *This method is to assign the projcts to the team 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid TeamProjectPojo entity,
			BindingResult bindingResult, Model model, HttpServletRequest request) {

		String displayMessage = null;
		try {

			List<Integer> beforeAssignedList = (List<Integer>) request.getSession().getAttribute("assignedProjectIds");
			List<Integer> assignedList = entity.getProjectId();
			//System.out.println("beforeAssignedList >>> "+beforeAssignedList);
			//System.out.println("assignedList >>> "+assignedList);
			int assignedProjectSize = beforeAssignedList.size();
			int newlyAssignedProjectSize = 0;
			if(assignedList != null)
				newlyAssignedProjectSize = assignedList.size();

			if (assignedProjectSize == 0 && newlyAssignedProjectSize == 0) {
				displayMessage = "Please assign atleast one project";
				
				model.addAttribute("assignprojectstoteam", "assignprojectstoteam");
				model.addAttribute("crudObj", new TeamProjectPojo());
				model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
				model.addAttribute("displayMessage", displayMessage);
				return "home";
			} else if (assignedProjectSize > 0 && newlyAssignedProjectSize == 0) {
				if (assignedProjectSize == 1)
					displayMessage = "1 project is removed";
				else
					displayMessage = assignedProjectSize
							+ " projects are removed ";
			} else if(assignedProjectSize == 0 && newlyAssignedProjectSize > 0){
				
				if(newlyAssignedProjectSize == 1)
					displayMessage=" 1 project is assigned";
				else
					displayMessage = newlyAssignedProjectSize+" projects are assigned ";
			}
			
			else{
				int samePojectAssigned = 0;
				for(Integer projectId : assignedList){
					
					for(Integer pid : beforeAssignedList){
						if(projectId.intValue() == pid.intValue())
							samePojectAssigned++;
					}
				}
			
				int removedProject = assignedProjectSize - samePojectAssigned;
				if(removedProject == 0)
					displayMessage = "";
				else if(removedProject == 1)
					displayMessage = " and 1 project is removed";
				else
					displayMessage = " and "+removedProject+" projects are removed";
					
					int assignedProject = 	newlyAssignedProjectSize - samePojectAssigned;
					
					if(assignedProject == 0 && assignedProjectSize == newlyAssignedProjectSize){
						displayMessage = "No changes are made";
						model.addAttribute("assignprojectstoteam", "assignprojectstoteam");
						model.addAttribute("crudObj", new TeamProjectPojo());
						model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
						model.addAttribute("displayMessage", displayMessage);
						return "home";
					}
					else if(assignedProject == 0){
						
							if(removedProject == 0)
								displayMessage = "";
							else if(removedProject == 1)
								displayMessage = " 1 project is removed";
							else
								displayMessage = removedProject+" projects are removed";
							
						
					}
					else if(assignedProject == 1)
						displayMessage = "1 project is assigned"+displayMessage;
					else
						displayMessage = assignedProject+" projects are assigned"+displayMessage;
				
			}
			//System.out.println(displayMessage);
			model.addAttribute("displayMessage", displayMessage);
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
			Integer teamId = entity.getTeamId();
			teamProjectService.deleteByTeamId(teamId);

			List<Integer> projectsList = entity.getProjectId();
			if (projectsList != null) {
				Iterator<Integer> iterator = projectsList.iterator();
				while (iterator.hasNext()) {
					//model.addAttribute("crudObj", new TeamProjectPojo());
					Integer projId = iterator.next();
					TeamProject teamProject = new TeamProject();
					TeamProjectId teamProjectComp = new TeamProjectId();
					teamProjectComp.setTeamId(entity.getTeamId());
					teamProjectComp.setProjectId(projId);
					teamProject.setTeamProjId(teamProjectComp);
					teamProject.setCreatedBy(entity.getCreatedBy());
					teamProject.setCreatedDate(dateInIndia);
					teamProject.setModifiedBy(entity.getModifiedBy());
					teamProject.setModifiedDate(dateInIndia);
					teamProjectService.save(teamProject);
					model.addAttribute("projectsassigned",
							"Projects Assigned To Team");
				}
			} else {
				model.addAttribute("projectsassigned",
						"Projects Assigned To Team");
			}
			AuditLogRecord record = new AuditLogRecord();
			// record.setActionDate(new java.util.Date());
			record.setActionDate(dateInIndia);
			Team team = teamService.getById(entity.getTeamId());

			record.setActionData("Team name is " + team.getName());
			record.setActionType(AuditType.ASSIGN);
			record.setActionPerformed("Team Assigned");
			record.setIpOrigin(request.getSession().getAttribute("ipAddress")
					.toString());
			/*
			 * try { InetAddress inetAddress = InetAddress.getLocalHost();
			 * record.setIpOrigin(inetAddress.getHostAddress()); } catch
			 * (UnknownHostException e) { e.printStackTrace(); }
			 */
			record.setUserId((Integer) request.getSession().getAttribute(
					"userid"));
			record.setUserName(request.getSession().getAttribute("username")
					.toString());
			auditRecordService.saveRecord(record);

			model.addAttribute("assignprojectstoteam", "assignprojectstoteam");
			model.addAttribute("crudObj", new TeamProjectPojo());
			model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
			model.addAttribute("displayMessage", displayMessage);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This is a ajax based request to get the assigned projects to the team by taking the teamid as the parameter
	 */
	@RequestMapping(value="/getassignedteamprojects",method=RequestMethod.GET)
	public void getAssignedTeamProjects(Model model,@RequestParam("teamId") String teamId,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			if(teamId == null || teamId == ""){
				//System.out.println("getAssignedTeamProjects");
				return;
			}
			Integer temId = Integer.parseInt(teamId);
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId(userId);
			List<Project> projList = projectService.findByTenantIdNotAssignedToTeam(temId, userTenant.getUserTenantComp().getTenantId());
			String trtaProjects = "";
			List<Integer> projectIds = new ArrayList<Integer>();
			
			if(projList != null){
				for(Project list : projList){
					trtaProjects += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
					projectIds.add(list.getId());
				}
			}
			//System.out.println("Added to Session : "+projectIds);
			request.getSession().setAttribute("assignedProjectIds", projectIds);
				try{
					PrintWriter out = response.getWriter();
					out.println(trtaProjects);
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
	 * This is ajax calling method to get the available teams to the projects by taking the teamid as the parameter
	 */
	@RequestMapping(value="/getavailableteamprojects",method=RequestMethod.GET)
	public void getAvailableTeamUsers(Model model,@RequestParam("teamId") String teamId,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			if(teamId == null || teamId == ""){
				//System.out.println("getAvailableTeamUsers");
				return;
			}
			Integer temId = Integer.parseInt(teamId);
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId(userId);
			
			
			List<Project> projList = projectService.findByTeamIdAndTenantId(temId, userTenant.getUserTenantComp().getTenantId());
			String trtProjects = "";
			if(projList != null){
				for(Project list : projList){
					trtProjects += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
				}
			}
				try{
					PrintWriter out = response.getWriter();
					out.println(trtProjects);
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
	public QTAdminService<TeamProject> getTService() {
		return teamProjectService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}

}
