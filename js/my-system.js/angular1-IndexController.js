import angular from "angular";
export default  class IndexController {

    constructor($scope) {
        $scope.txt = "zhang3";
        console.log("-----new IndexController");
        $scope.hi = function () {
            console.log("-----hi")
        }
        this.$scope = $scope;
        this.b ="bbb"

    }

    get a(){
        return "aaa"
    }

    showMap() {
        SystemJS.import('angular1-baiduMap').then(  angular.bind(this,function (/* Module */m) {

            console.log("------loading baidu map");
            var map = new BMap.Map("container");          // 创建地图实例
            var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
            map.centerAndZoom(point, 15);
            console.log("-------$scope",this );
            this.$scope.$digest();
        }));

    }
}

IndexController.$inject = ['$scope'];