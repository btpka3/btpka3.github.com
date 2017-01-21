function stateFactory($q, $log, $ocLazyLoad, futureState) {

    console.log("----------- stateFactory : ", futureState)
    var def = $q.defer();

    $ocLazyLoad.load(futureState.src).then(function () {
        console.log("----------- stateFactory : then : ", futureState)
        def.resolve();
    }, function (err) {
        console.log("----------- stateFactory : err : ", futureState)
        def.reject(err);
    });

    return def.promise;
}

stateFactory.$inject = [
    '$q',
    '$log',
    '$ocLazyLoad',
    'futureState'
];

export default stateFactory;
