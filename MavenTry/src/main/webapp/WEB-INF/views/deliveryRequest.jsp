<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delivery Request</title>

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
		<%@include file="navi.jsp"%>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Delivery Request</h1>
				</div>

				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#modalAddDeliveryType" onClick="addCargoUser()">Add
							Delivery Type</button>
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
										<th>Delivery Type</th>
										<th>Pick-up Address</th>
										<th>Destination Address</th>
										<th>Delivery Cost</th>
										<th>Delivery Status</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>

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
	<div class="modal fade" id="modalAddDeliveryType" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLongTitle"></h3>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<!-- Form Text field -->
				<form:form method="GET" modelAttribute="deliveryRequest"
					name="myform" id="myform">
					<form:input type="hidden" path="deliver_id" id="deliver_id" />
					<div class="modal-body">



						<!-- Input Delivery type -->
						<div>
							<label for="delivery_type">Delivery type: </label>
							<form:select path="delivery_type" id="delivery_type"
								class="form-control" placeholder="Delivery type">
								<form:options items="${deliveryTypes}" itemValue="id"
									itemLabel="mainte_delivery_type" />
								<form:errors path="delivery_type" cssClass="error" />
							</form:select>
						</div>

						<!-- Input item details-->
						<div>
							<label for="item_details">Item Details: </label>
							<form:textarea path="item_details" id="item_details"
								class="form-control" placeholder="Item Details" />
							<form:errors path="item_details" cssClass="error" />
						</div>

						<!-- Input Delivery Pick-up address -->
						<div>
							<label for="delivery_pickup_address">Pick-up Address: </label>
							<form:input path="delivery_pickup_address"
								id="delivery_pickup_address" class="form-control"
								placeholder="Pick-up Address" />
							<form:errors path="delivery_pickup_address" cssClass="error" />
						</div>

						<!-- Input Delivery Destination address -->
						<div>
							<label for="delivery_destination">Destination Address: </label>
							<form:input path="delivery_destination" id="delivery_destination"
								class="form-control" placeholder="Destination Address" />
							<form:errors path="delivery_destination" cssClass="error" />
						</div>
						
						<!-- Input Expected date to be delivered -->
						<div>
							<label for="preferred_date">Preferred Date to be Delivered: </label>
							<form:input type="date" path="preferred_date" id="preferred_date"
								class="form-control" placeholder="Preferred Date to be Delivered" />
							<form:errors path="preferred_date" cssClass="error" />
						</div>


						<div id="error" class="error"></div>

					</div>

					<div class="modal-footer">

						<!-- Close button -->
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>

						<!-- Register button -->
						<input type="button" class="btn btn-primary" value="Save"
							id="btnDeliveryType" onClick="insertOrUpdateDeliveryType()" />

					</div>
				</form:form>
			</div>
		</div>
	</div>


	<!-- Delete Modal -->
	<div class="modal fade" id="modalDeleteDeliveryRequest" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<form name="deleteForm" id="deleteForm" method="GET">
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

						<h4>Delete this record?</h4>
					</div>

					<div class="modal-footer">

						<!-- Close button -->
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<!-- Close button -->

						<input type="button" id="btnDeliveryTypeDelete"
							class="btn btn-danger" onClick="deleteViaAjax()" value="Delete" />

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

	<script>
		/* Global variable for getting page context */
		var myContext = "${pageContext.request.contextPath}";
	</script>

	<!-- Custom function Javascript -->
	<script type="text/javascript"
		src="<c:url value="/static/delivery-request-script.js" />"></script>


	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(function() {
			/* Call function */
			populateDataTable();
		});
	</script>






</body>
</html>