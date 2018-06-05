'use strict';

App.controller('UserController', [ '$scope', 'User', 'UserProfile',
		'$uibModal', '$log',
		function($scope, User, UserProfile, $uibModal, $log) {
			var self = this;
			self.user = new User();
			self.profiles = new UserProfile();

			self.users = [];
			self.profiles = [];
			
//			$scope.itemList = [];

			self.fetchAllUsers = function() {
				// console.log("FIRST LOADING HERE?");
				self.users = User.query();
				// console.log('self.users: ', self.users);
			};

			self.createUser = function() {
				self.user.$save(function() {
					self.fetchAllUsers();
				});
			};

			self.updateUser = function() {
				self.user.$update(function() {
					self.fetchAllUsers();
				});
			};

			self.deleteUser = function(identity) {
				var user = User.get({
					id : identity
				}, function() {
					user.$delete(function() {
						console.log('Deleting user with id ', identity);
						self.fetchAllUsers();
					});
				});
			};

			self.fetchAllUsers();

			self.submit = function() {
				if (self.user.id == null) {
					console.log('Saving New User', self.user);
					self.createUser();
				} else {
					console.log('Upddating user with id ', self.user.id);
					self.updateUser();
					console.log('User updated with id ', self.user.id);
				}
				self.reset();
			};

			self.edit = function(id) {
				console.log('id to be edited', id);
				for (var i = 0; i < self.users.length; i++) {
					if (self.users[i].id === id) {
						self.user = angular.copy(self.users[i]);
						break;
					}
				}

			};

			// Populate Select option dropdown
			self.fetchAllProfiles = function() {
				self.profiles = UserProfile.query();
				console.log('self.profiles: ', self.profiles);
			};

			self.fetchAllProfiles();

			$scope.open = function(id) {

				$log.info("parameter id: " + id);
				$scope.items = [];

				if (id != null) {
					$log.info("ENTRANCE model ", self.user);
					for (var i = 0; i < self.users.length; i++) {
						if (self.users[i].id === id) {
							self.user = angular.copy(self.users[i]);
							$scope.items = angular.copy(self.users[i]);
							// console.log('ID: ', self.user.id);
							// console.log('USERNAME: ', self.user.usernameId);
							// console.log('FIRSTNAME: ', self.user.firstName);
							// console.log('LASTNAME: ', self.user.lastName);
							// console.log('EMAIL: ', self.user.email);
							// console.log('PASSWORD: ', self.user.password);
							// console
							// .log('PROFILE: ',
							// self.user.userProfiles[0].type);

							$log.info("TRUE model ", $scope.items);
							break;

						}
					}
				} else {
					self.reset();
					$scope.items = angular.copy(self.user);
					// $scope.items = self.user;
									
					$log.info("FALSE model user ", self.user);

				}

				var modalInstance = $uibModal.open({
					templateUrl : 'myModal1',
					controller : 'ModalInstance',
					size : 'lg',
					resolve : {
						items : function() {
							return $scope.items;
						}
					}
				});
			};

//			// On dropdown selection change get selected value
//			$scope.changedValue = function(item) {
//			    //$scope.itemList.push(item);
//			    //console.log("SELECTED ",  $scope.itemList);
//			    
//			    for (var i = 0; i < self.profiles.length; i++) {
//					if (self.profiles[i].id === item) {
//						$scope.itemList = angular.copy(self.profiles[i]);
//				
//						$log.info("TRUE model ", $scope.itemList);
//						break;
//
//					}
//			    }
//			  };     
			
			$scope.showForm = function(id) {
				$scope.message = "Show Form Button Clicked";
				console.log($scope.message);
				$scope.userForm = [];
				for (var i = 0; i < self.users.length; i++) {
					if (self.users[i].id === id) {
						self.user = angular.copy(self.users[i]);
						console.log('ID: ', self.user.id);
						// console.log('USERNAME: ', self.user.usernameId);
						// console.log('FIRSTNAME: ', self.user.firstName);
						// console.log('LASTNAME: ', self.user.lastName);
						// console.log('EMAIL: ', self.user.email);
						// console.log('PASSWORD: ', self.user.password);
						// console
						// .log('PROFILE: ',
						// self.user.userProfiles[0].type);

					}
				}

				var modalInstance = $uibModal.open({
					templateUrl : 'modal-form.jsp',
					controller : ModalInstanceCtrlv1,
					scope : $scope,
					resolve : {
						userForm : function() {
							return $scope.userForm1;
						}
					}
				});
			};

			self.remove = function(id) {
				console.log('id to be deleted', id);
				if (self.user.id === id) {// If it is the one
					// shown on screen,
					// reset screen
					self.reset();
				}
				self.deleteUser(id);
			};

			self.reset = function() {
				self.user = new User();
				//$scope.form.userForm.$setPristine(); // reset Form
			};

		} ]);

