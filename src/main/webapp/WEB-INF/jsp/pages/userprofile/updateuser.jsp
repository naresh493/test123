 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>


<script type="text/javascript">
	function checkMail(){
		//var email = document.getElementById("emailid").value;
		var firstName = document.getElementById('firstName').value;
		var lastName = document.getElementById('surName').value;
		var landLine = document.getElementById('landline').value;
		var mobile = document.getElementById('mobile').value;
		var domain = document.getElementById('domain').value;
		var experience = document.getElementById('experience').value;
		var projectWorked = document.getElementById('projectWorked').value;
		var iChars = "!@#$%^&amp;*()+=-[]\\\';,./{}|\":&lt;&gt;?";
		var numbers = /^[0-9]+$/;
		//var atpos = email.indexOf("@");
	    //var dotpos = email.lastIndexOf(".");
	    if(firstName == null || firstName == ""){
			alert("Please fill the First Name");
			return false;
		} else if(/[^a-zA-Z0-9\_\/]/.test( firstName )){
			alert('FirstName should not contain the special characters other than underscore(_)');
		    return false;
		}else if(lastName == null || lastName == ""){
			alert("Please fill the Last Name");
			return false;
		}else if(/[^a-zA-Z0-9\_\/]/.test( lastName )){
			alert('LastName should not contain the special characters other than underscore(_)');
		    return false;
		}
		else if(domain == null || domain == ""){
			alert("Please fill the domain");
			return false;
		}else if(/[^a-zA-Z0-9\_\/]/.test( domain )){
			alert('Domain should not contain the special characters other than underscore(_)');
		    return false;
		}
		else if(experience == null || experience == ""){
			alert("Please fill the Experience");
		    return false;
		}
		else if(/[^0-9\.\/]/.test( experience )){
		    alert('Experience should contain Only Numerics and dot.');
		    return false;
		}
		else if(projectWorked == null || projectWorked == ""){
			alert("Please fill the Number of projects Worked.");
		    return false;
		}
		else if(!projectWorked.match(numbers)){
		    alert('Number of Projects Worked should contain Only Numeric value');
		    return false;
		}
		/* else if(email == null || email == ""){
			alert("Please Enter the email address");
			return false;
		}
		else if(atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
			alert("Not A Valid Email Address");
			return false;
		} */
		else if(mobile == null || mobile == ""){
			alert("Please Enter the Mobile Number.");
			return false;
		}
		else if(!mobile.match(numbers)){
			alert("Mobile number should contain numbers only");
			return false;
		}
		else if( landLine != "" ){
			if(!landLine.match(numbers)){
				alert("Landline should contain only numbers");
				return false;
			}	
		}
		selectAllValues();
		return true;
	}	   
		
</script>

<script>
	function selectAllValues(){
		$("#to option").prop("selected", "selected");
    	$("#toprojects option").prop("selected", "selected");
    	$("#toteams option").prop("selected", "selected");
    	$("#signup").submit();
	}
</script>
<script>
	    function moveAll(from, to) {
	        $('#'+from+' option').remove().appendTo('#'+to); 
	    }
	    
	    function moveSelected(from, to) {
	        $('#'+from+' option:selected').remove().appendTo('#'+to); 
	    }
    </script>  
    <script>
	    function moveAllProjects(fromprojects, toprojects) {
	        $('#'+fromprojects+' option').remove().appendTo('#'+toprojects); 
	    }
	    
	    function moveSelectedProjects(fromprojects, toprojects) {
	        $('#'+fromprojects+' option:selected').remove().appendTo('#'+toprojects); 
	    }
    </script>  
    <script>
	    function moveAllTeams(fromteams, toteams) {
	        $('#'+fromteams+' option').remove().appendTo('#'+toteams); 
	    }
	    
	    function moveSelectedTeams(fromteams, toteams) {
	        $('#'+fromteams+' option:selected').remove().appendTo('#'+toteams); 
	    }
    </script>   
    <script> 
$(document).ready(function(){
  $("#flip").click(function(){
    $("#panel").slideDown("slow");
    $("#panel1").hide();
    $("#panel2").hide();
  });
});
$(document).ready(function(){
	  $("#flip1").click(function(){
	    $("#panel1").slideDown("slow");
	    $("#panel").hide();
	    $("#panel2").hide();
	  });
	});
