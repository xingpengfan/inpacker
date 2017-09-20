config.$inject = ['$locationProvider', '$routeProvider']
export default function config($locationProvider, $routeProvider) {
    $locationProvider.html5Mode(true)
    const query = {
        template: require('./flow/query/query.html'),
        controller: 'QueryController as vm'
    }
    $routeProvider
        .when('/', query)
        .when('/ig', query)
        .when('/px', query)
        .when('/ig/@:username', {
            template: require('./flow/config/igconfig.html'),
            controller: 'IgConfigController as vm'
        })
        .when('/px/@:username', {
            template: require('./flow/config/pxconfig.html'),
            controller: 'PxConfigController as vm'
        })
        .when('/p/:packId', {
            template: require('./flow/pack/pack.html'),
            controller: 'PackController as vm'
        })
        .when('/about', {
            template: require('./templates/about.html')
        })
        .otherwise({
            template: require('./templates/404.html')
        })
}
