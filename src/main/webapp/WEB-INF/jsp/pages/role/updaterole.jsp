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
    $("#panel").slideToggle("slow");
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
		  selectAllValues();
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
	 <c:url value="/content/role/updaterole" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Edit Role</b></font><br /><br />
				</td>
			</tr>
			<c:choose>
				
				<c:when test='${not empty roleupdated}'> 
					<tr>
							<td colspan="2" align="center"><font color="green" size="3"><b>${roleupdated}</b></font><br /><br /></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</table>
		<table class="tblFormData">
			<tr>
				<td>
					<form:label  for="name" path="name">Role Name<font color='red' size='3'>*</font></form:label>
				</td>	
				<td>
					<spring:bind path="name">
			              <form:input path="name" id="roleName" autocomplete="off" size="30" type="text" class="input-large" placeholder="Role Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label  for="description" path="description">Description</form:label>
				</td>
				<td>
					<spring:bind  path="description">
			              <form:textarea id="roleDesc" maxlength="100" path="description" rows="3" cols="28" type="text" class="input-large"  placeholder="Description  (Maximum 100 Characters)"/>
			              
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
									
									<form:select id="from" path="permissions" style="width:255px;height:155px;">
										<form:options items="${available_permissions_list}" itemLabel="name" itemValue="id" />
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
	        						<form:select id="to" path="permissionsIds" style="width:255px;height:155px;">
										<form:options items="${assigned_permissions_list}" itemLabel="name" itemValue="id" />
									</form:select>
   						 		</td>
   							</tr>
						</table>
					</td>
										
					</tr>
			</table>
			</div>
	        <spring:bind path="modifiedBy">
	              <form:hidden path="modifiedBy" value="${sessionScope.userid}" />
	        </spring:bind>
	        
	        <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	        
	        <spring:bind path="id">
	              <form:hidden path="id" value="${idToBeUpdated}" />
	        </spring:bind>
	        <br/>
	        <table align="center">
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary" value="Update" onclick="return validatePage()"/>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			<tr>
		</table>
		<script>
    function moveAll(from, to) {
        $('#'+from+' option').remove().appendTo('#'+to); 
    }
    
    function moveSelected(from, to) {
        $('#'+from+' option:selected').remove().appendTo('#'+to); 
    }
    </script> 
	</form:form>
	