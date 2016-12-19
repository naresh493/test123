<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>



<c:set var="pageUrl" scope="request">
	 <c:url value="/content/ldapconfiguration/saveldap" />
  </c:set>
  
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>LDAP Configuration</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty ldapconfigurationcreated}'> 
				<tr>
					<td colspan="2" align="center">
						<b>${ldapconfigurationcreated}</b><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>
		<table class="tblFormData">
		<tr>
				<td>
					<label>Name <font color='red' size='3'>*</font></label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="configName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="name" size="30" type="text" class="input-large" placeholder="Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
		<tr>
				<td>
					<label>Server IP <font color='red' size='3'>*</font></label>
				</td>
				<td>
					<spring:bind path="serverip">
			              <form:input id="configName" title="Only Alphabets,Numbers and Underscore" path="serverip" size="30" type="text" class="input-large" placeholder="IpAddress" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
		
			<tr>
				<td><label>Port Number <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="port">
			              <form:input path="port" size="30" type="text" class="input-large" placeholder="Port" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			
			<tr>
				<td><label>Ldap Base <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="base">
			              <form:input id="port" path="base" title="only numerics" size="30" type="text" class="input-large" placeholder="Base" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Ldap UserName <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="username">
			               <form:input path="username" size="30" title="Only Numbers" type="text" class="input-large" placeholder="UserName" />
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Ldap Password <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="password">
			               <form:input path="password" size="30" title="Only Numbers" type="password" class="input-large" placeholder="Password" />
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td>
					<form:label for="tenants" path="tenantId">Select Tenant<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<form:select id="tenants" path="tenantId" required="true">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${tenant_list}" itemLabel="name" itemValue="id" />
					</form:select>
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
					  	 <input type="submit" class="btn btn-primary" value="Save"/> 
					</c:if>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
					
		       		<input type="reset" class="btn btn-primary" value="Reset" />
		        </td>
			<tr>
		</table>
	</form:form>
	