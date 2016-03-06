$(function () {

    var m = require("./m");


    // 监控 输入框 值的变更
    $("#kw").blur(function (e) {
        console.log("^^^^^^^^^^^^^^^^^^ kw : " + $(event.target).val());
    });


    console.log("^^^^^^^^^^^^^^^^^^ window.require : " + window.require);


    console.log("^^^^^^^^^^^^^^^^^^ nw.require : " + nw.require);
    console.log("^^^^^^^^^^^^^^^^^^ nw.App.dataPath : " + nw.App.dataPath);
    console.log("^^^^^^^^^^^^^^^^^^ c : " + c);
    console.log("^^^^^^^^^^^^^^^^^^ colors : " + colors);


    setInterval(function () {
            global.arr[2]++;
            m.arr[2]++;
            console.log("=================== browser2 :"
                + " global.arr = " + JSON.stringify(global.arr)
                + " m.arr = " + JSON.stringify(m.arr)
                + "   " + global.require)
        },
        3000
    );

});

var colors = require('colors');

