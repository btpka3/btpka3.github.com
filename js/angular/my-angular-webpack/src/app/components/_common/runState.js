function runState($rootScope, $log, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        $log.info("$stateChangeError : fromState = " + JSON.stringify(fromState.name) +
            ", toState = " + JSON.stringify(toState.name) + ", error = ", error);
    });
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        $log.info("$stateChangeStart : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
    });
    $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
        $log.info("$stateNotFound : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(unfoundState.name));
    });
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.errorsMsg = true;
        $log.info("$stateChangeSuccess : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
    });
    console.log("eeeeeeeeeeeeee");
}
runState.$inject = ['$rootScope', '$log', '$state', '$stateParams'];

export default runState;
