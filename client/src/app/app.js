import angular from 'angular';
import ngRoute from 'angular-route';

import icons from './icons/icons.module';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import './main.css';

import config from './app.config';

import AppLocation from './services/applocation.service';
import InstagramService from './services/instagram.service';

import IgSearchController from './search/igsearch.controller';
import PackConfController from './pack_conf/pack_conf.controller';
import PackStatusController from './pack_status/pack_status.controller';

angular
    .module('app', [ngRoute, icons])
    .constant('CHECK_STATUS_INTERVAL', 2000)
    .config(config)
    .service('appLocation', AppLocation)
    .service('ig', InstagramService)
    .controller('IgSearchController', IgSearchController)
    .controller('PackConfController', PackConfController)
    .controller('PackStatusController', PackStatusController)
    .name;
