<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/password/updatepassword" />
  </c:set>
  
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
	<div style="margin:90px auto; width:400px; height:300px">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Change Password</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty passwordchanged}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${passwordchanged}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>
		
		<table id="tblCreateProject" class="tblFormData">
			
			<tr>
				<td><label>Current Password <font color='red'>*</font></label></td>
				<td>
					<spring:bind  path="currentPassword">
			        	<form:input id="cpassword" path="currentPassword" size="30" type="password" class="input-large" placeholder="Current Password" required="true"/>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>New Password <font color='red'>*</font></label></td>
				<td>
					<spring:bind  path="newPassword">
			              <form:input id="password" path="newPassword" size="30" type="password" class="input-large" placeholder="New Password" required="true"/>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Confirm Password <font color='red'>*</font></label></td>
				<td>
					<spring:bind  path="conformNewPassword">
			              <form:input id="conformPassword" path="conformNewPassword" size="30" type="password" class="input-large" placeholder="Confirm Password" required="true"/>
			        </spring:bind>
				</td>
			</tr>
		
			<tr>
				<td colspan="2" align="center">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Change Password" onclick="return CheckPwdAndCpwd()"/>&nbsp;
		        </td>
			<tr>
		</table>
		<script type="text/javascript">
			function CheckPwdAndCpwd(){
					var currentpwd=document.getElementById('cpassword');
					var password=document.getElementById("password");
					var cpassword=document.getElementById("conformPassword");
					if(currentpwd.value.length == 0){
						alert("Please enter current password");
						return false;
					}else if(password.value.length == 0) {
						alert("Please enter the new Password");
						return false;
					}else if(cpassword.value.length == 0){
						alert("Please enter the confirm password");
						return false;
					} else{
						
						if(password.value != cpassword.value){
							alert("NewPassword should Be Same as Confirm Password");
							return false;
						}else{
							return true;
						}
					}
					
			}
		</script>
		</div>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
			
		
	</form:form>
	