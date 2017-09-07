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

resolveIgUser.$inject = ['$route', 'api'];
function resolveIgUser($route, api) {
    return api.getIgUser($route.current.params.username).then(user => user);
}

resolvePack.$inject = ['$route', 'api'];
function resolvePack($route, api) {
    return api.getPack($route.current.params.packId).then(pack => pack);
}
