<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/team/save" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create New Team</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty teamcreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${teamcreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty teamalreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${teamalreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table id="tblCreateProject" class="tblFormData">
			
			<tr>
				<td>
					<form:label for="name" path="name">Team Name<font color='red'>*</font></form:label>
				</td>
				<td>
					<spring:bind  path="name">
			              <form:input id="teamName" path="name" size="30" type="text" class="input-large" placeholder="Team Name" required="true"/>
			        </spring:bind>
				</td>
			</tr>
			
			<tr>
				<td>
					<form:label id="description" for="description" path="description">Description</form:label>
				</td>
				<td>
					<spring:bind  path="description">
			              <form:textarea id="description" maxlength="255" path="description" rows="3" cols="28" type="text" class="input-large"  placeholder="Description  (Maximum 255 Characters)"/>
			        </spring:bind>
				</td>
			</tr>
			
			<spring:bind path="createdBy">
	              <form:hidden path="createdBy" value="${sessionScope.userid}" />
	        </spring:bind>
	        
	        <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	        
	        <spring:bind path="modifiedBy">
	              <form:hidden path="modifiedBy" value="${sessionScope.userid}" />
	        </spring:bind>
	        
	        <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	        
	       
			<tr>
				<td colspan="2" align="center">
					
					<c:if test="${empty crudObj.id}">
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Create" onclick="return validatePage()"/>&nbsp;
					  	 <input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
						 <input type="reset" class="btn btn-primary"  value="Reset" />
					</c:if>
		        </td>
			<tr>
		</table>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript">
			function validatePage() {
				var team = document.getElementById('teamName').value;
				if(team == null || team == "") {
					alert('Please enter the team name');
					return false;
				} else { 
					return true;
				}
			}
		</script>
	</form:form>
	