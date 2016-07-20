/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';

function aaa(i) {
    return i * 10;
}

function* bbb(i) {
    var v = i;
    console.log("--------------- b0 : ", v);
    v = yield aaa(v);
    console.log("--------------- b1 : ", v);
    v = yield (v);
    console.log("--------------- b2 : ", v);
    return 999;
}

function* ccc(i) {
    var v = i;
    console.log("----- a0 : ", v);
    v = yield aaa(v);

    console.log("----- a1 : ", v);
    v = yield aaa(v);

    console.log("----- a2 : ", v);
    v = yield* bbb(v);

    console.log("----- a3 : ", v);
    v = yield aaa(v);

    console.log("----- a4 : ", v);

    return 888;
}


let enumerator = ccc(1);
console.log("--------------------------- c0 : ", enumerator);
console.log("--------------------------- c1 : ", enumerator.next(2));
console.log("--------------------------- c2 : ", enumerator.next(3));
console.log("--------------------------- c3 : ", enumerator.next(4));

console.log("--------------------------- c4 : ", enumerator.next(5));
console.log("--------------------------- c5 : ", enumerator.next(6));
console.log("--------------------------- c6 : ", enumerator.next(7));

console.log("--------------------------- c7 : ", enumerator.next(8));
