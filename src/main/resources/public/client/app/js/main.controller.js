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
            gettingUserInfo: false,
            userNotFound: false,
            userInfo: false
        }

        activate();

        function activate() {}

        function search() {
            if (!isValidSearchInput()) return;
            getUserInfo();
        }

        function getUserInfo() {
            showGettingUserInfo();
            $http.get('http://localhost:8080/api/user/' + vm.searchInput)
                .then((resp) => {
                    showUserInfo();
                }, (resp) => {
                    showUserNotFound();
                });
        }

        function isValidSearchInput() {
            return vm.searchInput && vm.searchInput !== '';
        }

        function showUserSearch() {
            vm.show.userSearch = true;
            vm.show.gettingUserInfo = false;
            vm.show.userNotFound = false;
            vm.show.userInfo = false;
        }

        function showGettingUserInfo() {
            vm.show.userSearch = false;
            vm.show.gettingUserInfo = true;
            vm.show.userNotFound = false;
            vm.show.userInfo = false;
        }

        function showUserNotFound() {
            vm.show.userSearch = false;
            vm.show.gettingUserInfo = false;
            vm.show.userNotFound = true;
            vm.show.userInfo = false;
        }

        function showUserInfo() {
            vm.show.userSearch = false;
            vm.show.gettingUserInfo = false;
            vm.show.userNotFound = false;
            vm.show.userInfo = true;
        }

    }

})()