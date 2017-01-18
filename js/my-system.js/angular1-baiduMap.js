var baiduMapModuleName = "bMap";
// if (!SystemJS.has(baiduMapModuleName)) {
    window.HOST_TYPE = "2";
    window.BMap_loadScriptTime = (new Date).getTime();
    import   "baiduMap"
//     SystemJS.import('https://api.map.baidu.com/getscript?v=2.0&ak=AA9iQNKiR6Dp8YKZnMQjGSev&services=').then(function (m) {
//         console.log("m.BMap = ",m.BMap);
//         SystemJS.set(baiduMapModuleName, m);
//     }, (err) => {
//         console.log("百度地图加载出错", err)
//     });
// }
