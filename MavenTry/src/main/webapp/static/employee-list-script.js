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
function addEmployee() {
	/* Clear input fields */
	clearTextField();

	/* Change Form action to Register mode */
	$('#myform').attr('action', '' + myContext + '/employeeList');

	/* Set text value of button */
	$('#btnEmployee').val("Register");

	/* Change class of attr of button */
	$("#btnEmployee").attr('class', 'btn btn-primary');
}

/* Clear all textfield value */
function clearTextField() {

	/* Parse string into boolean */
	var hasError = $.parseJSON($("#errorStatus").val());

	if (!hasError) {
		/* Set default to 0 */
		$("#id").val("0");
		$("#name").val(null);
		$("#address").val(null);
		$("#salary").val(null);
		$("#ssn").val(null);
		$("#joiningDate").val(null);

		/* Clear all span with error class messages */
		$('.error').text('');

	}

}

/* Fetch employee information via ssn id using json response using @RequestParam */
function searchEmployeeDetailViaAjax(elem) {
	var stringResponse;
	var id = elem.id;

	/* Hide error response message */
	$('#error').hide();

	/* Set value of input text to false */
	$("#hasError").val("false");

	/* Get id value in hidden input text field */
	$("#id").val(id);

	/* Set text value of button */
	$('#btnEmployee').val("Update");

	/* Change class of attr of button */
	$("#btnEmployee").attr('class', 'btn btn-success');

	/* Set Parameters */
	var dataParameter = {
		ssn : id,
	};
	$.ajax({
		url : '' + myContext + '/search-employee-by-ajax',
		type : "GET",
		datatype : "json",
		data : dataParameter,
		error : function() {
			alert('Error: ' + "Server not respond");
		},
		success : function(response) {

			/* Convert response into String format */
			stringResponse = JSON.stringify(response);

			console.log("stringResponse " + stringResponse);

			/* Parse json response to get value of each key */
			var obj = JSON.parse(stringResponse);

			/* Set modal text field value */
			$("#id").val(obj.id);
			$("#name").val(obj.name);
			$("#address").val(obj.address);
			$("#salary").val(obj.salary);
			$("#ssn").val(obj.ssn);

			/* Convert obj into string array */
			// var datesToString = JSON.stringify(obj.joiningDate.values);
			/*
			 * Replace all comma "," symbol in string with blank space ""
			 */
			// datesToString = datesToString.replace(/,/g, "-").replace(/\[/g,
			// "")
			// .replace(/\]/g, "");
			/* Set Date format to YYYY-MM-dd */
			$("#joiningDate").val(obj.joiningDate);

		}
	});
	/* $('#myform').attr('action', '' + myContext + '/updateEmployeeList'); */
}

/* Fetch Employee Ssn id */
function fetchDeleteId(obj) {
	var id = obj.id;
	/* Set hidden text field value */
	$("#deleteId").val(id);
	// $('#deleteForm').attr('action', '' + myContext + '/getEmployeeList');
}

/* Delete Employee by Ssn id */
function deleteViaAjax() {
	var stringResponse;

	/* Disable button to prevent redundant ajax request */
	$("#btnEmployeeDelete").prop('disabled', true);

	/* Get hidden text field value in modal delete */
	var id = $("#deleteId").val();

	/* Set Parameters */
	var dataParameter = {
		ssn : id,
	};
	$.ajax({
		url : '' + myContext + '/delete-employee-by-ajax',
		type : "POST",
		datatype : "json",
		data : dataParameter,
		error : function(e) {
			console.log("ERROR: ", e);
		},
		success : function(response) {

			/* Enable button to make ajax request again after response return */
			$("#btnEmployeeDelete").prop('disabled', false);

			/* Populate DataTable */
			populateDataTable();

			/* Hide modal */
			$('#modalDeleteEmployee').modal('hide');

		}
	});

}

/* Populate DataTable of list of all employee existed using ajax */
function populateDataTable() {
	$("#dataTables-example").dataTable().fnDestroy();

	/* set class and onClick event listener */
	var buttonEditClass = 'class="btn btn-success" data-toggle="modal"';
	buttonEditClass += 'data-target="#modalAddEmployee"';
	buttonEditClass += 'onClick="searchEmployeeDetailViaAjax(this)"';

	var buttonDeleteClass = 'class="btn btn-danger" data-toggle="modal"';
	buttonDeleteClass += 'data-target="#modalDeleteEmployee"';
	buttonDeleteClass += 'onClick="fetchDeleteId(this)"'

	$
			.ajax({
				'url' : '' + myContext + '/ajaxEmployeeList',
				'method' : "GET",
				'contentType' : 'application/json'
			})
			.done(
					function(data) {
						var dataToString = JSON.stringify(data);
						console.log("dataToString " + dataToString);

						$('#dataTables-example')
								.dataTable(
										{
											responsive : true,
											"aaData" : data,
											"columns" : [
													{
														"data" : "name"
													},
													{
														"data" : "joiningDate"
													},
													{
														"data" : "salary"
													},
													{
														"data" : "address"
													},
													{
														"data" : "ssn"
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
															var buttonID = full.ssn;
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
	var getButtonName = $("#btnEmployee").val();

	if (getButtonName == "Register") {
		action = "ajaxAddEmployee";
		message = "Employee has been added to the list successfully.";
	} else {
		action = "ajaxEditEmployee";
		message = "Employee has been updated successfully.";
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
	$("#btnEmployee").prop('disabled', true);

	/* get the text field form values */
	var id = $('#id').val();
	var name = $('#name').val();
	var address = $('#address').val();
	var salary = $('#salary').val();
	var joiningDate = $('#joiningDate').val();
	var ssn = $('#ssn').val();

	console.log(joiningDate);

	/* Set parameters value */
	var setParameters = "name=" + name + "&address=";
	setParameters += "&salary=" + salary + "&joiningDate=" + joiningDate;
	setParameters += "&ssn=" + ssn;

	$.ajax({

		type : "POST",
		url : myContext + '/' + action,
		data : "id=" + id + "&name=" + name + "&address=" + address
				+ "&salary=" + salary + "&joiningDate=" + joiningDate + "&ssn="
				+ ssn,
		datatype : "json",
		success : function(response) {
			var stringResponse = JSON.stringify(response)
			console.log("response ajax " + stringResponse);
			// we have the response

			var obj = JSON.parse(stringResponse);

			if (obj.status == "SUCCESS") {
				/*
				 * Enable button to make ajax request again after response
				 * return
				 */
				$("#btnEmployee").prop('disabled', false);

				var userInfo = "<ol>";

				for (i = 0; i < obj.result.length; i++) {

					/* Create html elements */

					userInfo += "<br><li><b>Name</b> : " + obj.result[i].name;

					userInfo += "<br><li><b>Address</b> : "
							+ obj.result[i].address;

					userInfo += "<br><li><b>Salary</b> : "
							+ obj.result[i].salary;

					userInfo += "<br><li><b>Ssn</b> : " + obj.result[i].ssn;

					userInfo += "<br><li><b>Joining Date</b> : "
							+ obj.result[i].joiningDate;

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
				$('#modalAddEmployee').modal('hide');

			} else {
				/*
				 * Enable button to make ajax request again after response
				 * return
				 */
				$("#btnEmployee").prop('disabled', false);

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

		error : function(e) {
			/*
			 * Enable button to make ajax request again after response return
			 */
			$("#btnEmployee").prop('disabled', false);
			alert('Error: ' + "Server not respond");

		}

	});

}