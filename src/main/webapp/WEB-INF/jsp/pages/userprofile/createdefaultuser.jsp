<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<script type="text/javascript">
	function checkMail(){
		var email = document.getElementById("emailid").value;
		var atpos = email.indexOf("@");
	    var dotpos = email.lastIndexOf(".");
	    if(atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
			alert("Not A Valid Email Address");
			return false;
		}else{
			return true;
		}
	}
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/userprofile/savedefaultuser" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="curdObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create Tenant</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty defaultusercreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${defaultusercreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty defaultuserexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${defaultuserexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table class="tblFormData">
		<tr>
				<td>
					<form:label for="name" path="tenantName">Tenant Name <font color="red" size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="tenantName">
			              <form:input id="tenantName" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" path="tenantName" size="30" type="text" class="input-large" placeholder="Tenant Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			<tr>
				<td>
					<form:label for="companyName" path="companyName">Company Name <font color="red" size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="companyName">
			              <form:input id="companyName" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" path="companyName" size="30" type="text" class="input-large" placeholder="Company Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			<tr>
				<td>
					<form:label for="displayName" path="displayName">Display Name <font color="red" size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="displayName">
			              <form:input id="displayName" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" path="displayName" size="30" type="text" class="input-large" placeholder="Display Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			
			<tr>
				<td><label>First Name <font color="red" size="3">*</font></label></td>
				<td>
					<spring:bind path="firstName">
			              <form:input path="firstName" id="firstname" size="30" autocomplete="off"  pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" type="text" class="input-large" placeholder="First Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Last Name <font color="red" size="3">*</font></label></td>
				<td>
					<spring:bind path="surName">
			              <form:input path="surName" size="30" pattern="[a-zA-Z0-9_]*" autocomplete="off" title="Only Alphabets,Numbers and Underscore" type="text" class="input-large" placeholder="Last Name" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>User Name <font color="red" size="3">*</font></label></td>
				<td>
					<spring:bind path="userName">
			              <form:input path="userName" id="userName" size="30" autocomplete="off"  pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" type="text" class="input-large" placeholder="User Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Email Address <font color="red" size="3">*</font></label></td>
				<td>
					<spring:bind path="emailAddress">
			              <form:input path="emailAddress" id="emailid" size="30" type="text" autocomplete="off"  class="input-large" placeholder="Email Address" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Land Line </label></td>
				<td>
					<spring:bind path="landline">
			              <form:input path="landline" pattern="[0-9]*" autocomplete="off"  title="only numbers" size="50" type="tel" class="input-large" placeholder="Landline"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			
			<tr>
				<td><label>Mobile <font color="red" size="3">*</font></label></td>
				<td>
					<spring:bind path="mobile">
			              <form:input path="mobile" pattern="[0-9]*" title="only numbers" autocomplete="off"  size="50" type="tel" class="input-large" placeholder="Mobile" required="true"/>
			             
			        </spring:bind>
			        </td>
			        </tr>
			        
		 	
			 <tr>
			 
		 	
			 <tr>
			        <td>
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
			        <spring:bind path="isPasswordChangeRequired">
			              <form:hidden path="isPasswordChangeRequired" value="0" />
			        </spring:bind>
			       
			    </td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<c:if test="${empty crudObj.id}">
					  	 <input type="submit" class="btn btn-primary" value="Save" onclick="return checkMail()"/>
					</c:if>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		       		<input type="reset" class="btn btn-primary" value="Reset"/>

		        </td>
			<tr>
		</table>
		
		
	</form:form>
	