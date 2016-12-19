<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%

	//TODO: this code will change depening on the pre-authentication solution

	//log the user out
	session.removeAttribute("userId");
	session.invalidate();
	
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<title>Logout Successful!</title>
	<style type="text/css">
      body {
        
      }
      </style>
</head>

<body>
	<center>
	<p><h4>You have successfully been logged out of the system.</h4></p> Click <a href="/QlikTestAdmin">here</a> to login again.
	</center>
</body>

</html>