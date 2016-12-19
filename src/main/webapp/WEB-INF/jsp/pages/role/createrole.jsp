<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script> 
$(document).ready(function(){
  $("#flip").click(function(){
    $("#panel").slideToggle("fast");
  });
});

</script>
<script type="text/javascript">
	function validatePage(){
		var roleName = document.getElementById('roleName').value;
		var roleDesc = document.getElementById('roleDesc').value;
		var roleResp = document.getElementById('roleResp').value;
		var code, i, len;
		if(roleName.trim() == "" || roleName.trim() == null){
			alert('Please Enter Role Name with out leading and tailing spaces');
			return false;
		}
		 for (i = 0, len = roleName.length; i < len; i++) {
		    code = roleName.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Role Name.');
		      return false;
		    }
		  }
		  for (i = 0, len = roleDesc.length; i < len; i++) {
		    code = roleDesc.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Description.');
		      return false;
		    }
		  }
		  if(roleResp.trim() == "" || roleResp.trim() == null){
				alert('Please Provide Role And Responsibility.');
				return false;
			}
		  for (i = 0, len = roleResp.length; i < len; i++) {
			    code = roleResp.charCodeAt(i);
			    if (!(code > 47 && code < 58) && // numeric (0-9)
			        !(code > 64 && code < 91) && // upper alpha (A-Z)
			        !(code > 96 && code < 123)&& // lower alpha (a-z)
			        !(code == 32 || code==95 || code==44)) {
			        alert('only Alphanumeric, under Score,comma and space allowed in Role And Responsibility .');
			      return false;
			    }
			  }
			$("#to option").prop("selected", "selected");
	    	$("#signup").submit();
			return true;
	}
	
</script>
<style>
    	#flip{
    		background-color:#c3c3c3;
    		color: #fff;
    	}
		#panel,#flip
		{
		padding:5px;
		text-align:center;
		border:solid 1px #c3c3c3;
		width:60%;
    	margin: 0 auto;
		}
		#panel
		{
		padding:50px;
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
	 <c:url value="/content/role/saverole" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create New Role</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty rolecreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${rolecreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty rolealreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${rolealreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table id="tblCreateProject" class="tblFormData">
			
			<tr>
				<td>
					<form:label for="roleName" path="name">Role Name<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind  path="name">
			              <form:input id="roleName" autocomplete="off" pattern="[a-zA-Z0-9_ ]*" title="only Alphanumeric and under Score" path="name" size="30" type="text" class="input-large" placeholder="Role Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="description" path="description">Description</form:label>
				</td>
				<td>
					<spring:bind  path="description">
			              <form:textarea id="roleDesc" maxlength="255" path="description" rows="3" cols="28" type="text" class="input-large"  placeholder="Description  (Maximum 255 Characters)"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="roleAndResponsibility" path="roleAndResponsibility">Role And Resposibility<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind  path="roleAndResponsibility">
			              <form:textarea id="roleResp" maxlength="255" path="roleAndResponsibility" rows="3" cols="28" type="text" class="input-large"  placeholder="Role And Responsibility  (Maximum 255 Characters)"/>
			              
			        </spring:bind>
				</td>
			</tr>
			</table>
			<div id="flip">Assign Permissions To Role</div>
			<div id="panel">
			<table align="center">
				<tr align="center" >
			</table>
			<table align="center">
				<tr align="center">
					<td>
						<table align="center" >
							<tr>
								<td >
									<div><font size="2">Available Permissions</font></div>
										<form:select id="from" path="permissions" multiple="multiple" style="width:255px;height:155px;" >
											<form:options items="${permissions_list}" itemLabel="name" itemValue="id" />
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
	        						<form:select id="to" for="permaId" path="permissionsIds" multiple="multiple" style="width:255px;height:155px;">
										<form:options title="sample" itemValue="id"/>
									</form:select>
   						 		</td>
   							</tr>
						</table>
					</td>
										
					</tr>
			</table>
			</div>
			<br/>
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
					
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Create" onclick="return validatePage()"/>&nbsp;
					  	 <input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
						<input type="reset" class="btn btn-primary" value="Reset" />
		        </td>
			</tr>
		</table>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
			
		<script>
    function moveAll(from, to) {
        $('#'+from+' option').remove().appendTo('#'+to); 
    }
    
    function moveSelected(from, to) {
        $('#'+from+' option:selected').remove().appendTo('#'+to); 
    }
    </script> 
	</form:form>
	