<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<script>
		    function moveAll(from, to) {
		        $('#'+from+' option').remove().appendTo('#'+to); 
		    }
		    
		    function moveSelected(from, to) {
		        $('#'+from+' option:selected').remove().appendTo('#'+to); 
		    }
		    function opennewtenant(){
		    	var q = document.getElementById("tenant");
		    	$("#tenant").show();
		    	$("#signup").hide();
		    
		    
		    }
		   
    </script>
<style>
	label{
		cursor:auto !important;
	}
</style>
<style type="text/css">


.popup { 
      background-color: #DDD; 
      height: 300px; width: 500px; 
      border: 5px solid #666; 
      position: absolute; visibility: hidden; 
      font-family: Verdana, Geneva, sans-serif; 
      font-size: small; text-align: justify; 
      padding: 5px; overflow: auto; 
      z-index: 2; 
} 
.popup_bg { 
      position: absolute; 
      visibility: hidden; 
      height: 100%; width: 100%; 
      left: 0px; top: 0px; 
      filter: alpha(opacity=70); /* for IE */ 
      opacity: 0.7; /* CSS3 standard */ 
      background-color: #999; 
      z-index: 1; 
} 
.close_button { 
      font-family: Verdana, Geneva, sans-serif; 
      font-size: small; font-weight: bold; 
      float: right; color: #666; 
      display: block; text-decoration: none; 
      border: 2px solid #666; 
      padding: 0px 3px 0px 3px; 
} 
body { margin: 0px; } 


	    select {
	        width: 205px;
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
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/project/save" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create New Project</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty projectcreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${projectcreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty projectalreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${projectalreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table id="tblCreateProject" class="tblFormData">
			
			<tr>
				<td>
					<label>Project Name <font color='red' size="2">*</font></label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="projectName" autocomplete="off" pattern="[0-9a-zA-Z_ ]*" title="only letters,numbers and Underscore" path="name" size="30" type="text" class="input-large" placeholder="Project Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="startDate" path="startDate">Start Date<font color='red' size="2">*</font></form:label>
				</td>
				<td>
					<spring:bind path="startDate">
			              <form:input id="startdate" type="text" autocomplete="off" title="Select date from datepicker" class="datepicker" size="5" path="startDate" itemLabel="startDate" readonly="true" style="background:white;"/>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="endDate" path="endDate">End Date<font color='red' size="2">*</font></form:label>
				</td>
				<td>
					<spring:bind path="endDate">
			              <form:input id="enddate" type="text" autocomplete="off" title="Select date from datepicker" class="datepicker" size="5" path="endDate" readonly="true" style="background:white;"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			
			<tr id="trTenants">
			
				<td>
					<form:label id="tenantList" for="tenantId" path="tenantId">Select Tenant<font color='red' size="2">*</font></form:label>
				</td>
				<td>
					<form:select id="tenantList" path="tenantId" required="true" onmouseover="reloadtenant()">
						<form:option value="" label="--Please Select--" />
						<form:options items="${tenant_list}" itemLabel="name" itemValue="id"/>
					</form:select>
				</td>
				
				<td>
				
				
				<input type="button" value ="create tenant" onclick ="openpopup('popup1')" />
				</td>
				
			</tr>
			<tr>
				<td>
					<form:label id="roleDesc" for="description" path="description">Description</form:label>
				</td>
				<td>
					<spring:bind  path="description">
			              <form:textarea id="projectDesc" maxlength="255" path="description" rows="3" cols="28" type="text" class="input-large"  placeholder="Description  (Maximum 255 Characters)"/>
			              
			        </spring:bind>
				</td>
			</tr>
			</table>
			<div id="flip">Assign Packages To Project</div>
			<div id="panel">
			<table align="center">
				<tr align="center" >
			</table>
			<table align="center">
				<tr align="center">
					<td>
						<table align="center" >
							<tr id="traPermissions" >
								<td >
									<div><font size="2">Available Packages</font></div>
									<form:select id="from" path="modules" style="width:177px;height:155px;">
										<form:options items="${module_list}" itemLabel="name" itemValue="id" />
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
									<div><font size="2">Assigned Packages</font></div>
	        						<form:select id="to" for="licenseId" path="moduleId" multiple="multiple" style="width:177px;height:155px;">
										<form:options itemValue="id"/>
									</form:select>
   						 		</td>
   							 </tr>
						</table>
					</td>
										
				</tr>
			</table>
			</div>
			<br>
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
	        
	         <spring:bind path="disabled">
	              <form:hidden path="disabled" value="0" />
	        </spring:bind>
	        <table align="center">
			<tr>
				<td colspan="2" align="center">
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
										<c:if test="${empty crudObj.id}">
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Create" onclick="return validateDates()"/>&nbsp;
					  	 <input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
						<input type="reset" class="btn btn-primary" value="Reset" />
					</c:if>
		        </td>
			<tr>
		</table>
	</form:form>
	<form:form id="signup" action="${pageUrl}" class="form-createuser"  commandName="curdObj" method="POST">
	
	<c:if test='${not empty defaultusercreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${defaultusercreated}</b></font><br /><br />
					</td>
				</tr>
				<%-- <c:set var="defaultusercreated" value=""/> --%>
	 				
			</c:if>
			
			<c:if test='${not empty defaultuserexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${defaultuserexists}</b></font><br /><br />
					</td>
				</tr>
				<%-- <c:set var="defaultuserexists" value=""/> --%>
			</c:if>
	
	<div id="popup1" class="popup" >
<table class="tblHeader-inner">
<tr id="tenants" align="center"><td colspan="2" align="center"><font size="3"><b>Create Tenant</b></font><br /><br /></td></tr>
<tr><td id="tenantss" style="width:auto;height:auto;text-color:#ffcc00;"> </td></tr>
<tr><td></td></tr>
		<tr align="center">
<td align="center"> <label>Tenant Name <font color="red" size="3">*</font></label></td>
<td align="center"><input id="tenantname" name="tenantname" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="Tenant Name" required="true"/></td>
</tr>	
	<tr align="center">
<td align="center"> <label>Company Name<font color="red" size="3">*</font></label></td>
<td align="center"><input id="companyname" name ="companyname" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="Company Name" required="true"/></td>
</tr>		
<tr align="center">
<td align="center"> <label>Display Name <font color="red" size="3">*</font></label></td>
<td align="center"><input id="displayname" name="displayname" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="Display Name" required="true"/></td>
</tr>
<tr align="center">
<td align="center"> <label>First Name <font color="red" size="3">*</font></label></td>
<td align="center"><input id="firstname" name="firstname" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="First Name" required="true"/></td>
</tr>
<tr align="center">
<td align="center"> <label>Last Name <font color="red" size="3">*</font></label></td>
<td align="center"><input id="lastname" name="lastname" pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="Last Name" required="true"/></td>
</tr>
<tr align="center">
<td align="center"> <label>User Name <font color="red" size="3">*</font></label></td>
<td align="center"><input id="username" name ="username"pattern="[a-zA-Z0-9_ ]*"  autocomplete="off" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="User Name" required="true"/></td>
</tr>
<tr align="center">
<td align="center"> <label>Email Id <font color="red" size="3">*</font></label></td>
<td align="center"><input id="emailid"  name="emailid" autocomplete="off" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" placeholder="Email Id" required="true"/></td>
</tr>
<tr align="center">
<td align="center"> <label>Land Line <font color="red" size="3"></font></label></td>
<td align="center"><input id="landline" pattern="[0-9]*" name="landline"  autocomplete="off" title="Only Numbers" size="30" type="text" class="input-large" placeholder="Land Line" required="true"/></td>
</tr>
<tr align="center">
<td align="center"> <label>Mobile<font color="red" size="3">*</font></label></td>
<td align="center"><input id="mobile" pattern="[0-9]*" name="mobile" autocomplete="off" title="Only Numbers" size="30" type="text" class="input-large" placeholder="Mobile" required="true"/></td>
</tr>

<tr align="center">
<td>
<c:if test="${empty crudObj.id}">
					  	 <input type="button" class="btn btn-primary" value="Save" id="close" onclick="ValidateEmail()"/>
					</c:if>

</td></tr>

</table>

	</div>
	
	
	</form:form>
	<div id="bg" class="popup_bg"></div> 
	<link rel="stylesheet" href="/QlikTestAdmin/static/css/jquery-ui-1.8.16.custom.css">
	
	<script type="text/javascript">
	
	 	 
	function ValidateEmail(){
		
		var numbers = /^[0-9]+$/;
			var landLine = document.getElementById('landline').value;
			var mobile = document.getElementById('mobile').value;
		
		   if(mobile == null || mobile == ""){
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
		
			if(document.getElementById('emailid').value == "")
			{
				alert("You have to enter email address ! ");
				return false;
			}
		 	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(document.getElementById('emailid').value))  
		 	 {  
		 		savetenantforpopup('userprofile');
		   		 return (true);  
		 	 }  
		    alert("You have entered an invalid email address!")  
		    return (false);  
	}  
	
	 function openpopup(id){ 
		
	      //Calculate Page width and height 
	      var pageWidth = window.innerWidth; 
	      var pageHeight = window.innerHeight; 
	      if (typeof pageWidth != "number"){ 
	      if (document.compatMode == "CSS1Compat"){ 
	            pageWidth = document.documentElement.clientWidth; 
	            pageHeight = document.documentElement.clientHeight; 
	      } else { 
	            pageWidth = document.body.clientWidth; 
	            pageHeight = document.body.clientHeight; 
	      } 
	      }  
	      //Make the background div tag visible... 
	      var divbg = document.getElementById('bg'); 
	      divbg.style.visibility = "visible"; 
	        
	      var divobj = document.getElementById(id); 
	      divobj.style.visibility = "visible"; 
	      if (navigator.appName=="Microsoft Internet Explorer") 
	      computedStyle = divobj.currentStyle; 
	      else computedStyle = document.defaultView.getComputedStyle(divobj, null); 
	      //Get Div width and height from StyleSheet 
	      var divWidth = computedStyle.width.replace('px', ''); 
	      var divHeight = computedStyle.height.replace('px', ''); 
	      var divLeft = (pageWidth - divWidth) / 2; 
	      var divTop = (pageHeight - divHeight) / 2; 
	      //Set Left and top coordinates for the div tag 
	      divobj.style.left = divLeft + "px"; 
	      divobj.style.top = divTop + "px"; 
	      //Put a Close button for closing the popped up Div tag 
	      if(divobj.innerHTML.indexOf("closepopup('" + id +"')") < 0 ) 
	      divobj.innerHTML = "<a href=\"#\" onclick=\"closepopup('" + id +"')\"><span class=\"close_button\">X</span></a>" + divobj.innerHTML; 
	} 
	function closepopup(id){ 
	      var divbg = document.getElementById('bg'); 
	      divbg.style.visibility = "hidden"; 
	      var divobj = document.getElementById(id); 
	      divobj.style.visibility = "hidden"; 
	} 
	
$(document).ready(function() {
		$("#tenant").hide();
		$("input").filter('.datepicker').datepicker({
			minDate : 0,
			showOn: 'button',
			buttonImage: "/QlikTestAdmin/static/images/calendar.png",
			buttonImageOnly: true,
			dateFormat : 'yy-mm-dd'
		});
		
	});
</script>
	<link rel="stylesheet" href="/QlikTestAdmin/static/css/jquery-ui-1.8.16.custom.css">
	
	<script type="text/javascript">
	
	
$(document).ready(function() {
		
		$("input").filter('.datepicker').datepicker({
			minDate : 0,
			showOn: 'button',
			buttonImage: "/QlikTestAdmin/static/images/calendar.png",
			buttonImageOnly: true,
			dateFormat : 'yy-mm-dd'
		});
	});
</script>
<script type="text/javascript">
function validateDates(){
	var startdate = document.getElementById('startdate').value;
	var enddate = document.getElementById('enddate').value;
	var name = document.getElementById('projectName').value;
	var projectDesc = document.getElementById('projectDesc').value;
	var code, i, len;
	if((startdate == null || startdate == "") || (enddate == null || enddate == "")){
		alert("Please Select StartDate and End Date");
		return false;
	}
	else if(startdate > enddate){
		alert("End Date should not be before start date");
		return false;
	}else if(name.trim() == null || name.trim() == ""){
		alert("Please enter name without leading and tailing spaces");
		return false;
	}
	  for (i = 0, len = name.length; i < len; i++) {
	    code = name.charCodeAt(i);
	    if (!(code > 47 && code < 58) && // numeric (0-9)
	        !(code > 64 && code < 91) && // upper alpha (A-Z)
	        !(code > 96 && code < 123)&& // lower alpha (a-z)
	        !(code == 32 || code==95)) {
	        alert('only Alphanumeric, under Score and space allowed in Project Name.');
	      return false;
	    }
	  } 
	  for (i = 0, len = projectDesc.length; i < len; i++) {
	    code = projectDesc.charCodeAt(i);
	    if (!(code > 47 && code < 58) && // numeric (0-9)
	        !(code > 64 && code < 91) && // upper alpha (A-Z)
	        !(code > 96 && code < 123)&& // lower alpha (a-z)
	        !(code == 32 || code==95)) {
	        alert('only Alphanumeric, under Score and space allowed in Description.');
	      return false;
	    }
	  }
	  return true;
}
</script>
