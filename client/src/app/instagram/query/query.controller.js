export default QueryController

QueryController.$inject = ['location']
function QueryController(location) {
    const vm = this

    vm.go = () => {
        if (!vm.isValidInput()) return
        location.openPackConf(vm.input)
    }

    vm.isValidInput = () => vm.input && vm.input !== ''
}
