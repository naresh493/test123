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
 	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>Edit Bug Tracking Configuration</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty configurationupdated}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b>${configurationupdated}</b>
				</td>
			</tr>
		</c:if>
	</table><br />	
	<div>
	<table id="editobjspyconfig" class="table table-striped table-bordered table-condensed table-hover">
		<thead>
			<tr>
				<th colspan="1">Name</th>
				<th colspan="0">Url</th>
				
				<!-- <th colspan="2">Assigned Projects</th> -->
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty bugtrk_list}">
				<c:forEach items="${bugtrk_list}" var="item">
					<tr>
						<td colspan="1"><a href="bugtrk/${item.id}/updatebugtrk">${item.configurationname}</a><br/>
							<c:if test="${empty item.modifiedBy}">
								<em class="help-inline">QlikTestSysAdmin created on ${item.createdDate}</em>
							</c:if>
							<c:if test="${not empty item.modifiedBy}">
								<em class="help-inline">QlikTestSysAdmin ${empty item.modifiedBy?'':'updated on'}  ${item.modifiedDate}</em>
							</c:if>
						</td>
						<td colspan="0">${item.url}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</div><br />
	<c:if test="${fn:length(bugtrk_list)>10}">
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