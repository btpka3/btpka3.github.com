if (jQuery) {
    jQuery.noConflict();
}

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
    '$urlMatcherFactoryProvider',

    function ($stateProvider,
              $urlRouterProvider,
              $urlMatcherFactoryProvider) {

        $urlMatcherFactoryProvider.strictMode(false);
        $urlRouterProvider.otherwise('/');
    }]);