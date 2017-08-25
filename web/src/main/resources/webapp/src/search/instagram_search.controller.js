(function() {
    angular.module('inpacker').controller('InstagramSearchController', InstagramSearchController);

    InstagramSearchController.$inject = ['$http', '$routeParams', 'locationService', 'icon'];

    function InstagramSearchController($http, $routeParams, locationService, icon) {
        var vm = this;

        vm.search = search;
        vm.showUserNotFound = showUserNotFound;
        vm.showPackNotFound = showPackNotFound;
        vm.closeUserNotFoundAlert = closeUserNotFoundAlert;
        vm.closePackNotFoundAlert = closePackNotFoundAlert;
        vm.searching = false;

        activate();

        function activate() {
            vm.iconClass = icon.defaultIcon();
            vm.userNotFoundMessage = 'User ' + $routeParams.unf + ' not found';
            vm.packNotFoundMessage = 'Pack ' + $routeParams.pnf + ' not found';
        }

        function search() {
            if (!isValidInput()) return;
            vm.searching = true;
            locationService.openPackConf(vm.input);
        }

        function isValidInput() {
            return vm.input && vm.input !== '';
        }

        function showUserNotFound() {
            return $routeParams.unf != null;
        }

        function showPackNotFound() {
            return $routeParams.pnf != null;
        }

        function closeUserNotFoundAlert() {
            locationService.openSearch();
        }

        function closePackNotFoundAlert() {
            locationService.openSearch();
        }
    }
})();
