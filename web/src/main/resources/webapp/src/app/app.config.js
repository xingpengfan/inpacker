config.$inject = ['$locationProvider', '$routeProvider'];
export default function config($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('');
    $routeProvider
        .when('/', {
            template: require('./search/search.html'),
            controller: 'IgSearchController as vm'
        })
        .when('/@:username', {
            template: require('./pack_conf/pack_conf.html'),
            controller: 'PackConfController as vm',
            resolve: {
                user: instagramUser
            }
        })
        .when('/p/:packId', {
            template: require('./pack_status/pack_status.html'),
            controller: 'PackStatusController as vm',
            resolve: {
                pack: instagramPack
            }
        })
        .when('/about', {
            template: require('./templates/about.html')
        })
        .otherwise({
            template: require('./templates/404.html')
        });
}

instagramUser.$inject = ['$route', 'ig', 'locationService'];
function instagramUser($route, ig, locationService) {
    let username = $route.current.params.username;
    return ig.getUser(username).then(user => {
        if (user == null) locationService.openSearch(username);
        return user;
    });
}

instagramPack.$inject = ['$route', 'ig', 'locationService'];
function instagramPack($route, ig, locationService) {
    let packId = $route.current.params.packId;
    return ig.getPack(packId).then(pack => {
        if (pack == null) locationService.openSearch(null, packId);
        return pack;
    });
}
