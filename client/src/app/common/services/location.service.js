export default LocationService

LocationService.$inject = ['$location']
function LocationService($location) {
    return {
        query,
        pxQuery,
        config,
        igConfig,
        pxConfig,
        pack
    }

    function query() {
        $location.path('/')
    }

    function pxQuery() {
        $location.path('/px')
    }

    function config(username) {
        if ($location.path() === '/px')
            pxConfig(username)
        else
            igConfig(username)
    }

    function igConfig(username) {
        $location.path('/ig/@' + username)
    }

    function pxConfig(username) {
        $location.path('/px/@' + username)
    }

    function pack(packId) {
        $location.path('/p/' + packId)
    }
}
