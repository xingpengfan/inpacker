export default class PackConfController {
    constructor($routeParams, ig, appLocation, user) {
        if (user == null) return;
        this.user = user;
        this.$routeParams = $routeParams;
        this.ig = ig;
        this.appLocation = appLocation;
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
        this.ig.createPack(this.settings).then(pack => {
            if (pack != null) this.appLocation.openPack(pack.id);
            this.processing = false;
        });
    }

    searchAnotherUser() {
        this.appLocation.openSearch();
    }

    shortenedUsername() {
        if (this.user == null) return;
        if (this.user.username.length > 18) return this.user.username.substring(0, 18) + '..';
        else return this.user.username;
    }

    preview() {
        let p = '';
        if (this.settings.includeImages)
            if (this.settings.fileNamePattern === 'id') p += '1756...364.jpg, ';
            else if (this.settings.fileNamePattern === 'index') p += '1.jpg, ';
            else if (this.settings.fileNamePattern === 'utctime') p += '2017-02-25T15:36:59Z.jpg, ';
            else if (this.settings.fileNamePattern === 'timestamp') p += '1497899933.jpg, ';
        if (this.settings.includeVideos)
            if (this.settings.fileNamePattern === 'id') p += '4606...591.mp4';
            else if (this.settings.fileNamePattern === 'index') p += '2.mp4';
            else if (this.settings.fileNamePattern === 'utctime') p += '2016-05-10T14:24:20Z.mp4';
            else if (this.settings.fileNamePattern === 'timestamp') p += '1497788183.mp4';
        return p;
    }

    possibleToCreatePack() {
        return !this.processing && (this.settings.includeImages || this.settings.includeVideos);
    }
}

PackConfController.$inject = ['$routeParams', 'ig', 'appLocation', 'user'];
