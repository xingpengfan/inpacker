export default class IgSearchController {
    constructor($routeParams, appLocation, icon) {
        this.$routeParams = $routeParams;
        this.icon = icon;
        this.appLocation = appLocation;
        this.iconClass = icon.defaultIcon();
        this.userNotFoundMessage = 'User ' + $routeParams.unf + ' not found';
        this.packNotFoundMessage = 'Pack ' + $routeParams.pnf + ' not found';
    }

    search() {
        if (!this.isValidInput()) return;
        this.searching = true;
        this.appLocation.openPackConf(this.input);
    }

    isValidInput() {
        return this.input && this.input !== '';
    }

    showUserNotFound() {
        return this.$routeParams.unf != null;
    }

    showPackNotFound() {
        return this.$routeParams.pnf != null;
    }

    closeUserNotFoundAlert() {
        this.appLocation.openSearch();
    }

    closePackNotFoundAlert() {
        this.appLocation.openSearch();
    }
}

IgSearchController.$inject = ['$routeParams', 'appLocation', 'icon'];
