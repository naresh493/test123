 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<html>
<head>

</head>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/reports/updatereports" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Update Report</b></font><br /><br />
				</td>
			</tr>
			<c:choose>
				<c:when test='${not empty reportupdated and reportupdated eq "Data cannot be saved. Pl. try again."}'> 
					<tr>
							<td colspan="2" align="center"><font color="red" size="3">${reportupdated}</font><br /><br /></td>
					</tr>
				</c:when>
				<c:when test='${not empty reportupdated}'> 
					<tr>
							<td colspan="2" align="center"><font color="green" size="3"><b>${reportupdated}</b></font><br /><br /></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</table>
		<table class="tblFormData">
			<tr>
				<td><label> Name<font color='red'>*</font></label></td>
				<td>
					<spring:bind path="name">
			              <form:input path="name" autocomplete="off" size="30" type="text" class="input-large" placeholder="Project Name" disabled="true"/>
			              
			             
			        </spring:bind>
				</td>
			<tr>
			<tr>
				<td>
					<form:label for="displayname" path="displayname">Display Name <font color="red" size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="displayname">
			              <form:input type="text" autocomplete="off" title="Select date from datepicker" size="5" path="displayname" itemLabel="startDate" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="description" path="description">Description <font color="red" size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="description">
			              <form:input type="text" autocomplete="off" title="Select date from datepicker" size="5" path="description" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			</tr>
			<%-- <spring:bind path="createdBy">
             		<form:hidden path="createdBy" value="${sessionScope.username}" />
	        </spring:bind>
	        
	        <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind> --%>
	        
	        <spring:bind path="modifiedBy">
	              <form:hidden path="modifiedBy" value="${sessionScope.username}" />
	        </spring:bind>
	        
	        <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	        
	        <spring:bind path="id">
	              <form:hidden path="id" value="${idToBeUpdated}" />
	        </spring:bind>
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary" value="Update" />
		        </td>
			<tr>
		</table>
	</form:form>
	</html>