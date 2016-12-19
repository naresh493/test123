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
	    select {
  color: #000;
  color: rgba(0,0,0,0);
  text-shadow: 0 0 0 #000;
}
    </style>
    <script type="text/javascript">
	function validatePage() {
		var projectId = document.getElementById('idProject').value;
		if(projectId == null || projectId == ""){
			alert('Please select the Project');
			return false;
		}else{
			$("#to option").prop("selected", "selected");
	    	$("#signup").submit();
			return true;
		}
	}
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/userproject/assign" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Users To Project</b></font><br /><br />
				</td>
			</tr>
			<%-- <c:if test='${not empty usersassigned}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${usersassigned}</b></font><br /><br />
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
		
		<c:if test="${not empty project_list}">
		
		
		
		<table id="tblCreateProject" class="tblFormData">
		
				<tr>
				<td>
					<form:label for="projectList" path="projectId">Select A Project<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<form:select  id="idProject" path="projectId" required="true"  style="color:#333333" onchange="getAssignedUsers('userproject');getAvailableUsers('userproject')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
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
					<tr id="traUsers" >
					<td >
							<div><font size="2">Available Users</font></div>
							<form:select id="from"  for="userId" path="userId" multiple="multiple" style="width:177px;height:155px;">
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
					<tr id="trAsUsers" >
						<td colspan="2" >
						<div><font size="2">Assigned Users</font></div>
        						<form:select id="to" for="users" path="users" multiple="multiple" style="width:177px;height:155px;">
									<form:options itemValue="id"/>
								</form:select>
   						 	</td>
   						 </tr>
					</table>
					</td>
										
					</tr>
				
				</table>
			<br>
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
		<script>
    function moveAll(from, to) {
        $('#'+from+' option').remove().appendTo('#'+to); 
    }
    
    function moveSelected(from, to) {
        $('#'+from+' option:selected').remove().appendTo('#'+to); 
    }
    </script> 
		</form:form>