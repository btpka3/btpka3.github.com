// 这里因为没有任何依赖，所以省略第一个参数
define(function () {
    console.log("------------ `load-amd.html.aaa.js` loaded ");
    return {
        a1: "111",
        a2: function (msg) {
            return "aaa " + msg;
        }
    };
});


