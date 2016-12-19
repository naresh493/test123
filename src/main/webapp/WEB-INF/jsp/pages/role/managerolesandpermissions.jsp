<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<style type="text/css">
label{
cursor: default !important;
}
</style>
<style type="text/css">
	    select {
	        width: 200px;
	        float: left;
	    }
	    .controls {
	        width: 40px;
	        float: left;
	        margin: 10px;
	    }
	    .controls a {
	        background-color: #222222;
	        border-radius: 4px;
	        border: 2px solid #000;
	        color: #ffffff;
	        padding: 2px;
	        font-size: 14px;
	        text-decoration: none;
	        display: inline-block;
	        text-align: center;
	        margin: 5px;
	        width: 20px;
	    }
    </style>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/rolepermissions/savepermissionstorole" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Permissions to Role</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty permissionsassigned}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${permissionsassigned}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		
		<c:if test="${not empty role_list}">
		
		
		
		<table id="tblCreateProject" class="tblFormData">
		
				<tr>
				<td>
					<form:label for="idRole" path="roleId">Select Role<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<form:select id="idRole" path="roleId" required="true" style="color:#333333" onchange="getRoleBasedPermissions('rolepermissions');getRoleAssignBasedPermissions('rolepermissions')">
					<form:option value="" label="--Please Select Role--"/>
						<form:options items="${role_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>


				</table>
				<table align="center">
					<tr align="center" >
					
					</table>
					<table align="center">
					<tr align="center">
					<td>
					<table align="center" >
					<tr id="traPermissions" >
					<td >
							<div><font size="2">Available Permissions</font></div>
							<form:select id="from"  for="permId" path="permissions" multiple="multiple" style="width:255px;height:155px;">
								<form:options title="testing" itemValue="id" />
							</form:select>
						</td>
						</tr>
					</table>
					</td>
					<td>
						<div class="controls"> 
					        <a href="javascript:moveAll('from', 'to')">&gt;&gt;</a> 
					        <a href="javascript:moveSelected('from', 'to')">&gt;</a> 
					        <a href="javascript:moveSelected('to', 'from')">&lt;</a> 
					        <a href="javascript:moveAll('to', 'from')" href="#">&lt;&lt;</a> 
					    </div>
					
					</td>
					<td>
					
					<table>
					<tr id="trPerm" >
						<td colspan="2" >
						<div><font size="2">Assigned Permissions</font></div>
        						<form:select id="to" for="permaId" path="permissionIds" multiple="multiple" style="width:255px;height:155px;" required="true">
									<form:options title="sample" itemValue="id"/>
								</form:select>
   						 	</td>
   						 </tr>
					</table>
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
	      
	       </table>
	       <br>
	       <table align="center">
			<tr>
				<td>
					
					 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Save" onclick="selectAllValues()"/>&nbsp;
					 <a href=<%=request.getContextPath() %>/content/cancel>
 							<input type="button" name="cancel" class="btn btn-primary" value="Cancel" />
						</a>
		        </td>
			<tr>
		</table>
		</c:if>
		 <c:if test="${empty role_list}">
		 <table class="tblHeader-inner">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No Permissions Available*---------</font></td>
					
				</tr>
				</table>
				
		</c:if>
		<script>
    function moveAll(from, to) {
        $('#'+from+' option').remove().appendTo('#'+to); 
    }
    
    function moveSelected(from, to) {
        $('#'+from+' option:selected').remove().appendTo('#'+to); 
    }
    </script> 
    
		 
		</form:form>