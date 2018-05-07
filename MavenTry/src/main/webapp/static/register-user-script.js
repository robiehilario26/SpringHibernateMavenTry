/* This JavaScript file provide all the jquery and ajax function
 * for retrieving data, deleting data, updating data, clearing
 * input text field and populate dataTable columns.
 *
 * myContext is a global variable in jsp, equivalent into
 * ${pageContext.request.contextPath} - pageContext whatever is the context
 * path of the project.
 * 
 */

/* Add user */
function addUser() {
	/* Clear input fields */
	clearTextField();

	/* Set text value of button */
	$('#btnUser').val("Register");

	/* Change class of attr of button */
	$("#btnUser").attr('class', 'btn btn-primary');
}

/* Clear all textfield value */
function clearTextField() {

	/* Set default to 0 / null */
	$("#id").val(null);
	$("#firstName").val(null);
	$("#lastName").val(null);
	$("#email").val(null);
	$("#usernameId").val(null);
	$("#password").val(null);
	$("#userProfiles").val(null);

	/* Clear error validation message */
	$("#error").empty();

}

/*
 * Fetch user information via cargo user id using json response using
 * @RequestParam
 */
function searchUserDetailViaAjax(elem) {
	var stringResponse;
	var id = elem.id;

	/* Clear error validation message */
	$("#error").empty();

	/* Get id value in hidden input text field */
	$("#id").val(id);

	/* Set text value of button */
	$('#btnUser').val("Update");

	/* Change class of attr of button */
	$("#btnUser").attr('class', 'btn btn-success');

	/* Disable button to prevent unnecessary submit */
	$("#btnUser").prop('disabled', true);

	/* Set Parameters */
	var dataParameter = {
		id : id,
	};
	$.ajax({
		url : '' + myContext + '/search-user-by-ajax',
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
			console.log(stringResponse);
			/* Parse json response to get value of each key */
			var obj = JSON.parse(stringResponse);
			console.log("stringResponse" + stringResponse);
			/* Set modal text field value */
			$("#id").val(obj.id);
			$("#firstName").val(obj.firstName);
			$("#lastName").val(obj.lastName);
			$("#email").val(obj.email);
			$("#usernameId").val(obj.usernameId);
			$("#password").val(obj.password);

			var x = JSON.stringify(obj.userProfiles[0]);
			console.log("x " + x);

			x = JSON.parse(x);
			console.log("new " + x.id);

			$("#userProfiles").val(x.id);

			/* Enable button to submit */
			$("#btnUser").prop('disabled', false);

		}
	});

}

/* Fetch User id */
function fetchDeleteId(obj) {
	var id = obj.id;
	/* Set hidden text field value */
	$("#deleteId").val(id);

}

/* Delete User by id */
function deleteViaAjax() {
	var stringResponse;

	/* Disable button to prevent redundant ajax request */
	$("#btnUserDelete").prop('disabled', true);

	/* Get hidden text field value in modal delete */
	var id = $("#deleteId").val();

	/* Set Parameters */
	var dataParameter = {
		userId : id,
	};
	$.ajax({
		url : '' + myContext + '/ajaxDeletetUser',
		type : "GET",
		contentType : "application/json; charset=utf-8",
		datatype : "json",
		data : dataParameter,
		error : function(xhr, desc, err) {
			$("#btnUserDelete").prop('disabled', false);
			if (xhr.status == 500) {
				alert('Error: ' + "Server not respond ");
			}
			if (xhr.status == 403) {
				alert('Error: ' + "Access Denied");
			}
		},
		success : function(response) {

			/* Enable button to make ajax request again after response return */
			$("#btnUserDelete").prop('disabled', false);

			/* Populate DataTable */
			populateUserDataTable();

			/* Hide modal */
			$('#modalDeleteUser').modal('hide');

		}
	});

}

