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

        function openSearch(userNotFound) {
            if (userNotFound) $location.search('nf', userNotFound);
            else $location.search('nf', null);
            $location.path('/');
        }

        function openPackConf(username) {
            $location.search('nf', null);
            $location.path('/@' + username);
        }

        function openPack(packId) {
            $location.path('/packs/' + packId);
        }
    }

})();
