config.$inject = ['$locationProvider', '$routeProvider'];
export default function config($locationProvider, $routeProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider
        .when('/', {
            template: require('./instagram/search/search.html'),
            controller: 'IgSearchController as vm'
        })
        .when('/@:username', {
            template: require('./instagram/pack_conf/pack_conf.html'),
            controller: 'PackConfController as vm',
            resolve: {
                user: resolveIgUser
            }
        })
        .when('/p/:packId', {
            template: require('./common/pack/pack.html'),
            controller: 'PackController as vm',
            resolve: {
                pack: resolvePack
            }
        })
        .when('/about', {
            template: require('./templates/about.html')
        })
        .otherwise({
            template: require('./templates/404.html')
        });
}

resolveIgUser.$inject = ['$route', 'api', 'location'];
function resolveIgUser($route, api, location) {
    let username = $route.current.params.username;
    return api.getIgUser(username).then(user => {
        if (user == null) location.openSearch(username);
        return user;
    });
}

resolvePack.$inject = ['$route', 'api', 'location'];
function resolvePack($route, api, location) {
    let packId = $route.current.params.packId;
    return api.getPack(packId).then(pack => {
        if (pack == null) location.openSearch(null, packId);
        return pack;
    });
}