$(document).ready(function(){
	  $("#flip2").click(function(){
	    $("#panel2").slideDown("slow");
	    $("#panel1").hide();
	    $("#panel").hide();
	  });
	});
</script>
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
    <style>
    	#flip{
    		background-color:#c3c3c3;
    		color: #fff;
    	}
		#panel,#flip
		{
		padding:10px;
		text-align:center;
		border:solid 1px #c3c3c3;
		width:110%;
    	margin: 0 auto;
		}
		#panel
		{
		padding:50px;
		}
</style>
<style>
    	#flip1{
    		background-color:#c3c3c3;
    		color: #fff;
    	}
		#panel1,#flip1
		{
		padding:5px;
		text-align:center;
		border:solid 1px #c3c3c3;
		width:110%;
    	margin: 0 auto;
		}
		#panel1
		{
		padding:50px;
		display:none;
		}
		.tblFormData tr th,td
		{
		padding: 10px;
		}
		
		
</style> 
<style>
    	#flip2{
    		background-color:#c3c3c3;
    		color: #fff;
    	}
		#panel2,#flip2
		{
		padding:5px;
		text-align:center;
		border:solid 1px #c3c3c3;
		width:110%;
    	margin: 0 auto;
		}
		#panel2
		{
		padding:50px;
		display:none;
		}
		.tblFormData tr th,td
		{
		padding: 3px;
		}
		
		
