/**
 * Promise
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise
 *
 */
'use strict';
// 一个小题目：有一个 字符串数组，要求遍历：每隔1秒，打印其中下一个元素。
var messages = ["aaa", "bbb", "ccc"];
var pArr = [];
for (var i = 0; i < messages.length; i++) {

    // var msg = messages[i];
    c(messages[i]);

    // pArr.push(new Promise((resolve, reject)=> {
    //     setTimeout(function () {
    //         console.log(new Date(), msg);
    //         resolve();
    //     }, 1000);
    // }));
    // if (i > 0) {
    //     pArr[i - 1].then(pArr[i]);
    // }
}

function c(msg, newPromise) {
    pArr.push(new Promise((resolve, reject)=> {
        console.log(new Date(), "222");
        setTimeout(function () {
            console.log(new Date(), msg);
            resolve(msg + " : 1 ");
        }, 2000);
    }));
    if (pArr.length >= 2) {
        console.log(new Date(), "111");
        pArr[pArr.length - 2].then((r)=> {
            console.log(new Date(), "---" + r);
            return pArr[pArr.length - 1];
        });
    }
}


