import "bootstrap/dist/css/bootstrap.css";
import "./index.css";
import "babel-polyfill";
import MyAmdModule from "./MyAmdModule.js";
import MyCmdModule from "./MyCmdModule.js";
import MyEs6Module from "./MyEs6Module.js";
//import "imports-loader?this=>window!./MyShimmingModule.js";
import "./MyShimmingModule.js";
import "./MyShimmingModule2-1.js";
import LoadBootStrap from "./LoadBootStrap.js";
import LoadBaiduMap from "./LoadBaiduMap.js";
import "./index-scss.scss";
import {bbb} from "./MyAsyncModule.js";

window.amdHi = function () {
    var myAmdModule = new MyAmdModule();
    console.log("--------------------index.js amdHi() : " + myAmdModule.hi("111"));
};
window.cmdHi = function () {
    var myCmdModule = new MyCmdModule();
    console.log("--------------------index.js cmdHi() : " + myCmdModule.hi("222"));
};

window.es6Hi = function () {
    var myEs6Module = new MyEs6Module();
    console.log("--------------------index.js es6Hi() : " + myEs6Module.hi("333"));
};

window.shimHi = function () {
    console.log("-----", MyShimmingModule);

    // FIXME: 如何加载？
    var myShimmingModule = new MyShimmingModule();
    console.log("--------------------index.js shimHi() : " + myShimmingModule.hi("444"));
};
window.shimHi2 = function () {


    console.log("-----", MyShimmingModule2);
    // require("./MyShimmingModule2-1.js");

    // FIXME: 如何加载？
    var myShimmingModule = new MyShimmingModule2();
    console.log("--------------------index.js shimHi2() : " + myShimmingModule2.hi("444"));
};


window.loadBt = function () {

    var loadBootStrap = new LoadBootStrap();
    console.log("--------------------index.js loadBt() : " + loadBootStrap.hi("555"));
};

window.loadBdMap = function () {
    var loadBaiduMap = new LoadBaiduMap();
    console.log(new Date() + "--------------------index.js loadBaiduMap() : " + loadBaiduMap.hi("666"));
};

window.lazyLoadMe = function () {

    require.ensure(["./LazyLoadMe.js"], function () { // 这个语法痕奇怪，但是还是可以起作用的
        var LazyLoadMe = require('./LazyLoadMe.js');
        var lazyLoadMe = new LazyLoadMe();
        console.log(new Date() + "--------------------index.js lazyLoadMe() : " + lazyLoadMe.hi("777"));
    });
};


window.asyncTest = function () {

    bbb(999).then(function (result) {
        console.log(new Date() + "----------- asyncTest : " + result);
    })
};



