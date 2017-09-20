export default ApiService

ApiService.$inject = ['$http']
function ApiService($http) {
    return {
        getIgUser,
        getPxUser,
        getPack,
        createPack
    }

    function getIgUser(username) {
        return $http.get('/api/ig/user/' + username)
            .then(resp => resp.data, resp => null)
    }

    function getPxUser(username) {
        return $http.get('/api/px/user/' + username)
            .then(resp => resp.data, resp => null)
    }

    function getPack(id) {
        return $http.get('/api/packs/ig/' + id + '/status')
            .then(resp => resp.data, resp => null)
    }

    function createPack(config) {
        return $http.post('/api/packs/ig', config)
            .then(resp => resp.data, resp => null)
    }
}
