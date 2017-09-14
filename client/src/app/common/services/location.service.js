export default LocationService

LocationService.$inject = ['$location']
function LocationService($location) {
    return {
        query,
        config,
        pack
    }

    function query() {
        $location.path('/')
    }

    function config(username) {
        $location.path('/@' + username)
    }

    function pack(packId) {
        $location.path('/p/' + packId)
    }
}
