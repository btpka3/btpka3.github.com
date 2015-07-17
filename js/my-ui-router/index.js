angular.module('myApp', ['ui.router']);


var app = angular.module('myApp');
app.config(['$stateProvider', function ($stateProvider) {

    $stateProvider.state("user", {
        url: "/user",
        views: {
            // KEY 的命名规则：  "${ui-view 的名字}@${state的名字}"
            "@": {
                // 内容会填充到 id_1 下
                templateUrl: 'views/user/index.root.html'
            },
            'view2@': {
                // 内容会填充到 id_2 下
                templateUrl: 'views/user/view2.root.html'
            },
            '@user': {
                // 内容会填充到 id_3 下
                templateUrl: 'views/user/index.user.html'
            },
            'view4@user': {
                // 内容会填充到 id_4 下
                templateUrl: 'views/user/view4.user.html'
            }
        }
    });
}]);
