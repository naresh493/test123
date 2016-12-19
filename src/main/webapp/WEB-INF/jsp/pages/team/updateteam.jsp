 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/team/update" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Update Team</b></font><br /><br />
				</td>
			</tr>
			<c:choose>
				<c:when test='${not empty teamupdated and teamupdated eq "Data cannot be saved. Pl. try again."}'> 
					<tr>
							<td colspan="2" align="center"><font color="red" size="3">${teamupdated}</font><br /><br /></td>
					</tr>
				</c:when>
				<c:when test='${not empty teamupdated}'> 
					<tr>
							<td colspan="2" align="center"><font color="green" size="3"><b>${teamupdated}</b></font><br /><br /></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</table>
		<table class="tblFormData">
			<tr>
				<td><label>Team Name</label></td>
				<td>
					<spring:bind path="name">
			              <form:input path="name" size="30" type="text" class="input-large" placeholder="Team Name" required="true"/>
			              <form:errors path="name" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			
			<%-- <spring:bind path="createdBy">
             		<form:hidden path="createdBy" value="${sessionScope.username}" />
	        </spring:bind>
	        
	        <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind> --%>
	        
	        <spring:bind path="modifiedBy">
	              <form:hidden path="modifiedBy" value="${sessionScope.userid}" />
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
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			<tr>
		</table>
	</form:form>
	