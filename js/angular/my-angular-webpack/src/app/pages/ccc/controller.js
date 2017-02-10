function controller($scope, $state) {
    $scope.go = function (state) {
        console.log("--------------" + state);
        $state.go(state);
    };
    $scope.count = 300;
}
controller.$inject = ['$scope', '$state'];

export default controller ;
