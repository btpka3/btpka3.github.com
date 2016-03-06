var colors = require('colors');

// test: 通过 全局 node 环境传递共享变量
global.arr = [
    100000,  // test node
    200000,  // test browser window1
    300000   // test browser window2
];
var m = require("./m");

// FIXME 1. 立即执行的话，打印不出来。
// FIXME 2. setTimeout 被执行两次
setTimeout(
    function () {
        console.log("8888888888888888888888888888888888888888888 " + global.arr);
    },
    10
);


setInterval(function () {
        var ele = window.document.getElementById("submitBtn");
        global.arr[0]++;
        m.arr[0]++;

        //+ ele ? ele.innerText : "null"
        console.log("=================== node1    :"
            + " global.arr = " + JSON.stringify(global.arr)
            + " m.arr = " + JSON.stringify(m.arr)
        );
    },
    3000
);
