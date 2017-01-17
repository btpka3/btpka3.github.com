angular
    .module('app', ['ngMaterial', 'ngMessages', "oc.lazyLoad", 'ngAnimate'])
    .controller('DemoCtrl', [
        '$scope',
        "$ocLazyLoad", "$injector",
        function ($scope,
                  $ocLazyLoad, $injector) {

            $scope.load = function () {
                $ocLazyLoad.load([
                    'oc-lazyLoad-1-layout.js',
                    "https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.css",
                    "https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js",
                ]).then(function (m) {
                    console.log('-----------m', m);

                    var appService = $injector.get('appService');
                    var commonService = $injector.get('commonService');
                    var layoutService = $injector.get('layoutService');

                    commonService.hi();
                    layoutService.hi();
                    appService.hi();

                    var common = $injector.get('common');
                    var layout = $injector.get('layout');
                    var app = $injector.get('app');

                    common.hey();
                    layout.hey();
                    app.hey();
                }, function (err) {
                    console.log('-----------e', err);
                });
            };


            $scope.msgs = msgs;
            // console.log(msgs);
        }])
    // .config(['commonProvider', 'layoutProvider', 'appProvider', '$ocLazyLoadProvider',
    //     function (commonProvider, layoutProvider, appProvider, $ocLazyLoadProvider) {
    //         commonProvider.setName("common");
    //         layoutProvider.setName("layout");
    //         appProvider.setName("app");
    //         msgs.push("---------angular.module('app').config()")
    //
    //         $ocLazyLoadProvider.config({
    //             debug: true,
    //             events: true
    //         });
    //     }])
    .run(function () {
        msgs.push("---------angular.module('app').run()")
    })
    .factory('appService', [function () {
        return {
            hi: function () {
                msgs.push("---------angular.module('app.layout').service('appService').hi()");
            }
        };
    }])
    .provider('app', [function () {
        var name = "zzz";
        this.setName = function (value) {
            name = value;
        };
        this.$get = [function () {
            return {
                hey: function () {
                    msgs.push("---------angular.module('app.app').provider('app').hey() = " + name);
                }
            }
        }];
    }]);