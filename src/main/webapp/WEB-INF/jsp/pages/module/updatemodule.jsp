<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<style>
	label{
		cursor:auto !important;
	}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">
		function twoFns(){
			var boo = checkRow();
			if(boo == false){
				return false;
			}else{
				insRow();
				return true;
			}
		}
		
		/* function checkRow(){
			var table = document.getElementById('POITable');
			var rowCount = table.rows.length;
			var modId;
			var modValue;
			if(rowCount-1 == 0) {
				return true;
			} else {
				try{
					modId = document.getElementById('POITable').rows[rowCount-1].cells[0].getElementsByTagName('select')[0].value;
				}catch(err){
					modId = null;
				}
				try{
					modValue = document.getElementById('POITable').rows[rowCount-1].cells[1].getElementsByTagName('input')[0].value;
				}catch(err){
					modValue = null;
				}
				if((modId == null || modId == "")||(modValue == null || modValue == "")){
					alert('Please select the existing row');
					return false;
				}else{
					return true;
				}
			}
			
		} */
		
		function checkRow(){
			
			var table = document.getElementById('POITable');
	    	var rowCount = table.rows.length;
	    	var modId;
	    	var modValue;
	    	var code, i, len;
	    	try{
	    		modId = document.getElementById('POITable').rows[rowCount-1].cells[0].getElementsByTagName('select')[0].value;
	    	}catch(err){
	    		modId = null;
	    	}
	    	try{
	    		modValue = document.getElementById('POITable').rows[rowCount-1].cells[1].getElementsByTagName('input')[0].value;
	    		 for (i = 0, len = modValue.length; i < len; i++) {
				    code = modValue.charCodeAt(i);
				    if (!(code > 47 && code < 58)) {
				        alert('Value Text box Should accept only digit.');
				        document.getElementById('POITable').rows[rowCount-1].cells[1].getElementsByTagName('input')[0].value = '';
				     	return false;
				    }
	    		 }   
	    	}catch(err){
	    		modValue = null;
	    	}
	    	if((modId == null || modId == "")||(modValue == null || modValue == "")){
	    		alert('Please select the existing row');
	    		return false;
	    	}else{
	    		return true;
	    	}
			
			
	    	/* var table = document.getElementById('POITable');
	    	var rowCount = table.rows.length;
	    	var modId;
	    	var modValue;
	    	var code, i, len;
	    	try{
	    		modId = document.getElementById('POITable').rows[rowCount-1].cells[0].getElementsByTagName('select')[0].value;
	    	}catch(err){
	    		modId = null;
	    	}
	    	try{
	    		modValue = document.getElementById('POITable').rows[rowCount-1].cells[1].getElementsByTagName('input')[0].value;
	    		 for (i = 0, len = modValue.length; i < len; i++) {
				    code = modValue.charCodeAt(i);
				    if (!(code > 47 && code < 58)) {
				        alert('Value Text box Should accept only digit.');
				        document.getElementById('POITable').rows[rowCount-1].cells[1].getElementsByTagName('input')[0].value = '';
				     	return false;
				    }
	    		 }   
	    	}catch(err){
	    		modValue = null;
	    	}
	    	if((modId == null || modId == "")||(modValue == null || modValue == "")){
	    		alert('Please select the existing row');
	    		return false;
	    	}else{
	    		return true;
	    	} */
	    }
	    
		
		function deleteRow(obj) {
			var table = document.getElementById("POITable");
	    	var rowLength = table.rows.length;
	    	if(rowLength == 2){
	    		alert('Atleast One Row You have to Select.');
	    		return false;
	    	}
	    	var b = confirm("Do you want to delete!");
	    	if(b == true){
	    		 var index = obj.parentNode.parentNode.rowIndex;
	 	        var table = document.getElementById("POITable");
	 	        table.deleteRow(index);
	 	        alert('Row deleted successfully');
	    	}
		    /* var index = obj.parentNode.parentNode.rowIndex;
		    var table = document.getElementById("POITable");
		    table.deleteRow(index); */
		}
		function insRow()
		{
		    var x=document.getElementById('POITable');
		    if((x.rows.length-1) == 0) {
		    	alert("wait i will insert a new row");
		    	var inp1 = new_row.cells[0].getElementsByTagName('select')[0];
			    inp1.value = '';
			    var inp2 = new_row.cells[1].getElementsByTagName('input')[0];
			    inp2.value = '';
			    x.appendChild(new_row);
		    } else {
		    	var new_row = x.rows[1].cloneNode(true);
			    var len = x.rows.length;
			    var inp1 = new_row.cells[0].getElementsByTagName('select')[0];
			    inp1.value = '';
			    var inp2 = new_row.cells[1].getElementsByTagName('input')[0];
			    inp2.value = '';
			    x.appendChild(new_row);
		    }
		    
		}
