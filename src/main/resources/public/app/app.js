(function() {

    angular.module('inpacker', []);

    angular.module('inpacker')
           .controller('AppController', AppController);

    function AppController() {
        var vm = this;

        vm.user = null;
        vm.view = 'search';
        vm.showSearch = showSearch;
        vm.showInfo = showInfo;
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
            status: false
        };

        activate();

        function activate() {
            showSearch();
        }

        // views
        function showSearch() {
            vm.view = 'search';
        }

        function showInfo() {
            vm.view = 'info';
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

})();

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
            if (vm.user.isPrivate) {
                ac.showSecretIcon();
            } else {
                ac.showUserIcon();
            }
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

})();

(function() {

    angular.module('inpacker')
           .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['$scope', '$http'];

    function SettingsController($scope, $http) {
        var vm = this;
        var ac = $scope.ac;

        vm.pack = pack;
        vm.settings = {
            includeImages: true,
            includeVideos: true,
            fileNamePattern: 'index'
        };
        vm.preview = preview;

        activate();

        function activate() {
            vm.userPicUrl = ac.user.profilePic;
            ac.showCogsIcon();
        }

        function pack() {
            ac.showPack();
            $http.post('/api/pack/' + ac.user.username, vm.settings)
                .then((resp) => {
                    ac.pack = resp.data;
                }, (resp) => {});
        }

        function preview() {
            let p = '';
            if (vm.settings.includeImages)
                if (vm.settings.fileNamePattern === 'id')
                    p += '1756364.jpg, ';
                else if (vm.settings.fileNamePattern === 'index')
                    p += '1.jpg, ';
            if (vm.settings.includeVideos)
                if (vm.settings.fileNamePattern === 'id')
                    p += '4606591.mp4';
                else if (vm.settings.fileNamePattern === 'index')
                    p += '2.mp4';
            return p;
        }

    }

})();

(function() {

    angular.module('inpacker')
           .controller('PackController', PackController);

    PackController.$inject = ['$scope', '$http', '$interval'];

    function PackController($scope, $http, $interval) {
        var vm = this;
        var ac = $scope.ac;

        var timer;

        vm.user = ac.user;

        activate();

        function activate() {
            ac.showCogIcon();
            ping();
        }

        function ping() {
            timer = $interval(() => getPackStatus(), 3000);
        }

        function getPackStatus() {
            if (ac.pack.name === '') return;
            $http.get('/api/pack/' + ac.pack.name + '/status')
                .then((resp) => {
                    ac.pack.status = resp.data.status;
                    if (ac.pack.status) {
                       packIsDone();
                    }
                }, (resp) => {})
        }

        function packIsDone() {
            $interval.cancel(timer);
            ac.showCheckIcon();
        }
    }

})();
