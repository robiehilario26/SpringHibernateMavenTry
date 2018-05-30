<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> --%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="csrf-token" content="${_csrf.token}">
<title>Angular Js User</title>

<!-- Bootstrap Core CSS -->
<link
	href="<c:url value='/static/vendor/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet">

<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">

<!-- MetisMenu CSS -->
<link
	href="<c:url value='/static/vendor/metisMenu/metisMenu.min.css' />"
	rel="stylesheet">

<!-- DataTables CSS -->
<link
	href="<c:url value='/static/vendor/datatables-plugins/dataTables.bootstrap.css' />"
	rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
	href="<c:url value='/static/vendor/datatables-responsive/dataTables.responsive.css' />"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="<c:url value='/static/dist/css/sb-admin-2.css' />"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="<c:url value='/static/vendor/font-awesome/css/font-awesome.min.css' />"
	rel="stylesheet">



<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div id="wrapper">

		<!-- Navigation -->
		<%@include file="navi.jsp"%>

		<div id="page-wrapper" ng-app="myApp">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Angular js module test</h1>
				</div>

				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row" ng-controller="UserController as ctrl">
				<div class="col-lg-12">
					<div>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#modalUser" ng-click=open(null)>Add User</button>
					</div>

					<div class="panel panel-default">
						<!-- The code below hide as for now, remove when no further usage in final release -->
						<div class="panel-heading" style="display: none">${success}</div>

						<!-- Display success message here when successfully insert data -->
						<div id="info" class="panel-heading success" style="display: none"></div>

						<!-- The code below is not been use as for now, remove when no further usage in final release -->
						<input type="hidden" id="errorStatus" value="${errorStatus}" />

						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>Firstname</th>
										<th>Lastname</th>
										<th>Email</th>
										<th>Username</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="u in ctrl.users | orderBy:'firstName'">
										<td><span ng-bind="u.firstName"></span></td>
										<td><span ng-bind="u.lastName"></span></td>
										<td><span ng-bind="u.email"></span></td>
										<td><span ng-bind="u.usernameId"></span></td>
										<td>
											<button type="button" ng-click="ctrl.edit(u.id)"
												class="btn btn-success custom-width" data-toggle="modal"
												data-target="#modalUser">Edit</button>
											<button type="button" ng-click="ctrl.remove(u.id)"
												class="btn btn-danger custom-width">Remove</button>
											<button class="btn btn-primary" ng-click=open(u.id)>Open
												form</button>
											<button class="btn btn-primary" ng-click=showForm(u.id)>Show
												form</button>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->

				<script type="text/ng-template" id="myModal1">
	<div class="modal-header">
    <h3>Create A New Account!</h3>
	</div>
        <form name="form.userForm" ng-submit="submitForm()" novalidate>
    <div class="modal-body">
		
	<input type="text" id="token" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

		<!-- ID-->
		  <input type="text" name="userId" class="form-control" ng-model="userInfo.id"/>

        <!-- FIRST NAME -->
        <div class="form-group">
            <label>First Name</label>
            <input type="text" name="fname" class="form-control" ng-model="userInfo.firstName" required>
            <p ng-show="form.userForm.fname.$invalid && !form.userForm.fname.$pristine" class="help-block">Your First name is required.</p>
        </div>

 		<!-- LAST NAME -->
        <div class="form-group">
            <label>Last Name</label>
            <input type="text" name="lname" class="form-control" ng-model="userInfo.lastName" required>
            <p ng-show="form.userForm.lname.$invalid && !form.userForm.lname.$pristine" class="help-block">Your Last name is required.</p>
        </div>

        <!-- EMAIL -->
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" class="form-control" ng-model="userInfo.email" required>
            <p ng-show="form.userForm.email.$invalid && !form.userForm.email.$pristine" class="help-block">Enter a valid email.</p>
        </div>

		<!-- USERNAME -->
        <div class="form-group">
            <label>Username</label>
            <input type="text" name="username" class="form-control" ng-model="userInfo.usernameId" ng-minlength="3" ng-maxlength="100" required>
            <p ng-show="form.userForm.username.$error.minlength" class="help-block">Username is too short.</p>
            <p ng-show="form.userForm.username.$error.maxlength" class="help-block">Username is too long.</p>
        </div>

		<!-- PASSWORD -->
        <div class="form-group"> 
            <label>Password</label>
            <input type="text" name="password" class="form-control" ng-model="userInfo.password" ng-minlength="3" ng-maxlength="100" required>
            <p ng-show="form.userForm.password.$error.minlength" class="help-block">Password is too short.</p>
            <p ng-show="form.userForm.password.$error.maxlength" class="help-block">Password is too long.</p>
        </div>

	<!-- USER PROFILES -->
        <div class="form-group" ng-controller="UserController as ctrl1">
            <label>User Profile</label>
            <select id="userProfile" name="userProfile" class="form-control"
        ng-model ="userInfo.userProfiles.id">                                
            <option ng:repeat="profile in ctrl1.profiles" value="{{profile.id}}">     
                {{profile.type}}
            </option>
    	</select>
        </div> 

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-primary" value="{{!userInfo.id ? 'Add' : 'Update'}}" ng-disabled="form.userForm.$invalid">OK</button>
        <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
    </div>
