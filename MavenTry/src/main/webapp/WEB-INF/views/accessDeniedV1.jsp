<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Page not found</title>
<!-- Bootstrap Core CSS -->
<link
	href="<c:url value='/static/vendor/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet" />

<!-- Custom CSS -->
<link href="<c:url value="/static/css/style.css" />"
	rel="stylesheet">

<!-- You can change the theme colors from here -->
<link href="<c:url value="/static/css/blue.css"  />" id="theme"
	rel="stylesheet">

</head>

<body class="fix-header card-no-border">
	<!-- ============================================================== -->
	<!-- Main wrapper - style you can find in pages.scss -->
	<!-- ============================================================== -->
	<section id="wrapper" class="error-page">
		<div class="error-box">
			<div class="error-body text-center">
				<h1>404</h1>
				<h3 class="text-uppercase">Page Not Found !</h3>
				<p class="text-muted m-t-30 m-b-30">YOU SEEM TO BE TRYING TO
					FIND HIS WAY HOME</p>
				<a href="<c:url value="/home" />"
					class="btn btn-info btn-rounded waves-effect waves-light m-b-40">Back
					to home</a>
			</div>
			
		</div>
	</section>
	<!-- ============================================================== -->
	<!-- End Wrapper -->
	<!-- ============================================================== -->
	<!-- ============================================================== -->
	<!-- jQuery -->

	<script src="<c:url value="/static/vendor/jquery/jquery.min.js" />"></script>

	<!-- Bootstrap tether Core JavaScript -->
	<script src="<c:url value="/static/js/tether.min.js" />"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="<c:url value="/static/vendor/bootstrap/js/bootstrap.min.js" />"></script>

	<!--Wave Effects -->
	<script src="<c:url value="/static/js/waves.js" />"></script>
</body>

</html>