var ModalInstanceCtrlv1 = function($scope, $uibModalInstance, userForm, $log,
		User) {

	var testModal = this;
	testModal.user = new User();

	console.log('ModalInstanceCtrlv1 $scope.userForm: ', $scope.form.userForm1);
	$scope.form = {}
	$scope.submitForm = function() {
		if ($scope.form.userForm1.$valid) {
			console.log('user form is in scope');
			$log
					.info("name $scope.form.userForm: ",
							$scope.form.userForm1.name);
			$log.info("email $scope.form.userForm: ",
					$scope.form.userForm1.email);
			// testModal.user = $scope.form.userForm1;
			$log.info("SUBMIT ", testModal.user);

			$uibModalInstance.close('closed');
		} else {
			console.log('userform is not in scope');
		}

	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
};

App.controller('ModalDemoCtrl', function($scope, $uibModal, $log) {

	$scope.items = [ 'item1', 'item2', 'item3' ];

	$scope.animationsEnabled = true;

	$scope.open = function(size) {

		var modalInstance = $modal.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'myModal',
			controller : 'ModalInstanceCtrl',
			size : size,
			resolve : {
				items : function() {
					return $scope.items;
				}
			}
		});

		modalInstance.result.then(function(selectedItem) {
			$scope.selected = selectedItem;
		}, function() {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

	$scope.toggleAnimation = function() {
		$scope.animationsEnabled = !$scope.animationsEnabled;
	};

});

// Please note that $modalInstance represents a modal window (instance)
// dependency.
// It is not the same as the $modal service used above.

App.controller('ModalInstanceCtrl', function($scope, $uibModalInstance, items) {

	console.log("test ModalInstanceCtrl: " + items);

	$scope.items = items;
	$scope.selected = {
		item : $scope.items[0]
	};

	$scope.ok = function() {
		$modalInstance.close($scope.selected.item);
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
});

// Populate modal with data
App
		.controller(
				'ModalInstance',
				function($scope, $uibModalInstance, items, $log, User) {

					$scope.form = {};
					var myModal = this;
					myModal.user = new User();

					myModal.users = [];

					console.log("ITEMS ", items);

					// Populate Select option dropdown
					myModal.fetchAllProfiles = function() {
						myModal.profiles = UserProfile.query();
						console.log('myModal.profiles: ', myModal.profiles);
					};

					// Populate DataTable $GET
					myModal.fetchAllUsers = function() {
						console.log("SECOND LOADING HERE?");
						myModal.users = User.query();
						console.log('myModal.users: ', myModal.users);
					};

					// Create user $POST
					myModal.createUser = function() {
						console.log("myModal.user CREATED: ", myModal.user);
						myModal.user.$save(function() {
							myModal.fetchAllUsers();
						});
					};

					// Update $PUT
					myModal.updateUser = function() {
						console.log("myModal.user UPDATE: ", myModal.user);
						myModal.user.$update(function() {
							$log.info("DETECT PUT");
							myModal.fetchAllUsers();
						});
					};

					// Fetch user info $GET
					myModal.getUser = function(identity) {
						var user = User.get({
							id : identity
						}, function() {
							console.log("myModal.user GET: ", myModal.user);
							$log.info("DETECT GET");
						});
					};
					
					myModal.reset = function() {
						myModal.user = new User();
						//$scope.form.userForm.$setPristine(); // reset Form
					};

					$scope.userInfo = items;

					// $scope.userInfo.userProfiles.id =
					// items.userProfiles[0].id;

					if ($scope.userInfo.id == null) {
						$scope.userInfo.id = "0";
						myModal.reset();
						
						console.log("NO ID");
					} else {
						
						$scope.userInfo.userProfiles.id = items.userProfiles[0].id;
						console.log("DETECT ID", items.userProfiles[0].id);
					}

					// $scope.userInfo.userProfiles[0].id =
					// $scope.userInfo.userProfiles[0].id;

					$scope.submitForm = function() {
						if ($scope.form.userForm.$valid) {

							// console.log("FORM SELECTED "+
							// $scope.form.userForm.userInfo.userProfiles.id);

							// console.log('CSRF_TOKEN', csrftoken); // Token

							myModal.user = $scope.userInfo;

							// myModal.user.userProfiles[0].id =
							// $scope.userInfo.userProfiles.id;

							console.log('ON SUBMIT ', myModal.user);

							// if ($scope.userInfo.userId === null) {
							// console.log('new user created');
							// // Call function for POST
							// myModal.createUser();
							// } else {
							//			
							// console.log('update existed user');
							// // Call function for PUT
							// myModal.updateUser();
							//
							// }

							// Call function for GET
							// myModal.getUser($scope.userInfo.id);

							// $log.info("id ", $scope.userInfo.id );

							// $log.info("myModal.user test ", myModal.user);
							//
							// $log.info("ID test", myModal.user.id);

							if (myModal.user.id == "") {
																								
								$log.info("NULL myModal.user test ",
										myModal.user);
																													
								$log.info("ID test", myModal.user.id);

								// Call function for POST
								myModal.createUser();

							} else {
								$log.info("NOT NULL myModal.user test ",
										myModal.user);

								myModal.user.userProfiles[0].id = $scope.userInfo.userProfiles.id;

								$log.info("ID test",
										myModal.user.userProfiles[0].id);

								// Call function for PUT
								myModal.updateUser();

							}
							
							myModal.reset();

							// $log.info("id $scope.form.userForm: " ,
							// $scope.form.userForm.userId);
							// $log.info("fname $scope.form.userForm: " ,
							// $scope.form.userForm.fname);
							// $log.info("lname $scope.form.userForm: " ,
							// $scope.form.userForm.lname);
							// $log.info("email $scope.form.userForm: " ,
							// $scope.form.userForm.email);

							$uibModalInstance.close('closed');
						} else {
							console.log('userform is not in scope');
						}

					};

					$scope.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				});

// var csrftoken = (function() {
// // not need Jquery for doing that
// var metas = window.document.getElementsByTagName('meta');
//
// // finding one has csrf token
// for (var i = 0; i < metas.length; i++) {
//
// if (metas[i].name === "csrf-token") {
//
// return metas[i].content;
// }
// }
//
// })();
//
// App.constant('CSRF_TOKEN', csrftoken);

// angular.module('myApp').controller('ModalInstanceCtrl', function ($scope,
// $modalInstance, items) {
//
// console.log("test Instance: "+ items);
//	
// $scope.items = items;
// $scope.selected = {
// item: $scope.items[0]
// };
//
// $scope.ok = function () {
// $modalInstance.close($scope.selected.item);
// };
//
// $scope.cancel = function () {
// $modalInstance.dismiss('cancel');
// };
// });
