(function() {

    angular.module('inpacker', []);

    angular.module('inpacker')
           .controller('AppController', AppController);

    AppController.$inject = [];

    function AppController() {
        var vm = this;

        vm.user = null;
        vm.show = {
            search: false,
            info: false,
            pack: false
        };
        vm.showSearch = showSearch;
        vm.showInfo = showInfo;
        vm.showPack = showPack;
        vm.showInstagramIcon = showInstagramIcon;
        vm.showCogIcon = showCogIcon;
        vm.showCheckIcon = showCheckIcon;
        vm.showSecretIcon = showSecretIcon;

        vm.packUrl = '';

        activate();

        function activate() {
            showSearch();
        }

        // views
        function showSearch() {
            setShow(true, false, false);
        }

        function showInfo() {
            setShow(false, true, false);
        }

        function showPack() {
            setShow(false, false, true);
        }

        function setShow(v1, v2, v3) {
            vm.show.search = v1;
            vm.show.info = v2;
            vm.show.pack = v3;
        }

        // main icon
        function showInstagramIcon() {
            vm.mainIconClass = 'fa fa-lg fa-instagram';
        }

        function showCogIcon() {
            vm.mainIconClass = 'fa fa-lg fa-cog fa-spin fa-fw';
        }

        function showCheckIcon() {
            vm.mainIconClass = 'fa fa-lg fa-check green-check';
        }

        function showSecretIcon() {
            vm.mainIconClass = 'fa fa-lg fa-user-secret';
        }

    }

})()