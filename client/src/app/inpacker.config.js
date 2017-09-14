config.$inject = ['$locationProvider', '$routeProvider']
export default function config($locationProvider, $routeProvider) {
    $locationProvider.html5Mode(true)
    $routeProvider
        .when('/', {
            template: require('./flow/query/query.html'),
            controller: 'QueryController as vm'
        })
        .when('/@:username', {
            template: require('./flow/config/config.html'),
            controller: 'ConfigController as vm'
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
