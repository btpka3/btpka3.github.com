/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';
// 模拟一下 koa.js


var co = require('co');

function* aaa() {
    var res = yield [
        Promise.resolve(1),
        Promise.resolve(2),
        Promise.resolve(3),
    ];
    console.log(res); // => [1, 2, 3]
}

var e = aaa();
console.log("1=======", e.next());
console.log("2=======", e.next());

//co(aaa).catch(onerror)