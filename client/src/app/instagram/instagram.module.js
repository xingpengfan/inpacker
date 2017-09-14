import angular from 'angular'

import QueryController from './query/query.controller'
import PackConfController from './config/config.controller'

export default angular
                    .module('inpacker.instagram', [])
                    .controller('QueryController', QueryController)
                    .controller('PackConfController', PackConfController)
                    .name
