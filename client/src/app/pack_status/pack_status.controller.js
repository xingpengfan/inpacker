export default class PackStatusController {
    constructor($interval, $scope, $routeParams, ig, pack, CHECK_STATUS_INTERVAL) {
        if (pack === null) return;
        this.pack = pack;

        this.$interval = $interval;
        this.$routeParams = $routeParams;
        this.ig = ig;
        this.checkStatusInterval = CHECK_STATUS_INTERVAL;

        this.interval = $interval(() => this.updatePack(), this.checkStatusInterval);
        $scope.$on('$destroy', () => $interval.cancel(this.interval));
    }

    updatePack() {
        this.ig.getPack(this.$routeParams.packId).then(pack => {
            this.pack = pack;
            if (pack.is_done) this.done();
        });
    }

    done() {
        this.$interval.cancel(this.interval);
    }
}

PackStatusController.$inject = ['$interval', '$scope', '$routeParams', 'ig', 'pack', 'CHECK_STATUS_INTERVAL'];
