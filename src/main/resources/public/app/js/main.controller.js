(function() {

    angular.module('inpacker', []);

    angular.module('inpacker')
           .controller('MainController', MainController);

    MainController.$inject = ['$http'];

    function MainController($http) {
        var vm = this;

        vm.createZip = function() {
            $http.post('/api/create/' + 'firstZipEver')
                .then((resp) => {
                    console.log(resp.status);
                }, (resp) => {
                    console.log(resp.status);
                })
        }

    }

})()