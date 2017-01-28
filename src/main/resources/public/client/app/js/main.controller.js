(function() {

    angular.module('inpacker', []);

    angular.module('inpacker')
           .controller('MainController', MainController);

    MainController.$inject = ['$http'];

    function MainController($http) {
        var vm = this;

        vm.search = search;

        function search() {
            if (!isValidSearchInput()) return;
            alert(vm.searchInput);
        }

        function isValidSearchInput() {
            return vm.searchInput && vm.searchInput !== '';
        }

    }

})()