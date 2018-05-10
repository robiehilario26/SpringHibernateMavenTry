/* This JavaScript file provide all the jquery and ajax function
 * for retrieving data, deleting data, updating data, clearing
 * input text field and populate dataTable columns.
 *
 * myContext is a global variable in jsp, equivalent into
 * ${pageContext.request.contextPath} - pageContext whatever is the context
 * path of the project.
 * 
 */

/* Add employee */
function addCargoUser() {
	/* Clear input fields */
	clearTextField();

	/* Set text value of button */
	$('#btnDeliveryType').val("Save");

	/* Change class of attr of button */
	$("#btnDeliveryType").attr('class', 'btn btn-primary');
}

/* Clear all textfield value */
function clearTextField() {

	/* Set default to 0 */
	$("#delivery_type").val(null);
	$("#item_details").val(null);
	$("#delivery_pickup_address").val(null);
	$("#delivery_destination").val(null);
	$("#preferred_date").val(null);

	/* Clear error validation message */
	$("#error").empty();

}

/*
 * Fetch information via delivertype id using json response using @RequestParam
 */
function searchDeliveryTypeDetailViaAjax(elem) {
	var stringResponse;
	var id = elem.id;

	/* Clear error validation message */
	$("#error").empty();

	/* Get id value in hidden input text field */
	$("#deliver_id").val(id);

	/* Set text value of button */
	$('#btnDeliveryType').val("Update");

	/* Change class of attr of button */
	$("#btnDeliveryType").attr('class', 'btn btn-success');

	/* Disable button to prevent unnecessary submit */
	$("#btnDeliveryType").prop('disabled', true);

	/* Set Parameters */
	var dataParameter = {
		id : id,
	};
	$.ajax({
		url : '' + myContext + '/ajaxFetchRequestDetails',
		type : "GET",
		contentType : "application/json; charset=utf-8",
		datatype : "json",
		data : dataParameter,
		error : function() {
			alert('Error: ' + "Server not respond");
		},
		success : function(response) {

			/* Convert response into String format */
			stringResponse = JSON.stringify(response);

			/* Parse json response to get value of each key */
			var obj = JSON.parse(stringResponse);

			/* Set modal text field value */
			$("#delivery_type").val(obj.delivery_type);
			$("#item_details").val(obj.item_details);
			$("#delivery_pickup_address").val(obj.delivery_pickup_address);
			$("#delivery_destination").val(obj.delivery_destination);
			$("#preferred_date").val(obj.preferred_date);

			/* Enable button to submit */
			$("#btnDeliveryType").prop('disabled', false);

		}
	});

}

var global_id;
/* Fetch Delivery id */
function fetchDeliverId(obj) {
	var id = obj.id;
	/* Pass delivery id to global variable */
	global_id = id;

	$("#morris-bar-chart").empty(); // clear chart so it

}

/* Fetch deliveryType id */
function fetchDeleteId(obj) {
	var id = obj.id;
	/* Set hidden text field value */
	$("#deleteId").val(id);
}

/* Update selected Beeding entry by id */
function deleteDeliveryRequestViaAjax() {
	var stringResponse;

	/* Disable button to prevent redundant ajax request */
	$("#btnDeliveryTypeDelete").prop('disabled', true);

	/* Get hidden text field value in modal delete */
	var id = $("#deleteId").val();

	/* Set Parameters */
	var dataParameter = {
		id : id
	};
	$.ajax({
		url : '' + myContext + '/ajaxDeleteDeliveryRequest',
		type : "GET",
		contentType : "application/json; charset=utf-8",
		datatype : "json",
		data : dataParameter,
		error : function(xhr, desc, err) {
			$("#btnDeliveryTypeDelete").prop('disabled', false);
			if (xhr.status == 500) {
				alert('Error: ' + "Server not respond ");
			}
			if (xhr.status == 403) {
				alert('Error: ' + "Access Denied");
			}
		},
		success : function(response) {

			/* Enable button to make ajax request again after response return */
			$("#btnDeliveryTypeDelete").prop('disabled', false);

			/* Populate DataTable */
			populateDataTable();

			/* Hide modal */
			$('#modalDeleteDeliveryRequest').modal('hide');

		}
	});

}


/* Update selected Beeding entry by id */
function beedViaAjax() {
	var stringResponse;

	/* Disable button to prevent redundant ajax request */
	$("#btnSelectBeeding").prop('disabled', true);

	/* Get hidden text field value in modal delete */
	var id = $("#beedId").val();

	/* Set Parameters */
	var dataParameter = {
		id : id,
		deliver_id : global_id
	};
	$.ajax({
		url : '' + myContext + '/ajaxChooseBeedEntry',
		type : "GET",
		contentType : "application/json; charset=utf-8",
		datatype : "json",
		data : dataParameter,
		error : function(xhr, desc, err) {
			$("#btnSelectBeeding").prop('disabled', false);
			if (xhr.status == 500) {
				alert('Error: ' + "Server not respond ");
			}
			if (xhr.status == 403) {
				alert('Error: ' + "Access Denied");
			}
		},
		success : function(response) {

			/* Enable button to make ajax request again after response return */
			$("#btnSelectBeeding").prop('disabled', false);

			/* Populate DataTable */
			populateDataTable();

			/* Populate Morris Bar Chart */
			populateBeedingChart()

			/* Hide modal */
			$('#modalSelectBeeding').modal('hide');

		}
	});

}

