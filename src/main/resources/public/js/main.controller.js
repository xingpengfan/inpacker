(function() {

    angular.module('inpacker', []);

    angular.module('inpacker')
           .controller('MainController', MainController);

    MainController.$inject = ['$http'];

    function MainController($http) {
        var vm = this;

        vm.search = search;
        vm.showSearch = showSearch;
        vm.showNotFound = showNotFound;
        vm.closeUserNotFoundMessage = closeUserNotFoundMessage;
        vm.createZip = createZip;
        vm.show = {
            search: false,
            info: false,
            pack: false
        };
        vm.searching = false;

        activate();

        function activate() {
            showSearch();
//            vm.user = {
//                username: 'fakeinstagramuser_qwerty',
//                fullName: 'Fake User',
//                isPrivate: false,
//                isVerified: true,
//                count: 123,
//                profilePic: 'https://assets-cdn.github.com/images/modules/site/home-ill-build.png',
//                instagramId: '1273172631723',
//                biography: 'i am a fake Instagram user'
//            };
//            showInfo();
        }

        // controlling views
        function showSearch() {
            vm.show.search = true;
            vm.show.info = false;
            vm.show.pack = false;
            vm.searchInput = '';
            showInstagramIcon();
        }

        function showInfo() {
            vm.show.search = false;
            vm.show.info = true;
            vm.show.pack = false;
            if (vm.user.isPrivate) {
                showSecretIcon();
            } else {
                showInstagramIcon();
            }
        }

        function showPack() {
            vm.show.search = false;
            vm.show.info = false;
            vm.show.pack = true;

            showCogIcon();
            vm.showDownloadUrl = false;
            vm.downloadUrl = '/zip/' + vm.user.username + '.zip';
        }
        // -----------------------

        // controlling search view
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
        // -----------------------------

        // controlling info view
        function getUser() {
            vm.searching = true;
            $http.get('/api/user/' + vm.searchInput)
                .then((resp) => {
                    vm.user = resp.data;
                    vm.user.instagramPageLink = 'https://www.instagram.com/' + vm.user.username + '/';
                    vm.searching = false;
                    showInfo();
                }, (resp) => {
                    vm.searching = false;
                    showUserNotFound();
                });
        }

        function createZip() {
            showPack();
            $http.post('/api/zip/' + vm.user.username)
                .then((resp) => {
                    showDownloadUrl();
                    showCheckIcon();
                }, (resp) => {});
        }

        function showDownloadUrl() {
            vm.showDownloadUrl = true;
        }
        // -----------------------------

        // controlling main icon
        function showInstagramIcon() {
            vm.mainIconClass = 'fa fa-lg fa-instagram';
        }

        function showCogIcon() {
            vm.mainIconClass = 'fa fa-lg fa-cog fa-spin fa-fw';
        }

        function showCheckIcon() {
            vm.mainIconClass = 'fa fa-lg fa-check green-check';
        }

        function showSecretIcon() {
            vm.mainIconClass = 'fa fa-lg fa-user-secret';
        }

        // ---------------------

    }

})()
