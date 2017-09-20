export default QueryController

QueryController.$inject = ['location', '$location']
function QueryController(location, $location) {
    const vm = this

    activate()

    function activate() {
        vm.px = vm.ig = false
        if ($location.path() === '/px') {
            vm.px = true
            vm.placeholder = '500px username'
        } else {
            vm.ig = true
            vm.placeholder = 'Instgram username'
        }

    }

    vm.go = () => {
        if (!vm.isValidInput()) return
        location.config(vm.input)
    }

    vm.isValidInput = () => vm.input && vm.input !== ''
}
