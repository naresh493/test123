<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<script type="text/javascript">
	function checkMail(){
		var email = document.getElementById("emailid").value;
		var userName = document.getElementById('userName').value;
		var firstName = document.getElementById('firstName').value;
		var lastName = document.getElementById('surName').value;
		var landLine = document.getElementById('landline').value;
		var mobile = document.getElementById('mobile').value;
		var domain = document.getElementById('domain').value;
		var experience = document.getElementById('experience').value;
		var projectWorked = document.getElementById('projectWorked').value;
		var iChars = "!@#$%^&amp;*()+=-[]\\\';,./{}|\":&lt;&gt;?";
		var numbers = /^[0-9]+$/;
		var atpos = email.indexOf("@");
	    var dotpos = email.lastIndexOf(".");
	    
	    
	    
	    /* 
	    var code, i, len;
		for (i = 0, len = name.length; i < len; i++) {
		    code = name.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Permission Name.');
		      return false;
		    }
		  }
		var str =  document.getElementById('permissionDesc').value;
		  for (i = 0, len = str.length; i < len; i++) {
		    code = str.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Description.');
		      return false;
		    }
		  }
		  for (i = 0, len = aliasName.length; i < len; i++) {
			    code = aliasName.charCodeAt(i);
			    if (!(code > 47 && code < 58) && // numeric (0-9)
			        !(code > 64 && code < 91) && // upper alpha (A-Z)
			        !(code > 96 && code < 123)&& // lower alpha (a-z)
			        !(code == 32 || code==95)) {
			        alert('only Alphanumeric, under Score and space allowed in Display Name.');
			      return false;
			    }
			  }
	    
	    
	    
	   */  
	    
	    if(userName == null || userName ==""){
			alert("Please fill the UserName");
			return false;
		}else if(/[^a-zA-Z0-9\_\/]/.test( userName )){
		    alert('UserName should not contain the special characters other than underscore(_)');
		    return false;
		} else if(firstName == null || firstName == ""){
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
		else if(email == null || email == ""){
			alert("Please Enter the email address");
			return false;
		}
		else if(atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
			alert("Not A Valid Email Address");
			return false;
		}
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
		/* else if(mobile == null || mobile == ""){
			alert("Please Enter the Mobile Number.");
			return false;
		}else if(userName.match(iChars)){
			alert("UserName should not contain the special characters other than underscore(_)");
			return false;
		}
		else if(!mobile.match(numbers)){
			alert("Mobile number should contain numbers only");
			return false;
		}
		/* else if(landLine == null || landLine == ""){
			selectAllValues();
			return true;
		}
		else if(!landLine.match(numbers)){
			alert("Landline should contain only numbers");
			return false;
		} */
		/* else{
		 	if(domain!=null && domain!=""){
					if(/[^a-zA-Z0-9\_\/]/.test( domain )){
					alert('Domain should not contain special characters');
				    return false;
				    }
			}
			if(experience!=null && experience!=""){
			 		if(/[^0-9\.\/]/.test( experience )){
			 		alert('Experience should Contain Only Numerics and dot');
				    return false;
					}
			}
	  		if(projectWorked!=null && projectWorked!=""){
	  				if(!projectWorked.match(numbers)){
	  				alert('Number Of Projects Worked Should Contain Only Numeric Value');
				    return false;
				}
			}
	  		if(landLine!=null && landLine!=""){
					if(!landLine.match(numbers)){
					alert("Landline should contain only numbers");
					return false;
				}
	  		}		
			selectAllValues();
			return true;
	}
	} 	   */ 
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
		padding: 3px;
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
	 <c:url value="/content/userprofile/saveuser" />
  </c:set>
  		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" style="text-align:center;text-color:green;"></td>
			</tr>
			<c:if test='${not empty usercreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${usercreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty userexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${userexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		
		
 <div>
	<form:form id="signup" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create User</b></font><br /><br />
				</td>
			</tr>
		</table>
		
		
		<table class="tblFormData">
			<tr>
					<td><label>User Name <font color='red'>*</font></label></td>
					<td>
						<spring:bind path="userName">
				              <form:input path="userName" id="userName" size="30" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" type="text" class="input-large" placeholder="User Name"/>
				             
				        </spring:bind>
					</td>
				</tr>
			<tr>
				<td><label>First Name <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="firstName">
			              <form:input path="firstName" id="firstName" autocomplete="off" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="First Name"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Last Name <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="surName">
			              <form:input path="surName" id="surName" autocomplete="off" size="30" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" type="text" class="input-large" placeholder="Last Name"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Domain <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="domain">
			              <form:input path="domain" id="domain" autocomplete="off" size="100" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" type="text" class="input-large" placeholder="Domain"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Experience <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="experience">
			              <form:input path="experience" id="experience" autocomplete="off" size="10" pattern="[0-9.]*" title="Only Numbers and Dot" type="text" class="input-large" placeholder="Experience"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>No. Of Projects Worked <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="noOfProjectsWorked">
			              <form:input path="noOfProjectsWorked" id="projectWorked" autocomplete="off" size="10" pattern="[0-9]*" title="Only Numbers" type="text" class="input-large" placeholder="No. Of Projects Worked"/>
			        </spring:bind>
			    </td>
			</tr>
			
			
			<tr>
				<td><label>Email Address <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="emailAddress">
			              <form:input path="emailAddress" id="emailid" autocomplete="off" size="30" type="text" class="input-large" placeholder="Email Address"/>
			        </spring:bind>
			    </td>
			</tr>
			
			<tr>
				<td><label>Land Line</label></td>
				
				<td>
					<spring:bind path="landline">
			              <form:input id="landline" path="landline" autocomplete="off" size="50" pattern="[09]*" title="Only Numbers" type="text" class="input-large" placeholder="Landline"/>
			              
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Mobile <font color='red'>*</font></label></td>
				<td>
					<spring:bind path="mobile">
			              <form:input id="mobile" path="mobile" autocomplete="off" size="50" type="text" pattern="[0-9]*" title="Only Numbers" class="input-large" placeholder="Mobile"/>
			             
			        </spring:bind>
			    </td>
			        </tr>
			</table>
			<table id="assignRoleUser" align="center">
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
											<form:options itemValue="id"/>
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
														<form:options itemValue="id"/>
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
													<form:options itemValue="id"/>
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
			        <br/>
			</tr>
			
			
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" class="btn btn-primary" value="Create" onclick="return checkMail()"/>&nbsp;
		       		<input type="reset" class="btn btn-primary" value="Reset" />
		       		<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			</tr>
		</table>
		
	</form:form>
	</div>