import angular from 'angular';

import IgSearchController from './search/igsearch.controller';
import PackConfController from './pack_conf/pack_conf.controller';

export default angular
                    .module('inpacker.instagram', [])
                    .controller('IgSearchController', IgSearchController)
                    .controller('PackConfController', PackConfController)
                    .name;
