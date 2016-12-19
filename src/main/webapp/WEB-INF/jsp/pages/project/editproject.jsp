<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
	<script src="static/js/jquery.tablesorter.min.js"></script>
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
  			$("table#editusers").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
			
	</script>
	<style>
		body {
	      
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
</head>
<body>
<c:set var="searchUrl" scope="request">
	 <c:url value="/content/project/searcheditproject"/>
</c:set>
  <c:set var="pageUrl" scope="request">
	 <c:url value="/content/project/update" />
  </c:set>
  <form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="GET">
	 <table class="tblHeader-inner">
	 
	 		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					
					Edit Project</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty projectupdated}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<font color="red" size="3">${projectupdated}</font>
				</td>
			</tr>
		</c:if>
	</table><br />
	</form:form>
	 <form:form class="form-viewproject" commandName="crudObj" action="${searchUrl}" method="POST">
	 <table  cellpadding="10" cellspacing="5" align="center">
	<tr>
		<td>
			<form:label for="startDate" path="startingDate" style="margin-left:16px;margin-right:13px;">Start Date</form:label>
		</td>
		<td>
			<spring:bind path="startingDate">
				<form:input id="idstartDate" autocomplete="off"  title="Select date from datepicker"  type="text" class="datepicker" size="5" path="startingDate" style="width:200px; margin-left:10px;" itemLabel="startDate"/>
			</spring:bind></td>
		<td style="padding-left:20px;">
			<form:label for="endDate" path="endingDate">End Date</form:label>
		</td>
		<td>
			<spring:bind path="endingDate">
				<form:input id="idendDate" autocomplete="off" title="Select date from datepicker" type="text" class="datepicker" size="5" path="endingDate" style="width:200px; margin-left:10px;"  />
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>
			<form:label for="projectId" path="id" style="margin-left:16px;margin-right:13px;">Project Name</form:label>
		</td>
		<td>
			<form:select id="idProject" path="id" style="width:200px; margin-left:10px;">
				<form:option value="" label="--Please Select--"/>
				<form:options items="${projectname_list}" itemLabel="name" itemValue="id" />
			</form:select>
		</td>
		<td align="right">
			<label>Tenant <font color='red' size='3'></font></label>
		</td>
		<td colspan="2">
			<form:select id="tenant" path="tenantId" style="color:#333333;width:200px; margin-left:10px;">
				<form:option value="" label="--Please Select--"/>
				<form:options items="${tenantname_list}" itemLabel="name" itemValue="id" />
			</form:select></td>
					
		<td style="padding-left:20px;" ><input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;"  onclick="return validatePage()"/>
			<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/project/editproject'"/>
		</td>
	</tr>

</table>
</form:form>
<div>
	<!-- <table id="editproject" class="table table-striped table-bordered table-condensed table-hover"> -->
	<table id="editusers" class="table table-striped table-bordered table-hover">
		<thead></thead>
		<tbody>
			<c:if test="${not empty project_list}">
				<c:forEach items="${project_list}" var="item">
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
	<%-- <c:if test="${fn:length(project_list)>10}">
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
  			$("table#editproject").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
	</script>	 --%>
	<%-- <c:if test="${fn:length(project_list)>10}">
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
  			$("table#editusers").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
	</script>	 --%>
	
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
	
</body>
</html>