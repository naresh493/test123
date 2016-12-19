package com.infotree.qliktest.admin.dao.referencedata;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.common.constants.ProjectTypeEnum;
import com.infotree.qliktest.admin.entity.errorlogs.AutomationExecutionErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.BrokenlinksErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.ErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.ImportTestCasesErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.ObjectSpyErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.ParallelExecutionErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.PerformanceErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.QlikTestErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.QuartzSchedulerErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.SikuliErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.SocketWebLogPojo;
import com.infotree.qliktest.admin.entity.errorlogs.VideoCaptureErrorLogs;
import com.infotree.qliktest.admin.helperpojo.ErrorLogFormPojo;
import com.mongodb.gridfs.GridFSDBFile;

@Repository
public class ErrorLogsDaoImpl implements ErrorLogsDao {

	@Autowired
	@Qualifier(value="exceptionMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Autowired
	@Qualifier(value="gridTemplate")
	private GridFsTemplate gridFsTemplate;
	
	/*public int logCount( ) {
		long count = mongoTemplate.count(new Query(), SikuliErrorLogs.class);
		return (int) count;
	}
	
	@Override
	public List<ErrorLogs> getAllLogs() {
		
		List<ErrorLogs> loggingInfo=null; 
		System.out.println("Total records : "+logCount());
		List<SikuliErrorLogs> sikuliLoggingInfo=mongoTemplate.findAll(SikuliErrorLogs.class);
		//mongoTemplate.count(query, entityClass)
		
		List<QlikTestErrorLogs> qlikTestLoggingInfo=mongoTemplate.findAll(QlikTestErrorLogs.class);
		List<VideoCaptureErrorLogs> videoCaptureLoggingInfo=mongoTemplate.findAll(VideoCaptureErrorLogs.class);
		List<ObjectSpyErrorLogs> objectSpyLoggingInfo=mongoTemplate.findAll(ObjectSpyErrorLogs.class);
		List<QuartzSchedulerErrorLogs> quartzScheduler=mongoTemplate.findAll(QuartzSchedulerErrorLogs.class);
		List<ParallelExecutionErrorLogs> parallelExecution=mongoTemplate.findAll(ParallelExecutionErrorLogs.class);
		List<PerformanceErrorLogs> performance=mongoTemplate.findAll(PerformanceErrorLogs.class);
		
		
		loggingInfo=new ArrayList<ErrorLogs>();
		
		for(SikuliErrorLogs log:sikuliLoggingInfo){
			loggingInfo.add(log);
			
		}
		
		
		 * for(QlikTestErrorLogs log:qlikTestLoggingInfo){
			loggingInfo.add(log);
		}
		for(VideoCaptureErrorLogs log:videoCaptureLoggingInfo){
			loggingInfo.add(log);
		}
		for(ObjectSpyErrorLogs log:objectSpyLoggingInfo){
			loggingInfo.add(log);
		}
		for(QuartzSchedulerErrorLogs log:quartzScheduler){
			loggingInfo.add(log);
		}
		for(ParallelExecutionErrorLogs log:parallelExecution)
		{
			loggingInfo.add(log);
		}
		for(PerformanceErrorLogs log:performance)
		{
			loggingInfo.add(log);
		}
		return loggingInfo;
	}
*/
	
	@SuppressWarnings("deprecation")
	@Override
	public List<ErrorLogs> getLogsByTimeAndProjectName(
			ErrorLogFormPojo errorLogFormPojo,int page,int pageLimit) {
		String projectType=errorLogFormPojo.getProjectType();
		Date startDate=errorLogFormPojo.getStartDate();
		Date endDate=errorLogFormPojo.getEndDate();
		Query query = new Query();
		if(startDate!=null && endDate!=null)
		{
			//endDate.setDate(endDate.getDate()+1);
			endDate.setDate(endDate.getDate()+1);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
			query.addCriteria(
					Criteria.where("timestamp").gte(startDate)
					.andOperator(
						Criteria.where("timestamp").lt(endDate)));
		}
		else if(startDate==null && endDate!=null)
		{
			endDate.setDate(endDate.getDate()+1);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
			query.addCriteria(Criteria.where("timestamp").lt(endDate));
		}
		else if(startDate!=null && endDate==null)
		{
			query.addCriteria(Criteria.where("timestamp").gte(startDate));
		}
		
		if(errorLogFormPojo.getLevel() != null && errorLogFormPojo.getLevel() != ""){
			query.addCriteria(Criteria.where("level").is(errorLogFormPojo.getLevel()));
		}
		query.skip(page * pageLimit);
		query.limit(pageLimit);   
		
		if(ProjectTypeEnum.QLIKTEST_ADMIN.getProjectType().equalsIgnoreCase(projectType))
		{
			List<QlikTestErrorLogs> qlikTestLogInfo=null;
			qlikTestLogInfo=mongoTemplate.find(query, QlikTestErrorLogs.class);
			return new ArrayList<ErrorLogs>(qlikTestLogInfo);
		}
		else if(ProjectTypeEnum.SIKULI.getProjectType().equalsIgnoreCase(projectType))
		{
			List<SikuliErrorLogs> sikuliLogInfo=null;
			sikuliLogInfo=mongoTemplate.find(query, SikuliErrorLogs.class);		
			
			return new ArrayList<ErrorLogs>(sikuliLogInfo);
		}
		else if(ProjectTypeEnum.VIDEO_CAPTURE.getProjectType().equalsIgnoreCase(projectType))
		{
			List<VideoCaptureErrorLogs> videoCaptureLogInfo=null;
			videoCaptureLogInfo=mongoTemplate.find(query, VideoCaptureErrorLogs.class);		
			
			return new ArrayList<ErrorLogs>(videoCaptureLogInfo);
		}
		else if(ProjectTypeEnum.OBJECT_SPY.getProjectType().equalsIgnoreCase(projectType))
		{
			List<ObjectSpyErrorLogs> objectSpyLogInfo=null;
			objectSpyLogInfo=mongoTemplate.find(query, ObjectSpyErrorLogs.class);
			
			return new ArrayList<ErrorLogs>(objectSpyLogInfo);
		}
		else if(ProjectTypeEnum.QUARTZ_SCHEDULER.getProjectType().equalsIgnoreCase(projectType)){
			
			List<QuartzSchedulerErrorLogs> quartzSchedulerLogInfo=null;
			quartzSchedulerLogInfo=mongoTemplate.find(query, QuartzSchedulerErrorLogs.class);
			
			return new ArrayList<ErrorLogs>(quartzSchedulerLogInfo);
		}
		else if(ProjectTypeEnum.PARALLEL_EXECUTION.getProjectType().equalsIgnoreCase(projectType)){
			List<ParallelExecutionErrorLogs> errorLogs=mongoTemplate.find(query, ParallelExecutionErrorLogs.class);
			return new ArrayList<ErrorLogs>(errorLogs);
		}
		else if(ProjectTypeEnum.PERFORMANCE.getProjectType().equalsIgnoreCase(projectType)){
			List<PerformanceErrorLogs> errorLogs=mongoTemplate.find(query, PerformanceErrorLogs.class);
			return new ArrayList<ErrorLogs>(errorLogs);
		}
		else if(ProjectTypeEnum.IMPORT_TESTCASES.getProjectType().equalsIgnoreCase(projectType)){
			List<ImportTestCasesErrorLogs> errorLogs = null;
			try{
					errorLogs = mongoTemplate.find(query, ImportTestCasesErrorLogs.class);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ArrayList<ErrorLogs>(errorLogs);
		}
		else if(ProjectTypeEnum.BROKENLINKS.getProjectType().equalsIgnoreCase(projectType)){
			List<BrokenlinksErrorLogs> errorLogs = null;
			try{
					errorLogs = mongoTemplate.find(query, BrokenlinksErrorLogs.class);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ArrayList<ErrorLogs>(errorLogs);
		}
		else if(ProjectTypeEnum.AUTOMATIONEXECUTION.getProjectType().equalsIgnoreCase(projectType)){
			List<AutomationExecutionErrorLogs> errorLogs = null;
			try{
					errorLogs = mongoTemplate.find(query, AutomationExecutionErrorLogs.class);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ArrayList<ErrorLogs>(errorLogs);
		}
		return null;
	}


	@SuppressWarnings("deprecation")
	@Override
	public List<SocketWebLogPojo> getSocketWebLogs(
			ErrorLogFormPojo errorLogFormPojo) 
		{
		Pattern namesRegex = Pattern.compile("^socketweblog.*");
		Query query=new Query();
		Date startDate = errorLogFormPojo.getStartDate();
		Date endDate = errorLogFormPojo.getEndDate();
		if(startDate!=null && endDate!=null)
		{
			endDate.setDate(endDate.getDate()+1);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
			query.addCriteria(
					Criteria.where("uploadDate").gte(startDate)
					.andOperator(
						Criteria.where("uploadDate").lte(endDate)
					.andOperator(Criteria.where("filename").regex(namesRegex))));
				
		}
		else if(startDate==null && endDate!=null)
		{
			endDate.setDate(endDate.getDate()+1);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
			query.addCriteria(Criteria.where("uploadDate").lte(endDate)
					.andOperator(Criteria.where("filename").regex(namesRegex)));
		}
		else if(startDate!=null && errorLogFormPojo.getEndDate()==null)
		{
			query.addCriteria(Criteria.where("uploadDate").gte(startDate)
					.andOperator(Criteria.where("filename").regex(namesRegex)));
		}
		else{
			query.addCriteria(Criteria.where("filename").regex(namesRegex));
		}
		List<GridFSDBFile> files = null;
		try{
			files=gridFsTemplate.find(query);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//List<GridFSDBFile> files=gridFsTemplate.find(query);
		List<SocketWebLogPojo> list=new ArrayList<SocketWebLogPojo>();
		SocketWebLogPojo socket=null;
		
		for (GridFSDBFile file : files) {
			socket=new SocketWebLogPojo();
			socket.setFilename(file.getFilename());
			socket.setUploadDate(file.getUploadDate());
			socket.setId(file.getId().toString());
			list.add(socket);
		}
		
		return list;
	}

	
	@Override
	public InputStream getSocketWebFile(String id) {
		
		//Query query=new Query(Criteria.where("_id").all(new ObjectId(id)));
		Query query=new Query(Criteria.where("_id").is(new ObjectId(id)));
		List<GridFSDBFile> list=gridFsTemplate.find(query);
		if(list.size()>0)
		{
			return list.get(0).getInputStream();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public int getColumnCount(ErrorLogFormPojo errorLogFormPojo){
		
		String projectType=errorLogFormPojo.getProjectType();
		Date startDate=errorLogFormPojo.getStartDate();
		Date endDate=errorLogFormPojo.getEndDate();
		Query query = new Query();
		if(startDate!=null && endDate!=null)
		{
			endDate.setDate(endDate.getDate()+1);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
			query.addCriteria(
					Criteria.where("timestamp").gte(startDate)
					.andOperator(
						Criteria.where("timestamp").lt(endDate)));
		}
		else if(startDate==null && endDate!=null)
		{
			endDate.setDate(endDate.getDate()+1);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
			query.addCriteria(Criteria.where("timestamp").lt(endDate));
		}
		else if(startDate!=null && endDate==null)
		{
			query.addCriteria(Criteria.where("timestamp").gte(startDate));
		}
		
		if(errorLogFormPojo.getLevel() != null && errorLogFormPojo.getLevel() != ""){
			query.addCriteria(Criteria.where("level").is(errorLogFormPojo.getLevel()));
		}
		
		if(ProjectTypeEnum.QLIKTEST_ADMIN.getProjectType().equalsIgnoreCase(projectType))
		{
			return (int)mongoTemplate.count(query, QlikTestErrorLogs.class);
		}
		else if(ProjectTypeEnum.SIKULI.getProjectType().equalsIgnoreCase(projectType))
		{
			return (int)mongoTemplate.count(query, SikuliErrorLogs.class);		
		}
		else if(ProjectTypeEnum.VIDEO_CAPTURE.getProjectType().equalsIgnoreCase(projectType))
		{
			return (int)mongoTemplate.count(query, VideoCaptureErrorLogs.class);		
		}
		else if(ProjectTypeEnum.OBJECT_SPY.getProjectType().equalsIgnoreCase(projectType))
		{
			return (int)mongoTemplate.count(query, ObjectSpyErrorLogs.class);
		}
		else if(ProjectTypeEnum.QUARTZ_SCHEDULER.getProjectType().equalsIgnoreCase(projectType)){
			
			return (int)mongoTemplate.count(query, QuartzSchedulerErrorLogs.class);
		}
		else if(ProjectTypeEnum.PARALLEL_EXECUTION.getProjectType().equalsIgnoreCase(projectType)){
			return (int)mongoTemplate.count(query, ParallelExecutionErrorLogs.class);
		}
		else if(ProjectTypeEnum.PERFORMANCE.getProjectType().equalsIgnoreCase(projectType)){
			return (int)mongoTemplate.count(query, PerformanceErrorLogs.class);
		}
		else if(ProjectTypeEnum.IMPORT_TESTCASES.getProjectType().equalsIgnoreCase(projectType)){
			return (int)mongoTemplate.count(query, ImportTestCasesErrorLogs.class);
		}
		else if(ProjectTypeEnum.BROKENLINKS.getProjectType().equalsIgnoreCase(projectType)){
			return (int)mongoTemplate.count(query, BrokenlinksErrorLogs.class);
		}
		else if(ProjectTypeEnum.AUTOMATIONEXECUTION.getProjectType().equalsIgnoreCase(projectType)){
			return (int)mongoTemplate.count(query, AutomationExecutionErrorLogs.class);
		}
		return 0;
	}
}
