/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';
// 一个小题目：有一个 字符串数组，要求遍历：每隔1秒，打印其中下一个元素。

var co = require('co');

function sleep(time) {
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            resolve(111);
        }, time);
    });
}

function* bbb(arr) {
    console.log(new Date(), "--------------start");
    for (var i = 0; i < arr.length; i++) {
        yield sleep(1000);
        console.log(new Date(), arr[i]);
    }
    console.log(new Date(), "--------------end");
}

co(bbb, ["aaa", "bbb", "ccc"]);