/* Populate DataTable of list of all deliveryType existed using ajax */
function populateDataTable() {
	$("#dataTables-example").dataTable().fnDestroy();

	/* set class and onClick event listener */
	var buttonEditClass = 'class="btn btn-success" data-toggle="modal"';
	buttonEditClass += 'data-target="#modalAddDeliveryType"';
	buttonEditClass += 'onClick="searchDeliveryTypeDetailViaAjax(this)"';

	var buttonDeleteClass = 'class="btn btn-danger" data-toggle="modal"';
	buttonDeleteClass += 'data-target="#modalDeleteDeliveryRequest"';
	buttonDeleteClass += 'onClick="fetchDeleteId(this)"';

	var buttonBeedingClass = 'class="btn btn-warning" data-toggle="modal"';
	buttonBeedingClass += 'data-target="#modalBeeding"';
	buttonBeedingClass += 'onClick="fetchDeliverId(this)"';

	$
			.ajax({
				'url' : '' + myContext + '/ajaxDeliveryRequestList',
				'method' : "GET",
				'contentType' : 'application/json'
			})
			.done(
					function(data) {
						var dataToString = JSON.stringify(data);
						$('#dataTables-example')
								.dataTable(
										{
											responsive : true,
											"aaData" : data,
											"columns" : [
													{
														"data" : "deliveryType.mainte_delivery_type"
													},
													{
														"data" : "delivery_pickup_address"
													},
													{
														"data" : "delivery_destination"
													},

													{
														"data" : "deliveryType.delivery_price"
													},

													{
														"data" : "delivery_status"
													},

													{

														/*
														 * Add button to
														 * dataTable
														 */
														sortable : false,
														"render" : function(
																data, type,
																full, meta) {
															var buttonID = full.deliver_id;
															var drawActionButton = ' <button id='
																	+ buttonID
																	+ ' '
																	+ buttonEditClass
																	+ ' >Edit</button> ';
															drawActionButton += ' <button id='
																	+ buttonID
																	+ ' '
																	+ buttonDeleteClass
																	+ ' >Delete</button> ';
															drawActionButton += ' <button id='
																	+ buttonID
																	+ ' '
																	+ buttonBeedingClass
																	+ ' >Beeding Chart</button> ';
															return drawActionButton;
														}
													} ]
										})
					});
}

/*
 * This function will insert or update base on the value of button name. Set the
 * url action of ajax Set message to be display
 */
function insertOrUpdateDeliveryType() {
	var action, message;
	var getButtonName = $("#btnDeliveryType").val();

	if (getButtonName == "Save") {
		action = "ajaxAddDeliveryRequest";
		message = "Delivery request has been added to the list successfully.";
	} else {
		action = "ajaxUpdateDeliveryRequest";
		message = "Delivery request has been updated successfully.";
	}

	/* Call function */
	validateAndInsertUsingAjax(action, message);
}

/*
 * This function will validate all the input field inside the form and return a
 * response if result got an error. otherwise if no error in result is found it
 * will insert the data into database
 */
function validateAndInsertUsingAjax(action, message) {

	/* Disable button to prevent redundant ajax request */
	$("#btnDeliveryType").prop('disabled', true);

	/* get the text field form values */
	var id = $('#deliver_id').val();
	var type = $('#delivery_type').val();
	var details = $('#item_details').val();
	var address = $('#delivery_pickup_address').val();
	var destination = $('#delivery_destination').val();
	var date = $('#preferred_date').val();

	$.ajax({

		type : "GET",
		url : myContext + '/' + action,
		data : "deliver_id=" + id + "&delivery_type=" + type
				+ "&delivery_pickup_address=" + address + "&item_details="
				+ details + "&delivery_destination=" + destination
				+ "&preferred_date=" + date,
		contentType : "application/json; charset=utf-8",
		datatype : "json",
		crossDomain : "TRUE",
		success : function(response) {
			var stringResponse = JSON.stringify(response)
			// we have the response

			var obj = JSON.parse(stringResponse);

			if (obj.status == "SUCCESS") {
				/*
				 * Enable button to make ajax request again after response
				 * return
				 */
				$("#btnDeliveryType").prop('disabled', false);

				var userInfo = "<ol>";

				for (i = 0; i < obj.result.length; i++) {

					/* Create html elements */

					userInfo += "<br><li><b>Delivery type</b> : "
							+ obj.result[i].delivery_type;

					userInfo += "<br><li><b>Item details:</b> : "
							+ obj.result[i].item_details;

					userInfo += "<br><li><b>Delivery address</b> : "
							+ obj.result[i].delivery_pickup_address;

					userInfo += "<br><li><b>Delivery destination</b> : "
							+ obj.result[i].delivery_destination;
							
				}

				userInfo += "</ol>";

				/* Draw message in #info div */
				$('#info').html(message + userInfo);

				/* Show and hide div */
				$('#error').hide('slow');
				$('#info').show('slow');

				/* Populate DataTable */
				populateDataTable();

				/* Hide modal */
				$('#modalAddDeliveryType').modal('hide');

			} else {
				/*
				 * Enable button to make ajax request again after response
				 * return
				 */
				$("#btnDeliveryType").prop('disabled', false);

				var errorInfo = "";

				for (i = 0; i < response.result.length; i++) {

					errorInfo += "<br>" + (i + 1) + ". "
							+ response.result[i].code;

				}

				/* Show error message from response */
				$('#error').html(
						"Please correct following errors: " + errorInfo);

				/* Show and hide div */
				$('#info').hide('slow');
				$('#error').show('slow');

			}

		},

		/* xhr.status shows server respond */
		error : function(xhr, desc, err) {
			/*
			 * Enable button to make ajax request again after response return
			 */

			$("#btnDeliveryType").prop('disabled', false);
			if (xhr.status == 500) {
				alert('Error: ' + "Server not respond ");
			}
			if (xhr.status == 403) {
				alert('Error: ' + "Access Denied");
			}

		}

	});

}

