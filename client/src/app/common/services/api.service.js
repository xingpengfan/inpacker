export default class ApiService {
    constructor($http) {
        this.$http = $http;
    }

    getIgUser(username) {
        return this.$http.get('/api/ig/user/' + username)
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

// export default class FakeApiService {
//     constructor($http) {
//         this.$http = $http;
//     }
//
//     getIgUser(username) {
//         let iguser = {
//             instagramId: '123123123_3213213123',
//             username: username,
//             isPrivate: false,
//             fullName: 'BoJack Horseman',
//             biography: 'biography',
//             profilePic: '//avatars1.githubusercontent.com/u/9919?v=4&s=200',
//             count: 56,
//             isVerified: false
//         };
//         return {then: function(f) {return f(iguser);}};
//     }
//
//     getPack(id) {
//         let packstatus = {
//             id: 'pack_id',
//             is_done: false,
//             is_failed: false,
//             packed_count: 233,
//             failed_count: 0,
//             items_count: 365
//         };
//         return {then: function(f) {return f(packstatus);}};
//     }
//
//     createPack(config) {
//         let packstatus = {
//             id: 'pack_id',
//             is_done: false,
//             is_failed: false,
//             packed_count: 233,
//             failed_count: 0,
//             items_count: 365
//         };
//         return {then: function(f) {return f(packstatus);}};
//     }
// }
//
// FakeApiService.$inject = ['$http'];
