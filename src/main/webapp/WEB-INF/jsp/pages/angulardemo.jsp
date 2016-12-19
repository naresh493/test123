<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-ng-app>
<head>
	<title>Angular JSWebsite</title>
	<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
	<script type="text/javascript" src="<c:url value="/static/js/underscore-min.js" />"></script>
	
	<!-- [if lte IE 8]> -->
	<!-- <script type="text/javascript">
	var customTags = ["data-ng-app", "data-ng-include", "data-ng-pluralize", "data-ng-show", "data-ng-model", "data-ng-controller", "data-ng-view", "alert", "tabset", "tab"]
	.concat(["data-ng:include", "data-ng:pluralize", "data-ng:view"]);// Optionally these for CSS
	for(var t=0;t<customtags.length;t++) document.createelement(customtags[t]);<br=""/></script> -->
	<!-- <![endif] -->

	<link rel="stylesheet" href="<c:url value="/static/css/bootstrap-2.3.2.css" />" />
	<%-- <jsp:include page="/WEB-INF/jsp/template/css.jsp" />
	<jsp:include page="/WEB-INF/jsp/template/js.jsp" /> --%>
</head>
<body data-ng-controller="PhoneListController">

	Write some text in textbox:
    <input type="text" data-ng-model="sometext" />
 
    <h1 data-ng-show="somtext">Hello {{ sometext }}</h1>
    
{{1 +2}}
</body>
</html>