// 线上模式，依赖 'qh-wap-front.views'，所需的 view 都已经是Js/Angular的module了

var app = angular.module('myApp', [
    'ngResource',
    'ui.router',
    'myApp.views'
]);
