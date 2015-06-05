app.config([
  '$stateProvider',
  '$urlRouterProvider',

  function ($stateProvider,
            $urlRouterProvider) {

    $stateProvider.state("user", {
      abstract: true,
      url: '/user',
      template: 'aaa<div ui-view></div>bbb'
    });


    $stateProvider.state('user.list', {
      url: '',
      templateUrl: 'views/user/list.html',
      //views: {
      //  "@": {
      //    templateUrl: 'user/list.html'
      //  }
      //},
      resolve: {
        a: function () {
          return 1111;
        }
      },
      controller: [
        '$scope',
        'userService',

        function ($scope, userService) {
          console.log(111, userService);
          $scope.num = 111;
          $scope.userList = userService.query();
        }
      ]
    });

    $stateProvider.state('user.detail', {
      url: '/{userId}',
      templateUrl: 'views/user/detail.html',
      //views: {
      //  "@": {
      //    templateUrl: 'user/list.html'
      //  }
      //},
      resolve: {
        a: function () {
          return 1111;
        }
      },
      controller: [
        '$scope',
        '$state',
        '$stateParams',
        'userService',

        function ($scope, $state, $stateParams, userService) {
          console.log(222, userService, $stateParams.userId);
          $scope.num = 222;

          // $urlMatcherFactoryProvider.strictMode(false);
          //if (!$stateParams.userId) {
          //  $state.go('user.list');
          //  return;
          //}
          $scope.user = userService.get({userId: $stateParams.userId});
        }
      ]
    });

  }]);