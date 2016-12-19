<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/userprofile/savedefaultusertotenant" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Default User To Tenant</b></font><br /><br />
				</td>
			</tr>
			<c:choose>
				<c:when test="${not empty userassignedtotenant and userassignedtotenant eq 'Error in saving data. Pl. try again.'}">
					<tr>
						<td colspan="2" align="center">
							<font color="red" size="2"><b>${userassignedtotenant}</b></font><br /><br />
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="2" align="center">
							<font color="green" size="2"><b>${userassignedtotenant}</b></font><br /><br />
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<table class="tblFormData">
			<tr>
				<td>
					<form:label path="id">Select User</form:label>
				</td>
				<td>
					<form:select path="id">
						<form:options items="${default_user_list}" itemLabel="firstName" itemValue="id" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td>
					<form:label path="firstName">Select Tenant<br /></form:label>
				</td>
				<td>
					<form:select id="tenantList" path="firstName">
						<form:options items="${tenant_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<c:if test="${empty crudObj.id}">
					  	 <input type="submit" class="btn btn-primary" value="Save" />&nbsp;
					</c:if>
		        </td>
			<tr>
		</table>
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
        <spring:bind path="disabled">
              <form:hidden path="disabled" value="0" />
        </spring:bind>
	</form:form>
	