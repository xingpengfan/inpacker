import angular from 'angular'

import common from '../common/common.module'

import QueryController    from './query/query.controller'
import IgConfigController from './config/igconfig.controller'
import PxConfigController from './config/pxconfig.controller'
import PackController     from './pack/pack.controller'

export default angular
                    .module('inpacker.flow', [common])
                    .controller('QueryController', QueryController)
                    .controller('IgConfigController', IgConfigController)
                    .controller('PxConfigController', PxConfigController)
                    .controller('PackController', PackController)
                    .name
