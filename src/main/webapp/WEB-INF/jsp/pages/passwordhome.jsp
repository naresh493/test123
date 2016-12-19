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
		<!-- Header -->
		<jsp:include page="/WEB-INF/jsp/template/header1.jsp" />
		<!-- Menubar -->
		<!-- body -->
		<br>
		<div class="maincontent">
			<c:choose>
				<c:when test='${empty forgotpassword and empty changepasswordforfirstlogin}'>
						 <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
				</c:when>
				
				
				<c:when test='${not empty forgotpassword}'>
					<jsp:include page="/WEB-INF/jsp/pages/password/forgotpassword.jsp"/>
				</c:when>
				<c:when test='${not empty changepasswordforfirstlogin}'>
					<jsp:include page="/WEB-INF/jsp/pages/password/changepasswordfirstlogin.jsp"/>
				</c:when>
				
				
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			</div>
		<!-- body -->
	<!-- Footer -->
	<jsp:include page="/WEB-INF/jsp/template/footer.jsp" />
</div>
</body>
</html>