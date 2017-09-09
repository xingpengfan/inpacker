import angular from 'angular';
import ngRoute from 'angular-route';

import icons     from './icons/icons.module';
import common    from './common/common.module';
import instagram from './instagram/instagram.module';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import './main.css';

import config from './inpacker.config';

export default angular
                    .module('inpacker', [ngRoute, icons, common, instagram])
                    .config(config)
                    .name;
