import angular from 'angular'

import ApiService from './services/api.service'
import LocationService from './services/location.service'

export default angular
                    .module('inpacker.common', [])
                    .constant('UPDATE_PACK_STATUS_INTERVAL', 2000)
                    .factory('api', ApiService)
                    .factory('location', LocationService)
                    .name
