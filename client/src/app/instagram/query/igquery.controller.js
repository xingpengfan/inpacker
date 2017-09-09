export default IgQueryController;

IgQueryController.$inject = ['$routeParams', 'location'];
function IgQueryController($routeParams, location) {
    const vm = this;

    activate();

    function activate() {
        vm.notFoundAlertMessage = '';
        if ($routeParams.unf != null)
            vm.notFoundAlertMessage = 'User ' + $routeParams.unf + ' not found';
        else if ($routeParams.pnf != null)
            vm.notFoundAlertMessage = 'Pack ' + $routeParams.pnf + ' not found';
    }

    vm.search = () => {
        if (!vm.isValidInput()) return;
        vm.searching = true;
        location.openPackConf(vm.input);
    }

    vm.isValidInput = () => vm.input && vm.input !== '';

    vm.closeNotFoundAlert = () => {
        vm.notFoundAlertMessage = '';
        location.openSearch();
    }
}
