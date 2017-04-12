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
            .when('/p/:packId', {
                templateUrl: 'pack_status/pack_status.html',
                controller: 'PackStatusController as vm',
                resolve: {
                    pack: instagramPack
                }
            })
            .when('/about', {
                templateUrl: 'templates/about.html'
            })
            .otherwise({
                templateUrl: 'templates/404.html'
            });
    }

    instagramUser.$inject = ['$route', 'ig', 'locationService']

    function instagramUser($route, ig, locationService) {
        let username = $route.current.params.username;
        return ig.getUser(username)
            .then((user) => {
                if (user == null) locationService.openSearch(username);
                return user;
            });
    }

    instagramPack.$inject = ['$route', 'ig', 'locationService'];

    function instagramPack($route, ig, locationService) {
        let packId = $route.current.params.packId;
        return ig.getPack(packId)
            .then((pack) => {
                if (pack == null) locationService.openSearch(null, packId);
                return pack;
            });
    }

})();
