export default PackConfController;

PackConfController.$inject = ['$routeParams', 'api', 'location', 'user'];
function PackConfController($routeParams, api, location, user) {
    if (user == null) {
        location.openSearch($routeParams.username);
        return;
    }
    const vm = this;

    activate();

    function activate() {
        vm.user = user;
        vm.settings = {
            username: user.username,
            includeImages: true,
            includeVideos: true,
            fileNamePattern: 'timestamp'
        };
        vm.filenameExamples = new Map([
            ['id',        {img: '1756...364.jpg',           vid: '4606...591.mp4'}],
            ['index',     {img: '1.jpg',                    vid: '2.mp4'}],
            ['utctime',   {img: '2017-02-25T15:36:59Z.jpg', vid: '2016-05-10T14:24:20Z.mp4'}],
            ['timestamp', {img: '1497899933.jpg',           vid: '1497788183.mp4'}]
        ]);
        vm.waitingResp = false;
        vm.user.instagramPageLink = 'https://www.instagram.com/' + vm.user.username + '/';
    }

    vm.createPackClick = () => {
        vm.waitingResp = true;
        api.createPack(vm.settings).then(pack => {
            if (pack != null) location.openPack(pack.id);
            vm.waitingResp = false;
        });
    }

    vm.backToSearchClick = () => location.openSearch();

    vm.username = () => {
        if (vm.user.username.length > 18)
            return vm.user.username.substring(0, 18) + '..'
        else
            return vm.user.username;
    }

    vm.ready = () => !vm.waitingResp && vm.user.count > 0 && (vm.settings.includeVideos || vm.settings.includeImages);

    vm.filenameExample = () => {
        let example = '';
        if (vm.settings.includeImages)
            example += vm.filenameExamples.get(vm.settings.fileNamePattern).img + ', ';
        if (vm.settings.includeVideos)
            example += vm.filenameExamples.get(vm.settings.fileNamePattern).vid;
        if (example === '')
            example = 'no files included';
        return example;
    }
}
