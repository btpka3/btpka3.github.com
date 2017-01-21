function runState($rootScope, $log, $state, $stateParams) {
    console.log("eeeeeeeeeeeeee111");
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        $log.info("$stateChangeError111 : fromState = " + JSON.stringify(fromState.name) +
            ", toState = " + JSON.stringify(toState.name) + ", error = ", error);
    });
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        $log.info("$stateChangeStart111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
    });
    $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
        $log.info("$stateNotFound111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(unfoundState.name));
    });
    $rootScope.$on('$stateChangeSuccess111', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.errorsMsg = true;
        $log.info("$stateChangeSuccess111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
    });
    console.log("eeeeeeeeeeeeee112");
}
runState.$inject = ['$rootScope', '$log', '$state', '$stateParams'];

export default runState;
