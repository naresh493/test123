<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags'%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>QlikTest Administration</title>
	<link rel="stylesheet" href="static/css/_styles123.css" media="screen" />
</head>
<body>

<table width="1350" border="0">
	<tr>
		<td align="left">
			<a href="http://qliktest.com"><img src="static/images/qliktest-logo.png" alt="QlikTest" title="QlikTest" /></a>
		</td>
		<td align="right">Hi, <font color='blue'><c:out value="${username}" /> (<c:out value="${roleName}"/>)</font><br />
			<font size="2"><a href="/QlikTestAdmin">[Sign Out]</a></font>
		</td>
	</tr>
</table>
<table width="1350" border="0">
	<tr>
		<td colspan="2" align="center"><font size="6"><b>QlikTest Administration</b></font>
		</td>
	</tr>
</table>
	<!-- <iframe src="importPage.jsp" height="650" width="300" scrolling="yes" frameborder="no"> -->
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
		
		<!-- FOR APP ADMIN -->
		<c:if test="${ roleName eq 'APP_ADMIN' }">
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
					<label for="folder3">Projects</label> <input type="checkbox" checked id="folder3" /> 
					<ol>
						<c:forEach items="${projectList}" var="item">
							<li class="file"><a href="">${item}</a></li>
						</c:forEach>
					</ol>
				</li>
				<li>
					<label for="folder4">Modules</label> <input type="checkbox" checked id="folder4" /> 
					<ol>
						<c:forEach items="${moduleList}" var="item">
							<li class="file"><a href="">${item}</a></li>
						</c:forEach>
					</ol>
				</li>
				<li>
					<label for="folder5">Team</label> <input type="checkbox" checked id="folder5" /> 
					<ol>
						<c:forEach items="${teamList}" var="item">
							<li class="file"><a href="">${item}</a></li>
						</c:forEach>
					</ol>
				</li>
			</ol>
		</c:if>
		
		<!-- FOR TEST DESIGNER -->
		<c:if test="${ roleName eq 'TEST_DESIGNER' }">
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
					<label for="folder2">Objects</label> <input type="checkbox" checked id="folder2" /> 
					<ol>
						<c:forEach items="${objectList}" var="item">
							<li class="file"><a href="">${item}</a></li>
						</c:forEach>
					</ol>
				</li>
				<li>
					<label for="folder3">Test Case</label> <input type="checkbox" checked id="folder3" /> 
					<ol>
						<c:forEach items="${testCaseList}" var="item">
							<li class="file"><a href="">${item}</a></li>
						</c:forEach>
					</ol>
				</li>
			</ol>
		</c:if>
		
		<!-- FOR TEST EXECUTOR -->
		<c:if test="${ roleName eq 'TEST_EXECUTOR' }">
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
					<label for="folder2">Test Suite</label> <input type="checkbox" checked id="folder2" /> 
					<ol>
						<c:forEach items="${testSuiteList}" var="item">
							<li class="file"><a href="">${item}</a></li>
						</c:forEach>
					</ol>
				</li>
			</ol>
		</c:if>
	<!-- </iframe> -->
</body>
</html>

<!-- Bootstrap sliding dropdown menu example -->
<!-- <!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Twitter Bootstrap Menu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="static/css/bootstrap-2.3.2.css" rel="stylesheet">
    <link href="static/css/bootstrap-responsive.css" rel="stylesheet">
    <body>
        <div class="container">
                <div class="dropdown clearfix">
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" style="display: block; position: static; margin-bottom: 5px; *width: 180px;">
                        <li><a tabindex="-1" href="#">Action</a></li>
                        <li><a tabindex="-1" href="#">Another action</a></li>
                        <li><a tabindex="-1" href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li class="dropdown-submenu"><a tabindex="-1" href="#">More options</a>
                            <ul class="dropdown-menu">
                                <li><a tabindex="-1" href="#">Second level link</a></li>
                                <li><a tabindex="-1" href="#">Second level link</a></li>
                                <li><a tabindex="-1" href="#">Second level link</a></li>

                                    <li class="dropdown-submenu"><a tabindex="-1" href="#">Sub with sub</a>
                                    <ul class="dropdown-menu">
                                    <li><a tabindex="-1" href="#">SubSub1</a></li>
                                    <li><a tabindex="-1" href="#">SubSub2</a></li>
                                    </ul>
                                    </li>
                                <li><a tabindex="-1" href="#">Second level link</a></li>
                            </ul>
                        </li>
                    </ul>
            </div>
            </body>

    </html> -->