//app.js
var KsFooter = require('./lib/ks-footer/ks-footer.js');

App({
    onLaunch: function () {
        //调用API从本地缓存中获取数据
        var logs = wx.getStorageSync('logs') || [];
        logs.unshift(Date.now());
        wx.setStorageSync('logs', logs);


        for (var libKey in this.lib) {
            if (this.lib.hasOwnProperty(libKey)) {
                let lib = this.lib[libKey];
                lib.wx = wx;
                lib.app = this;
            }
        }
    },
    getUserInfo: function (cb) {
        var that = this;
        if (this.globalData.userInfo) {
            typeof cb == "function" && cb(this.globalData.userInfo)
        } else {
            //调用登录接口
            wx.login({
                success: function () {
                    wx.getUserInfo({
                        success: function (res) {
                            that.globalData.userInfo = res.userInfo
                            that.globalData.signature = res.signature

                            console.log("----------------getUserInfo().signature = ", res.signature);
                            typeof cb == "function" && cb(that.globalData.userInfo)
                        }
                    })
                }
            })
        }
    },


    lib: {
        ksFooter: new KsFooter(this, wx, [
            'pages/home/home.wxml',
            'pages/cart/cart.wxml',
            'pages/cart/cart.wxml'
        ], 0)
    },

    globalData: {
        userInfo: null,
        username: "zhang3",

        // tpl: { // 模板相关的数据
        //     ksFooter: {
        //         selectedIdx: 0
        //     }
        // },
        page: {

            // // 页面上要用的数据, 各个页面配置
            // "pages/home/home": {
            //
            // },
            // "pages/cart/cart": {
            //
            // }
        }

    }
});

