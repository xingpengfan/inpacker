(function() {
    'use strict';

    angular.module('inpacker', []);

    angular.module('inpacker').constant('CHECK_STATUS_INTERVAL', 3000);

    angular.module('inpacker')
           .controller('AppController', AppController);

    function AppController() {
        var vm = this;

        vm.user = null;
        vm.view = 'search';
        vm.showSearch = showSearch;
        vm.showSettings = showSettings;
        vm.showPack = showPack;
        vm.showInstagramIcon = showInstagramIcon;
        vm.showCogIcon = showCogIcon;
        vm.showCheckIcon = showCheckIcon;
        vm.showUserIcon = showUserIcon;
        vm.showSecretIcon = showSecretIcon;
        vm.showCogsIcon = showCogsIcon;

        vm.pack = {
            name: '',
            ready: false,
            amount: 0
        };

        activate();

        function activate() {
            showSearch();
        }

        // views
        function showSearch() {
            vm.view = 'search';
        }

        function showSettings() {
            vm.view = 'settings';
        }

        function showPack() {
            vm.view = 'pack';
        }

        // main icon
        function showInstagramIcon() {
            vm.mainIconClass = 'fa fa-lg fa-instagram';
        }

        function showCogIcon() {
            vm.mainIconClass = 'fa fa-lg fa-cog fa-spin fa-fw';
        }

        function showCheckIcon() {
            vm.mainIconClass = 'fa fa-lg fa-check';
        }

        function showUserIcon() {
            vm.mainIconClass = 'fa fa-lg fa-user-circle-o';
        }

        function showSecretIcon() {
            vm.mainIconClass = 'fa fa-lg fa-user-secret';
        }

        function showCogsIcon() {
            vm.mainIconClass = 'fa fa-lg fa-cogs';
        }

    }

})();

(function() {

    angular.module('inpacker')
           .controller('SearchController', SearchController);

    SearchController.$inject = ['$http', '$scope'];

    function SearchController($http, $scope) {
        var vm = this;
        var ac = $scope.ac;

        vm.search = search;
        vm.showNotFound = showNotFound;
        vm.closeUserNotFoundAlert = closeUserNotFoundAlert;
        vm.searching = false;

        activate();

        function activate() {
            ac.showInstagramIcon();
        }

        // stuff

        function search() {
            if (!isValidInput()) return;
            closeUserNotFoundAlert();
            getUser();
        }

        function isValidInput() {
            return vm.input && vm.input !== '';
        }

        function showUserNotFound() {
            vm.userNotFoundMessage = 'User ' + vm.input + ' not found';
        }

        function showNotFound() {
            return vm.userNotFoundMessage && vm.userNotFoundMessage !== '';
        }

        function closeUserNotFoundAlert() {
            vm.userNotFoundMessage = '';
        }

        function getUser() {
            vm.searching = true;
            $http.get('/api/user/' + vm.input)
                .then((resp) => {
                    let user = resp.data;
                    user.instagramPageLink = 'https://www.instagram.com/' + user.username + '/';
                    vm.searching = false;
                    ac.user = user;
                    ac.showSettings();
                }, (resp) => {
                    vm.searching = false;
                    showUserNotFound();
                });
        }

    }

})();

(function() {

    angular.module('inpacker')
           .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['$scope', '$http'];

    function SettingsController($scope, $http) {
        var vm = this;
        var ac = $scope.ac;

        vm.pack = pack;
        vm.searchAnotherUser = searchAnotherUser;
        vm.shortenedUsername = shortenedUsername;
        vm.settings = {
            includeImages: true,
            includeVideos: true,
            fileNamePattern: 'index'
        };
        vm.preview = preview;

        activate();

        function activate() {
            vm.userPicUrl = ac.user.profilePic;
            if (ac.user.isPrivate)
                ac.showSecretIcon();
            else
                ac.showCogsIcon();
        }

        function pack() {
            ac.showPack();
            $http.post('/api/pack/' + ac.user.username, vm.settings)
                .then((resp) => {
                    ac.pack = resp.data;
                }, (resp) => {});
        }

        function searchAnotherUser() {
            ac.showSearch();
        }

        function shortenedUsername() {
            if (ac.user.username.length > 22)
                return ac.user.username.substring(0, 22) + '..';
            else
                return ac.user.username;
        }

        function preview() {
            let p = '';
            if (vm.settings.includeImages)
                if (vm.settings.fileNamePattern === 'id')
                    p += '1756...364.jpg, ';
                else if (vm.settings.fileNamePattern === 'index')
                    p += '1.jpg, ';
            if (vm.settings.includeVideos)
                if (vm.settings.fileNamePattern === 'id')
                    p += '4606...591.mp4';
                else if (vm.settings.fileNamePattern === 'index')
                    p += '2.mp4';
            return p;
        }

    }

})();

(function() {

    angular.module('inpacker')
           .controller('PackController', PackController);

    PackController.$inject = ['$scope', '$http', '$interval', 'CHECK_STATUS_INTERVAL'];

    function PackController($scope, $http, $interval, checkStatusInterval) {
        var vm = this;
        var ac = $scope.ac;

        var timer;

        vm.user = ac.user;

        activate();

        function activate() {
            if (ac.pack.ready)
                ac.showCheckIcon();
            else
                ac.showCogIcon();
            timer = $interval(() => getPackStatus(), checkStatusInterval);
        }

        function getPackStatus() {
            if (ac.pack.name === '') return;
            $http.get('/api/pack/' + ac.pack.name + '/status')
                .then((resp) => {
                    ac.pack = resp.data;
                    if (ac.pack.ready) {
                       done();
                    }
                }, (resp) => {})
        }

        function done() {
            $interval.cancel(timer);
            ac.showCheckIcon();
        }
    }

})();
