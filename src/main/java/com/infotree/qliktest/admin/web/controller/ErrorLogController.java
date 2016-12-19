package com.infotree.qliktest.admin.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.common.constants.ProjectTypeEnum;
import com.infotree.qliktest.admin.entity.errorlogs.ErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.SocketWebLogPojo;
import com.infotree.qliktest.admin.helperpojo.ErrorLogFormPojo;
import com.infotree.qliktest.admin.service.referencedata.ErrorLogDetailsService;

@RequestMapping(value="/errorlogs")
@Controller
public class ErrorLogController {

	private static final Logger logger = LoggerFactory.getLogger(ErrorLogController.class);
	
	@Autowired
	private ErrorLogDetailsService errorLogDetailsService;
	
	/**
	 * This method returns the UI to show the error logs details
	 */
	@RequestMapping( value="/getErrorLog",method=RequestMethod.GET)
	public String getErrorLogHome(@ModelAttribute("errorlog") ErrorLogFormPojo errorLogPojo,Model model,HttpServletRequest request)
	{
		List<ErrorLogs> errorLogs=null;
		//errorLogs=errorLogDetailsService.getAllErrorLogs();
		model.addAttribute("errorloghome", "errorloghome");
		model.addAttribute("flag", "nothing");
		model.addAttribute("errorlogs", errorLogs);
		return "home";
	}
	
	/**
	 * This method is to fetch the error logs by applying the pagination
	 */
	@RequestMapping(value="/getErrorLogDetails/{page}",method= RequestMethod.POST)
	public String getErrorLogView(@PathVariable Integer page,@ModelAttribute("errorlog") ErrorLogFormPojo errorLogPojo,BindingResult errors,Model model,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
			session.setAttribute("errorLog", errorLogPojo);
		String startDate=errorLogPojo.getStartingDate();
		String endDate=errorLogPojo.getEndingDate();
		int count = 0;
		logger.info("Inside ErrorLogController...");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
				if(!(startDate.equals("")) && !(endDate.equals(""))){
					errorLogPojo.setStartDate(sdf.parse(startDate));
					errorLogPojo.setEndDate(sdf.parse(endDate));
				}
				else if(!(startDate.equals("")) && (endDate.equals(""))){
					errorLogPojo.setStartDate(sdf.parse(startDate));
				}
				else if((startDate.equals("")) && !(endDate.equals(""))){
					errorLogPojo.setEndDate(sdf.parse(endDate));
				}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("errorloghome", "errorloghome");
		if(errors.hasErrors())
		{
			model.addAttribute("errorLogFormPojo", errorLogPojo);
			return "home";
		}
		List<ErrorLogs> errorLogs=null;
		if(errorLogPojo.getSubmit().equalsIgnoreCase("Refresh"))
		{
			model.addAttribute("errorlogs", errorLogs);
			model.addAttribute("flag", "nothing");
			return "home";
		}
		else{	
			
			if(errorLogPojo.getProjectType().equalsIgnoreCase(ProjectTypeEnum.WEB_SOCKET.getProjectType())){
					List<SocketWebLogPojo> list=null;
					list=errorLogDetailsService.getSocketWebLogs(errorLogPojo);
					model.addAttribute("flag", "false");
					model.addAttribute("socketWebLogs", list);
			}
			else{
					count=errorLogDetailsService.getColumnCount(errorLogPojo);
					int limitsPerPage = 10;
					int noOfPages = count/10;
					if(count%10 != 0){
						noOfPages = noOfPages+1;
					}
					
					model.addAttribute("numberofpages", noOfPages);
					model.addAttribute("currentpage", page);
					errorLogs=errorLogDetailsService.getErrorLogsByDate(errorLogPojo,page,limitsPerPage);
					model.addAttribute("errorlogs", errorLogs);
					model.addAttribute("flag", "true");
			}
			model.addAttribute("errorlog",errorLogPojo);
			return "home";
		}
		
	}
	
	/**
	 * This method is to apply the pagination to get the all error logs
	 */
	@RequestMapping(value="/getErrorLogDetails/{page}",method=RequestMethod.GET)
	public String getErrorLogView1(@PathVariable Integer page,@ModelAttribute("errorlog") ErrorLogFormPojo errorLogPojo1,BindingResult errors,Model model,HttpServletRequest request)
	{
		HttpSession session=request.getSession(false);
		ErrorLogFormPojo errorLogPojo = (ErrorLogFormPojo)session.getAttribute("errorLog");
		String startDate=errorLogPojo.getStartingDate();
		String endDate=errorLogPojo.getEndingDate();
		int count = 0;
		model.addAttribute("errorlog",errorLogPojo);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
				if(!(startDate.equals("")) && !(endDate.equals(""))){
					errorLogPojo.setStartDate(sdf.parse(startDate));
					errorLogPojo.setEndDate(sdf.parse(endDate));
				}
				else if(!(startDate.equals("")) && (endDate.equals(""))){
					errorLogPojo.setStartDate(sdf.parse(startDate));
				}
				else if((startDate.equals("")) && !(endDate.equals(""))){
					errorLogPojo.setEndDate(sdf.parse(endDate));
				}
				
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("errorloghome", "errorloghome");
		
		if(errors.hasErrors())
		{
			model.addAttribute("errorLogFormPojo", errorLogPojo);
			return "home";
		}
		List<ErrorLogs> errorLogs=null;
		if(errorLogPojo.getSubmit().equalsIgnoreCase("Refresh"))
		{
			model.addAttribute("errorlogs", errorLogs);
			model.addAttribute("flag", "true");
			return "home";
		}
		else{	
			if(errorLogPojo.getProjectType().equalsIgnoreCase(ProjectTypeEnum.WEB_SOCKET.getProjectType())){
					List<SocketWebLogPojo> list=null;
					list=errorLogDetailsService.getSocketWebLogs(errorLogPojo);
					model.addAttribute("flag", "false");
					logger.info("Error Log for SocketWEbProject.");
					model.addAttribute("socketWebLogs", list);
			}
			else{
					count=errorLogDetailsService.getColumnCount(errorLogPojo);
					
					int limitsPerPage = 10;
					int noOfPages = count/10;
					if(count%10 != 0){
						noOfPages = noOfPages+1;
					}
					
					model.addAttribute("numberofpages", noOfPages);
					model.addAttribute("currentpage", page);
					errorLogs=errorLogDetailsService.getErrorLogsByDate(errorLogPojo,page,limitsPerPage);
					model.addAttribute("errorlogs", errorLogs);
					model.addAttribute("flag", "true");
			}
			
			return "home";
		}
	}
	
	
	/**
	 * This method is to download the scoket web file from mongodb
	 */
	@RequestMapping( value="/getSocketWeb",method=RequestMethod.GET) 
	public String getSocketWeb(@ModelAttribute("errorlog") ErrorLogFormPojo errorLogPojo,Model model,HttpServletRequest request,HttpServletResponse response)
	{
		
		String id=request.getParameter("id");
		String filename=request.getParameter("filename");
		InputStream is=errorLogDetailsService.getSocketWebFile(id);
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition",
	                     "attachment;filename="+filename+".txt");
		
		int read=0;
		byte[] bytes = new byte[1024];
		OutputStream os=null;
		
			try {
				
				os = response.getOutputStream();
				while((read = is.read(bytes))!= -1){
					os.write(bytes, 0, read);
				}
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		model.addAttribute("errorloghome", "errorloghome");
		model.addAttribute("flag", "false");
		return null;
	}
	
	
}
