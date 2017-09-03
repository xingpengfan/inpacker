export default class ApiService {
    constructor($http) {
        this.$http = $http;
    }

    getIgUser(username) {
        return this.$http.get('/api/user/' + username)
            .then(resp => resp.data, resp => null);
    }

    getPack(id) {
        return this.$http.get('/api/packs/' + id + '/status')
            .then(resp => resp.data, resp => null);
    }

    createPack(config) {
        return this.$http.post('/api/packs', config)
            .then(resp => resp.data, resp => null);
    }
}

ApiService.$inject = ['$http'];
