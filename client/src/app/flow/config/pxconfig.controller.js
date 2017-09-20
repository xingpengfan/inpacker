export default PxConfigController;

PxConfigController.$inject = ['$routeParams', 'api', 'location']
function PxConfigController($routeParams, api, location) {
    const vm = this
    vm.showConfig = false
    vm.userNotFound = false
    vm.loading = true

    api.getPxUser($routeParams.username)
        .then(user => {
            vm.loading = false
            activate(user)
        })

    function activate(user) {
        if (user == null) {
            vm.showNotFound = true
            return
        }
        vm.user = user
        vm.showConfig = true
        vm.user.pxPageLink = 'https://500px.com/' + user.username
    }

    vm.username = () => {
        if (vm.user.username.length > 18)
            return vm.user.username.substring(0, 18) + '..'
        else
            return vm.user.username
    }

    vm.fullName = () => {
        return vm.user.firstname + ' ' + vm.user.lastname
    }

    vm.backToQueryClick = () => location.pxQuery()

    vm.ready = () => !vm.loading && vm.user.photos_count > 0

    vm.createPackClick = () => {
        const config = {
            username: vm.user.username
        }
        vm.loading = true
        api.createPxPack(config).then(pack => {
            if (pack != null) location.pack(pack.id)
            vm.loading = false
        })
    }
}
