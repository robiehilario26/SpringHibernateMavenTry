<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Beeding</title>

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

<!-- Morris Charts CSS -->
<link href="<c:url value="/static/vendor/morrisjs/morris.css" />"
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
					<h1 class="page-header">Beeding</h1>
				</div>


				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">


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


				<div class="modal-body">

					<div class="panel panel-default">
						<div class="panel-heading">Delivery Request Chart</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div id="morris-area-chart"></div>
						</div>
						<!-- /.panel-body -->
					</div>

				</div>

				<div class="modal-footer">

					<!-- Close button -->
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>

					<!-- Register button -->
					<input type="button" class="btn btn-primary" value="Save"
						id="btnDeliveryType" onClick="insertOrUpdateDeliveryType()" />

				</div>

			</div>
		</div>
	</div>


	<!-- Delete Modal -->
	<div class="modal fade" id="modalDeleteDeliveryType" tabindex="-1"
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

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="<c:url value="/static/vendor/metisMenu/metisMenu.min.js" />"></script>

	<!-- Morris Charts JavaScript -->
	<script src="<c:url value="/static/vendor/raphael/raphael.min.js" />"></script>

	<script src="<c:url value="/static/vendor/morrisjs/morris.min.js" />"></script>

	<script src="<c:url value="/static/data/morris-data.js" />"></script>

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
		src="<c:url value="/static/beeding-script.js" />"></script>


	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(function() {
			/* Call function */
			populateDataTable();

		});
	</script>


	<script>
		$('#modalAddDeliveryType').on('shown.bs.modal', function() { //listen for user to open modal
			$(function() {
				$("#morris-area-chart").empty(); //clear chart so it doesn't create multiple if multiple clicks
				// Create a Bar Chart with Morris
				var chart = Morris.Bar({
					element : 'morris-area-chart',
					 data: [{
				            period: '2010 Q1',
				            iphone: 666,
				            ipad: null,
				            itouch: 647
				        }, {
				            period: '2010 Q2',
				            iphone: 778,
				            ipad: 294,
				            itouch: 441
				        }, {
				            period: '2010 Q3',
				            iphone: 912,
				            ipad: 969,
				            itouch: 501
				        }, {
				            period: '2010 Q4',
				            iphone: 767,
				            ipad: 597,
				            itouch: 689
				        }, {
				            period: '2011 Q1',
				            iphone: 810,
				            ipad: 914,
				            itouch: 293
				        }, {
				            period: '2011 Q2',
				            iphone: 670,
				            ipad: 293,
				            itouch: 881
				        }, {
				            period: '2011 Q3',
				            iphone: 820,
				            ipad: 795,
				            itouch: 588
				        }, {
				            period: '2011 Q4',
				            iphone: 5073,
				            ipad: 967,
				            itouch: 175
				        }, {
				            period: '2012 Q1',
				            iphone: 0687,
				            ipad: 460,
				            itouch: 028
				        }, {
				            period: '2012 Q2',
				            iphone: 432,
				            ipad: 713,
				            itouch: 791
				        }],
				        xkey: 'period',
				        ykeys: ['iphone', 'ipad', 'itouch'],
				        labels: ['iPhone', 'iPad', 'iPod Touch'],
				        pointSize: 2,
				        hideHover: 'auto',
				        resize: true,
					stacked : true
				});

				// Fire off an AJAX request to load the data
			/* 	$.ajax({
					type : "GET",
					dataType : 'json',
					url : "${pageContext.request.contextPath}/static/data/morris-data.js", // This is the URL to the API

				}).done(function(data) {
					// When the response to the AJAX request comes back render the chart with new data
					chart.setData(data);
				}).fail(function() {
					// If there is no communication between the server, show an error
					alert("error occured");
				}); */
			});
		});
	</script>



</body>
</html>