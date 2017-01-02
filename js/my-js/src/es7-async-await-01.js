/**
 *
 */

'use strict';
// 如果在node中执行，则包含以下语句。注意：写在这里不管用，生成的代码会插入到最前面。
//require("babel-polyfill");

// FIXME not work
async function aaa(a1) {
    console.log("----------- aa0: ", new Date(), a1);
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            console.log("----------- aa1: ", new Date(), a1);
            resolve(111 + a1);
        }, 1000);
    });
}

async function bbb(v) {
    console.log("----------- bb0: ", new Date(), v);
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            console.log("----------- bb1: ", new Date(), v);
            resolve(222 + v);
        }, 2000);
    });
}


async function ccc(a1) {
    console.log("----------- 01 : ", new Date(), a1);
    let b1 = await aaa(10000);
    console.log("----------- 02 : ", new Date(), b1);
    let c1 = await bbb(20000);
    console.log("----------- 03 : ", new Date(), c1);
    return 3000000 + c1;
}



var s = ccc(999);
console.log("----------- s : ", s);