(function() {

    angular.module('inpacker').controller('InstagramSearchController', InstagramSearchController);

    InstagramSearchController.$inject = ['$http', '$routeParams', 'locationService'];

    function InstagramSearchController($http, $routeParams, locationService) {
        var vm = this;

        vm.search = search;
        vm.showNotFound = showNotFound;
        vm.closeUserNotFoundAlert = closeUserNotFoundAlert;
        vm.searching = false;

        activate();

        function activate() {
            vm.userNotFoundMessage = 'User ' + $routeParams.nf + ' not found';
        }

        function search() {
            if (!isValidInput())
                return;
            vm.searching = true;
            locationService.openPackConf(vm.input);
        }

        function isValidInput() {
            return vm.input && vm.input !== '';
        }

        function showNotFound() {
            return $routeParams.nf != null;
        }

        function closeUserNotFoundAlert() {
            locationService.openSearch();
        }

    }

})();
