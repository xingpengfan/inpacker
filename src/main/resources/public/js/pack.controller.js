(function() {

    angular.module('inpacker')
           .controller('PackController', PackController);

    PackController.$inject = ['$scope'];

    function PackController($scope) {
        var vm = this;
        var ac = $scope.ac;

        vm.user = ac.user;

        activate();

        function activate() {

        }
    }

})()