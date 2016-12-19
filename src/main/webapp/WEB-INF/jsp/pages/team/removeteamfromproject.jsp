<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<html>
<style>
table.menu{
	font-size:100%;
	position:absolute;
}
</style>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/teamproject/removeselectedteams"/>
  </c:set>
 <form:form id="signup" class="form-removeuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblFormData">
		   <tr>
				<td colspan="2" align="center"><font size="3"><b>Remove Team From Projects</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty teamsremoved}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${teamsremoved}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			</table>
			
		
		<c:if test="${not empty project_list}">
		<table id="tblData" class="tblFormData">
			<tr id="trProject">
				<td>
					<form:label for="idProject" path="projectId">Select Project</form:label>
				</td>
				<td>
					<form:select id="idProject" path="projectId"  onchange="ManageRemoveTeams('team')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			
			
		
			<tr id="trTeams">
				<td>
					<form:label for="idTeams" path="teamId">Select Teams</form:label>
				</td>
				<td>
						<form:select id="idTeams" path="teamId" multiple="multiple" style="width:250px;height:70px;">
							<form:options itemValue="id"/>
						</form:select>
					
				</td>
			</tr>
			
			
			
			
			
			<spring:bind path="createdBy">
             		<form:hidden id="createdBy" path="createdBy" value="${sessionScope.userid}" />
	        </spring:bind>
	         <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	       <spring:bind path="modifiedBy">
	              <form:hidden id="modifiedBy" path="modifiedBy" value="${sessionScope.userid}" />
	        </spring:bind>
	         <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	      <tr>
				<td colspan="2" align="center">
					
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Remove" />&nbsp;
		        </td>
			<tr>
			</table>
			</c:if>
				
	      <c:if test="${empty project_list}">
			 <table class="tblFormData">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No projects Available*---------</font></td>
					
				</tr>
				</table>
				</c:if>
</form:form>
</body>
</html>