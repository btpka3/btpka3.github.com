/*
app.config(['$routeProvider',
  function ($routeProvider) {
    $routeProvider
      .when('/user/:userId', {
        templateUrl: 'user/detail.tpl.html',
        controller: 'UserDetailController'
      })
  }]);

app.controller('UserDetailController', [
  '$scope',
  '$routeParams',
  'UserService',

  function ($scope, $routeParams, UserService) {


    $scope.num = 444;
    $scope.user = UserService.get({userId: $routeParams.userId})
  }]);
*/