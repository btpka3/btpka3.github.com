/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';
function sleep(time) {
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            resolve(111);
        }, time);
    });
}

function myAwait(p) {
    p.then(function (date) {

    }, function (err) {

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


