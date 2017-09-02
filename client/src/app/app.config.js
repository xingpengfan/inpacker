config.$inject = ['$locationProvider', '$routeProvider'];
export default function config($locationProvider, $routeProvider) {
    $locationProvider.html5Mode(true);
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

instagramUser.$inject = ['$route', 'ig', 'appLocation'];
function instagramUser($route, ig, appLocation) {
    let username = $route.current.params.username;
    return ig.getUser(username).then(user => {
        if (user == null) appLocation.openSearch(username);
        return user;
    });
}

instagramPack.$inject = ['$route', 'ig', 'appLocation'];
function instagramPack($route, ig, appLocation) {
    let packId = $route.current.params.packId;
    return ig.getPack(packId).then(pack => {
        if (pack == null) appLocation.openSearch(null, packId);
        return pack;
    });
}
