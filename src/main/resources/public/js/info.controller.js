(function() {

    angular.module('inpacker')
           .controller('InfoController', InfoController);

    InfoController.$inject = ['$http', '$scope'];

    function InfoController($http, $scope) {
        var vm = this;
        var ac = $scope.ac;

        vm.user = ac.user;

        vm.showSearch = showSearch;

        vm.next = next;

        activate();

        function activate() {

        }

        function showSearch() {
            ac.showSearch();
        }

        function next() {
            showSettings();
        }

        function showSettings() {
            ac.showSettings();
        }

    }

})()