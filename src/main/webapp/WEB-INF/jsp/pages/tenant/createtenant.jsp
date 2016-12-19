<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/tenant/save" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create New Tenant</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty tenantcreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${tenantcreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty projectsnotselected}'> 
				<tr>
					<td colspan="2" align="center">
						<div class="alert-message error fade in" data-alert="alert" >
							<a class="close" href="#">&times;</a>
							<p>${tenantcreated}</p>
						</div>
						<font color="red" size="2"><b>${projectsnotselected}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty tenantalreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${tenantalreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table id="tblCreateTenant" class="tblFormData">
			<tr>
				<td>
					<form:label for="id" path="id">Existing Tenants<br /><font color="green" size="2"><b>(cannot be edited)</b></font></form:label>
				</td>
				<td>
					<form:select id="tenantList" path="id" multiple="multiple" disabled="true">
						<form:options items="${tenant_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="name" path="name">Add New Tenant</form:label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="tenantName" path="name" size="30" type="text" class="input-large" placeholder="Tenant Name" required="true"/>
			              <form:errors path="name" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
	<%-- 		<tr>
				<td><label>First Name *</label></td>
				<td>
					<spring:bind path="firstName">
			              <form:input path="firstName" size="30" type="text" class="input-large" placeholder="First Name" required="true"/>
			              <form:errors path="firstName" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Sur Name *</label></td>
				<td>
					<spring:bind path="surName">
			              <form:input path="surName" size="30" type="text" class="input-large" placeholder="Sur Name"/>
			              <form:errors path="surName" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Email Address *</label></td>
				<td>
					<spring:bind path="emailAddress">
			              <form:input path="emailAddress" size="30" type="text" class="input-large" placeholder="Email Address" required="true"/>
			              <form:errors path="emailAddress" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Password</label></td>
				<td>
					<spring:bind path="password">
			              <form:input path="password" size="30" type="text" class="input-large" placeholder="Password" required="true"/>
			              <form:errors path="password" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Land line</label></td>
				<td>
					<spring:bind path="landline">
			              <form:input path="landline" size="50" type="text" class="input-large" placeholder="Landline"/>
			              <form:errors path="landline" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Mobile</label></td>
				<td>
					<spring:bind path="mobile">
			              <form:input path="mobile" size="50" type="text" class="input-large" placeholder="Mobile"/>
			              <form:errors path="mobile" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
						  </spring:bind>
					</td>
			</tr> --%>
			
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
	        
	         <spring:bind path="disabled">
	              <form:hidden path="disabled" value="0" />
	        </spring:bind>
			<tr>
				<td colspan="2" align="center">
					
					<c:if test="${empty crudObj.id}">
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Create" onsubmit="return doValidationForCreateTenant();" />&nbsp;
					</c:if>
		        </td>
			<tr>
		</table>
	</form:form>
	