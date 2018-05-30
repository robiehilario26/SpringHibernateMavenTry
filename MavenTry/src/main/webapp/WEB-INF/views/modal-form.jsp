<div class="modal-header">
    <h3>Create A New Account!</h3>
</div>
<form name="form.userForm" ng-submit="submitForm()" novalidate>
    <div class="modal-body">
        <!-- NAME -->
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" class="form-control" ng-model="name" required>
            <p ng-show="form.userForm.name.$invalid && !form.userForm.name.$pristine" class="help-block">You name is required.</p>
        </div>

        <!-- USERNAME -->
        <div class="form-group">
            <label>Username</label>
            <input type="text" name="username" class="form-control" ng-model="user.username" ng-minlength="3" ng-maxlength="8" required>
            <p ng-show="form.userForm.username.$error.minlength" class="help-block">Username is too short.</p>
            <p ng-show="form.userForm.username.$error.maxlength" class="help-block">Username is too long.</p>
        </div>

        <!-- EMAIL -->
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" class="form-control" ng-model="email" required>
            <p ng-show="form.userForm.email.$invalid && !form.userForm.email.$pristine" class="help-block">Enter a valid email.</p>
        </div>

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-primary" ng-disabled="form.userForm.$invalid">OK</button>
        <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
    </div>
</form>