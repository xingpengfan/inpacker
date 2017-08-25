(function() {
    angular.module('inpacker').controller('PackStatusController', PackStatusController);

    PackStatusController.$inject = ['$interval', '$routeParams', 'ig', 'icon', 'pack', 'CHECK_STATUS_INTERVAL'];

    function PackStatusController($interval, $routeParams, ig, icon, pack, checkStatusInterval) {
        if (pack == null) return;
        let vm = this;
        let timer;

        vm.pack = pack;

        activate();

        function activate() {
            vm.iconClass = vm.pack.is_done ? icon.packIsDone() : icon.creatingPack();

            timer = $interval(() => updatePack(), checkStatusInterval);
        }

        function updatePack() {
            ig.getPack($routeParams.packId).then(pack => {
                vm.pack = pack;
                if (pack.is_done) done();
            });
        }

        function done() {
            $interval.cancel(timer);
            vm.iconClass = icon.packIsDone();
        }
    }
})();
