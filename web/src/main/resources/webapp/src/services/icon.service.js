(function() {
    'use strict';
    angular.module('inpacker').factory('icon', iconService);

    function iconService() {
        let service = {
            defaultIcon: defaultIcon,
            privateUser: privateUser,
            packIsDone: packIsDone,
            creatingPack: creatingPack,
            packConfiguration: packConfiguration
        };
        return service;

        function defaultIcon() {
            return 'fa fa-lg fa-instagram';
        }

        function privateUser() {
            return 'fa fa-lg fa-user-secret';
        }

        function packIsDone() {
            return 'fa fa-lg fa-check';
        }

        function creatingPack() {
            return 'fa fa-lg fa-cog fa-spin fa-fw';
        }

        function packConfiguration() {
            return 'fa fa-lg fa-cogs';
        }
    }
})();
