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
		/*  $(document).ready(function(){
  			$("table#viewtenants").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		}); */
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center>"+data);
			
		}
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
			
			var projectname = document.getElementById('idProject').value;
			var packagename = document.getElementById('moduleId').value;
			var tenantname = document.getElementById('tenant').value;
			var startDate = document.getElementById('idstartDate').value;
			var endDate = document.getElementById('idendDate').value;
			if((projectname == null || projectname == "") && (packagename == null || packagename == "")&&(tenantname == null || tenantname == "")&&
					(startDate == null || startDate == "")&&(endDate == null || endDate == "")){
				alert("Please select a option to search");
				return false;
			}else{
				return true;
			}
			var strDate = new Date(startDate);
			var edDate = new Date(endDate);
			var fnDate = strDate-edDate;
			if(fnDate >= 0){
				alert('Please Enter Valid Start and EndDate');
				return false;
			}else{
				return true;
			}
		}
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5><p>"+data+"</p></center>");
			
		}
		
		 $(document).ready(function(){
	  			$("table#viewtenants").tablesorter({widthFixed: true, widgets: ['zebra']})
	  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
	  			updatePage();
			});
	</script>

	<style>
	table.table.table-striped{
		height: 50px;
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
			cursor:auto !important;
		}
	</style>
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/project/searchprojects"/>
  </c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	
	<table  cellpadding="10" cellspacing="5" align="center">
		<tr>
					<td colspan="4" align="center"><font size="3"><b>View Projects</b></font><br /><br />
					</td>
		</tr>
	</table>
	
	<table  cellpadding="10" cellspacing="5" align="center">
		<tr>
	
			<td>
				<form:label for="startDate" path="startingDate">Start Date</form:label>
			</td>
			<td>
				<spring:bind path="startingDate">
					<form:input id="idstartDate" autocomplete="off"  title="Select date from datepicker"  type="text" class="datepicker" size="5" path="startingDate" style="width:200px; " itemLabel="startDate"/>
				</spring:bind>
			</td>
			<td style="padding-left:20px;">
				<form:label for="endDate" path="endingDate">End Date</form:label>
			</td>
			<td>
				<spring:bind path="endingDate">
					<form:input id="idendDate" autocomplete="off" title="Select date from datepicker" type="text" class="datepicker" size="5" path="endingDate" style="width:200px; "  />
				</spring:bind>
			</td>
		</tr>
		<tr>
			<tr>
				
				<td>
					<form:label for="projectId" path="id" style="margin-right:13px;">Project Name</form:label>
				</td>
				<td>
					<form:select id="idProject" path="id">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${projectname_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				<td>
					<form:label for="moduleId" path="modId" style="margin-left:16px;margin-right:13px;">Package Name</form:label>
				</td>
				<td>
					<form:select id="moduleId" path="modId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${modulename_list}" itemLabel="name" itemValue="id"/>
					</form:select>
					
				</td>
		</tr>
		<tr>
			<td>
				<label>Tenant <font color='red' size='3'></font></label>
			</td>
			<td colspan="2">
				<form:select id="tenant" path="tenantId" style="color:#333333;width:200px; ">
					<form:option value="" label="--Please Select--"/>
					<form:options items="${tenantname_list}" itemLabel="name" itemValue="id" />
				</form:select>
			</td>
			<td style="padding-left:20px;" ><input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;" onclick="return validatePage()"/>
				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/project/viewproject'"/>
			</td>
				
		</tr>
</table>
	
	<table id="viewtenants" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Project Name</th>
				<th align="center">No.Of Packages</th>
				<th align="center">Start Date</th>
				<th align="center">End Date</th>
				<th align="center">Is Active</th>
				<th align="center">Description</th>
				<th align="center">Tenant Name</th>
				<th align="center">Created Date</th>
				<th align="center">Modified Date</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty project_list}">
				<c:forEach items="${project_list}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						
						<c:if test="${item.moduleCount eq 0}">
							<td align="center">${item.moduleCount}</td>
						</c:if>
						<c:if test="${item.moduleCount>0}">
							<td align="center"><a href="#" onclick="openModal('Assigned Packages','${item.moduleNames}')">${item.moduleCount}</a></td>
						</c:if>
						<td align="center">${item.startingDate}</td>
						<td align="center">${item.endingDate}</td>
						<td align="center">${item.disabled ? 'false' : 'true'}</td>
						<td align="center">${item.description}</td>
						<td align="center">${item.tenantName}</td>
						<td align="center">${item.createdDate}</td>
						<td align="center">${item.modifiedDate}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty project_list}">
				<table class="table table-striped table-bordered">
				<tr>
					<td align="center"><font color=blue>---------*No Projects Available*---------</font></td>
					
				</tr>
			</table>
			</c:if>
		</tbody>
	</table>
	
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
	<script src="static/js/jquery.tablesorter.min.js"></script>
	
</form:form>
</body>
</html>