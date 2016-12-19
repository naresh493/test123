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
	<style>
		label{
			cursor: auto !important;
		}
	</style>
	<script type="text/javascript">
	
	function validatePage() {
		var constraintname = document.getElementById('constraintname').value;
		var idModule = document.getElementById('idModule').value;
		if((constraintname == null || constraintname == "") && (idModule == null || idModule == "")){
			alert("Please select a option to search");
			return false;
		}else{
			return true;
		}
	}
</script>
	
</head>
<body>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/constraints/searchconstraints"/>
</c:set>
<c:set var="refreshUrl" scope="request">
	 <c:url value="/content/constraints/viewconstraints"/>
</c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					View Controls</b></font><br /><br />
			</td>
		</tr>
		
	</table>
	<table align="center">
		<tr>
			<td>
				<label>Name</label>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>	
				<form:input id="constraintname" path="name" autocomplete="off" type = "text" placeholder="Control Name" />
			</td>
			<td>
			&nbsp;&nbsp;&nbsp;
			</td>
			
			<td>
					<form:label for="moduleId" path="moduleId">Package</form:label>
					
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>
					<form:select id="idModule" path="moduleId" style="color:#333333;width:150px !important">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${module_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-bottom: 10px;" onclick="return validatePage()"/>&nbsp;
			</td>
			<td>
				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/constraints/viewconstraints'"/>
			</td>
			
		</tr>
	</table>
	<br>
	<table id="viewconstraints" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Control Name</th>
				<th align = "center">Description</th>
				<th align="center">No. Of Packages</th>
				<th align="center">Created Date</th>
				<th align="center">Modified Date</th>
				
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty constraint_list}">
				<c:forEach items="${constraint_list}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.description}</td>
						
						<c:if test="${item.numberOfPackages eq 0}">
							<td align="center">${item.numberOfPackages}</td>
						</c:if>
						<c:if test="${item.numberOfPackages>0}">
							<td align="center"><a href="#" onclick="openModal('Assigned Packages','${item.packageNames}')">${item.numberOfPackages}</a></td>
						</c:if>
						<td align="center">${item.createdDate}</td>
						<td align="center">${item.modifiedDate}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<c:if test="${empty constraint_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="5" align="center"><font color=blue>---------*No Controls Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
<br />
<c:if test="${fn:length(constraint_list)>10}">
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
  			$("table#viewconstraints").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
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
	</script>
	</c:if>
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<!-- <script type="text/javascript">
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
	</script> -->
</form:form>
</body>
</html>