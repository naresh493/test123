
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<html>
<head>
<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		
		
		$("input").filter('.datepicker').datepicker({
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
		 $(document).ready(function(){
  			$("table#viewprojects").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		 
		/* function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		} */
		
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

		function validatePage(){
			var startDate = document.getElementById('idstartDate').value;
			var endDate = document.getElementById('idendDate').value;
			var modId = document.getElementById('idModule').value;
			var projId = document.getElementById('idProject').value;
			if((startDate == null || startDate == '') && (endDate == null || endDate == '') && (projId == null || projId == "") && (modId == null || modId == "")){
				alert('Please select atleast a option to search');
				return false;
			}else{
				return true;
			}
		}
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
			
		}
		
			
	</script>
	<style>
		table.table.table-striped{
			height: 100px;
			width: 900px;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
	<style>
		label{
			cursor: auto !important;
		}
	</style>
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/reports/searchprojects"/>
  </c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Projects</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table align="center">
		<tr align="center">
				
				<td>
					<form:label for="startDate" path="startingDate">Start Date</form:label>
				</td>
				<td>
					<spring:bind path="startingDate">
			              <form:input id="idstartDate" autocomplete="off" title="Select date from datepicker"  type="text" class="datepicker" size="5" path="startingDate" itemLabel="startDate"/>
			              
			        </spring:bind>
				</td>
				
				<td>
					<form:label for="endDate" path="endingDate" style="margin-left:16px;margin-right:13px;">End Date</form:label>
				</td>
				<td>
					<spring:bind path="endingDate">
			              <form:input id="idendDate" autocomplete="off" title="Select date from datepicker" type="text" class="datepicker" size="5" path="endingDate"/>
			        </spring:bind>
				</td>
				</tr>
				<tr>
				<td>
					<form:label for="endDate" path="moduleId" style="margin-left:16px;margin-right:13px;">Package Name</form:label>
				</td>
				<td>
					<form:select id="idModule" path="moduleId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${module_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				<td>
					<form:label for="projectId" path="projectId" style="margin-left:16px;margin-right:13px;">Project Name</form:label>
				</td>
				<td>
					<form:select id="idProject" path="projectId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${projectname_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			</table>
			<table align="center">
				<tr align="center">
				<td colspan="2">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;"  onclick="return validatePage()"/>&nbsp;
					<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/reports/projectsreports'"/>
				</td>
			</tr>
	</table>
	<br>
	<table id="viewprojects" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Name</th>
				<th align="center">Start Date</th>
				<th align="center">End Date</th>
				<th align="center">No. Of Packages</th>
				<th align="center">Created Date</th>
				<th align="center">Modified Date</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty project_list}">
				<c:forEach items="${project_list}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.startingDate}</td>
						<td align="center">${item.endingDate}</td>
						<c:if test="${item.moduleCount eq 0}">
							<td align="center">${item.moduleCount}</td>
						</c:if>
						<c:if test="${item.moduleCount>0}">
							<td align="center"><a href="#" onclick="openModal('Available Packages','${item.moduleName}')">${item.moduleCount}</a></td>
						</c:if>
						<td align="center">${item.createdDate}</td>
						<td align="center">${item.modifiedDate}</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div id="maindiv"  ></div>
<br />
<c:if test="${fn:length(project_list)>10}">
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
	</c:if>
	
</form:form>
</body>
</html>