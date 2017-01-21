function controller($scope, $state) {
    $scope.go = function (state) {
        console.log("--------------" + state);
        $state.go(state);
    };
}
controller.$inject = ['$scope', '$state'];

export default controller ;
