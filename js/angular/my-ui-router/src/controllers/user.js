app.config([
    '$stateProvider',
    function ($stateProvider) {

        $stateProvider.state("user", {
            url: "/user",

            views: {
                "@": {
                    // 内容会填充到 id_1 下
                    templateUrl: 'views/user/index.root.html',
                    controller: ['$scope', '$http', function ($scope, $http) {

                    }]
                },
                'view2@': {
                    // 内容会填充到 id_2 下
                    templateUrl: 'views/user/view2.root.html',
                },
                '@user': {
                    // 内容会填充到 id_3 下
                    templateUrl: 'views/user/index.user.html',
                },
                'view4@user': {
                    // 内容会填充到 id_4 下
                    templateUrl: 'views/user/view4.user.html',
                }

            }
        });
    }]);
