import angular from 'angular'

import QueryController from './query/query.controller'
import PackConfController from './pack_conf/pack_conf.controller'

export default angular
                    .module('inpacker.instagram', [])
                    .controller('IgQueryController', QueryController)
                    .controller('PackConfController', PackConfController)
                    .name
