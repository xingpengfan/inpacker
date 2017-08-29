export default class LocationService {
    constructor($location) {
        this.$location = $location;
    }

    openSearch(userNotFound, packNotFound) {
        if (userNotFound)
            this.$location.search('unf', userNotFound);
        else
            this.$location.search('unf', null);
        if (packNotFound)
            this.$location.search('pnf', packNotFound);
        else
            this.$location.search('pnf', null);
        this.$location.path('/');
    }

    openPackConf(username) {
        this.$location.url(this.$location.path());
        this.$location.path('/@' + username);
    }

    openPack(packId) {
        this.$location.path('/p/' + packId);
    }
}

LocationService.$inject = ['$location'];