/* Populate DataTable of list of all User existed using ajax */
function populateUserDataTable() {
	$("#dataTables-example").dataTable().fnDestroy();

	/* set class and onClick event listener */
	var buttonEditClass = 'class="btn btn-success" data-toggle="modal"';
	buttonEditClass += 'data-target="#modalUser"';
	buttonEditClass += 'onClick="searchUserDetailViaAjax(this)"';

	var buttonDeleteClass = 'class="btn btn-danger" data-toggle="modal"';
	buttonDeleteClass += 'data-target="#modalDeleteUser"';
	buttonDeleteClass += 'onClick="fetchDeleteId(this)"'

	$
			.ajax({
				'url' : '' + myContext + '/ajaxUserList',
				'method' : "GET",
				'contentType' : 'application/json'
			})
			.done(
					function(data) {
						var dataToString = JSON.stringify(data);
						console.log("dataToString "+dataToString);
						$('#dataTables-example')
								.dataTable(
										{
											responsive : true,
											"aaData" : data,
											"columns" : [
													{
														"data" : "firstName"
													},
													{
														"data" : "lastName"
													},
													{
														"data" : "email"
													},

													{
														"data" : "usernameId"
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
															var buttonID = full.id;
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
	var getButtonName = $("#btnUser").val();

	if (getButtonName == "Register") {
		action = "ajaxAddUser";
		message = "User has been added to the list successfully.";
	} else {
		action = "ajaxEditUser";
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
	$("#btnUser").prop('disabled', true);

	/* get the text field form values */
	var id = $('#id').val();
	var fname = $('#firstName').val();
	var lname = $('#lastName').val();
	var email = $('#email').val();
	var username = $('#usernameId').val();
	var password = $('#password').val();

	var serializedData = $('form[name=userForm]').serialize();

	console.log("DATA " + serializedData);

	$
			.ajax({
				headers : {
					Accept : "application/json",
					"Content-Type" : "application/json"
				},
				type : "GET",
				url : myContext + '/' + action + '?_csrf=' + $("#token").val(),
				data : serializedData,
				contentType : "application/json; charset=utf-8",
				datatype : "json",
				crossDomain : "TRUE",
				success : function(response) {
					var stringResponse = JSON.stringify(response)
					// we have the response

					var obj = JSON.parse(stringResponse);

					if (obj.status == "SUCCESS") {
						/*
						 * Enable button to make ajax request again after
						 * response return
						 */
						$("#btnUser").prop('disabled', false);

						var userInfo = "<ol>";

						for (i = 0; i < obj.result.length; i++) {

							/* Create html elements */

							userInfo += "<br><li><b>firstName</b> : "
									+ obj.result[i].firstName;

							userInfo += "<br><li><b>lastName</b> : "
									+ obj.result[i].lastName;

							userInfo += "<br><li><b>email</b> : "
									+ obj.result[i].email;

							userInfo += "<br><li><b>username</b> : "
									+ obj.result[i].usernameId;

							userInfo += "<br><li><b>password</b> : "
									+ obj.result[i].password;

						}

						userInfo += "</ol>";

						/* Draw message in #info div */
						$('#info').html(message + userInfo);

						/* Show and hide div */
						$('#error').hide('slow');
						$('#info').show('slow');

						/* Populate DataTable */
						populateUserDataTable();

						/* Hide modal */
						$('#modalUser').modal('hide');

					} else {
						/*
						 * Enable button to make ajax request again after
						 * response return
						 */
						$("#btnUser").prop('disabled', false);

						var errorInfo = "";

						for (i = 0; i < response.result.length; i++) {

							errorInfo += "<br>" + (i + 1) + ". "
									+ response.result[i].code;

						}

						/* Show error message from response */
						$('#error')
								.html(
										"Please correct following errors: "
												+ errorInfo);

						/* Show and hide div */
						$('#info').hide('slow');
						$('#error').show('slow');

					}

				},

				/* xhr.status shows server respond */
				error : function(xhr, desc, err) {
					/*
					 * Enable button to make ajax request again after response
					 * return
					 */

					$("#btnUser").prop('disabled', false);
					if (xhr.status == 500) {
						alert('Error: ' + "Server not respond ");
					}
					if (xhr.status == 403) {
						alert('Error: ' + "Access Denied");
					}

				}

			});

}
