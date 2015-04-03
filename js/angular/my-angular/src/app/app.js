var app = angular.module('jujn-www-front', [
  'ngRoute',
  'ngResource',

  'jujn-www-front.templates'
]);

app.config(['$routeProvider',
  function ($routeProvider) {
    $routeProvider
      .when('/user', {
        templateUrl: 'user/index.tpl.html',
        controller: 'UserListController'
      })
      .otherwise({
        redirectTo: '/user/list'
      });
  }]);