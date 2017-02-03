(function() {

    angular.module('inpacker')
           .controller('PackController', PackController);

    PackController.$inject = ['$scope', '$http', '$interval'];

    function PackController($scope, $http, $interval) {
        var vm = this;
        var ac = $scope.ac;

        var timer;

        vm.user = ac.user;

        activate();

        function activate() {
            ac.showCogIcon();
            ping();
        }

        function ping() {
            timer = $interval(() => getPackStatus(), 3000);
        }

        function getPackStatus() {
            if (ac.pack.packName === '') return;
            $http.get('/api/pack/' + ac.pack.packName + '/status')
                .then((resp) => {
                    ac.pack.packStatus = resp.data.packStatus;
                    if (ac.pack.packStatus) {
                       packIsDone();
                    }
                }, (resp) => {})
        }

        function packIsDone() {
            $interval.cancel(timer);
            ac.showCheckIcon();
        }
    }

})()