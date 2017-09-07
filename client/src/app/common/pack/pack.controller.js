export default class PackController {
    constructor($interval, $scope, $routeParams, location, api, pack, updatePack) {
        if (pack == null) {
            location.openSearch(null, $routeParams.packId);
            return;
        }
        this.pack = pack;

        this.$interval = $interval;
        this.$routeParams = $routeParams;
        this.api = api;
        this.updatePackInterval = updatePack;

        this.interval = $interval(() => this.updatePack(), this.updatePackInterval);
        $scope.$on('$destroy', () => $interval.cancel(this.interval));
    }

    updatePack() {
        this.api.getPack(this.$routeParams.packId).then(pack => {
            this.pack = pack;
            if (pack.is_done) this.done();
        });
    }

    done() {
        this.$interval.cancel(this.interval);
    }
}

PackController.$inject = ['$interval', '$scope', '$routeParams', 'location', 'api', 'pack', 'UPDATE_PACK_STATUS_INTERVAL'];
