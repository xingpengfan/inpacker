export default ApiService

ApiService.$inject = ['$http']
function ApiService($http) {
    return {
        getIgUser,
        getPack,
        createPack
    }

    function getIgUser(username) {
        return $http.get('/api/ig/user/' + username)
            .then(resp => resp.data, resp => null)
    }

    function getPack(id) {
        return $http.get('/api/packs/' + id + '/status')
            .then(resp => resp.data, resp => null)
    }

    function createPack(config) {
        return $http.post('/api/packs', config)
            .then(resp => resp.data, resp => null)
    }
}
