export default LocationService;

LocationService.$inject = ['$location'];
function LocationService($location) {
    return {
        openSearch,
        openPackConf,
        openPack
    }

    function openSearch() {
        $location.path('/');
    }

    function openPackConf(username) {
        $location.path('/@' + username);
    }

    function openPack(packId) {
        $location.path('/p/' + packId);
    }
}
