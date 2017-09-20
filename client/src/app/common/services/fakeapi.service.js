export default FakeApiService

function FakeApiService() {
    var packed = 0
    return {
        getIgUser,
        getPxUser,
        getPack,
        createIgPack,
        createPxPack
    }

    function getIgUser(username) {
        let u = {
            instagramId: '123123123_3213213123',
            username: username,
            isPrivate: false,
            fullName: 'BoJack Horseman',
            biography: 'biography',
            profilePic: '//avatars1.githubusercontent.com/u/9919?v=4&s=200',
            count: 56,
            isVerified: true
        }
        return {then: function(f) {return f(u)}}
    }

    function getPxUser(username) {
        let u = {
            id: '123456',
            username: username,
            firstname: 'Walter',
            lastname: 'White',
            city: 'Albuquerque',
            userpic_url: '//avatars1.githubusercontent.com/u/9919?v=4&s=200',
            cover_url: '//www.everestian.com/photos/1.jpg',
            photos_count: 89

        }
        return {then: function(f) {return f(u)}}
    }

    function getPack(id) {
        packed += 10
        let p = {
            id: 'pack_id',
            is_done: packed >= 365,//false,
            is_failed: false,
            packed_count: packed,
            failed_count: 0,
            items_count: 365
        }
        return {then: function(f) {return f(p)}}
    }

    function createIgPack(config) {
        let p = {
            id: 'pack_id',
            is_done: false,
            is_failed: false,
            packed_count: 233,
            failed_count: 0,
            items_count: 365
        }
        return {then: function(f) {return f(p)}}
    }

    function createPxPack(config) {
        let p = {
            id: 'pack_id',
            is_done: false,
            is_failed: false,
            packed_count: 233,
            failed_count: 0,
            items_count: 365
        }
        return {then: function(f) {return f(p)}}
    }
}
