/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';
// 注意： yield 的表达式如果是 promise，则其并不会等其 resolve/reject 之后再继续，而是立即往后继续
//       所以 co.js 以及 ES7 中 async/await 就是用来解决该问题的

function sleep(time) {
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            resolve(111);
        }, time);
    });
}
function* ccc() {
    yield 1;
    yield 2;
    console.log(new Date(), "aaa");
    yield sleep(1000);
    console.log(new Date(), "bbb");
    yield 3;
}


let enumerator = ccc();
console.log(new Date(), enumerator.next());
console.log(new Date(), enumerator.next());
console.log(new Date(), enumerator.next());
console.log(new Date(), enumerator.next());
console.log(new Date(), enumerator.next());

