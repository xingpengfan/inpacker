import angular from 'angular';

import * as iconComponents from './icon.components';

export default angular
                    .module('inpacker.icons', [])
                    .component('instagramIcon', iconComponents.InstagramIconComponent)
                    .component('packConfIcon', iconComponents.PackConfIconComponent)
                    .component('privateUserIcon', iconComponents.PrivateUserIconComponent)
                    .component('creatingPackIcon', iconComponents.CreatingPackIconComponent)
                    .component('readyPackIcon', iconComponents.ReadyPackIconComponent)
                    .component('aboutIcon', iconComponents.AboutIconComponent)
                    .name;
