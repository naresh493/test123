<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<!-- <meta name="menu" content="home" /> -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="" />
    <meta name="author" content="" />
	<title>QlikTest Administration</title>
	<link rel="apple-touch-icon-precomposed" size="144x144" href="static/images/apple-touch-icon-144-precomposed.png" />
    <link rel="apple-touch-icon-precomposed" size="114x114" href="static/images/apple-touch-icon-114-precomposed.png" />
    <link rel="apple-touch-icon-precomposed" size="72x72" href="static/images/apple-touch-icon-72-precomposed.png" />
    <link rel="apple-touch-icon-precomposed" href="static/images/apple-touch-icon-57-precomposed.png" />
	<link rel="shortcut icon" href="static/images/favicon.png" />
	<link rel="stylesheet" href="static/css/bootstrap-2.3.2.css"/>
	<link rel="stylesheet" href="static/css/qliktest-admin.custom.css"/>
	<link rel="stylesheet" href="static/css/bootstrap-responsive.css"/>    
</head>
<body>
	<h2 class="banner">QlikTest Admin</h2>
<table>
	<tr>
		<td>You are logged in as <h5>Vijay</h5></td>
		<td><a class="btn" href="#">Sign Out</a></td>
	</tr>
</table>
	<sec:authorize ifAnyGranted='ROLE_ADMIN'>
		<h1>Only admin can see this</h1>
		<br />
		<a href='admin'> Admin Home </a>
	</sec:authorize>

<div class="container">
      <div class="masthead">
        <!-- <h3 class="muted">Project name</h3> -->
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <!-- <li class="active"><a href="#">Home</a></li>
                <li><a href="#">Projects</a></li>
                <li><a href="#">Services</a></li>
                <li><a href="#">Downloads</a></li>
                <li><a href="#">About</a></li>
                <li><a href="#">Contact</a></li> -->
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#">Users</a></li>
                <li><a href="#">Tenants</a></li>
                <li><a href="#">Projects</a></li>
                <li><a href="#">Licenses</a></li>
                <li class="dropdown" id="dropdownSysAdminMenu">
                   	<a class="dropdown-toggle" data-toggle="dropdown" href="#">General<b class="caret"></b></a>
                   	<ul class="dropdown-menu">
                        <li><a href="#">Create Default User</a></li>
                        <li><a href="#">Create Concurrent Users</a></li>
						<li><a href="#">Create IP Address Binding</a></li>
						<li><a href="#">Create Application Under Test</a></li>	                            
                        <li><a href="#">Create User</a></li>
                        <li><a href="#">Edit User</a></li>
                        <!-- <li class="divider"></li> -->
                        <li><a href="#">Update User</a></li>
						<li><a href="#">View Users</a></li>
                       </ul>
                   </li>
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>
      </div>
	<!-- placed at bottom so that the page loads faster -->
	<script src="static/js/jquery.js"></script>
    <script src="static/js/bootstrap-transition.js"></script>
    <script src="static/js/bootstrap-alerts.js"></script>
    <script src="static/js/bootstrap-modal.js"></script>
    <script src="static/js/bootstrap-dropdown.js"></script>
    <script src="static/js/bootstrap-scrollspy.js"></script>
    <script src="static/js/bootstrap-tabs.js"></script>
    <script src="static/js/bootstrap-tooltip.js"></script>
    <script src="static/js/bootstrap-popover.js"></script>
    <script src="static/js/bootstrap-buttons.js"></script>
    <script src="static/js/bootstrap-collapse.js"></script>
    <script src="static/js/bootstrap-carousel.js"></script>
    <script src="static/js/bootstrap-typeahead.js"></script>
</body>
</html>