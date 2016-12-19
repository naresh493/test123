<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/project/save" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;
				<font size="3"><b>Manage Projects</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty projectcreated}'> 
				<tr id="trMsg">
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${projectcreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty projectalreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<%-- <div class="alert-message error fade in" data-alert="alert" >
							<a class="close" href="#">&times;</a>
							<p>${tenantalreadyexists}</p>
						</div> --%>
						<font color="red" size="2"><b>${projectalreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table style="text-align:center;" id="selectMenu" class="tblData">
			<tr>
				<!-- <td align="center"><input type="button" class="btn btn-secondary" value="Create" onclick="ManageModule('create')" /></td>
				<td align="center"><input type="button" class="btn btn-secondary" value="Edit" onclick="ManageModule('edit')" /></td>
				<td align="center"><input type="button" class="btn btn-secondary" value="Delete" onclick="ManageModule('delete')" /></td> -->
				<td align="center">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;
				<a style="underline" class="green" href="#" onclick="ManageProject('create')">Create</a></td>
				<td align="center">/ <a class="yellow" href="#" onclick="ManageProject('edit')">Edit</a></td>
				<td align="center">/ <a class="yellow" href="#" onclick="ManageProject('delete')">Delete</a></td>
			</tr>
		</table>
		<table class="tblFormData">
			<tr id="trCreateProject">
				<td>
					<form:label for="id" path="id">Existing Projects<br /></form:label>
				</td>
				<td>
					<form:select id="projectList" path="id" multiple="multiple" disabled="true">
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			<tr id="trCreateProject1">
				<td>
					<form:label for="startDate" path="startDate">Start Date</form:label>
				</td>
				<td>
					<spring:bind path="startDate">
			              <form:input type="text" class="datepicker" size="5" path="startDate" required="true" value="${item.startDate}"/>
			              <form:errors path="startDate" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<tr id="trCreateProject2">
				<td>
					<form:label for="endDate" path="endDate">End Date</form:label>
				</td>
				<td>
					<spring:bind path="endDate">
			              <form:input type="text" class="datepicker" size="5" path="endDate" required="true" value="${item.endDate}"/>
			              <form:errors path="endDate" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<tr id="trCreateProject3">
				<td>
					<form:label for="concurrentUsers" path="concurrentUsers">Define Concurrent Users</form:label>
				</td>
				<td>
					<spring:bind path="concurrentUsers">
			              <form:input type="text" size="5" path="concurrentUsers" required="true" value="${item.concurrentUsers}"/>
			              <form:errors path="concurrentUsers" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<c:forEach items="${project_list}" var="item">
				<tr id="trEditProject">
					<td>
						<form:radiobutton path="name" value="${item.name}" onclick="populateProjectName()"/>
					</td>
					<td>
						&nbsp;&nbsp;<form:input type="text" size="5" path="concurrentUsers" required="true" value="${item.name}"/><br />
						&nbsp;&nbsp;<form:input type="text" class="datepicker" size="5" path="startDate" required="true" value="${item.startDate}"/><br />
						&nbsp;&nbsp;<form:input type="text" class="datepicker" size="5" path="startDate" required="true" value="${item.startDate}"/><br />
						&nbsp;&nbsp;<form:input type="text" size="5" path="concurrentUsers" required="true" value="${item.concurrentUsers}"/>
					</td>
				</tr>
			</c:forEach>
			<tr id="trDeleteProject">
				<td>
					<form:label for="id" path="id">Select Project<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<table>
							<c:forEach items="${active_project_list}" var="item">
								<tr id="trChkBoxProject">
									<td><form:radiobutton path="name" value="${item.name}"/></td>
									<td>&nbsp;&nbsp;${item.name}<br /></td>
								</tr>
							</c:forEach>
					</table>
				</td>
			</tr>
			<%-- <tr id="trCreateProject1">
				<td>
					<form:label for="startDate" path="startDate">Start Date</form:label>
				</td>
				<td>
					<spring:bind path="startDate">
			              <form:input type="text" class="datepicker" size="5" path="startDate" required="true"/>
			              <form:errors path="startDate" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<tr id="trCreateProject2">
				<td>
					<form:label for="endDate" path="endDate">End Date</form:label>
				</td>
				<td>
					<spring:bind path="endDate">
			              <form:input type="text" class="datepicker" size="5" path="endDate" required="true"/>
			              <form:errors path="endDate" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<tr id="trCreateProject3">
				<td>
					<form:label for="concurrentUsers" path="concurrentUsers">Define Concurrent Users</form:label>
				</td>
				<td>
					<spring:bind path="concurrentUsers">
			              <form:input type="text" size="5" path="concurrentUsers" required="true"/>
			              <form:errors path="concurrentUsers" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr> --%>
			<%-- <tr id="trDeleteProject">
				<td>
					<form:label for="id" path="id">Select Project<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<table>
							<c:forEach items="${active_project_list}" var="item">
								<tr id="trChkBoxProject">
									<td><form:radiobutton path="name" value="${item.name}"/></td>
									<td>&nbsp;&nbsp;${item.name}<br /></td>
								</tr>
							</c:forEach>
					</table>
				</td>
			</tr> --%>
			<tr id="trProjectName">
				<td>
					<form:label for="name" path="name">Project Name</form:label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="projectName" path="name" size="30" type="text" class="input-large" placeholder="Project Name" value="Project Name" required="true"/>
			              <form:errors path="name" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
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
	        
			<%-- <tr>
				<td>
					<select id="example28" multiple="multiple"></select>
				</td>
				<td>
					Multiselect with a 'Select all' option and filtering enabled using the <code>enableFiltering</code> option.
				</td>
			</tr> --%>
			<tr>
				<td colspan="2" align="center">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<c:if test="${empty crudObj.id}">
					  	 <input id="btnCreate" type="submit" class="btn btn-primary" value="Create" onsubmit="return doValidationForCreateTenant();" />
					  	 <input id="btnEdit" type="submit" class="btn btn-primary" value="Update" onsubmit="return doValidationForCreateTenant();" />
					  	 <input id="btnDelete" type="submit" class="btn btn-primary" value="Delete" onsubmit="return doValidationForCreateTenant();" />
					</c:if>
		        </td>
			<tr>
		</table>
	</form:form>
	<script type="text/javascript">
	$(document).ready(function() {
		$("input").filter('.datepicker').datepicker({
			minDate : 0,
			dateFormat : 'yy-mm-dd'
		});
	});
</script>
	