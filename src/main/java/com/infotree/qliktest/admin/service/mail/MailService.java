package com.infotree.qliktest.admin.service.mail;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;


/**
 * Sends email.
 */
@SuppressWarnings("unused")
@Service("mailService")
public class MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	private VelocityEngine velocityEngine;
    
    @Autowired
    private SimpleMailMessage simpleMailMessage;
    
    
    /**
     * @param from
     * @param to
     * @param subject
     * @param body
     */
    public void sendMail(final String from, final String to, final String subject, final Map<String, Object> model){
    	
    	MimeMessagePreparator preparator = new MimeMessagePreparator(){
    		public void prepare(MimeMessage mimeMessage) throws Exception{
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    			message.setTo(to);
    			message.setFrom(from);
    			String vmFileName = null;
    			message.setSubject(subject);
    			if(subject.equalsIgnoreCase("You Have Assigned for a Team")){
    				vmFileName = "mailtemplateuserteam.vm";
    			}
    			else if(subject.equalsIgnoreCase("New user created in QlikTest Admin Account")){
    				vmFileName = "mailtemplatetodefaultuser.vm";
    			}
    			else{
    				vmFileName = "mailtemplateforgotpassword.vm";
    			}
    			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
    					vmFileName, model);
    			message.setText(text, true);
    		}
    	};
    	this.mailSender.send(preparator);
    }
    
    /**
     * @param from
     * @param to
     * @param subject
     * @param body
     */
    public void sendMailToDefaultUser(final String from, final String to, final String subject, final Map<String, Object> model){
    	
    	MimeMessagePreparator preparator = new MimeMessagePreparator(){
    		public void prepare(MimeMessage mimeMessage) throws Exception{
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    			message.setTo(to);
    			message.setFrom(from);
    			message.setSubject(subject);
    			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
    					"mailtemplatetodefaultuser.vm", model);
    			message.setText(text, true);
    		}
    	};
    	this.mailSender.send(preparator);
    }
    /*
     * Sends the email with the updated tenant details
     */
		public void sendUpdateTenantMail(final String from, final String to, final String subject, final Map<String, Object> model){
		    	
		    	MimeMessagePreparator preparator = new MimeMessagePreparator(){
		    		public void prepare(MimeMessage mimeMessage) throws Exception{
		    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		    			message.setTo(to);
		    			message.setFrom(from);
		    			
		    			message.setSubject(subject);
		    			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
		    					"mailtemplateedittenant.vm", model);
		    			message.setText(text, true);
		    		}
		    	};
		    	this.mailSender.send(preparator);
		    }
    
		/*
		 * sends the mail to the Tenant when projects are assigned
		 */
		
		public void sendMailWithProjects(final String from, final String to, final String subject, final Map<String, Object> model){
	    	
	    	MimeMessagePreparator preparator = new MimeMessagePreparator(){
	    		public void prepare(MimeMessage mimeMessage) throws Exception{
	    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	    			message.setTo(to);
	    			message.setFrom(from);
	    			message.setSubject(subject);
	    			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
	    					"mailtemplateassginprojectstotenant.vm", model);
	    			message.setText(text, true);
	    		}
	    	};
	    	this.mailSender.send(preparator);
	    }
		
		/*
		 * Sends the mail when the user assigns to a role
		 */
		public void sendMailUserRole(final String from,final String to,final String subject,final Map<String,Object> model,final String type){
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
				@Override
				public void prepare(MimeMessage mimemessage) throws Exception {
					// TODO Auto-generated method stub
					MimeMessageHelper message = new MimeMessageHelper(mimemessage);
					message.setTo(to);
					message.setFrom(from);
					message.setSubject(subject);
					String text = null;
					if(type.equalsIgnoreCase("again"))
						text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mailtemplateuserroleagain.vm", model);
					else
						text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mailtemplateuserrole.vm", model);
					message.setText(text,true);
					
				}
			};
			this.mailSender.send(preparator);
		}
		
		/*
		 * send the mail when the user assigns for the project
		 */
		public void sendMailUserProject(final String from,final String to,final String subject,final Map<String,Object> model){
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					// TODO Auto-generated method stub
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					message.setTo(to);
					message.setFrom(from);
					message.setSubject(subject);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mailtemplateuserproject.vm", model);
					message.setText(text, true);
					
				}
			};
			this.mailSender.send(preparator);
		}
		
		/*
		 * sends the mail when the user removes from the project
		 */
		public void sendMailUserRemoveProject(final String from,final String to,final String subject,final Map<String,Object> model){
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					// TODO Auto-generated method stub
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					message.setTo(to);
					message.setFrom(from);
					message.setSubject(subject);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mailtemplateuserremoveproject.vm", model);
					message.setText(text, true);
					
				}
			};
			this.mailSender.send(preparator);
		}
		
	/*
	 * sends the mail when the user assigns to a team
	 */
		public void sendUserTeamMail(final String from,final String to,final String subject,final Map<String, Object> model){
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					// TODO Auto-generated method stub
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					message.setTo(to);
					message.setFrom(from);
					message.setSubject(subject);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mailtemplateuserteammail.vm", model);
					message.setText(text, true);
				}
			};
			this.mailSender.send(preparator);
		}
		
		public void alertSysAdmin(final String from, final String to, final String subject, final Map<String, Object> model){
	    	
	    	MimeMessagePreparator preparator = new MimeMessagePreparator(){
	    		public void prepare(MimeMessage mimeMessage) throws Exception{
	    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	    			message.setTo("koteswara.g@infotreesolutions.com");
	    			message.setFrom("anil.p@infotreesolutions.com");
	    			message.setSubject("Notification About Project EndDate");
	    			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
	    					"mailtemplatetodefaultuser.vm", model);
	    			message.setText(text, true);
	    		}
	    	};
	    	this.mailSender.send(preparator);
	    }
	 
	 /*
	  * for application administrator
	  */
	 public void alertAppAdmin(final String from, final String to, final String subject, final Map<String, Object> model){
	 	
	 	MimeMessagePreparator preparator = new MimeMessagePreparator(){
	 		public void prepare(MimeMessage mimeMessage) throws Exception{
	 			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	 			message.setTo("anil.p@infotreesolutions.com");
	 			message.setFrom("anil.p@infotreesolutions.com");
	 			message.setSubject("Notification About Project EndDate");
	 			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
	 					"mailtemplatetodefaultuser.vm", model);
	 			message.setText(text, true);
	 		}
	 	};
	 	this.mailSender.send(preparator);
	 }
    /**
     * @param alert
     */
    public void sendAlertMail(String alert){

    	SimpleMailMessage mailMessage = new SimpleMailMessage();
    	mailMessage.setText(alert);
    	mailSender.send(mailMessage);
    }
}