</form>
    </script> 



				<script type="text/ng-template" id="modal-form.jsp">
<div class="modal-header">
    <h3>Create A New Account!</h3>
</div>
<form name="form.userForm1" ng-submit="submitForm()" novalidate>
    <div class="modal-body">
		
		
        <!-- NAME -->
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" class="form-control" ng-model="firstName" required>
            <p ng-show="form.userForm1.firstName.$invalid && !form.userForm1.firstName.$pristine" class="help-block">You name is required.</p>
        </div>

        <!-- USERNAME -->
        <div class="form-group">
            <label>Username</label>
            <input type="text" name="usernameId" class="form-control" ng-model="usernameId" ng-minlength="3" ng-maxlength="80" required>
            <p ng-show="form.userForm1.usernameId.$error.minlength" class="help-block">Username is too short.</p>
            <p ng-show="form.userForm1.usernameId.$error.maxlength" class="help-block">Username is too long.</p>
        </div>

        <!-- EMAIL -->
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" class="form-control" ng-model="email" required>
            <p ng-show="form.userForm1.email.$invalid && !form.userForm1.email.$pristine" class="help-block">Enter a valid email.</p>
        </div>

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-primary" ng-disabled="form.userForm1.$invalid">OK</button>
        <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
    </div>
</form>
	
	</script>


			</div>

		</div>
		<!-- /.col-lg-6 -->
	</div>
	<!-- /.row -->
	</div>
	<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->


	<!-- Angular Js -->

	<!-- <script
		src="https://code.jquery.com/jquery-2.2.4.min.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.5/angular.min.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.0.0/ui-bootstrap-tpls.min.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-utils/0.1.1/angular-ui-utils.min.js"></script>
		
		<script
		src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
		
		<script
		src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
 -->
	<!-- <script
		src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular.js"></script>
	<script
		src="https://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.1.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular-animate.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
 -->
	
	<!-- Local copy Angular Js -->
	
	
	<script src="<c:url value='/static/angularJs/library/angular.js' />"></script>
	<script src="<c:url value='/static/angularJs/library/ui-bootstrap-tpls-1.3.1.js' />"></script>
	<script src="<c:url value='/static/angularJs/library/angular-animate.js' />"></script>
	<script src="<c:url value='/static/angularJs/library/angular-resource.js' />"></script>

	
	<script src="<c:url value='/static/angularJs/app.js' />"></script>
	<script src="<c:url value='/static/angularJs/example.js' />"></script>

	<script
		src="<c:url value='/static/angularJs/services/user_service.js' />"></script>
	<script
		src="<c:url value='/static/angularJs/controller/user_controller.js' />"></script>

	<script
		src="<c:url value='/static/angularJs/controller/modal_controller.js' />"></script>
	<!-- jQuery -->
	<script src="<c:url value="/static/vendor/jquery/jquery.min.js" />"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="<c:url value="/static/vendor/bootstrap/js/bootstrap.min.js" />"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="<c:url value="/static/vendor/metisMenu/metisMenu.min.js" />"></script>

	<!-- DataTables JavaScript -->
	<script
		src="<c:url value="/static/vendor/datatables/js/jquery.dataTables.min.js" />"></script>

	<script
		src="<c:url value="/static/vendor/datatables-plugins/dataTables.bootstrap.min.js" />"></script>

	<script
		src="<c:url value="/static/vendor/datatables-responsive/dataTables.responsive.js" />"></script>

	<!-- Custom Theme JavaScript -->
	<script src="<c:url value="/static/dist/js/sb-admin-2.js" />"></script>

	<script>
		/* Global variable for getting page context */
		var myContext = "${pageContext.request.contextPath}";
	</script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(function() {
			/* $('#dataTables-example').DataTable({
			    responsive: true
			}); */
		});
	</script>


</body>
</html>

