//index.js
//获取应用实例

var lib = require('../../lib/lib.js');

var app = getApp();


var a = {
    a: "aaa"
};
var b = {
    b: "bbb",
    c: "bbc"
};
var c = {
    c: "ccc"
};
var d = [1, 2, 3];
Object.assign(a, b, c, d);


var data = {
    motto: 'Hello World',
    userInfo: {},
    query: null,
    a: "aaa",
    o: {
        a: "aaa" + new Date(),
        b: "bbb" + new Date()
    },
    o1:"ooo1",
    arr: [
        "aa", "bb", "cc"
    ],
    v: 10,
    globalData: app.globalData
};


function testReq() {

    wx.request({
        url: "https://kingsilk.net/qh/mall/api/common/indexImg",
        success: function (resp) {
            console.log("-------testReq : ", resp)
        }
    })

}


Page({
    data: data,
    btn: function () {
        console.log("---------------111");
        data.o.a = "aab" + new Date();
        data.globalData.username = "zhang3" + new Date().getSeconds();
        this.setData(data);
        testReq();
    },


    inp: function (e) {
        data.v = e.detail.value;
        data.arr.length = 0;
        for (var i = 0; i < data.v; i++) {
            data.arr.push("data-" + i)
        }
        this.setData(data);

    },
    o: {
        test: function () {
            console.log("-----------o.test");
        }
    },
    ooo1: function (e) {
        console.log("-----------o1");
    },


    onLoad: function (option) {
        console.log("---------------app.globalData", app.globalData, a);
        lib.sayHi();
    }
})
;
