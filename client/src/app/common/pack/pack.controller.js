export default PackController;

PackController.$inject = ['$interval', '$scope', '$routeParams', 'location', 'api', 'pack', 'UPDATE_PACK_STATUS_INTERVAL']
function PackController($interval, $scope, $routeParams, location, api, pack, updatePackInterval) {
    if (pack == null) {
        location.openSearch(null, $routeParams.packId);
        return;
    }
    const vm = this;
    const PACK_ID = $routeParams.packId;
    var interval;

    activate();

    function activate() {
        vm.pack = pack;
        interval = $interval(() => vm.updatePack(), updatePackInterval);
        $scope.$on('$destroy', () => $interval.cancel(interval));
    }

    vm.updatePack = () => api.getPack(PACK_ID)
                            .then(pack => {
                                vm.pack = pack;
                                if (pack.is_done) vm.done();
                            });

    vm.done = () => $interval.cancel(interval);
}
