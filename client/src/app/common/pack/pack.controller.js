export default class PackController {
    constructor($interval, $scope, $routeParams, api, pack, CHECK_STATUS_INTERVAL) {
        if (pack === null) return;
        this.pack = pack;

        this.$interval = $interval;
        this.$routeParams = $routeParams;
        this.api = api;
        this.checkStatusInterval = CHECK_STATUS_INTERVAL;

        this.interval = $interval(() => this.updatePack(), this.checkStatusInterval);
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

PackController.$inject = ['$interval', '$scope', '$routeParams', 'api', 'pack', 'CHECK_STATUS_INTERVAL'];
