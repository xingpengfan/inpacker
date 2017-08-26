export default class IgSearchController {
    constructor($routeParams, locationService, icon) {
        this.$routeParams = $routeParams;
        this.icon = icon;
        this.locationService = locationService;
        this.iconClass = icon.defaultIcon();
        this.userNotFoundMessage = 'User ' + $routeParams.unf + ' not found';
        this.packNotFoundMessage = 'Pack ' + $routeParams.pnf + ' not found';
    }

    search() {
        if (!this.isValidInput()) return;
        this.searching = true;
        this.locationService.openPackConf(this.input);
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
        this.locationService.openSearch();
    }

    closePackNotFoundAlert() {
        this.locationService.openSearch();
    }
}

IgSearchController.$inject = ['$routeParams', 'locationService', 'icon'];
