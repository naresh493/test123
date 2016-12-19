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
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/reports/searchtenants" />
  </c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Tenants</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table align="center">
		<tr>
			 <td><label>Tenant <font color='red' size='3'></font></label></td>
			 <td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
				<td>
					<form:select id="tenant" path="id" style="color:#333333">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${tenantname_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td> 
				<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			
			<td>
				<form:label for="name" path="projectId">Select Project</form:label>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			<td>	
				<form:select id="idModules" path="projectId" style="color:#333333">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
				</form:select>
			</td>
			<td>
			&nbsp;&nbsp;&nbsp;
			</td>
			
			<td>
				
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;" onclick = "return validatePage();"/>&nbsp;
				<!-- <input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/tenant/viewtenants'"/> -->
				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/reports/tenantsreports'"/>
				
			</td>
		</tr>
	</table>
	<table id="viewprojects" class="table table-striped table-bordered table-hover" style="width:100%; word-break: break-all;">
		<thead align="center">
			<tr>
				<th align="center" style="width: 100px;">Tenant Name</th>
				<th align="center">User Name</th>
				<th align="center">First Name</th>
				<th align="center">Last Name</th>
				<th align="center">Email</th>
				<th align="center">Mobile</th>
				<th align="center">Projects</th>
				<th align="center">Created Date</th>
				<th align="center">Modified Date</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty tenant_list}">
				<c:forEach items="${tenant_list}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.userName}</td>
						<td align="center">${item.firstName}</td>
						<td align="center">${item.lastName}</td>
						<td align="center">${item.email}</td>
						<td align="center">${item.mobile}</td>
						<td align="center">${item.projects}</td>
						<td align="center">${item.createdDate}</td>
						<td align="center">${item.modifiedDate}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
<br />
<c:if test="${empty tenant_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="5" align="center"><font color=blue>---------*No Tenants Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
<c:if test="${fn:length(tenant_list)>10}">
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
	<script type="text/javascript">
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
		
	</script>
	
</c:if>

	<script type="text/javascript">
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
			var projectname = document.getElementById('idModules').value;
			var tenantname = document.getElementById('tenant').value;
			if((projectname == null || projectname == "") &&(tenantname == null || tenantname == "")){
				alert("Please select a option to search");
				return false;
			}else{
				return true;
			}
		}
		
	</script>
</form:form>
</body>
</html>