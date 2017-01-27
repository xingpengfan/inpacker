(function() {

    angular.module('inpacker', []);

    angular.module('inpacker')
           .controller('MainController', MainController);

    MainController.$inject = ['$http'];

    function MainController($http) {
        var vm = this;

    }

})()