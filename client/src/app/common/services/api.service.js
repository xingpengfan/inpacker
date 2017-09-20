export default ApiService

ApiService.$inject = ['$http']
function ApiService($http) {
    return {
        getIgUser,
        getPxUser,
        getPack,
        createIgPack,
        createPxPack
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

    function createIgPack(config) {
        return $http.post('/api/packs/ig', config)
            .then(resp => resp.data, resp => null)
    }

    function createPxPack(config) {
        return $http.post('/api/packs/px', config)
            .then(resp => resp.data, resp => null)
    }
}
