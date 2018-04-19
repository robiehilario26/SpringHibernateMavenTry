<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rest API</title>
</head>
<body>

</body>
ID:
<input type="text" id="txtId" />
<button id="btnSearch" onClick="searchById()">Search</button>
<!-- jQuery -->

<script src="<c:url value="/static/vendor/jquery/jquery.min.js" />"></script>
<script>
var myContext = "${pageContext.request.contextPath}";
</script>
<script src="<c:url value="/static/ajaxClass.js" />"></script>


</html>