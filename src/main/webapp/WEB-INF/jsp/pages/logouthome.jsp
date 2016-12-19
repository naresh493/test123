<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags'%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@page session="true" %>
<!DOCTYPE html>
<html lang="en">


<head>


<%
	//TODO: remove this  when pre-authentication solution is in place
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", -1);
%>
		<title>QlikTest Administration</title>
		<jsp:include page="/WEB-INF/jsp/template/css.jsp" />
</head>
<body>

<div class="wrapper">
		
		<div class="maincontent">
			<c:choose>
				<c:when test='${empty logout}'>
						 <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
				</c:when>
				
				
				<c:when test='${not empty logout}'>
					<jsp:include page="/WEB-INF/index.jsp" />
				</c:when>
				
				
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			</div>
</div>
</body>
</html>