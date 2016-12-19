<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/removeuser" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner" >
			<tr>
				<td align="center"><font size="3"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Delete User</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty userdeleted}'> 
				<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="green" size="2"><b>${userdeleted}</b></font><br /><br />
				</td>
			</tr>
			</c:if>
		</table>
		<table class="tblFormData" >
			<tr>
				<td align="center">
					<spring:bind path="id">
					 	<form:select path="id" id="projectId" name="projectId" cssClass="selectpicker show-menu-arrow">
					 		 <form:option label="Select User" value="0" selected="selected"/>
			                 <form:options items="${active_users}" itemLabel="firstName" itemValue="id" />
			            </form:select>
			        </spring:bind>
				</td>
			<tr>
			<tr>
				<td class="tblFormData" align="center"><br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary :hover" value="Delete" />&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        </td>
			<tr>
		</table><br /><br /><br /><br /><br /><br /><br /><br /><br />
	</form:form>
	