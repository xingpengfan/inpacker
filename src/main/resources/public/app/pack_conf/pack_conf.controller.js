(function() {

    angular.module('inpacker').controller('PackConfController', PackConfController);

    PackConfController.$inject = ['$scope', '$http', '$routeParams', 'instagramService', 'locationService', 'user'];

    function PackConfController($scope, $http, $routeParams, instagramService, locationService, user) {
        var vm = this;
        var ac = $scope.ac;

        vm.user = user;
        vm.createPackClick = createPackClick;
        vm.searchAnotherUser = searchAnotherUser;
        vm.shortenedUsername = shortenedUsername;
        vm.settings = {
            username: user.username,
            includeImages: true,
            includeVideos: true,
            fileNamePattern: 'index'
        };
        vm.preview = preview;

        activate();

        function activate() {
            vm.user.instagramPageLink = 'https://www.instagram.com/' + vm.user.username + '/';
            if (vm.user.isPrivate) vm.iconClass = 'fa fa-lg fa-user-secret';
            else vm.iconClass = 'fa fa-lg fa-cogs';
        }

        function createPackClick() {
            instagramService.createPack(vm.settings)
                .then((pack) => {
                    if (pack != null) locationService.openPack(pack.id);
                });
        }

        function searchAnotherUser() {
            locationService.openSearch();
        }

        function shortenedUsername() {
            if (vm.user.username.length > 18)
                return vm.user.username.substring(0, 18) + '..';
            else
                return vm.user.username;
        }

        function preview() {
            let p = '';
            if (vm.settings.includeImages)
                if (vm.settings.fileNamePattern === 'id')
                    p += '1756...364.jpg, ';
                else if (vm.settings.fileNamePattern === 'index')
                    p += '1.jpg, ';
                else if (vm.settings.fileNamePattern === 'date')
                    p += '2017-02-25T15:36:59Z.jpg, ';
            if (vm.settings.includeVideos)
                if (vm.settings.fileNamePattern === 'id')
                    p += '4606...591.mp4';
                else if (vm.settings.fileNamePattern === 'index')
                    p += '2.mp4';
                else if (vm.settings.fileNamePattern === 'date')
                    p += '2016-05-10T14:24:20Z.mp4';
            return p + ' ...';
        }

    }

})();
