<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<style>
		body {
	       
	      }
		table.table.table-striped{
			width: 350px;
			margin: 0 auto;
			background-color: #f9f9f9;
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
	<script type="text/javascript">
	function validatepage(){
		var namewspaces = document.getElementById('constraintname').value;
		var moduleId = document.getElementById('idModule').value;
		var name = namewspaces.trim();
		if(namewspaces.length != name.length){
			alert('Please enter the name with out leading and tailing spaces');
			return false;
		}else if((name == null || name == "") && (moduleId == null || moduleId == "")){
			alert('Please select a option to search');
			return false;
		}else {
			return true;
		}
		
	}
</script>
</head>
<body>
<c:set var="searchUrl" scope="request">
	 <c:url value="/content/constraints/searcheditconstraints"/>
</c:set>
  <c:set var="pageUrl" scope="request">
	 <c:url value="/content/constraints/update" />
  </c:set>
  <form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="GET">
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>Edit Control</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty constraintupdate}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;
						<font color="red" size="3">${constraintupdate}</font>
				</td>
			</tr>
		</c:if>
	</table>
	
	<br />
	
	</form:form>
	<form:form class="form-viewproject" commandName="crudObj" action="${searchUrl}" method="POST">
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
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-bottom: 10px;" onclick="return validatepage()"/>&nbsp;
			</td>
			<td>
				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/constraints/editconstraint'"/>
			</td>
			
		</tr>
	</table>
	</form:form>
	<div>
	<table id="editusers" class="table table-striped table-bordered table-hover">
		<thead>
		</thead>
		<tbody>
			<c:if test="${not empty constraint_list}">
				<c:forEach items="${constraint_list}" var="item">
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
	</div><br/>
	<c:if test="${empty constraint_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="1" align="center"><font color=blue>---------*No Controls Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
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