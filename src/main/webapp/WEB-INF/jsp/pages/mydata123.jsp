<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags'%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta name="author" content="The CSS Ninja">
	<meta name="keywords" content="CSS, Tree menu, checked pseudo-class, CSS Ninja">
	<meta name="description" content="Create a pure CSS tree folder structure with collapsible folders utilising checkboxes along with the checked pseudo-class">
	<meta name="robots" content="all">
	<meta name="copyright" content="The CSS Ninja">
	<link rel="stylesheet" type="text/css" href="static/css/_styles123.css" media="screen">
	
</head>
<html>
<body>
	<!-- FOR SYS ADMIN -->
	<c:if test="${ roleName eq 'SYS_ADMIN' }">
 		<ol class="tree">
			<li>
				<label for="folder1">General</label> <input type="checkbox" checked id="folder1" /> 
				<ol>
					<c:forEach items="${generalList}" var="item">
						<li class="file"><a href="">${item}</a></li>
					</c:forEach>
				</ol>
			</li>
			<li>
				<label for="folder2">Users</label> <input type="checkbox" checked id="folder2" /> 
				<ol>
					<c:forEach items="${userList}" var="item">
						<li class="file"><a href="">${item}</a></li>
					</c:forEach>
				</ol>
			</li>
			<li>
				<label for="folder3">Tenants</label> <input type="checkbox" checked id="folder3" /> 
				<ol>
					<c:forEach items="${tenantList}" var="item">
						<li class="file"><a href="">${item}</a></li>
					</c:forEach>
				</ol>
			</li>
			<li>
				<label for="folder4">Projects</label> <input type="checkbox" checked id="folder4" /> 
				<ol>
					<c:forEach items="${projectList}" var="item">
						<li class="file"><a href="">${item}</a></li>
					</c:forEach>
				</ol>
			</li>
			<li>
				<label for="folder5">Licenses</label> <input type="checkbox" checked id="folder5" /> 
				<ol>
					<c:forEach items="${licenseList}" var="item">
						<li class="file"><a href="">${item}</a></li>
					</c:forEach>
				</ol>
			</li>
		</ol>
	</c:if>
	</body>
<html>