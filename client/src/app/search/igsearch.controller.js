export default class IgSearchController {
    constructor($routeParams, location) {
        this.$routeParams = $routeParams;
        this.location = location;

        this.notFoundAlertMessage = '';
        if ($routeParams.unf != null) this.notFoundAlertMessage = 'User ' + $routeParams.unf + ' not found';
        else if ($routeParams.pnf != null) this.notFoundAlertMessage = 'Pack ' + $routeParams.pnf + ' not found';
    }

    search() {
        if (!this.isValidInput()) return;
        this.searching = true;
        this.location.openPackConf(this.input);
    }

    isValidInput() {
        return this.input && this.input !== '';
    }

    closeNotFoundAlert() {
        this.notFoundAlertMessage = '';
        this.location.openSearch();
    }
}

IgSearchController.$inject = ['$routeParams', 'location'];
