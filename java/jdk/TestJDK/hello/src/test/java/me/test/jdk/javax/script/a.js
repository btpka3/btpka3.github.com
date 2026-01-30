/*创建时间2016-03-28 20:16:39 PM */
define(function (require, exports, module) {
    function init() {
        B();
        var toUrl = appUtils.getLocalCookie("user_to_url");
        var userInfo = appUtils.getSStorageInfo("stock_userInfo");
        if (userInfo && userInfo != '') {
            appUtils.pageBack();
        }

        var a = r.setMainHeight(t, !0);
        $(t + " .picture").height(a - $(t + ".login_form").outerHeight() - 40);
        r.systemKeybord();
        $(t).css("overflow-x", "hidden");
        var uPre = appUtils.getSStorageInfo("_prePageCode", 'deal');
        u = appUtils.getPageParam("topage") || appUtils.getSStorageInfo("nextPage", !0) || (uPre == "account/activePhone" ? '' : uPre) || appUtils.getSStorageInfo("prePage", !0);
//      u = "/tztweb/zt/zt_pass.html";
        console.log("u=" + u);
        if (!u && toUrl && toUrl != "") {
            u = toUrl;
//      	appUtils.delLocalCookie("user_to_url");
        }
        console.log("u=" + u);
        if ("credit/asset/asset" == u) {
            $(t + ".top_title h3").text("融资融券登录"),
                $(t + ".select_box p").text("融资融券账号登录").attr("id", "RZRQZJACCOUNT");
            $(t + "#account").attr("placeholder", "请输入融资融券账号");
            $(t + "#password").attr("placeholder", "请输入融资融券密码");
            $(t + ".stockAHide").hide();
            $(t + ".creditHide").show();
            $(t + "#account").val("");
            $(t + "#password").val("");
            issaveaccount("creSavaAcc");
        } else {
            u || (u = "account/index");
            $(t + ".top_title h3").text("普通交易登录");
            $(t + ".select_box p").text("资金账号登录").attr("id", "ZJACCOUNT");
            $(t + ".stockAHide").show(),
                $(t + ".creditHide").hide();
            $(t + "#account").val("");
            $(t + "#password").val("");
            var appUa = navigator.userAgent.toLowerCase();
            console.log(appUa);
            //l.iAlert("appUa3="+appUa);
            if (appUa.indexOf('huanshoulv') >= 0) {
                //如果是换手率进入获取身份证号
                $('.j_login').remove();
                $('.ljs_login').show();
                $('.ljs_card').show();
                var cardCode = appUtils.getSStorageInfo("cardCode", !0);
                if (!cardCode) {
                    r.getCardId(function () {
                        cardCode = appUtils.getSStorageInfo("cardCode", !0);
                        getId(cardCode, 'C00069', 'shanxi');
                    });
                } else {
                    r.getCardId(function () {
                        cardCode = appUtils.getSStorageInfo("cardCode", !0);
                        getId(cardCode, 'C00069', 'shanxi');
                    });
//                  getId(cardCode,'C00069','shanxi');
                }
            } else if (appUa.indexOf('lufax') >= 0) {
                //如果是陆金所进入获取身份证号
                $('.j_login').remove();
                $('.ljs_login').show();
                $('.ljs_card').show();
                var cardCode = appUtils.getSStorageInfo("cardCode", !0);
                if (!cardCode) {
                    r.getCardId(function () {
                        cardCode = appUtils.getSStorageInfo("cardCode", !0);
                        getId(cardCode, 'B15714');
                    });
                } else {
                    r.getCardId(function () {
                        cardCode = appUtils.getSStorageInfo("cardCode", !0);
                        getId(cardCode, 'B15714');
                    });
//	                    getId(cardCode,'B15714');
                }
            } else {
                //否则获取上次保存的账号
                $('.ljs_login').remove();
                issaveaccount("ordSavaAcc");
            }

        }
        r.clearStateTime();
        b();
        "0" == m.platform && $(t + "#password").attr("readonly", !1);
    }

    function B() {
        var debug = m.debug;//1debug模式，模拟手机绑定，0正常模式，清除mockPhone
        var mockPhone = 13000000000;
        var activePhone = appUtils.getSStorageInfo("acitve_phone") || appUtils.getSStorageInfo("is_acitve_phone");
//	        alert(activePhone);
//	        alert(activePhone == mockPhone);
        if (debug == 0 && activePhone && activePhone == mockPhone) {
            appUtils.setLStorageInfo("acitve_phone", "");
            r.setCookie("");
            appUtils.setSStorageInfo("is_acitve_phone", "");
        }
        var a = appUtils.getSStorageInfo("is_acitve_phone");
        a && /^\d{11}$/.test(a) ? n.activePhone = a : r.checkActive()
    }

    function b() {
        service_common.randomImg({}, function (a) {
            $(t + "#randomImg").attr("src", a.MESSAGE);
            CheckToken = a.CHECKTOKEN;
        });
        /*var a = c(10);
         v = a;
         var b = m.global.validateimg + "?r=" + a + "&mobileKey=" + a;
         $("#randomImg").attr("src", b);*/
    }

    function c(a) {
        for (var b = "", c = 0; a > c; c++) b += Math.floor(10 * Math.random());
        return b;
    }

    function getId(cardCode, channelCode, tag) {
        //console.log(tag)
        service_hq.getLJSFund({cardnoencode: cardCode, channelNo: channelCode, tag: tag}, function (oData) {
            if ("0" > oData.ERRORNO) return l.iLoading(!1), l.iAlert(oData.ERRORMESSAGE), !1;
            console.log(oData);
            var result = JSON.parse(oData.RESULT);
            if (result.errorType == 0) {
                $('#idcard').val(oData.CARDNO);
                $('#account').val(result.fundAccount);
            } else {
                if (channelCode == 'B15714') {
                    l.iAlert((result.errorType === 10002 ? '非陆金所开户，无法登陆' : result.errorMessage))
                } else {
                    if (channelCode == 'C00069') {
                        l.iAlert((result.errorType === 10002 ? '非换手率开户，无法登陆' : result.errorMessage))
                    }
                }
            }
        })
    }

    function bindPageEvent() {
        appUtils.bindEvent($(t + ".top_title .icon_back"), function () {
            pageBack();
        }, "click");
        appUtils.bindEvent($(t + "#saveAcc"), function (a) {
            var b = $(this).find("input").attr("checked");
            if (b) {
                $(this).find("input").attr("checked", !1)
            } else {
                $(t + "#saveAcc").find("input").attr("checked", !0);
            }
            a.stopPropagation();
            /*b ? $(this).find("input").attr("checked", !1) : l.iAlert("在本机保存账号可能存在一定的风险，请您注意！", 0, function() {
             $(t + "#saveAcc").find("input").attr("checked", !0);
             }, "确认"), a.stopPropagation();*/
        });
        appUtils.bindEvent($(t + "#login"), function () {
            return w = $(t + "#loginKind p").attr("id") || 'ZJACCOUNT', e() ? (f(), void 0) : !1;
        }, "click");
        appUtils.bindEvent($(t + "#randomImg"), function (a) {
            b(), a.stopPropagation();
        });
        appUtils.bindEvent($(t + "#password"), function (a) {
            q.callMessage({
                moduleName: "trade",
                funcNo: "50210",
                pageId: "account_login",
                eleId: "password",
                doneLable: "done",
                keyboardType: "5"
            }), a.stopPropagation();
        }, "click");
        appUtils.bindEvent($(t + "#password"), function (a) {
            q.callMessage({
                moduleName: "trade",
                funcNo: "50211"
            }), a.stopPropagation();
        }, "blur");
        appUtils.bindEvent($(t + "#loginKind"), function (a) {
            $(t + " #loginKind ul").slideToggle("fast"), a.stopPropagation();
        });
        appUtils.bindEvent($(t + "#loginKind ul li"), function (a) {
            $(t + "#loginKind p").html($(this).find("a strong").html()), $(t + "#loginKind p").attr("id", $(this).attr("id"));
            var b = "请输入" + $(this).find("a strong").html().substring(0, $(this).find("a strong").html().length - 2);
            $(t + "#account").attr("placeholder", b), $(t + " #loginKind ul").slideToggle("fast"),
                w = $(this).attr("id"), a.stopPropagation();
        });
        appUtils.bindEvent($(t), function (a) {
            a.target !== document.activeElement && document.activeElement.blur();
        }, "click");
    }

    function e() {
        return p.isEmpty($(t + "#account").val()) ? (l.iMsg(-1, "登录账号不能为空"), l.iLoading(!1),
            !1) : p.isEmpty($(t + "#password").val()) ? (l.iMsg(-1, "密码不能为空"), l.iLoading(!1),
            !1) : /^[0-9a-zA-Z]{6,12}$/.test($(t + "#password").val()) ? !0 : (l.iMsg(-1, "密码只允许输入0-9、a-z、A-Z且长度为6-12位"),
            l.iLoading(!1), !1);
    }

    function f() {
        var a = $(t + "#account").val(),
            c = $(t + "#password").val(),
            d = $(t + "#ticket").val();
        /*service_common.checkRandomImg({CheckToken:CheckToken,CheckCode:d}, function(a) {
         if ("0" > a.ERRORNO) return l.iLoading(!1), l.iAlert(a.ERRORMESSAGE), !1;
         getkey();
         }, {
         isLastReq: !1
         });
         */
        service_common.getKey({}, function (a) {
            if ("0" > a.ERRORNO) return l.iLoading(!1), l.iAlert(a.ERRORMESSAGE), !1;
            var b = a.MODULUS, e = require("endecryptUtils");
            if (b) {
                appUtils.setSStorageInfo("modulusKey", b);
                appUtils.setSStorageInfo("modulus_id", a.MODULUS_ID);
            }
            modulus_id = a.MODULUS_ID;
            c && (c = e.rsaEncrypt(b, '10001', $.trim(c)));
            var weixincode = appUtils.getSStorageInfo("weixincode", !0)
            var WXcode = appUtils.getSStorageInfo("WXcode", !0)
            if (weixincode && WXcode) {
                f();
            } else if (WXcode) {

                //console.log("wx="+WXcode);
                //f();

                service_hq.getWXcodeInfo({code: WXcode}, function (e) {
                        console.log(e);
                        if (e.res_info && e.res_info.openid) {
                            appUtils.setSStorageInfo("weixincode", e.res_info.openid + ',' + (e.res_info.accountno || ''), !0)
                            f();
                        } else {
                            l.iAlert('请退出微信公众号并重新登录');
                        }
                    }, {
                        isGlobal: !0,
                        isShowWait: !0
                    }
                )
            } else {
                f();
            }
        }, {
            isLastReq: !1
        });
        var e = "";
        e = "credit/asset/asset" == u ? "credit_userInfo" : "stock_userInfo";
        var f = function () {
            var f = {
                entrust_way: n.entrust_way,
                input_type: w,
                input_content: a,
                modulus_id: modulus_id,
                password: c,
                CheckToken: CheckToken,
                CheckCode: d,
                content_type: "",
                mobilekey: v,
                account_type: e,
                MobileCode: appUtils.getSStorageInfo("is_acitve_phone")
            };
            service_common.login(f, function (a) {
                if (0 > a.ERRORNO) return $(t + "#ticket").val(""), b(), l.iLoading(!1), l.iAlert(a.ERRORMESSAGE),
                    !1;
                var d = a;
                //if (a.DataSet ? d = a.DataSet : a.results && (d = a.results), d && d.length > 0) {
                //d[0].password = c, d[0].loginType = w;
                //s.zxgAsyncStoreFunc(d[0].fund_account);
                var f = {
                    fundAccount: d.FUNDACCOUNT,
                    clientId: d.USERCODE
                };
                r.bindFd(f, function () {
                    if (a.ACCOUNTLIST) {
                        var accountlist = a.ACCOUNTLIST.split('\3'), accountlistobj = {};
                        for (var aln = 0; aln < accountlist.length; aln++) {
                            if (accountlist[aln] == '') {
                                continue;
                            }
                            var listarray = accountlist[aln].split('|');
                            accountlistobj[listarray[0]] = {
                                account: listarray[1],
                                accounttypename: listarray[2]/*,
                                 accounttype:listarray[0]*/
                            }
                        }
                        appUtils.setSStorageInfo('accountlistobj', JSON.stringify(accountlistobj));
                    }
                    var e = JSON.stringify(d),
                        f = $(t + "#account").val(),
                        g = $(t + "#saveAcc .c1").attr("checked");
                    if ("credit/asset/asset" == u) {
                        h("creSavaAcc", g, f), appUtils.setSStorageInfo("credit_userInfo", e);
                        for (var i = {}, j = 0, m = d.length; m > j; j++) {
                            var o = d[j].exchange_type, p = d[j].holder_kind, v = "stock" + o + p;
                            i[v] = d[j];
                        }
                        appUtils.setSStorageInfo("StockAccount", JSON.stringify(i), !0);
                    } else {
                        h("ordSavaAcc", g, f);
                        appUtils.setSStorageInfo("stock_userInfo", e);
                        appUtils.setSStorageInfo("account", f);
                        appUtils.setSStorageInfo("jyloginflag", 2);
                        appUtils.setSStorageInfo("fund_account", d.FUNDACCOUNT)
                        for (var x in d) {
                            var val = d[x];
                            if (x == 'ACCOUNTLIST') {
                                val = val.replace(/\3/g, '\r\n');
                            }
                            if (typeof val != 'string') {
                                val = val.join('\r\n');
                            }
                            appUtils.setSStorageInfo(x.toLowerCase(), val);
                        }
                    }
                    q.callMessage({
                        funcNo: "60200",
                        flag: "0",
                        data: {
                            cust_code: ''
                        }
                    });
                    appUtils.setSStorageInfo("clientinfo", '');
                    appUtils.setSStorageInfo("jsessionid", '');
                    appUtils.setSStorageInfo("token", d.TOKEN);
                    appUtils.setSStorageInfo("intacttoserver", d.INTACTTOSERVER);
                    appUtils.setSStorageInfo("_isLoginIn", "true");
                    /*a.DataSet3 && (n.trade_flag = a.DataSet3[0].trade_flag), */
                    r.initLogoutTime();
                    l.iLoading(!1);
                    var x = appUtils.getSStorageInfo("_loginInPageCode"),
                        y = appUtils.getSStorageInfo("_loginInPageParam");
                    if (appUtils.clearSStorage("_loginInPageCode"), appUtils.clearSStorage("_loginInPageParam"), appUtils.setSStorageInfo("clientid", d.FUNDACCOUNT, !0),
                    x && "" != x) {
                        var z = JSON.parse(y);
                        appUtils.pageInit("account/login", x, z);
                    } else {
                        var sUrl = decodeURIComponent(u);
                        if (sUrl.indexOf('.htm') > -1) {
                            //T.changeURL(sUrl);
                            //window.sendDirect4Shell(u);
                            // if(window.navigator.userAgent.indexOf("Mac")>-1){
                            //     sUrl = sUrl.replace(/http:\/\//g,'');
                            // }
                            var nextPage = appUtils.getSStorageInfo("nextPage", !0);
                            if (sUrl == nextPage) {
                                appUtils.setSStorageInfo("nextPage", '', !0);
                                if (sUrl.indexOf('/') == 0) {
                                    sUrl = sUrl.slice(1);
                                }
                                window.sendDirect4Shell(sUrl);
                            } else {
                                var prePage = appUtils.getSStorageInfo("prePage", !0);
                                if (prePage) {
                                    // appUtils.clearSStorage("linkPage")
                                    appUtils.setSStorageInfo("prePage", '', !0);
                                    window.history.back();
                                } else {
                                    console.log("sUrl=" + sUrl);
                                    if (sUrl.indexOf('/') == 0) {
                                        sUrl = sUrl.slice(1);
                                    }
                                    window.sendDirect4Shell(sUrl);
                                    appUtils.delLocalCookie("user_to_url");
                                }
                            }
                        } else {
                            var param = appUtils.getPageParam();
                            param.tofunc && (param.isfunc = 1);
                            appUtils.pageInit("account/login", u, param);
                        }
                    }
                })
                /*} else l.iLoading(!1), b(), $(t + "#ticket").val(""), q.callMessage({
                 funcNo: "60200",
                 flag: "1"
                 }), l.iAlert("登录失败！");*/
            }, {
                isLastReq: !1
            });
        };
    }

    function issaveaccount(a) {
        if ("0" != m.platform) {
            accNum = q.callMessage({
                funcNo: "50043",
                key: a
            }).results[0].value
        } else {
            accNum = appUtils.getLStorageInfo(a);
        }
        if (accNum && "null" != accNum && "0" != accNum) {
            $(t + "#saveAcc .c1").attr("checked", !0);
            $(t + "#account").val(accNum)
        } else {
            $(t + " #saveAcc .c1").attr("checked", !1);
        }
        /*accNum = "0" != m.platform ? q.callMessage({
         funcNo: "50043",
         key: a
         }).results[0].value : appUtils.getLStorageInfo(a), accNum && "null" != accNum && "0" != accNum ? ($(t + "#saveAcc .c1").attr("checked", !0),
         $(t + "#account").val(accNum)) : $(t + " #saveAcc .c1").attr("checked", !1);*/
    }

    function h(a, b, c) {
        if (b) {
            if ("0" != m.platform) {
                q.callMessage({
                    funcNo: "50042",
                    key: a,
                    value: c
                })
            } else {
                appUtils.setLStorageInfo(a, c);
            }
        } else if ("0" != m.platform) {
            q.callMessage({
                funcNo: "50042",
                key: a,
                value: "0"
            })
        } else {
            appUtils.setLStorageInfo(a, "0")
        }
        /*b ? "0" != m.platform ? q.callMessage({
         funcNo: "50042",
         key: a,
         value: c
         }) : appUtils.setLStorageInfo(a, c) : "0" != m.platform ? q.callMessage({
         funcNo: "50042",
         key: a,
         value: "0"
         }) : appUtils.setLStorageInfo(a, "0");*/
    }

    function destroy() {
        //r.saveRecords("410"),
        u = "", v = "",
            $(t + "#account").val(""),
            $(t + "#password").val(""),
            $(t + "#ticket").val(""),
            $(t + "#account").attr("placeholder", "请输入资金账号"),
            $(t + "#password").attr("placeholder", "请输入交易密码"),
            $(t + "#loginKind ul").hide(), q.callMessage({
            moduleName: "trade",
            funcNo: "50211"
        });
    }

    function pageBack() {
        var prePage = appUtils.getSStorageInfo("prePage", !0);
        if (prePage) {
            var jsFunc = appUtils.getSStorageInfo(prePage + "+jsfuncname", !0);
            jsFunc && window.sessionStorage.removeItem(prePage + "+jsfuncname");
        }
        appUtils.pageInit("account/login", "account/index", {});
    }

    var appUtils = require("appUtils"),
        l = require("layerUtils"),
        m = require("gconfig"),
        n = m.global,
        service_common = require("service_common"),
        service_hq = require("service_hq"),
        p = require("validatorUtil"),
        q = {
            callMessage: function () {
            }
        },
        r = require("common"),
        s = require("commonHq"),
        t = "#account_login ",
        u = "",
        v = "",
        w = "0",
        CheckToken = "",
        modulus_id = '',
        x = {
            init: init,
            bindPageEvent: bindPageEvent,
            destroy: destroy,
            pageBack: pageBack
        };
    module.exports = x;
});
/*创建时间 2016-03-28 20:16:39 PM */