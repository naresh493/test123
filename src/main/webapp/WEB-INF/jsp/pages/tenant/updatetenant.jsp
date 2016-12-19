 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/tenant/updateTenant" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Update Tenant</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty tenantupdated}'> 
				<tr>
					<td colspan="2" align="center"><font color="green" size="3">${tenantupdated}</font><br /><br />
				</td>
			</tr>
			</c:if>
		</table>
		<table class="tblFormData">
			<tr>
				<td><label>Tenant Name <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="tenantName">
			              <form:input path="tenantName" size="30" type="text" pattern="[a-zA-Z0-9_]*" class="input-large" autocomplete="off" placeholder="Tenant Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			
			
			<tr id="trisActive">
				<td><label>First Name <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="firstName">
			              <form:input path="firstName" size="30" type="text" pattern="[a-zA-Z0-9_]*" class="input-large"  autocomplete="off" placeholder="First Name" required="true"/>
			             
			        </spring:bind>
				</td>
				</tr>
				<tr>
				<td><label>Last Name <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="surName">
			              <form:input path="surName" size="30" type="text" pattern="[a-zA-Z0-9_]*" class="input-large" autocomplete="off" placeholder="Sur Name" required="true"/>
			              
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Email <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="emailAddress">
			              <form:input path="emailAddress" size="50" type="text" class="input-large" autocomplete="off" placeholder="Email Address"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Land Line</label></td>
				<td>
					<spring:bind path="landline">
			              <form:input path="landline" size="50" type="text" pattern="[0-9]*" class="input-large" autocomplete="off" placeholder="Landline"/>
			              
			        </spring:bind>
			    </td>
			</tr>
			
			<tr>
				<td><label>Mobile </label></td>
				<td>
					<spring:bind path="mobile">
			              <form:input path="mobile" size="50" type="text" pattern="[0-9]*" class="input-large" autocomplete="off" placeholder="Mobile"/>
			              
						  
			        </spring:bind>
			        </td>
			        </tr>
			        
			        
			<tr>
				<td>
					<spring:bind path="createdBy">
	              		<form:hidden path="createdBy" value="${sessionScope.userid}" />
			        </spring:bind>
			        <%-- <spring:bind path="emailAddress">
	              		<form:hidden path="emailAddress" value="${crudObj.emailAddress}" />
			        </spring:bind> --%>
			        <spring:bind path="createdDate">
			              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
			        </spring:bind> 
			        <spring:bind path="modifiedBy">
			              <form:hidden path="modifiedBy" value="${sessionScope.userid}" />
			        </spring:bind>
			        
			        <spring:bind path="modificationDate">
			              <form:hidden path="modificationDate" value="<%=new java.util.Date()%>" />
			        </spring:bind>
			        <spring:bind path="id">
			              <form:hidden path="id" value="${idToBeUpdated}" />
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary" value="Update" />&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			<tr>
		</table>
	</form:form>
	