<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	session.removeAttribute("username");
	session.removeAttribute("roleName");
	session.removeAttribute("permList");
	session.removeAttribute("generalList");
	session.removeAttribute("userList");
	session.removeAttribute("tenantList");
	session.removeAttribute("projectList");
	session.removeAttribute("moduleList");
	session.removeAttribute("teamList");
	session.removeAttribute("objectList");
	session.removeAttribute("testCaseList");
	session.removeAttribute("testSuiteList");
	session.removeAttribute("licenseList");
	session.invalidate(); 
%>	
You are now logged out!!

<c:out value="${pageContext.request.contextPath}" />
<a href="${pageContext.request.contextPath}">go back</a>