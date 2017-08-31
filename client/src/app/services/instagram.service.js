export default class InstagramService {
    constructor($http) {
        this.$http = $http;
    }

    getUser(username) {
        return this.$http.get('/api/user/' + username).then(
            resp => {
                return resp.data;
            },
            resp => {
                return null;
            }
        );
    }

    getPack(id) {
        return this.$http.get('/api/packs/' + id + '/status').then(
            resp => {
                return resp.data;
            },
            resp => {
                return null;
            }
        );
    }

    createPack(config) {
        return this.$http.post('/api/packs', config).then(
            resp => {
                return resp.data;
            },
            resp => {
                return null;
            }
        );
    }
}

InstagramService.$inject = ['$http'];
