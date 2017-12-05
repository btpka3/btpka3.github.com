// https://pro.modao.cc/app/uTcdawq0KYDEeaJjjhQnEe38U3TWa4A#screen=sF9A8879F661482822509401
// 酒店主页

var app = getApp();

var a = app.globalData;

var pagePath = "pages/home/home";


var p = a.page[pagePath] = {
    followed: true,      // 是否已经关注钱皇
    ksFooter: {
        idx: 0
    }
};

var data = {
    a: a,   // 全局数据
    p: p    // 页面数据
};


Page({
    data: data,

    //事件处理函数
    bindViewTap: function () {
        wx.navigateTo({
            url: '../logs/logs'
        })
    },
    onShow: function () {
        this.setData(data);
    },
    btn: function () {
        console.log("---------------111");
    },
    onLoad: function (option) {
        var that = this;
        // 调用应用实例的方法获取全局数据
        app.getUserInfo(function (userInfo) {
            // 更新数据
            that.setData(data);
        });
    },


});
