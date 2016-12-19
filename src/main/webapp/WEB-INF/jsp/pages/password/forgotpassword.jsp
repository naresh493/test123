<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/password/getpassword" />
  </c:set>
<script type="text/javascript">
	function validatePage() {
		var mail = document.getElementById('username').value;
		if(mail == null || mail == ""){
			alert("Please enter the UserName");
			return false;
		}else{
			return true;
		}
	}
</script>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
	<div style="margin:90px auto; width:400px; height:300px">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Forgot Password</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty passwordsent}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${passwordsent}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>
		
		<table id="tblCreateProject" class="tblFormData">
			
			<tr>
				<td>
					<form:label id="userName" for="userName" path="userName">User Name<font color='red'>*</font></form:label>
				</td>
				<td>
					<spring:bind  path="userName">
			              <form:input id="username" path="userName" size="30" type="text" class="input-large" placeholder="User Name" required="true"/>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Get Password" onclick="return validatePage()"/>&nbsp;
		        </td>
			<tr>
		</table>
		<c:if test='${not empty passwordsent}'> 
				<tr>
					<td colspan="2" align="center">
						<a href=<%= request.getContextPath() %>>Login Again</a>
					</td>
				</tr>
			</c:if>
		</div>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
			
		<script type="text/javascript">
		
		
		</script>
	</form:form>
	