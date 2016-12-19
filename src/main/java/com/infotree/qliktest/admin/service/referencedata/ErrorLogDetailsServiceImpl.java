package com.infotree.qliktest.admin.service.referencedata;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.referencedata.ErrorLogsDao;
import com.infotree.qliktest.admin.entity.errorlogs.ErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.SocketWebLogPojo;
import com.infotree.qliktest.admin.helperpojo.ErrorLogFormPojo;

@Service
public class ErrorLogDetailsServiceImpl implements ErrorLogDetailsService{
	
	@Autowired
	private ErrorLogsDao errorLogsDAO;
	
	public List<ErrorLogs> getErrorLogsByDate(ErrorLogFormPojo errorLogFormPojo,int page,int pageLimit)
	{
		return errorLogsDAO.getLogsByTimeAndProjectName(errorLogFormPojo,page,pageLimit);
	}
	/*public List<ErrorLogs> getAllErrorLogs()
	{
		System.out.println("inside service");
		return errorLogsDAO.getAllLogs();
	}*/
	@Override
	public List<SocketWebLogPojo> getSocketWebLogs(
			ErrorLogFormPojo errorLogFormPojo) {
		return errorLogsDAO.getSocketWebLogs(errorLogFormPojo);
	}
	@Override
	public InputStream getSocketWebFile(String id) {
		return errorLogsDAO.getSocketWebFile(id);
	}
	@Override
	public int getColumnCount(ErrorLogFormPojo errorLogFormPojo) {
		return errorLogsDAO.getColumnCount(errorLogFormPojo) ;
	}
}
