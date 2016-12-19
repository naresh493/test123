<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<head>

<style>
		table.table.table-striped{
			height: 100px;
			width: 800px;
			background-color: #f9f9f9;
			margin: 0 auto;
			 
		}
		table tr td
		{
		min-width:100px;
		word-break: break-word;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
		
	</style>	
</head>
<body>

<link rel="stylesheet" href="/QlikTestAdmin/static/css/jquery-ui-1.8.16.custom.css">
	<script type="text/javascript">
	
	
	$(document).ready(function() {
		
		$("input").filter('.startdatepicker').datepicker({
			minDate : 0,
			showOn: 'button',
			buttonImage: "/QlikTestAdmin/static/images/calendar.png",
			buttonImageOnly: true,
			minDate: new Date(2014, 0, 1),
			dateFormat: 'yy-m-d',
			maxDate: '+0d',
	        autoclose: true
		});
		$("input").filter('.enddatepicker').datepicker({
			minDate : 0,
			showOn: 'button',
			buttonImage: "/QlikTestAdmin/static/images/calendar.png",
			buttonImageOnly: true,
			minDate: new Date(2014, 0, 1),
			dateFormat: 'yy-m-d',
			maxDate: new Date(2100, 11, 31),
	        autoclose: true
		});
	});
</script>

<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Error Logs </b></font><br /><br />
			</td>
		</tr>
	</table>
	
 <form action="getErrorLogDetails" method="POST">
 	<table  class="table table-striped  table-hover">
 		<tr><td>Start Date:</td><td><input type="text" name="startDate" title="Select date from datepicker" size="5"  autocomplete="off" class="startdatepicker"/></td>
 		<td>End Date:</td><td><input type="text" name="endDate" title="Select date from datepicker"size="5" autocomplete="off" class="enddatepicker"/></td></tr>
 		<tr><td>Choose Project Type: </td><td><select name="projectType">
 		<option value="qliktestadmin">QlikTestAdmin</option>
 		<option value="sikuli">Sikuli</option>
 		<option value="videocapture">VideoCapture</option>
 		<option value="objectspy">ObjectSpy</option>
 		</select></td>
 		<td><input type="submit" class="btn btn-primary" value="Search" name="submit"/></td>
 		<td><input type="submit" class="btn btn-primary" value="Refresh" name="submit"/></td></tr>
 	</table>


	<table id="viewallerrorlogs" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Level</th>
				<th align="center">ClassName</th>
				<th align = "center">Method</th>
				<th align="center">Message</th>
				<th align = "center">Timestamp</th>
				<th align = "center">LineNumber</th>
				<th align = "center">Host-ip</th>
				
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty errorlogs}">
				<c:forEach items="${errorlogs}" var="errorLog">
					<tr>
						<td align="center">${errorLog.level}</td>
						<td align="center">${errorLog.loggerName.className}</td> 
						<td align="center" >${errorLog.method}</td>
						<td align="center">${errorLog.message}</td>
						<td align="center" >${errorLog.timestamp}</td>
						<td align="center" >${errorLog.lineNumber}</td>
						<td align="center" >${errorLog.host.ip}</td> 
						
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	
	<c:if test="${fn:length(errorlogs)>10}">
	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onmouseup="setTimeout('updatePage()',500);">Next &gt;</a></li>
			<li class="next last"><a href="#" onmouseup="setTimeout('updatePage()',500);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="5" />
		<input type="hidden" class="pagesize" value="10" />
	</div>
	</c:if>
	
	
	<script type="text/javascript">
		 $(document).ready(function(){
  			$("table#viewallerrorlogs").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
		</script>
		 </form>
</body>
</html>