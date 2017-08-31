export default class PackStatusController {
    constructor($interval, $scope, $routeParams, ig, icon, pack, CHECK_STATUS_INTERVAL) {
        if (pack === null) return;
        this.pack = pack;

        this.$interval = $interval;
        this.$routeParams = $routeParams;
        this.ig = ig;
        this.icon = icon;
        this.checkStatusInterval = CHECK_STATUS_INTERVAL;

        this.iconClass = this.pack.is_done ? icon.packIsDone() : icon.creatingPack();
        this.timer = $interval(() => this.updatePack(), this.checkStatusInterval);
        $scope.$on("$destroy", () => $interval.cancel(this.timer));
    }

    updatePack() {
        this.ig.getPack(this.$routeParams.packId).then(pack => {
            this.pack = pack;
            if (pack.is_done) this.done();
        });
    }

    done() {
        this.$interval.cancel(this.timer);
        this.iconClass = this.icon.packIsDone();
    }
}

PackStatusController.$inject = ['$interval', '$scope', '$routeParams', 'ig', 'icon', 'pack', 'CHECK_STATUS_INTERVAL'];
