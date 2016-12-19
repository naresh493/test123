<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<style>
table.menu{
	font-size:100%;
	position:absolute;
}
</style>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/userproject/removeselectedusersfromproject"/>
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Remove Users From Project</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty usersremoved}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${usersremoved}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>
		<c:if test="${not empty project_list}">
		<table id="tblData" class="tblFormData">
			<tr id="trProject">
				<td>
					<form:label for="idProject" path="projectId">Select Project</form:label>
				</td>
				<td>
					<form:select id="idProject" path="projectId"  onchange="ManageRemoveUsers('project')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			<tr id="trUsers">
				<td>
					<form:label for="idUsers" path="userId">Select Users</form:label>
				</td>
				<td>
						<form:select id="idUsers" path="userId" multiple="multiple" style="width:250px;height:70px;">
							<form:options itemValue="id"/>
						</form:select>
					
				</td>
			</tr>
			
			
			<spring:bind path="createdBy">
             		<form:hidden id="createdBy" path="createdBy" value="${sessionScope.userid}" />
	        </spring:bind>
	         <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	       <spring:bind path="modifiedBy">
	              <form:hidden id="modifiedBy" path="modifiedBy" value="${sessionScope.userid}" />
	        </spring:bind>
	         <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	      <tr>
				<td colspan="2" align="center">
					
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Remove" onclick="return CheckBoxValidation()"/>&nbsp;
		        </td>
			<tr>
			</table>
			
				</c:if>
				
			<c:if test="${empty project_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No Projects Available*---------</font></td>
					
				</tr>
				</table>
				</c:if>
<script type="text/javascript">
	function CheckBoxValidation(){
		var options = document.getElementById('idUsers').options, count = 0;
		for (var i=0; i < options.length; i++) {
		  if (options[i].selected) count++;
		}
			if(count > 0){
				
				return true;
			}else{
				alert("Please select atleast a user");
				return false;
			}
			
		}
	</script>
	</form:form>
	