(function() {

    angular.module('inpacker')
           .controller('InfoController', InfoController);

    InfoController.$inject = ['$http', '$scope'];

    function InfoController($http, $scope) {
        var vm = this;
        var ac = $scope.ac;

        vm.user = ac.user;

        vm.showSearch = showSearch;
        vm.createZip = createZip;

        activate();

        function activate() {

        }

        function showSearch() {
            ac.showSearch();
        }

        function createZip() {
            ac.showPack();
            $http.post('/api/pack/' + vm.user.username)
                .then((resp) => {
                    ac.packUrl = resp.data.message;
                }, (resp) => {});
        }

    }

})()