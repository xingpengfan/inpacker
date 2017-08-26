import angular from 'angular';
import ngRoute from 'angular-route';

import 'bootstrap/dist/css/bootstrap.min.css';
import '../style/app.css';

import config from './app.config';

import IconService from './services/icon.service';
import LocationService from './services/location.service';
import InstagramService from './services/instagram.service';

import MainIconComponent from './components/main_icon/main_icon.component';

import IgSearchController from './search/igsearch.controller';
import PackConfController from './pack_conf/pack_conf.controller';
import PackStatusController from './pack_status/pack_status.controller';

import '../style/app.css';

angular
    .module('app', [ngRoute])
    .constant('CHECK_STATUS_INTERVAL', 2000)
    .config(config)
    .service('icon', IconService)
    .service('locationService', LocationService)
    .service('ig', InstagramService)
    .component('mainIcon', MainIconComponent)
    .controller('IgSearchController', IgSearchController)
    .controller('PackConfController', PackConfController)
    .controller('PackStatusController', PackStatusController);
