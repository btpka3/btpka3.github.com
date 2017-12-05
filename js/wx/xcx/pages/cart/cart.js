// https://pro.modao.cc/app/uTcdawq0KYDEeaJjjhQnEe38U3TWa4A#screen=sF9A8879F661482822509401
// 酒店主页

var app = getApp();
var data = {
    g: app.globalData,      // 全局数据
    followed: false,        // 是否已经关注钱皇
    footer1: {
        menu: "shelves"
    }
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
        this.setData({c: "ccc" + new Date()})
        wx.showToast({
            title: "aaaa",
            icon: "success"
        })
    },
    onLoad: function (option) {

        console.log('onLoad', arguments)
        var that = this;
        //调用应用实例的方法获取全局数据
        app.getUserInfo(function (userInfo) {
            //更新数据
            that.setData({
                userInfo: userInfo
            })
        })
    }
})