</style> 
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/userprofile/updateuser" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Edit User</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty userupdated}'> 
				<tr>
					<td colspan="2" align="center"><font color="red" size="3"><b>${userupdated}</b></font><br /><br />
				</td>
			</tr>
			</c:if>
		</table>
		<table class="tblFormData">
		
			
			<tr>
				<td><label>First Name <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="firstName">
			              <form:input path="firstName"  id ="firstName" autocomplete="off" size="30" type="text" class="input-large" placeholder="First Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Last Name <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="surName">
			              <form:input path="surName" id="surName" autocomplete="off" size="30" type="text" class="input-large" placeholder="Sur Name" required="true"/>
			              
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Domain <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="domain">
			              <form:input path="domain" id="domain" autocomplete="off" size="30" type="text" class="input-large" placeholder="Domain"/>
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Experience <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="experience">
			              <form:input path="experience" id="experience" autocomplete="off" size="30" type="text" class="input-large" placeholder="Experience"/>
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Number of Projects Worked <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="noOfProjectsWorked">
			              <form:input path="noOfProjectsWorked" id="projectWorked" autocomplete="off" size="30" type="text" class="input-large" placeholder="Number of Projects Worked"/>
			        </spring:bind>
			    </td>
			</tr>
			<%-- <tr>
				<td><label>Email Address <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="emailAddress">
			              <form:input path="emailAddress" id="emailid" autocomplete="off" size="30" type="text" class="input-large" placeholder="Email Address" required="true"/>
			              
			        </spring:bind>
			    </td>
			</tr> --%>
			
			<tr>
				<td><label>Land Line</label></td>
				<td>
					<spring:bind path="landline">
			              <form:input path="landline" id="landline" autocomplete="off" size="50" type="text" class="input-large" placeholder="Landline"/>
			              
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Mobile <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="mobile">
			              <form:input path="mobile"  id="mobile" autocomplete="off" size="50" type="text" class="input-large" placeholder="Mobile"/>
			             
			        </spring:bind>
				</td>
			</tr>
			</table>
				<table align="center">
			<tr>
			<td>        
			<div id="flip">Assign Roles To User</div>
			<div id="panel">
			<table>
				
					<tr>
						<td>
							<table>
								<tr>
									<td>
										<div><font size="2">Available Roles</font></div>
										<form:select id="from" path="roles" multiple="multiple" style="width:300px;height:155px;" >
											<form:options items="${available_roles}" itemLabel="name" itemValue="id" />
										</form:select>
						         			</td>
								</tr>
							</table>
						</td>
						<td>
							<table>
								<tr>
									<td>
										<div class="controls"> 
									        <a href="javascript:moveAll('from', 'to')">&gt;&gt;</a> 
									        <a href="javascript:moveSelected('from', 'to')">&gt;</a> 
									        <a href="javascript:moveSelected('to', 'from')">&lt;</a> 
									        <a href="javascript:moveAll('to', 'from')" href="#">&lt;&lt;</a> 
									    </div>
									</td>
								</tr>
							</table>
						
						</td>
						<td>
						
							<table>
								<tr id="trPerm" >
									<td colspan="2" >
										<div><font size="2">Assigned Roles</font></div>
		        						<form:select id="to" for="permaId" path="rolesList" multiple="multiple" style="width:300px;height:155px;">
											<form:options items="${assigned_roles}" itemLabel="name" itemValue="id"/>
										</form:select>
	   						 		</td>
	   							 </tr>
							</table>
						</td>
											
						</tr>
					</table>
				</div>
				</td>
				</tr>
				<tr>
					<td>
						<div id="flip1">Assign Projects To User</div>
						<div id="panel1">
						<table>
							
								<tr>
									<td>
										<table>
											<tr>
												<td>
													<div><font size="2">Available Projects</font></div>
													<form:select id="fromprojects" path="projects" multiple="multiple" style="width:300px;height:155px;" >
														<form:options items="${available_projects}" itemLabel="name" itemValue="id" />
													</form:select>
									         			</td>
											</tr>
										</table>
									</td>
									<td>
										<table>
											<tr>
												<td>
													<div class="controls"> 
												        <a href="javascript:moveAllProjects('fromprojects', 'toprojects')">&gt;&gt;</a> 
												        <a href="javascript:moveSelectedProjects('fromprojects', 'toprojects')">&gt;</a> 
												        <a href="javascript:moveSelectedProjects('toprojects', 'fromprojects')">&lt;</a> 
												        <a href="javascript:moveAllProjects('toprojects', 'fromprojects')" href="#">&lt;&lt;</a> 
												    </div>
												</td>
											</tr>
										</table>
									
									</td>
									<td>
									
										<table>
											<tr id="trPerm" >
												<td colspan="2" >
													<div><font size="2">Assigned Projects</font></div>
					        						<form:select id="toprojects" for="permaId" path="projectsList" multiple="multiple" style="width:300px;height:155px;">
														<form:options items="${assigned_projects}" itemLabel="name" itemValue="id"/>
													</form:select>
				   						 		</td>
				   							 </tr>
										</table>
									</td>
														
									</tr>
								</table>
							</div>
					</td>
				</tr>
				<tr>
					<td>
						<div id="flip2">Assign Teams To User</div>
						<div id="panel2">
						<table>
							
								<tr>
									<td>
										<table>
											<tr>
												<td>
													<div><font size="2">Available Teams</font></div>
													<form:select id="fromteams" path="teams" multiple="multiple" style="width:300px;height:155px;" >
														<form:options items="${available_teams}" itemLabel="name" itemValue="id" />
													</form:select>
									         			</td>
											</tr>
										</table>
									</td>
									<td>
										<table>
											<tr>
												<td>
													<div class="controls"> 
												        <a href="javascript:moveAllTeams('fromteams', 'toteams')">&gt;&gt;</a> 
												        <a href="javascript:moveSelectedTeams('fromteams', 'toteams')">&gt;</a> 
												        <a href="javascript:moveSelectedTeams('toteams', 'fromteams')">&lt;</a> 
												        <a href="javascript:moveAllTeams('toteams', 'fromteams')" href="#">&lt;&lt;</a> 
												    </div>
												</td>
											</tr>
										</table>
									
									</td>
									<td>
									
										<table>
											<tr id="trPerm" >
												<td colspan="2" >
													<div><font size="2">Assigned Teams</font></div>
					        						<form:select id="toteams" for="permaId" path="teamsList" multiple="multiple" style="width:300px;height:155px;">
														<form:options items="${assigned_teams}" itemLabel="name" itemValue="id"/>
													</form:select>
				   						 		</td>
				   							 </tr>
										</table>
									</td>
														
									</tr>
								</table>
							</div>
					</td>
				</tr>
				<tr>
					
			        <spring:bind path="id">
			              <form:hidden path="id" value="${crudObj.id}" />
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
			</table>
			<table align="center">
			<tr>
				<td>
					
					<input type="submit" class="btn btn-primary" value="Update" onclick="return checkMail()"/>&nbsp;
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			</tr>
		</table>
	</form:form>
	