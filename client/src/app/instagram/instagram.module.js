import angular from 'angular';

import IgQueryController from './query/igquery.controller';
import PackConfController from './pack_conf/pack_conf.controller';

export default angular
                    .module('inpacker.instagram', [])
                    .controller('IgQueryController', IgQueryController)
                    .controller('PackConfController', PackConfController)
                    .name;
