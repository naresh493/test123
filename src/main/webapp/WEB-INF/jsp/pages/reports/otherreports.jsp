<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<html>
<head>
	<style>
		table.table.table-striped{
			height: 100px;
			width: 500px;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
		
	</style>
	
	<script type="text/javascript">
		function validatepage(){
			var namewspaces = document.getElementById('name').value;
			var name = namewspaces.trim();
			var displaynamewspaces = document.getElementById('displayname').value;
			var displayname = displaynamewspaces.trim();
			var b = true;
			if((name.length != namewspaces.length) || (displaynamewspaces.length != displayname.length)){
				alert('Name and display name should not contain leading and tailing spaces');
				b = false;
			}else if((name == null || name == "")||(displayname == null || displayname =="")){
				alert('name and displayname can not be null');
				b = false;
			}else{
				b = true;
			}
			return b;
		}
	</script>
	
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/reports/editotherreports" />
  </c:set> 
  <c:set var="createUrl" scope="request">
  
	 <c:url value="/content/reports/savedashboardreports" />
  </c:set> 
<form:form id="otherrepform" class="form-viewproject"  commandName="crudObj" action="${pageUrl}" method="GET">
<div id="normal">
	<table id="normal" class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Dash Board Reports</b></font><br /><br />
			</td>
		</tr>
		</table>
</div>
<div id="create">
		<table id="create" class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Create Dash Board Reports</b></font><br /><br />
			</td>
		</tr>
		</table>
</div>
<div id="view">
		<table id="view" class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					View Dash Board Reports</b></font><br /><br />
			</td>
		</tr>
		</table>
</div>
<div id="edit">
		<table id="edit" class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Edit Dash Board Reports</b></font><br /><br />
			</td>
		</tr>
	</table>
</div>
	
	<br>
	<table id="actions" align="center">
	<tr align="center">
	<td>
	<input type="button" class="btn btn-primary"  value="create" onclick="createotherreports();" />
	<input type="button" class="btn btn-primary"  value="view" onclick="viewButton();" />
	<input type="button" class="btn btn-primary" value="edit" onclick="editButton();"/>
	
	</td>
	</tr>
	<c:if test='${not empty reportupdated}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<font color="red" size="3">${reportupdated}</font>
				</td>
			</tr>
		</c:if>
	</table>
	<input id="back" type="button" value="Back" class="btn btn-primary" onclick="backButton();" style="display:none; width:70px; margin:0 auto;" >
	<input id="back" type="button" value="Assign" style="display:none;">
	<br>
	
	<table id="viewprojects"  class="table table-striped table-bordered table-hover" >
		<thead align="center">
			<tr ><th align="center"> </th>
				<th align="center">Name</th>
				<th align="center">Display Name</th>
				<th align="center">Description</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty other_reports}">
				<c:forEach items="${other_reports}" var="item">
					<tr>
					    <td> <input type="checkbox" name="repid" value="${item.id}" id="ch1"  /> </td> 
						<td align="center">${item.name}</td>
						<td align="center">${item.displayname}</td>
						<td align="center">${item.description}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
<br />

<table id="viewdata" style="display:none;" border="1" class="table table-striped table-bordered table-hover" >

</table>

<table id="editdata" style="display:none;" border="2" >

</table>
</form:form>

<form:form id="createotherreports" class="form-viewproject"  commandName="crudObj" action="${createUrl}" method="POST">
<table id="createReport" style="display:none; width:500px; margin:0 auto;" >
<tr>
<td>
	<label>Name <font color='red' size="2">*</font></label>
				</td>
				<td >
					<spring:bind path="name">
			              <form:input id="name" autocomplete="off" pattern="[0-9a-zA-Z_ ]*" title="only letters,numbers and Underscore" path="name" size="30" type="text" class="input-large" placeholder="Report Name" required="true"/>
			              
			        </spring:bind>
				</td>

</tr>
<tr>
<td>
	<label> Display Name <font color='red' size="2">*</font></label>
				</td>
				<td>
					<spring:bind path="displayname">
			              <form:input id="displayname" autocomplete="off" pattern="[0-9a-zA-Z_ ]*" title="only letters,numbers and Underscore" path="displayname" size="30" type="text" class="input-large" placeholder="Display Name" required="true"/>
			              
			        </spring:bind>
				</td>

