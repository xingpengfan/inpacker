import angular from 'angular';

import ApiService from './services/api.service';
import LocationService from './services/location.service';

export default angular
                    .module('inpacker.common', [])
                    .service('api', ApiService)
                    .service('location', LocationService)
                    .name