</script>
<script type="text/javascript">
	function validatePage(){
		var modName = document.getElementById('moduleName').value;
		var str = document.getElementById('moduleDesc').value;
		var result = document.getElementById('moduleList').value;
		var code, i, len;
		if(modName.trim() == "" || modName.trim() == null){
			alert('Please Enter Package Name without leading and tailing spaces');
			return false;
		}
		  for (i = 0, len = modName.length; i < len; i++) {
		    code = modName.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Package Name.');
		      return (false);
		    }
		  } 
		  for (i = 0, len = str.length; i < len; i++) {
		    code = str.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Description.');
		      return (false);
		    }
		  }
		/* if(result == "") {
			alert("Please Select atleast one Control");
			return false;
		}
		else{
			$("#to option").prop("selected", "selected");
	    	$("#toreports option").prop("selected", "selected");
	    	$("#signup").submit();
			return true;
		} */
		  if(result != "")
			{
				var table = document.getElementById('POITable');
		    	var rowCount = table.rows.length;
		    	var modValue;
				var j;
		    	for (j = 1; j < rowCount; j++) {
		    	modValue = document.getElementById('POITable').rows[j].cells[1].getElementsByTagName('input')[0].value;
	    		if(modValue == ""){
					alert('Value Text Box Should not be Empty.');
					return false;
				}
		    	for (i = 0, len = modValue.length; i < len; i++) {
				    code = modValue.charCodeAt(i);
				    if (!(code > 47 && code < 58)) {
				        alert('Value Text box Should accept only digit.');
				        document.getElementById('POITable').rows[j].cells[1].getElementsByTagName('input')[0].value = '';
				     	return false;
				    	}
	    			 }   
		    	}
			}	
		if(result == "") {
			alert("Please Select atleast one Control");
			return false;
		}
		
		selectAllValues();
		return true;
	}
