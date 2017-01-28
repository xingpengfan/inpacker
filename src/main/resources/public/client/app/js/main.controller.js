(function() {

    angular.module('inpacker', []);

    angular.module('inpacker')
           .controller('MainController', MainController);

    MainController.$inject = ['$http'];

    function MainController($http) {
        var vm = this;

        vm.search = search;
        vm.show = {
            userSearch: true,
            userInfo: false
        }
        vm.showNotFound = showNotFound;
        vm.closeUserNotFoundMessage = closeUserNotFoundMessage;

        function search() {
            if (!isValidSearchInput()) return;
            getUserInfo();
        }

        function getUserInfo() {
            $http.get('http://localhost:8080/api/user/' + vm.searchInput)
                .then((resp) => {
                    showUserInfo();
                    vm.userInfo = resp.data;
                }, (resp) => {
                    showUserNotFound();
                });
        }

        function isValidSearchInput() {
            return vm.searchInput && vm.searchInput !== '';
        }

        function showUserSearch() {
            vm.show.userSearch = true;
            vm.show.userInfo = false;
        }

        function showUserInfo() {
            vm.show.userSearch = false;
            vm.show.userInfo = true;
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

    }

})()