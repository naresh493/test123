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
	function validatePage() {
		var teamId = document.getElementById('idTeam').value;
		var projects = document.getElementById('to').value;
		if(teamId == null || teamId == ""){
			alert('Please select the Team');
			return false;
		}
		else{
			$("#to option").prop("selected", "selected");
	    	$("#signup").submit();
			return true;
		}
	}
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/teamproject/save" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Team To Project</b></font><br /><br />
				</td>
			</tr>
			<%-- <c:if test='${not empty projectsassigned}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${projectsassigned}</b></font><br /><br />
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
		
		<c:if test="${not empty team_list}">
		
		
		
		<table id="tblCreateProject" class="tblFormData">
		
				<tr>
				<td>
					<form:label for="teamList" path="teamId">Select A Team<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<form:select  id="idTeam" path="teamId" required="true"  style="color:#333333" onchange="getAssignedProjects('teamproject');getAvailableProjects('teamproject')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${team_list}" itemLabel="name" itemValue="id" />
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
					<tr id="trtProjects" >
					<td >
							<div><font size="2">Available Projects</font></div>
							<form:select id="from"  for="projects" path="projects" multiple="multiple" style="width:177px;height:155px;">
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
					<tr id="trtaProjects" >
						<td colspan="2" >
						<div><font size="2">Assigned Projects</font></div>
        						<form:select id="to" for="projectId" path="projectId" multiple="multiple" style="width:177px;height:155px;">
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
		 
		<script>
    function moveAll(from, to) {
        $('#'+from+' option').remove().appendTo('#'+to); 
    }
    
    function moveSelected(from, to) {
        $('#'+from+' option:selected').remove().appendTo('#'+to); 
    }
    </script> 
		</form:form>