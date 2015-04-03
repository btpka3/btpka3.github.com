app.config(['$routeProvider',
  function ($routeProvider) {
    $routeProvider
      .when('/user', {
        templateUrl: 'user/index.tpl.html',
        controller: 'UserListController'
      })
  }]);

app.controller('UserListController', [
  '$scope',
  'UserService',

  function ($scope, UserService) {
    $scope.num = 333;
    $scope.userList = UserService.query()
  }]);

