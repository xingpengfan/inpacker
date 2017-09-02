export default class IgSearchController {
    constructor($routeParams, appLocation) {
        this.$routeParams = $routeParams;
        this.appLocation = appLocation;

        this.notFoundAlertMessage = '';
        if ($routeParams.unf != null) this.notFoundAlertMessage = 'User ' + $routeParams.unf + ' not found';
        else if ($routeParams.pnf != null) this.notFoundAlertMessage = 'Pack ' + $routeParams.pnf + ' not found';
    }

    search() {
        if (!this.isValidInput()) return;
        this.searching = true;
        this.appLocation.openPackConf(this.input);
    }

    isValidInput() {
        return this.input && this.input !== '';
    }

    closeNotFoundAlert() {
        this.notFoundAlertMessage = '';
        this.appLocation.openSearch();
    }
}

IgSearchController.$inject = ['$routeParams', 'appLocation'];
