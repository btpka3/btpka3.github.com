angular.module("app.common", [{
    files: [
        'https://cdn.bootcss.com/jquery/3.1.1/jquery.js'
    ],
    cache: true
}])
    .config(function () {
        msgs.push("---------angular.module('app.common').config()")
    })
    .run(function () {
        msgs.push("---------angular.module('app.common').run()");
        msgs.push("$=" + $)
    })
    .factory('commonService', [function () {
        return {
            hi: function () {
                msgs.push("---------angular.module('app.common').service('commonService').hi()");
            }
        };
    }])
    .provider('common', [function () {
        var name = "xxx";
        this.setName = function (value) {
            name = value;
        };
        this.$get = [function () {
            return {
                hey: function () {
                    msgs.push("---------angular.module('app.common').provider('common').hey() = " + name);
                }
            }
        }];
    }]);