</script>
<script> 
$(document).ready(function(){
  $("#flip").click(function(){
    $("#panel").slideToggle("slow");
    $("#panel1").hide();
  });
});
$(document).ready(function(){
	  $("#flip1").click(function(){
	    $("#panel1").slideToggle("slow");
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
		width:60%;
    	margin: 0 auto;
		}
		#panel1
		{
		padding:50px;
		display:none;
		}
</style>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/module/updatemodule" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST" >
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center">
					<font size="3"><b>Edit Package</b></font><br /><br />
				</td>
			</tr>
			
		</table>
		<table class="tblFormData">
			<tr>
				<td><label>Package Name</label></td>
				<td>
					<spring:bind path="name">
			              <form:input path="name" id="moduleName" autocomplete="off"  size="30" type="text" class="input-large" placeholder="Package Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Description</label></td>
				<td>
					<spring:bind path="description">
			              <form:textarea path="description" id="moduleDesc" autocomplete="off"  type="textarea" class="input-large" placeholder="Description"/>
			              
			        </spring:bind>
				</td>
			</tr>
			</table>
			<div id="flip">Assign Permissions To Package</div>
			<div id="panel">
			<table>
				<table align="center">
					<tr align="center" >
				</table>
				<table align="center">
					<tr align="center">
						<td>
							<table align="center" >
								<tr>
									<td>
										<div><font size="2">Available Permissions</font></div>
										<form:select id="from" path="permissions" multiple="multiple" style="width:255px;height:155px;" >
											<form:options items="${assigned_permissions_list}" itemLabel="name" itemValue="id" />
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
		        						<form:select id="to" path="permissionsId" multiple="multiple" style="width:255px;height:155px;" >
											<form:options items="${available_permissions_list}" itemLabel="name" itemValue="id" />
										</form:select>
	   						 		</td>
	   							 </tr>
							</table>
						</td>
											
						</tr>
					</table>
				</table>
				</div>
				<br/>
			<div id="flip1">Assign Reports To Package</div>
			<div id="panel1">
			<table>
				<table align="center">
					<tr align="center" >
				</table>
				<table align="center">
					<tr align="center">
						<td>
							<table align="center" >
								<tr>
									<td>
										<div><font size="2">Available Reports</font></div>
										
										<form:select id="fromreports" path="reports" multiple="multiple" style="width:255px;height:155px;" >
											<form:options items="${available_reports_list}" itemLabel="name" itemValue="id" />
										</form:select>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table align="center">
								<div class="controls"> 
							        <a href="javascript:moveAllReports('fromreports', 'toreports')">&gt;&gt;</a> 
							        <a href="javascript:moveSelectedReports('fromreports', 'toreports')">&gt;</a> 
							        <a href="javascript:moveSelectedReports('toreports', 'fromreports')">&lt;</a> 
							        <a href="javascript:moveAllReports('toreports', 'fromreports')" href="#">&lt;&lt;</a> 
							    </div>
							</table>
						
						</td>
						<td>
						
							<table>
								<tr id="trPerm" >
									<td colspan="2" >
										<div><font size="2">Assigned Reports</font></div>
		        						<form:select id="toreports" path="reportIds" multiple="multiple" style="width:255px;height:155px;" >
											<form:options items="${assigned_reports_list}" itemLabel="name" itemValue="id" />
										</form:select>
	   						 		</td>
	   							 </tr>
							</table>
						</td>
						</tr>
					</table>
				</table>
				</div>
				
				<div id="POItablediv">
   
   				<div style="width:50%; margin:0 auto;">
   				<p style="font-size:20px; color:#333; text-align:center; margin-top:10px;">Controls</p><br/>
    
			    <table  style="float: right; margin-bottom:10px;">
				    <tr>
				   		<td><input type="button" id="addmorePOIbutton" style="padding:5px 20px; font-weight:bold;" value="Add" onclick="return twoFns()"/></td>
				    </tr>
			    </table>
    
    
    <table id="POITable" border="1" class="tblFormData" >
    
    <thead align="center">
			<tr>
           	 	<th>Control Name</th>
            	<th>Value</th>
            	<th>Delete?</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty constraints_list}">
				<c:forEach items="${constraints_list}" var="item">
					<tr>
						<td align="center">
							<form:select id="moduleList" path="constraintId" style="color:#333333">
								<form:option value="${item.id}" label="${item.name}"/>
								<form:options items="${item.remainList}" itemLabel="name" itemValue="id" />
							</form:select>
							<%-- <form:select id="moduleList" required="true" path="constraintId" style="color:#333333">
										<form:option path="constraintId" value="${item.name}" itemLabel="name" itemValue="id"/>
										<form:options path="constraintId" items="${item.remainList}" itemLabel="name" itemValue="id" />
							</form:select> --%>
						</td>
						<td align="center">
							<spring:bind path="value">
								<form:input path="value" size="25" type="text" id="lngbox" value="${item.value}"/>
							</spring:bind>
						</td>
          				<td align="center"><img src="/QlikTestAdmin/static/images/deleteicon.png" onclick="deleteRow(this)" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
        
    </table>
				
				</div>
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
	        
	        <spring:bind path="id">
	              <form:hidden path="id" value="${idToBeUpdated}" />
	        </spring:bind>
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary" value="Update" onclick="return validatePage()"/>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			</tr>
			</table>
	<script>
	    function moveAll(from, to) {
	        $('#'+from+' option').remove().appendTo('#'+to); 
	    }
	    
	    function moveSelected(from, to) {
	        $('#'+from+' option:selected').remove().appendTo('#'+to); 
	    }
    </script> 
    <script>
	    function moveAllReports(fromreports, toreports) {
	        $('#'+fromreports+' option').remove().appendTo('#'+toreports); 
	    }
	    
	    function moveSelectedReports(fromreports, toreports) {
	        $('#'+fromreports+' option:selected').remove().appendTo('#'+toreports); 
	    }
    </script> 
	</form:form>
	