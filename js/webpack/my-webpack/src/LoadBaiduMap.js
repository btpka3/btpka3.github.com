console.log("----- LoadBaiduMap is loaded.");
function LoadBootStrap() {
    console.log("----- LoadBaiduMap is newed.");

    this.hi = function (name) {

        var $script = require("scriptjs");

        // "http://api.map.baidu.com/api?v=2.0&ak=AA9iQNKiR6Dp8YKZnMQjGSev",
        // "https://api.map.baidu.com/getscript?v=2.0&ak=AA9iQNKiR6Dp8YKZnMQjGSev&services="

        window.HOST_TYPE = "2";
        window.BMap_loadScriptTime = (new Date).getTime();
        $script([
            "https://api.map.baidu.com/getscript?v=2.0&ak=AA9iQNKiR6Dp8YKZnMQjGSev&services=",
        ], 'baiduMap');

        $script.ready('baiduMap', function () {
            var map = new BMap.Map("container");          // 创建地图实例
            var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
            map.centerAndZoom(point, 15);
            console.log("----- LoadBaiduMap drawed.");
        });
        return `LoadBaiduMap#hi ${name}`
    };
}
module.exports = LoadBootStrap;