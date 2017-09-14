export default IgQueryController;

IgQueryController.$inject = ['location', 'api'];
function IgQueryController(location, api) {
    const vm = this;

    vm.go = () => {
        if (!vm.isValidInput()) return;
        vm.waitingResp = true;
        location.openPackConf(vm.input);
    }

    vm.isValidInput = () => vm.input && vm.input !== '';
}
