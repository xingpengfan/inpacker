(function() {

    angular.module('inpacker')
           .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['$scope', '$http'];

    function SettingsController($scope, $http) {
        var vm = this;
        var ac = $scope.ac;

        vm.pack = pack;
        vm.settings = {
            includeImages: true,
            includeVideos: true,
            fileNamePattern: 'id'
        }

        activate();

        function activate() {
            vm.userPicUrl = ac.user.profilePic;
            ac.showCogsIcon();
        }

        function pack() {
            ac.showPack();
            $http.post('/api/pack/' + ac.user.username, vm.settings)
                .then((resp) => {
                    ac.pack = resp.data;
                }, (resp) => {});
        }

    }

})()