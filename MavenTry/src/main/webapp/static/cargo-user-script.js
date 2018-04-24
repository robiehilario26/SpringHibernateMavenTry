/* This JavaScript file provide all the jquery and ajax function
 * for retrieving data, deleting data, updating data, clearing
 * input text field and populate dataTable columns.
 *
 * myContext is a global variable in employeeList.jsp, equivalent into
 * ${pageContext.request.contextPath} - pageContext whatever is the context
 * path of the project.
 * 
 */

/* Add employee */
function addCargoUser() {
	/* Clear input fields */
	clearTextField();

	/* Set text value of button */
	$('#btnCargo').val("Register");

	/* Change class of attr of button */
	$("#btnCargo").attr('class', 'btn btn-primary');
}

/* Clear all textfield value */
function clearTextField() {

	/* Set default to 0 */
	$("#cargo_id").val("0");
	$("#cargo_driver").val(null);
	$("#cargo_vehicletype").val(null);
	$("#cargo_company").val(null);

}

/* Fetch employee information via cargo user id using json response using @RequestParam */
function searchCargoDetailViaAjax(elem) {
	var stringResponse;
	var id = elem.id;
		
	/* Get id value in hidden input text field */
	$("#cargo_id").val(id);

	/* Set text value of button */
	$('#btnCargo').val("Update");

	/* Change class of attr of button */
	$("#btnCargo").attr('class', 'btn btn-success');

	/* Disable button to prevent unnecessary submit */
	$("#btnCargo").prop('disabled', true);

	/* Set Parameters */
	var dataParameter = {
		id : id,
	};
	$.ajax({
		url : '' + myContext + '/search-cargo-user-by-ajax',
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
			$("#cargo_id").val(obj.cargo_id);
			$("#cargo_driver").val(obj.cargo_driver);
			$("#cargo_vehicletype").val(obj.cargo_vehicletype);
			$("#cargo_company").val(obj.cargo_company);

			/* Enable button to submit */
			$("#btnCargo").prop('disabled', false);

		}
	});

}

/* Fetch Employee Ssn id */
function fetchDeleteId(obj) {
	var id = obj.id;
	/* Set hidden text field value */
	$("#deleteId").val(id);

}

/* Delete Employee by Ssn id */
function deleteViaAjax() {
	var stringResponse;

	/* Disable button to prevent redundant ajax request */
	$("#btnCargoDelete").prop('disabled', true);

	/* Get hidden text field value in modal delete */
	var id = $("#deleteId").val();

	/* Set Parameters */
	var dataParameter = {
			cargo_id : id,
	};
	$.ajax({
		url : '' + myContext + '/delete-cargo-user-by-ajax',
		type : "GET",
		contentType : "application/json; charset=utf-8",
		datatype : "json",
		data : dataParameter,
		error : function(xhr, desc, err) {
			$("#btnCargoDelete").prop('disabled', false);
			if (xhr.status == 500) {
				alert('Error: ' + "Server not respond ");
			}
			if (xhr.status == 403) {
				alert('Error: ' + "Access Denied");
			}
		},
		success : function(response) {

			/* Enable button to make ajax request again after response return */
			$("#btnCargoDelete").prop('disabled', false);

			/* Populate DataTable */
			populateCargoDataTable();

			/* Hide modal */
			$('#modalDeleteCargoUser').modal('hide');

		}
	});

}

/* Populate DataTable of list of all employee existed using ajax */
function populateCargoDataTable() {
	$("#dataTables-example").dataTable().fnDestroy();

	/* set class and onClick event listener */
	var buttonEditClass = 'class="btn btn-success" data-toggle="modal"';
	buttonEditClass += 'data-target="#modalAddCargoUser"';
	buttonEditClass += 'onClick="searchCargoDetailViaAjax(this)"';

	var buttonDeleteClass = 'class="btn btn-danger" data-toggle="modal"';
	buttonDeleteClass += 'data-target="#modalDeleteCargoUser"';
	buttonDeleteClass += 'onClick="fetchDeleteId(this)"'

	$
			.ajax({
				'url' : '' + myContext + '/ajaxCargoList',
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
														"data" : "cargo_driver"
													},
													{
														"data" : "cargo_vehicletype"
													},
													{
														"data" : "cargo_company"
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
															var buttonID = full.cargo_id;
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
function insertOrUpdate() {
	var action, message;
	var getButtonName = $("#btnCargo").val();

	if (getButtonName == "Register") {
		action = "ajaxAddCargoUser";
		message = "User has been added to the list successfully.";
	} else {
		action = "ajaxEditCargoUser";
		message = "User has been updated successfully.";
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
	$("#btnCargoUser").prop('disabled', true);

	/* get the text field form values */
	var id = $('#cargo_id').val();
	var name = $('#cargo_driver').val();
	var vehicle = $('#cargo_vehicletype').val();
	var company = $('#cargo_company').val();

	$.ajax({

		type : "GET",
		url : myContext + '/' + action,
		data : "cargo_id=" + id + "&cargo_driver=" + name + "&cargo_vehicletype="
				+ vehicle + "&cargo_company=" + company,
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
				$("#btnCargo").prop('disabled', false);

				var userInfo = "<ol>";

				for (i = 0; i < obj.result.length; i++) {

					/* Create html elements */

					userInfo += "<br><li><b>Name</b> : "
							+ obj.result[i].cargo_driver;

					userInfo += "<br><li><b>Vehicle</b> : "
							+ obj.result[i].cargo_vehicletype;

					userInfo += "<br><li><b>Company</b> : "
							+ obj.result[i].cargo_company;

				}

				userInfo += "</ol>";

				/* Draw message in #info div */
				$('#info').html(message + userInfo);

				/* Show and hide div */
				$('#error').hide('slow');
				$('#info').show('slow');

				/* Populate DataTable */
				populateCargoDataTable();

				/* Hide modal */
				$('#modalAddCargoUser').modal('hide');

			} else {
				/*
				 * Enable button to make ajax request again after response
				 * return
				 */
				$("#btnCargo").prop('disabled', false);

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

			$("#btnCargo").prop('disabled', false);
			if (xhr.status == 500) {
				alert('Error: ' + "Server not respond ");
			}
			if (xhr.status == 403) {
				alert('Error: ' + "Access Denied");
			}

		}

	});

}
