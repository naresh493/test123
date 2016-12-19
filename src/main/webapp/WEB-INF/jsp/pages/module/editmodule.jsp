<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<style>
		body {
	        height: 1000px;
	      }
		table.table.table-striped{
			width: 300px;
			margin: 0 auto;
			background-color: #f9f9f9;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
	<script type="text/javascript">
		function validatePage() {
			var name = document.getElementById('name').value;
			var permId = document.getElementById('idPermission').value;
			var roleId = document.getElementById('idRole').value;
			var reportId = document.getElementById('idReport').value;
			var controlId = document.getElementById('idControl').value;
			if((name == null || name == "") && (permId == null || permId == "")&&(roleId == null || roleId == "")&&
					(reportId == null || reportId == "")&&(controlId == null || controlId == "")){
				alert("Please select a option to search");
				return false;
			}else{
				return true;
			}
		}
	</script>
	
</head>
<body>
<c:set var="searchUrl" scope="request">
	 <c:url value="/content/module/searcheditmodules"/>
</c:set>
  
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					
					Edit Package</b></font><br />
			</td>
		</tr>
		
		<c:if test='${not empty moduleupdated}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<font color="red" size="3">${moduleupdated}</font>
				</td>
			</tr>
		</c:if>
	</table><br />	
	<form:form class="form-viewproject" commandName="crudObj" action="${searchUrl}" method="POST">
	<table align="center">
	<tr align="center">		
				
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idName" path="name">Name</form:label>
				</td>
				<td>	
					<form:input id="name" path="name" autocomplete="off" type = "text" placeholder="Package Name" />
				</td>
				<td style="text-align:right;padding-right:18px;" >
					<form:label  for="idPermission" path="permissionId">Permission</form:label>
				</td>
				<td >
					<form:select id="idPermission" path="permissionId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${permissions_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
				<tr align="center">
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idRole" path="roleId">Role</form:label>
				</td>
				
				<td>
					<form:select id="idRole" path="roleId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${roles_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idReport" path="reportId">Report</form:label>
				
				</td>
				<td>
					<form:select id="idReport" path="reportId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${reports_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
				<tr align="center">
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idControl" path="conId">Control</form:label>
				</td>
				
				<td>
					<form:select id="idControl" path="conId" width="1px">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${constraints_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td style="text-align:right;padding-right:18px;">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" onclick="return validatePage()"/>&nbsp;
				</td>
				
			</tr>
	</table>
	</form:form>
	<div>
	<table id="editusers" class="table table-striped table-bordered table-condensed table-hover">
		<thead>
		</thead>
		<tbody>
			<c:if test="${not empty module_list}">
				<c:forEach items="${module_list}" var="item">
					<tr>
						<td colspan="1"><a href="edit/${item.id}/update">${item.name}</a><br/>
							<c:if test="${empty item.modifiedBy}">
								<em class="help-inline">${item.createdName} created on ${item.createdDate}</em>
							</c:if>
							<c:if test="${not empty item.modifiedBy}">
								<em class="help-inline">${item.createdName} ${empty item.modifiedBy?'':'updated on'}  ${item.modifiedDate}</em>
							</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</div><br />
	<c:if test="${fn:length(module_list)>10}">
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
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#editusers").tablesorter({widthFixed: true, widgets: ['zebra']})
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
		
	</script>
</body>
</html>