angular.module("app.layout", [
    {
    files: [
        "oc-lazyLoad-1-common.js"
    ],
    cache: true
}
])
    .config(function () {
        msgs.push("---------angular.module('app.layout').config()")
    })
    .run(function () {
        msgs.push("---------angular.module('app.layout').run()")
    })
    .factory('layoutService', [function () {
        return {
            hi: function () {
                msgs.push("---------angular.module('app.layout').service('layoutService').hi()");
            }
        };
    }])
    .provider('layout', [function () {
        var name = "yyy";
        this.setName = function (value) {
            name = value;
        };
        this.$get = [function () {
            return {
                hey: function () {
                    msgs.push("---------angular.module('app.layout').provider('layout').hey() = " + name);
                }
            }

        }];
    }]);
