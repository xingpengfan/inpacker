import angular from 'angular'

import common from '../common/common.module'

import QueryController  from './query/query.controller'
import ConfigController from './config/config.controller'
import PackController   from './pack/pack.controller'

export default angular
                    .module('inpacker.flow', [common])
                    .controller('QueryController', QueryController)
                    .controller('ConfigController', ConfigController)
                    .controller('PackController', PackController)
                    .name
