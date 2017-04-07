(function() {
    'use strict';

    angular.module('inpacker').config(config);

    config.$inject = ['$locationProvider', '$routeProvider'];

    function config($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('');
        $routeProvider
            .when('/', {
                templateUrl: 'search/search.html',
                controller: 'InstagramSearchController as vm'
            })
            .when('/@:username', {
                templateUrl: 'pack_conf/pack_conf.html',
                controller: 'PackConfController as vm',
                resolve: {
                    user: instagramUser
                }
            })
            .when('/packs/:packId', {
                templateUrl: 'pack_status/pack_status.html',
                controller: 'PackStatusController as vm',
                resolve: {
                    pack: instagramPack
                }
            })
            .otherwise('/');
    }

    instagramUser.$inject = ['$route', 'instagramService', 'locationService']

    function instagramUser($route, instagramService, locationService) {
        let username = $route.current.params.username;
        return instagramService.getUser(username)
            .then((user) => {
                if (user == null) locationService.openSearch(username);
                return user;
            });
    }

    instagramPack.$inject = ['$route', 'instagramService', 'locationService'];

    function instagramPack($route, instagramService, locationService) {
        let packId = $route.current.params.packId;
        return instagramService.getPack(packId)
            .then((pack) => {
                if (pack == null) locationService.openSearch(null, packId);
                return pack;
            });
    }

})();
