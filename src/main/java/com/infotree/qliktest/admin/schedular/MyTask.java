package com.infotree.qliktest.admin.schedular;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;

@Service
public class MyTask {
     
	
	@Autowired
	private SimpleMailMessage simpleMailMessage;
	    
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	public void sendAlertToAppAdmin(){
		List<Project> list = projectService.list();
		Iterator<Project> itr = list.iterator();
		while(itr.hasNext()){
		}
		
	}

	public void sayHello() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		List<Project> list = projectService.list();
		Iterator<Project> itr = list.iterator();
		while (itr.hasNext()) {
			final Project p = itr.next();
			final String projDate = dateFormat.format(p.getEndDate());
			String today = dateFormat.format(new Date());
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				int numOfDays = cal.get(Calendar.DAY_OF_YEAR);
				Date edate = dateFormat.parse(projDate);
				cal.setTime(edate);
				int numOfpdays = cal.get(Calendar.DAY_OF_YEAR);
				final int z = numOfpdays - numOfDays;
				if (z>0 && z<7) {
                  
					ProjectTenant pt = projectTenantService.findByProjectId(p.getId());
					UserTenant ut = userTenantService.findByTenantId(pt.getProjectTenantComp().getTenantId());
					final User user = userProfileService.getById(ut.getUserTenantComp().getUserId());
					final String email = user.getEmailAddress();
					MimeMessagePreparator preparator = new MimeMessagePreparator() {
						public void prepare(MimeMessage mimeMessage)
								throws Exception {
							String from = "dev@infotreesolutions.com";
							String to = email;
							String subject = "Notification About Project End Date";
							MimeMessageHelper message = new MimeMessageHelper(
									mimeMessage);
							message.setTo(to);
							message.setFrom(from);
							message.setSubject(subject);
							/*message.setText("Dear System Administrator," + "\n\n\n\n" + "Project End Date="
									+ projDate + "\n\n" + "No. of Days Left:"
									+ z + "\n\n\n\n\n"
									+ "Thanks,\nQlikTest Administrator");*/
							message.setText("Dear "+user.getUserName() + " ,\n\n" + 
									"Project Name: "+p.getName()+"\n"+
									"End Date : "+ projDate + "\n" + "No. of Days Left: "
							+ z + "\n\n\n"
							+ "Thanks,\nQlikTest Administrator");
						}

					};
					this.mailSender.send(preparator);
					}
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (projDate.equals(today)) {
				projectService.UpdateStatusById(p.getId());
			} else {
				//projectService.UpdateStatus1ById(p.getId());
			}
		}
	}
}
