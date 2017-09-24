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
            vm.userNotFound = true
            return
        }
        vm.user = user
        vm.showConfig = true
        vm.config = {
            username: vm.user.username,
            fileNamePattern: 'name'
        }
        vm.user.pxPageLink = 'https://500px.com/' + user.username
        vm.filenameExamples = new Map([
            ['id','475812.jpg'],
            ['index','1.jpg'],
            ['name','Eiffel Tower_234578.jpg']
        ])
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
        vm.loading = true
        api.createPxPack(vm.config).then(pack => {
            if (pack != null) location.pack(pack.id)
            vm.loading = false
        })
    }

    vm.filenameExample = () => {
        return vm.filenameExamples.get(vm.config.fileNamePattern)
    }

}
