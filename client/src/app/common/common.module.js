import angular from 'angular';

import ApiService from './services/api.service';
import LocationService from './services/location.service';

import PackController from './pack/pack.controller';

export default angular
                    .module('inpacker.common', [])
                    .constant('CHECK_STATUS_INTERVAL', 2000)
                    .service('api', ApiService)
                    .service('location', LocationService)
                    .controller('PackController', PackController)
                    .name
