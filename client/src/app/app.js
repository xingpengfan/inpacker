import angular from 'angular';
import ngRoute from 'angular-route';

import icons from './icons/icons.module';
import common from './common/common.module';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import './main.css';

import config from './app.config';

import IgSearchController from './search/igsearch.controller';
import PackConfController from './pack_conf/pack_conf.controller';

export default angular
                    .module('app', [ngRoute, icons, common])
                    .config(config)
                    .controller('IgSearchController', IgSearchController)
                    .controller('PackConfController', PackConfController)
                    .name;
