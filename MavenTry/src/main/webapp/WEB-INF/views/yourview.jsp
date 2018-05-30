<!-- Register Modal -->
	<div class="modal fade" id="modalUser" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
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
				<form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
					<input type="hidden" ng-model="ctrl.user.id" />
					<div class="modal-body">
						<input type="hidden" id="token" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<!-- Input First Name -->
						<div>
						<label class="col-md-2 control-lable" for="fname">First Name</label>
							<input type="text" ng-model="ctrl.user.firstName" id="fname" class="firstname form-control input-sm" placeholder="Enter your first name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.fname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.fname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.fname.$invalid">This field is invalid </span>
                                  </div>
						</div>

						<!-- Input Last Name -->
						<div>
							<%-- <label for="lastName">Last name: </label>
							<form:input path="lastName" id="lastName" class="form-control"
								placeholder="Last name" />
							<form:errors path="lastName" cssClass="error" /> --%>
							<label class="col-md-2 control-lable" for="lname">Last Name</label>
							<input type="text" ng-model="ctrl.user.lastName" id="lname" class="lastname form-control input-sm" placeholder="Enter your last name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.lname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.lname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.lname.$invalid">This field is invalid </span>
                                  </div>
						</div>

						<!-- Input Email -->
						<div>
							<%-- <label for="email">Email: </label>
							<form:input path="email" id="email" class="form-control"
								placeholder="Email" />
							<form:errors path="email" cssClass="error" /> --%>
							 <label class="col-md-2 control-lable" for="email">Email</label>
							 <input type="email" ng-model="ctrl.user.email" id="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.email.$error.required">This is a required field</span>
                                      <span ng-show="myForm.email.$invalid">This field is invalid </span>
                                  </div>
						</div>

						<!-- Input User name -->
						<div>
							<%-- <label for="ssoId">Username: </label>
							<form:input path="usernameId" id="usernameId" class="form-control"
								placeholder="Username" />
							<form:errors path="usernameId" cssClass="error" /> --%>
							<label class="col-md-2 control-lable" for="uname">First Name</label>
							<input type="text" ng-model="ctrl.user.usernameId" id="uname" class="username form-control input-sm" placeholder="Enter your username" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.uname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.uname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.uname.$invalid">This field is invalid </span>
                                  </div>
						</div>

						<!-- Input Password -->
						<div>
							<%-- <label for="password">Password: </label>
							<form:input path="password" id="password" class="form-control"
								placeholder="password" />
							<form:errors path="password" cssClass="error" /> --%>
							<label class="col-md-2 control-lable" for="password">First Name</label>
							<input type="text" ng-model="ctrl.user.password" id="pword" class="password form-control input-sm" placeholder="Enter your password" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.pword.$error.required">This is a required field</span>
                                      <span ng-show="myForm.pword.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.pword.$invalid">This field is invalid </span>
                                  </div>
						</div>

						<%-- <div>
							<!-- Select Role Type -->
							<label for="userProfiles">Account Type: </label>
							<form:select name="userProfiles" id="userProfiles"
								path="userProfiles" items="${roles}"
								itemValue="id" itemLabel="type" class="form-control" />
							<form:errors path="userProfiles" cssClass="error" />
						</div> --%>


						<div id="error" class="error"></div>

					</div>

					<div class="modal-footer">

						<!-- Close button -->
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>

						<!-- Register button -->
						<input type="button" class="btn btn-primary" value="Register"
							id="btnUser" onClick="insertOrUpdate()" />

					</div>
				</form>
			</div>
		</div>
	</div>
