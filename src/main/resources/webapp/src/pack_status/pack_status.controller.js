(function() {

    angular.module('inpacker').controller('PackStatusController', PackStatusController);

    PackStatusController.$inject = ['$interval', '$routeParams', 'instagramService', 'pack', 'CHECK_STATUS_INTERVAL'];

    function PackStatusController($interval, $routeParams, instagramService, pack, checkStatusInterval) {
        if (pack == null) return;
        let vm = this;
        let timer;

        vm.pack = pack;

        activate();

        function activate() {
            if (vm.pack.is_done) showCheckIcon();
            else showCogIcon();

            timer = $interval(() => updatePack(), checkStatusInterval);
        }

        function updatePack() {
            instagramService.getPack($routeParams.packId)
                .then((pack) => {
                    vm.pack = pack;
                    if (pack.is_done) done();
                })
        }

        function done() {
            $interval.cancel(timer);
            showCheckIcon();
        }

        function showCogIcon() {
            vm.iconClass = 'fa fa-lg fa-cog fa-spin fa-fw';
        }

        function showCheckIcon() {
            vm.iconClass = 'fa fa-lg fa-check';
        }

    }

})();
