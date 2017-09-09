export default LocationService;

LocationService.$inject = ['$location'];
function LocationService($location) {
    return {
        openSearch,
        openPackConf,
        openPack
    }

    function openSearch(userNotFound, packNotFound) {
        if (userNotFound)
            $location.search('unf', userNotFound);
        else
            $location.search('unf', null);
        if (packNotFound)
            $location.search('pnf', packNotFound);
        else
            $location.search('pnf', null);
        $location.path('/');
    }

    function openPackConf(username) {
        $location.url($location.path());
        $location.path('/@' + username);
    }

    function openPack(packId) {
        $location.path('/p/' + packId);
    }
}
