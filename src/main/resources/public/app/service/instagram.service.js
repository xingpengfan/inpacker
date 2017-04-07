(function() {
    'use strict';

    angular.module('inpacker').factory('instagramService', instagramService);

    instagramService.$inject = ['$http'];

    function instagramService($http) {
        var service = {
            getUser: getUser,
            getPack: getPack,
            createPack: createPack
        };
        return service;

        function getUser(username) {
            return $http.get('/api/user/' + username)
                .then((resp) => {
                    return resp.data;
                }, (resp) => {
                    return null;
                });
        }

        function getPack(id) {
            return $http.get('/api/packs/' + id + '/status')
                .then((resp) => {
                    return resp.data;
                }, (resp) => {
                    return null;
                });
        }

        function createPack(config) {
            return $http.post('/api/packs', config)
                .then((resp) => {
                    return resp.data;
                }, (resp) => {
                    return null;
                });
        }
    }

})();
