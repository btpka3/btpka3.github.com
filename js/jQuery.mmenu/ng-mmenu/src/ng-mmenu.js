/**
 * Angular plugin for jQuery.mmenu.
 *
 * jQuery.mmenu:
 *      http://mmenu.frebsite.nl
 */

"use strict";

angular.module('ng-mmenu', [])
    .directive('mmenu', ["$log", function ($log) {

        function link(scope, element, attrs, controllers) {
            $log.info(JSON.stringify(scope.mmenuOpt));
            $log.info(JSON.stringify(element.html()));
            $log.info(JSON.stringify(attrs));

            var mmenuOpt = {};
            if (typeof (scope.mmenuOpt) === "object") {
                mmenuOpt = angular.extend(mmenuOpt, scope.mmenuOpt);
            }
            var mmenuConf = {};
            if (typeof (scope.mmenuConf) === "object") {
                mmenuConf = angular.extend(mmenuConf, scope.mmenuConf);
            }

            element.mmenu(mmenuOpt, mmenuConf);
        }

        return {
            restrict: "AE",
            transclude: true,
            template: "<div ng-transclude></div>",
            scope: {
                mmenuOpt: "=",
                mmenuConf: "="
            },
            link: link
        };
    }]);
