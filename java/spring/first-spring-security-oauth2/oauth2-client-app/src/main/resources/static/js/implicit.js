(function () {

    angular.module('MyApp', ['ngMaterial', 'ngMessages', 'ngMdIcons'])
        .controller('DemoCtrl', ['$scope', '$http', '$log', function ($scope, $http, $log) {
            var resourceUrl = "http://a.localhost:10001/o2/photo";

            var vm = $scope.vm = {
                at: null   // 获取的 access_token
            };

            // 构建配置的数据
            var jso = new JSO({
                providerID: "oauth2-authorization-server",
                client_id: "MY_CLIENT",
                redirect_uri: "http://c.localhost:10003/implicit.html",
                authorization: "http://a.localhost:10001/oauth/authorize",
                scopes: {request: ["read", "write"]},
                debug: true
            });

            // 检查是否是从 认证服务器回来的。
            jso.callback(location.href, function (at) {
                vm.at = at;
                $log.log("------------ return from OAuth server", at);
            }, "oauth2-authorization-server");



            // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
            jso.on('redirect', function (url) {
                $log.info("-------redirect : " + url);
                location.href = url;
                //JSO.redirect(url)
            });

            $scope.goOAuth = function () {

                // 由 JSO 生成相应的的URL
                jso.getToken(function (token) {
                    vm.at = token;
                    console.log("I got the token: ", token);
                }, {});
            };

            $scope.clearAt = function(){
                jso.wipeTokens();
                vm.at = null;
            };


            $scope.getResouce = function(){
                $http({
                    method:"GET",
                    url:resourceUrl,
                    headers:{
                        'Authorization': 'Bearer '+vm.at.access_token
                    }
                }).then(function(resp){
                    $log.error("------- getResource: OK : ", resp)
                },function(resp){
                    $log.error("------- getResource: ERROR : ", resp)
                })
            };


        }])
})();