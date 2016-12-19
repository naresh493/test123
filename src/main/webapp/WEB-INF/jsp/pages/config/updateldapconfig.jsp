 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/ldapconfiguration/updateLdap" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Edit Ldap Configuration</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty configurationupdated}'> 
				<tr>
					<td colspan="2" align="center"><b>${configurationupdated}</b><br /><br />
				</td>
			</tr>
			</c:if>
		</table>
		<table class="tblFormData">
			<tr>
				<td>
					<form:label for="ldapName" path="name">Name <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="ldapName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="name" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="ldapIp" path="serverip">Server Ip <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="serverip">
			              <form:input id="ldapIp" title="Only Alphabets,Numbers and Underscore" path="serverip" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="ldapPort" path="port">Port <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="port">
			              <form:input id="ldapPort" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="port" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="ldapBase" path="base">Base <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="base">
			              <form:input id="ldapBase" path="base" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="userName" path="username">UserName <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="username">
			              <form:input id="userName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="username" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			<spring:bind path="id">
				<form:hidden path="id"/>
	        </spring:bind>
			
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
	