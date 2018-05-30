App2.controller("testModalCtrl", ['$scope', 'dialog', 'dataForTheModal', 'otherDataForTheModal',
             function testModalCtrl($scope, dialog, dataForTheModal, otherDataForTheModal) {
    $scope.someData = dataForTheModal;
    $scope.someOtherData = otherDataForTheModal;

    $scope.someActionOnTheModal = function () {
        //do whatever work here, then close the modal
        $scope.dataResultFromTheModal = 'something that was updated' 
        dialog.close(dataResultFromTheModal); // close this modal
    };
    
    
    $scope.openModal = function () {
        var d = $dialog.dialog({
            templateUrl: 'modalTemplate.jsp',
            controller: 'testModalCtrl',
            resolve: {
                dataForTheModal: function () {
                    return 'this is my data';
                },
                otherDataForTheModal: function () {
                    return 'this is my other data';
                }
                //pass as many parameters as you need to the modal                    
            }
        });
        d.open()
            .then(function (dataResultFromTheModal) {
                if (dataResultFromTheModal) {
                    $scope.something = dataResultFromTheModal     
                    /*when the modal is closed, $scope.something will be updated with                     
                    the data that was updated in the modal (if you need this behavior) */
                }
            });
    };
}]);