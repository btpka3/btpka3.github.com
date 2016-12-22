(function () {

    angular.module('MyApp', ['ngMaterial', 'ngMessages', 'ngMdIcons'])
        .controller('DemoCtrl', ['$scope', '$http', '$log', function ($scope, $http, $log) {

            var vm = $scope.vm = {
                at: null,   // 获取的 access_token
                sec: null // 图片列表
            };

            $scope.getSec = function () {
                $http({
                    method: "GET",
                    url: "sec",

                    headers: {
                        "Accept": "application/json, */*", // 排除 "text/plain"
                        "X-Requested-With": "XMLHttpRequest"
                    }
                }).then(function (resp) {
                    $log.error("------- getResource: OK : ", resp);
                    vm.sec = resp.data;
                }, function (resp) {

                    if (resp.status === 401) {
                        location.href = "login?target_url=" + encodeURIComponent(location.href)
                    }
                    $log.error("------- getResource: ERROR : ", resp);
                })
            };

        }])
})();