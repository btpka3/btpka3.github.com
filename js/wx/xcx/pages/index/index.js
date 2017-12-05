//index.js
//获取应用实例
var app = getApp();
var data = {
    motto: 'Hello World',
    userInfo: {},
    query: null,
    b: "bbb",
    globalData: app.globalData
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
        // setInterval(function () {
        //     wx.showToast({
        //         title: "aaaa" + new Date(),
        //         icon: "success"
        //     })
        // }, 10000);
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
