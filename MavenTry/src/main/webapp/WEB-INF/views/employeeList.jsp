<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee</title>

<!-- Bootstrap Core CSS -->
<link
	href="<c:url value='/static/vendor/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet">

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
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">SB Admin v2.0</a>
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i
						class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-messages">
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend..</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>Read
									All Messages</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-messages --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-tasks fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-tasks">
						<li><a href="#">
								<div>
									<p>
										<strong>Task 1</strong> <span class="pull-right text-muted">40%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100" style="width: 40%">
											<span class="sr-only">40% Complete (success)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 2</strong> <span class="pull-right text-muted">20%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-info" role="progressbar"
											aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
											style="width: 20%">
											<span class="sr-only">20% Complete</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 3</strong> <span class="pull-right text-muted">60%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-warning"
											role="progressbar" aria-valuenow="60" aria-valuemin="0"
											aria-valuemax="100" style="width: 60%">
											<span class="sr-only">60% Complete (warning)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 4</strong> <span class="pull-right text-muted">80%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-danger"
											role="progressbar" aria-valuenow="80" aria-valuemin="0"
											aria-valuemax="100" style="width: 80%">
											<span class="sr-only">80% Complete (danger)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>See
									All Tasks</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-tasks --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-alerts">
						<li><a href="#">
								<div>
									<i class="fa fa-comment fa-fw"></i> New Comment <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-twitter fa-fw"></i> 3 New Followers <span
										class="pull-right text-muted small">12 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-envelope fa-fw"></i> Message Sent <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-tasks fa-fw"></i> New Task <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-upload fa-fw"></i> Server Rebooted <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>See
									All Alerts</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-alerts --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#"><i class="fa fa-user fa-fw"></i> User
								Profile</a></li>
						<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
						</li>
						<li class="divider"></li>
						<li><a href="<c:url value='/' />"><i
								class="fa fa-sign-out fa-fw"></i> Logout</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->

			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<li class="sidebar-search">
							<div class="input-group custom-search-form">
								<input type="text" class="form-control" placeholder="Search...">
								<span class="input-group-btn">
									<button class="btn btn-default form-control" type="button">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div> <!-- /input-group -->
						</li>
						<li><a href="<c:url value='/home' />"><i
								class="fa fa-dashboard fa-fw"></i> Dashboard</a></li>
						<li class="active"><a href="#"><i
								class="fa fa-bar-chart-o fa-fw"></i> Employee <span
								class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="<c:url value='/getEmployeeList' />">List</a></li>
								<li><a href="morris.html">Users</a></li>
							</ul> <!-- /.nav-second-level --></li>
						<li><a href="tables.html"><i class="fa fa-table fa-fw"></i>
								Tables</a></li>
						<li><a href="forms.html"><i class="fa fa-edit fa-fw"></i>
								Forms</a></li>
						<li><a href="#"><i class="fa fa-wrench fa-fw"></i> UI
								Elements<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="panels-wells.html">Panels and Wells</a></li>
								<li><a href="buttons.html">Buttons</a></li>
								<li><a href="notifications.html">Notifications</a></li>
								<li><a href="typography.html">Typography</a></li>
								<li><a href="icons.html"> Icons</a></li>
								<li><a href="grid.html">Grid</a></li>
							</ul> <!-- /.nav-second-level --></li>
						<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>
								Multi-Level Dropdown<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="#">Second Level Item</a></li>
								<li><a href="#">Second Level Item</a></li>
								<li><a href="#">Third Level <span class="fa arrow"></span></a>
									<ul class="nav nav-third-level">
										<li><a href="#">Third Level Item</a></li>
										<li><a href="#">Third Level Item</a></li>
										<li><a href="#">Third Level Item</a></li>
										<li><a href="#">Third Level Item</a></li>
									</ul> <!-- /.nav-third-level --></li>
							</ul> <!-- /.nav-second-level --></li>
						<li><a href="#"><i class="fa fa-files-o fa-fw"></i>
								Sample Pages<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="blank.html">Blank Page</a></li>
								<li><a href="<c:url value='/' />">Login Page</a></li>
							</ul> <!-- /.nav-second-level --></li>
					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">List</h1>
				</div>

				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#modalAddEmployee" onClick="addEmployee()">Add
							Employee</button>
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
										<th>Name</th>
										<th>Joining Date</th>
										<th>Salary</th>
										<th>Address</th>
										<th>SSN</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${employees}" var="employee">
										<tr class="odd gradeX">
											<td>${employee.name}</td>
											<td>${employee.joiningDate}</td>
											<td>${employee.salary}</td>
											<td>${employee.address}</td>
											<td>${employee.ssn}</td>
											<td align="center"><input type="button"
												id="${employee.ssn}" class="btn btn-success" value="Edit"
												data-toggle="modal" data-target="#modalAddEmployee"
												onClick="searchEmployeeDetailViaAjax(this)" /> <input
												data-toggle="modal" data-target="#modalDeleteEmployee"
												type="button" id="${employee.ssn}"
												onClick="fetchDeleteId(this)" class="btn btn-danger"
												value="Delete" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>

		</div>
		<!-- /.col-lg-6 -->
	</div>
	<!-- /.row -->
	</div>
	<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->





	<!-- Register Modal -->
	<div class="modal fade" id="modalAddEmployee" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Add
						employee</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<!-- Form Text field -->
				<form:form method="POST" modelAttribute="employee" name="myform"
					id="myform">
					<form:input type="hidden" path="id" id="id" name="id" />
					<div class="modal-body">


						<!-- Input Employee Name -->
						<div>
							<label for="name">Name: </label>
							<form:input path="name" id="name" class="form-control"
								placeholder="Full name" />
							<form:errors path="name" cssClass="error" />
						</div>

						<div>
							<!-- Input Joining Date -->
							<label for="joiningDate">Joining Date: </label>
							<form:input type="date" path="joiningDate" id="joiningDate"
								class="form-control" placeholder="mm-dd-yyyy" />
							<form:errors path="joiningDate" cssClass="error" />
						</div>

						<div>
							<!-- Input Salary -->
							<label for="salary">Salary: </label>
							<form:input path="salary" id="salary" class="form-control"
								placeholder="0" />
							<form:errors path="salary" cssClass="error" />
						</div>

						<div>
							<!-- Input Address -->
							<label for="salary">Address: </label>
							<form:input path="address" id="address" class="form-control"
								placeholder="Address" />
							<form:errors path="address" cssClass="error" />
						</div>

						<div>
							<!-- Input SSN -->
							<label for="ssn">SSN: </label>
							<form:input path="ssn" id="ssn" class="form-control"
								placeholder="0" />
							<form:errors path="ssn" cssClass="error" />
						</div>
						<div id="error" class="error"></div>

					</div>

					<div class="modal-footer">

						<!-- Close button -->
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>

						<!-- Register button -->
						<input type="button" class="btn btn-primary" value="Register"
							id="btnEmployee" onClick="insertOrUpdate()" />

					</div>
				</form:form>
			</div>
		</div>
	</div>


	<!-- Delete Modal -->
	<div class="modal fade" id="modalDeleteEmployee" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<form name="deleteForm" id="deleteForm" method="POST">
			<div class="modal-dialog modal-sm" role="document">
				<div class="modal-content">
					<div class="modal-header">

						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body">
						<!-- Hidden input field for id -->
						<input type="hidden" id="deleteId" />

						<h4>Delete this employee?</h4>
					</div>

					<div class="modal-footer">

						<!-- Close button -->
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<!-- Close button -->

						<input type="button" id="btnEmployeeDelete" class="btn btn-danger"
							onClick="deleteViaAjax()" value="Delete" />

					</div>

				</div>
			</div>
		</form>
	</div>


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

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(function() {
			populateDataTable();
		});
	</script>




	<script>
		/* Global variable for getting page context */
		var myContext = "${pageContext.request.contextPath}";
	</script>

	<!-- Custom function Javascript -->
	<script type="text/javascript"
		src="<c:url value="/static/employee-list-script.js" />"></script>


</body>
</html>