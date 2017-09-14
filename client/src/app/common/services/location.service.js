export default LocationService;

LocationService.$inject = ['$location'];
function LocationService($location) {
    return {
        openQuery,
        openPackConf,
        openPack
    }

    function openQuery() {
        $location.path('/');
    }

    function openPackConf(username) {
        $location.path('/@' + username);
    }

    function openPack(packId) {
        $location.path('/p/' + packId);
    }
}
