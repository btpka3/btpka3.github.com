(function () {

    angular.module('MyApp', ['ngMaterial', 'ngMessages', 'ngMdIcons'])
        .controller('DemoCtrl', ['$scope', '$http', '$log', function ($scope, $http, $log) {
            var resourceUrl = "http://a.localhost:10001/o2/photo";

            var vm = $scope.vm = {
                reqMsg: null    // 请求的消息
            };


            var ws,hi;


            connectWs();
            connectHi();


            $scope.sendWs = function () {
                $log.info('sendWs : Sent: ' + vm.reqMsg);
                ws.send(vm.reqMsg);
            };
            $scope.sendHi = function () {
                $log.info('sendHi : Sent: ' + vm.reqMsg);
                hi.send(vm.reqMsg);
            };


            function connectWs() {
                ws = new SockJS('/myWs');
                ws.onopen = function () {
                    $log.log('connectWs : open');
                };
                ws.onmessage = function (e) {
                    $log.log('connectWs : message : ', e.data);
                };
                ws.onclose = function () {
                    $log.log('connectWs : close');
                };

                //ws.send('test');
                //ws.close();
            }
            function connectHi() {
                hi = new SockJS('/myWs');
                hi.onopen = function () {
                    $log.log('connectHi : open');
                };
                hi.onmessage = function (e) {
                    $log.log('connectHi : message : ', e.data);
                };
                hi.onclose = function () {
                    $log.log('connectHi : close');
                };
            }

        }])
})();