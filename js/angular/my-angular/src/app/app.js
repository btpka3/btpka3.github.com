var app = angular.module('myApp', [
  // 'ngRoute',
  'ngResource',
  'ui.router',

  'myApp.templates'
]);

app.run([
  '$rootScope',
  '$state',
  '$stateParams',

  function ($rootScope,
            $state,
            $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
  }
]);

app.config([
  '$stateProvider',
  '$urlRouterProvider',
  function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider.state("home", {
      url: "/",
      templateUrl: "common/404.tpl.html"
      //template: '111'
    });


    //  .state('user', {
    //    templateUrl: 'user/index.tpl.html',
    //    controller: 'UserListController'
    //  })
    //  .state('user.list', {
    //    templateUrl: 'user/index.tpl.html',
    //    controller: 'UserListController'
    //  })
    //  .otherwise({
    //    redirectTo: '/user/list'
    //  });
  }]);