(function() {

    angular.module('inpacker')
           .controller('SearchController', SearchController);

    SearchController.$inject = ['$http', '$scope'];

    function SearchController($http, $scope) {
        var vm = this;
        var ac = $scope.ac;

        vm.search = search;
        vm.showNotFound = showNotFound;
        vm.closeUserNotFoundMessage = closeUserNotFoundMessage;
        vm.searching = false;

        activate();

        function activate() {
            ac.showInstagramIcon();
        }

        // stuff

        function search() {
            if (!isValidSearchInput()) return;
            closeUserNotFoundMessage();
            getUser();
        }

        function isValidSearchInput() {
            return vm.searchInput && vm.searchInput !== '';
        }

        function showUserNotFound() {
            vm.userNotFoundMessage = 'User ' + vm.searchInput + ' not found';
        }

        function showNotFound() {
            return vm.userNotFoundMessage && vm.userNotFoundMessage !== '';
        }

        function closeUserNotFoundMessage() {
            vm.userNotFoundMessage = '';
        }

        function getUser() {
            vm.searching = true;
            $http.get('/api/user/' + vm.searchInput)
                .then((resp) => {
                    let user = resp.data;
                    user.instagramPageLink = 'https://www.instagram.com/' + user.username + '/';
                    vm.searching = false;
                    ac.user = user;
                    ac.showInfo();
                }, (resp) => {
                    vm.searching = false;
                    showUserNotFound();
                });
        }

    }

})()