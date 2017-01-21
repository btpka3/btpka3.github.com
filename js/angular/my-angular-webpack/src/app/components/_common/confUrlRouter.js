function confUrlRouter($urlRouterProvider) {
    $urlRouterProvider.otherwise('/');
}
confUrlRouter.$inject = ['$urlRouterProvider'];

export default confUrlRouter;
