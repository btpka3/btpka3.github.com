$(function () {



    // 监控 输入框 值的变更
    $("#kw").blur(function (e) {
        console.log("^^^^^^^^^^^^^^^^^^ kw : " + $(event.target).val());
    });


    console.log("^^^^^^^^^^^^^^^^^^ require : " + window.require);

    var nw = require('nw.gui');
    nw.arr = [111];

    console.log("^^^^^^^^^^^^^^^^^^ nw.arr : " + JSON.stringify(nw.arr));
});

