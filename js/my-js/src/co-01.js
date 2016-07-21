/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';
var co = require('co');

function sleep(time) {
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            resolve(111);
        }, time);
    });
}

console.log(new Date(), "--------------start");
co(function*() {
    yield sleep(1000);
    console.log(new Date(), "aaa");
    yield sleep(1000);
    console.log(new Date(), "bbb");
    yield sleep(1000);
    console.log(new Date(), "ccc");
    return "ddd";
}).then(function (value) {
    console.log(new Date(), "--------------end, " + value);
}, function (err) {
    console.error(err.stack);
});

