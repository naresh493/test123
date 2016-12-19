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
<script type="text/javascript">
	function validatePage(){
		var userId = document.getElementById('idUser').value;
		if(userId == null || userId == ""){
			alert('Please select the user');
			return false;
		} else {
			$("#to option").prop("selected", "selected");
	    	$("#signup").submit();
			return true;
		}
	}
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/userrole/saveuserrole" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Roles To User</b></font><br /><br />
				</td>
			</tr>
			<%-- <c:if test='${not empty rolesassigned}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${rolesassigned}</b></font><br /><br />
					</td>
				</tr>
			</c:if> --%>
			<c:if test='${not empty displayMessage}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${displayMessage}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		
		<c:if test="${not empty user_list}">
		
		
		
		<table id="tblCreateProject" class="tblFormData">
		
				<tr>
				<td style="text-align:right;padding-right:18px;">
					<form:label for="userList" path="">Select A User :<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<form:select  id="idUser" path="userId" required="true"  style="color:#333333" onchange="getAssignedRoles('userrole');getAvailableRoles('userrole')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${user_list}" itemLabel="userName" itemValue="id" />
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
					<tr id="traRoles" >
					<td >
							<div><font size="2">Available Roles</font></div>
							<form:select id="from"  for="roleId" path="roles" multiple="multiple" style="width:177px;height:155px;">
								<form:options itemValue="id" />
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
					<tr id="trRoles" >
						<td colspan="2" >
						<div><font size="2">Assigned Roles</font></div>
        						<form:select id="to" for="roleId" path="roleId" multiple="multiple" style="width:177px;height:155px;">
									<form:options itemValue="id"/>
								</form:select>
   						 	</td>
   						 </tr>
					</table>
					</td>
										
					</tr>
				
				</table>
			
			<table align="center">
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
					 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Save" onclick="return validatePage()"/>&nbsp;
					 <input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			</tr>
		</table>
		</c:if>
		 <c:if test="${empty user_list}">
		 <table class="tblHeader-inner">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No Users Available*---------</font></td>
					
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