</tr>
<tr>
<td>
	<form:label for="description" path="description">Description <font color='red' size="2">*</font></form:label>
				</td>
				<td>
					<spring:bind path="description">
			              <form:input id="description" autocomplete="off" pattern="[0-9a-zA-Z_ ]*" title="only letters,numbers and Underscore" path="description" size="30" type="text" class="input-large" placeholder="Description"/>
			              
			        </spring:bind>
				</td>
</tr>
<tr>
<td></td>
<td><input type="submit" class="btn btn-primary" value="create" onclick="return validatepage()"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href=<%=request.getContextPath() %>/content/cancel>
 					<input type="button" name="cancel" class="btn btn-primary" value="Cancel" />
					</a>
					</td>

</tr>

					
</table>
</form:form>

	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onclick="setTimeout('updatePage()',100);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onclick="setTimeout('updatePage()',100);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onclick="setTimeout('updatePage()',100);">Next &gt;</a></li>
			<li class="next last"><a href="#" onclick="setTimeout('updatePage()',100);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="5" />
		<input type="hidden" class="pagesize" value="10" />
	</div>
	
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript" language="JavaScript">
		$(document).ready(function(){
			
  			$("table#viewprojects").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
  			
  			 
		});
		 
		
		 function editButton(){
			 $('#normal').hide();
				$('#create').hide();
				$('#view').hide();
				$('#edit').show();
			 var boxes = $("input:checked");
			 if(boxes.length ==1){
				//alert($("input:checked").val());
				//var action = $("#otherrepform").attr('action');
				//alert('action >> '+action);
				//action = action +'/'+ $("input:checked").val()+'/update';
				//alert('after mod >> '+action);
				//$("#otherrepform").attr("action",action);
				$("#otherrepform").submit();
			 }else if(boxes.length ==0){
				 alert('You have to select atleast one check box.');
			 }else{
				 alert('You can select only one check box to edit report.');
			 }
			 
			
	  
		}
		
		function backButton(){
			document.getElementById("actions").style.display = "table";
	    	 document.getElementById("viewprojects").style.display = "table";
	    	 document.getElementById("viewdata").style.display = "none";
	    	document.getElementById("pager").style.display = "table";
	    	document.getElementById("back").style.display = "none";
	    	document.getElementById("createReport").style.display = "none";
	      $("#viewdata").empty(); 
		}
		
		function viewButton(){
			$('#normal').hide();
			$('#create').hide();
			$('#view').show(); 
			$('#edit').hide();
			var html = '';
  			html = html+'<tr><th align="center"> </th>';
  			html = html+'<th align="center">Name</th>';
  			html = html+'<th align="center">Display Name</th>';
  			html = html+'<th align="center">Description</th>';
  			html = html+'</tr>';
  			
  			$("#viewdata").append(html);
			$('input[type=checkbox]').each(function () {
		           if (this.checked) {
		        	   html = '';
	  			    	html = html+'<tr >';
	  			    	$(this).closest('tr').find('td').each(function() {
	  			    		html = html+"<td>"+ $(this).text() + "</td>";            
	  			    	});
	  			     html = html+'</tr>';
	  			    	$("#viewdata").append(html);
		           }else{
		        	   html = '';
		           }
		          
		});
			var boxes = $("input:checked");
			 if(boxes.length ==0){
				 $("#viewdata").empty();
				 alert('You have to select atleast one check box.');
			 }else{
			document.getElementById("createReport").style.display = "none";
			document.getElementById("actions").style.display = "none";
	    	document.getElementById("viewprojects").style.display = "none";
	    	document.getElementById("viewdata").style.display = "table";
	    	document.getElementById("pager").style.display = "none";
	    	document.getElementById("back").style.display = "table";
			 }
		}
		
		
		function createotherreports(){
			$('#normal').hide();
			$('#create').show();
			$('#view').hide();
			$('#edit').hide();
			document.getElementById("createReport").style.display = "table";
			document.getElementById("actions").style.display = "none";
	    	 document.getElementById("viewprojects").style.display = "none";
	    	 document.getElementById("viewdata").style.display = "none";
	    	document.getElementById("pager").style.display = "none";
	    	document.getElementById("back").style.display = "none";
			
		}
		
		/* function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
		 */
		 function updatePage() {
				document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
				 var pageNum = document.getElementById('disp').value;
				var res = pageNum.split("/");
				if(res[0]==res[1]){
					$('.next').addClass("active");
				}else{
					$('.next').removeClass("active");
				}
				if(res[0]==1){
					$('.prev').addClass("active");
				}else{
					$('.prev').removeClass("active");
				}
				
			}
		 
	</script>

</body>
</html>