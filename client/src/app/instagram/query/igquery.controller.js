export default IgQueryController;

IgQueryController.$inject = ['$routeParams', 'location', 'api'];
function IgQueryController($routeParams, location, api) {
    const vm = this;

    activate();

    function activate() {
        vm.notFoundAlertMessage = '';
        if ($routeParams.unf != null)
            vm.notFoundAlertMessage = 'User ' + $routeParams.unf + ' not found';
        else if ($routeParams.pnf != null)
            vm.notFoundAlertMessage = 'Pack ' + $routeParams.pnf + ' not found';
    }

    vm.go = () => {
        if (!vm.isValidInput()) return;
        vm.waitingResp = true;
        location.openPackConf(vm.input);
    }

    vm.isValidInput = () => vm.input && vm.input !== '';

    vm.closeNotFoundAlert = () => {
        vm.notFoundAlertMessage = '';
        location.openSearch();
    }
}
