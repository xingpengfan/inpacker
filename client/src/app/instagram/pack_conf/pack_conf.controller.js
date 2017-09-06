export default class PackConfController {
    constructor($routeParams, api, location, user) {
        if (user == null) return;
        this.user = user;
        this.$routeParams = $routeParams;
        this.api = api;
        this.location = location;
        this.settings = {
            username: user.username,
            includeImages: true,
            includeVideos: true,
            fileNamePattern: 'timestamp'
        };
        this.processing = false; // waiting for the response of post create pack
        this.user.instagramPageLink = 'https://www.instagram.com/' + this.user.username + '/';
    }

    createPackClick() {
        this.processing = true;
        this.api.createPack(this.settings).then(pack => {
            if (pack != null) this.location.openPack(pack.id);
            this.processing = false;
        });
    }

    searchAnotherUser() {
        this.location.openSearch();
    }

    shortenedUsername() {
        if (this.user == null) return;
        if (this.user.username.length > 18) return this.user.username.substring(0, 18) + '..';
        else return this.user.username;
    }

    filenameExample() {
        let example = '';
        const names = new Map([
            ['id',        {img: '1756...364.jpg', vid: '4606...591.mp4'}],
            ['index',     {img: '1.jpg', vid: '2.mp4'}],
            ['utctime',   {img: '2017-02-25T15:36:59Z.jpg', vid: '2016-05-10T14:24:20Z.mp4'}],
            ['timestamp', {img: '1497899933.jpg', vid: '1497788183.mp4'}]
        ]);
        if (this.settings.includeImages)
            example += names.get(this.settings.fileNamePattern).img + ', ';
        if (this.settings.includeVideos)
            example += names.get(this.settings.fileNamePattern).vid;
        return example;
    }

    possibleToCreatePack() {
        return !this.processing && (this.settings.includeImages || this.settings.includeVideos);
    }
}

PackConfController.$inject = ['$routeParams', 'api', 'location', 'user'];
