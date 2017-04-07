(function() {
    'use strict';

    angular.module('inpacker').factory('locationService', locationService);

    locationService.$inject = ['$location'];

    function locationService($location) {
        var service = {
            openSearch: openSearch,
            openPackConf: openPackConf,
            openPack: openPack
        };
        return service;

        function openSearch(userNotFound, packNotFound) {
            if (userNotFound) $location.search('unf', userNotFound);
            else $location.search('unf', null);
            if (packNotFound) $location.search('pnf', packNotFound);
            else $location.search('pnf', null);
            $location.path('/');
        }

        function openPackConf(username) {
            $location.url($location.path());
            $location.path('/@' + username);
        }

        function openPack(packId) {
            $location.path('/p/' + packId);
        }
    }

})();
