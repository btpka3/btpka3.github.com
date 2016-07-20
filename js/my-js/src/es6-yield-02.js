/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';

function aaa(a1) {
    console.log("----------- aa0: ", new Date(), a1);
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            console.log("----------- aa1: ", new Date(), a1);
            resolve(111 + a1);
        }, 1000);
    });
}


function bbb(b1) {
    console.log("----------- bb0: ", new Date(), b1);
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            console.log("----------- bb1: ", new Date(), b1);
            resolve(222 + b1);
        }, 2000);
    });
}

function* ccc(a1) {
    console.log("----------- 01 : ", new Date(), a1);
    let b1 = yield aaa(a1);
    console.log("----------- 02 : ", new Date(), b1);
    let c1 = yield bbb(b1);
    console.log("----------- 03 : ", new Date(), c1);
    return 3000000 + c1;
}

console.log("----------- v- : ", new Date());

let enumerator = ccc(10000);
console.log("----------- v0 : ", new Date(), enumerator);


let v1 = enumerator.next(20000);
console.log("----------- v1 : ", new Date(), v1);
v1.value.then((d)=> {
    console.log("----------- v10: ", new Date(), d);

    let v2 = enumerator.next(30000);
    console.log("----------- v2 : ", new Date(), v2);
    v2.value.then((d)=> {
        console.log("----------- v20: ", new Date(), d);

        let v3 = enumerator.next(40000);
        console.log("----------- v3 : ", new Date(), v3);
    });
});


