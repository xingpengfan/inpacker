import angular from 'angular'
import ngRoute from 'angular-route'

import icons     from './icons/icons.module'
import flow      from './flow/flow.module'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'font-awesome/css/font-awesome.min.css'
import './main.css'

import config from './inpacker.config'

export default angular
                    .module('inpacker', [ngRoute, icons, flow])
                    .config(config)
                    .name