/*
 * This method will call when modal is open. It will populate the chart
 * automatically base on the JSON response from the server
 */
$('#modalBeeding').on('shown.bs.modal', function() { // listen for user to
	// open modal

	$(function() {

		populateBeedingChart(); 

	});
});

/* Fetch the selected Beeding entry the user click */
var thisDate, thisData, parser, price;
$("#morris-bar-chart").on('click', function() {

	// Find data and date in the actual morris diply below the graph.

	thisDataHtml = $(".morris-hover-point").html().split(":");
	thisData = thisDataHtml[1].trim();

	$("#beedId").val(thisData);

	var fetchClass = document.querySelectorAll("[class='morris-hover-point']");
	for (var i = 0; i < fetchClass.length; i++) {
		// grab x[i].innerHTML (or textContent or innerText)
		parser = fetchClass[i].innerHTML.split(":");

		// Get delivery price
		if (i > 0) {
			price = parser[1];
		}
	}

	var beedinfo = "<ol>";
	beedinfo += "<br><li><b>Beeding entry</b> : " + thisData;
	beedinfo += "<br><li><b>Delivery Price</b> : " + price;
	beedinfo += "</ol>";

	/* Draw message in #info div */
	$('#beedInfo').html(beedinfo);

	$('#modalSelectBeeding').modal('show');
});

/* Get selection option selected value */
$('#delivery_type').on('change', function() {
	var id = this.value;

	/* Set Parameters */
	var dataParameter = {
		id : id,
	};
	$.ajax({
		url : '' + myContext + '/search-delivery-type-by-ajax',
		type : "GET",
		contentType : "application/json; charset=utf-8",
		datatype : "json",
		data : dataParameter,
		error : function() {
			alert('Error: ' + "Server not respond");
		},
		success : function(response) {

			/* Convert response into String format */
			stringResponse = JSON.stringify(response);

			/*
			 * Parse json response to get value of each key
			 */
			var obj = JSON.parse(stringResponse);

			/* Create Description message */
			var deliveryDescription = "<b>Description: </b>";
			deliveryDescription += "Delivery Cost: "
			deliveryDescription += '<b>' + obj.delivery_price + '</b>.';
			deliveryDescription += " Delivery Maximum weight(kg): "
			deliveryDescription += '<b>' + obj.delivery_weight + '</b>.';

			/* Draw message in div */
			$('#description').html(deliveryDescription);
			$('#panel_description').show('slow');
		}
	});
})

function populateBeedingChart() {
	var userBeedChoice;

	/* Set Parameters */
	var dataParameter = {
		id : global_id,
	};

	// Fire off an AJAX request to load the data
	$.ajax({
		type : "GET",
		dataType : 'json',
		url : '' + myContext + '/ajaxSearchBeedingRequest',
		// This is the URL to the API
		data : dataParameter,
	}).done(function(data) {
		// When the response to the AJAX request comes back
		// render
		// the chart
		// with new data
		// If date response lenght is more than 0 it will
		// set the
		// chart value

		if (data.length > 0) {
			// Set beed choice
			userBeedChoice = data[0].deliveryRequest.user_beed_choice;
			// Populate chart data
			chart.setData(data);
		}

	}).fail(function() {
		// If there is no communication between the server,
		// show an
		// error
		alert("error occured");
	});
	
	$("#morris-bar-chart").empty(); // clear chart so it
	
	var chart = Morris.Bar({
		element : 'morris-bar-chart',
		data : [ 0, 0 ],
		barColors : function(row, series, type) {

			/* Change Bar chart color base on the current user id session */
			if (row.label == userBeedChoice) {
				return "#1AB244"; // green
			} else
				return "#7a92a3"; // maroon
		},
		xkey : 'beeding_id',
		ykeys : [ 'beeding_id', 'beeding_startingprice' ],
		labels : [ 'Beed entry', 'Delivery Price' ],
		pointSize : 2,
		xLabelAngle : 45,
		hideHover : 'auto',
		resize : true,
		stacked : true
	});
}
