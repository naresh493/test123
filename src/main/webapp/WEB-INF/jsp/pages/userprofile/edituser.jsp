<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<style>
		body {
	       
	      }
		table.table.table-striped{
			width: 900px;
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
  <c:set var="pageUrl" scope="request">
	 <c:url value="/content/updateuser" />
  </c:set>
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>Edit User</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty userupdated}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<font color="red" size="3">${userupdated}</font>
				</td>
			</tr>
		</c:if>
	</table><br />	
	<div>
	<table id="editusers" class="table table-striped table-bordered table-condensed table-hover">
		<thead>
			<tr>
				<th colspan="1">User Name</th>
				<th colspan="1">First Name</th>
				<th colspan="0">Last Name</th>
				<th colspan="0">Email Address</th>
				<!-- <th colspan="0">Domain</th>
				<th colspan="0">Experience</th>
				<th colspan="0">Number Of Projects Worked</th> -->
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty user_list}">
				<c:forEach items="${user_list}" var="item">
					<tr>
						<td colspan="1"><a href="users/${item.id}/update">${item.userName}</a><br/>
							<c:if test="${empty item.modifiedBy}">
								<em class="help-inline">${item.createdName} created on ${item.createdDate}</em>
							</c:if>
							<c:if test="${not empty item.modifiedBy}">
								<em class="help-inline">${item.createdName} ${empty item.modifiedBy?'':'updated on'}  ${item.modifiedDate}</em>
							</c:if>
						</td>
						<td colspan="0">${item.firstName}</td>
						<td colspan="0">${item.surName}</td>
						<td colspan="0">${item.emailAddress}</td>
						<%-- <td colspan="0">${item.domain}</td>
						<td colspan="0">${item.experience}</td>
						<td colspan="0">${item.noOfProjectsWorked}</td> --%>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</div><br />
<c:if test="${fn:length(user_list)>10}">
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
</c:if>		
</body>
</html>