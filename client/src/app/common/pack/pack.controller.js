export default PackController

PackController.$inject = ['$interval', '$scope', '$routeParams', 'api', 'UPDATE_PACK_STATUS_INTERVAL']
function PackController($interval, $scope, $routeParams, api, updatePackInterval) {
    const vm = this
    const PACK_ID = $routeParams.packId
    var interval
    vm.packNotFound = false
    vm.loading = true
    
    api.getPack(PACK_ID).then(pack => {
        vm.loading = false
        activate(pack)
    })
    
    function activate(pack) {
        if (pack == null) {
            vm.packNotFound = true
            return
        }
        vm.pack = pack
        interval = $interval(() => vm.updatePack(), updatePackInterval)
        $scope.$on('$destroy', () => $interval.cancel(interval))
    }

    vm.updatePack = () => api.getPack(PACK_ID)
                            .then(pack => {
                                vm.pack = pack
                                if (pack.is_done) vm.done()
                            })

    vm.done = () => $interval.cancel(interval)
}
