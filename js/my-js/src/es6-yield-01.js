/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';

function* bbb() {
    yield 3;
    yield 4;
}

function* ccc() {
    yield 1;
    yield 2;
    yield* bbb();
    yield 5;
}


let enumerator = ccc();
console.log(enumerator.next());
console.log(enumerator.next());
console.log(enumerator.next());
console.log(enumerator.next());
console.log(enumerator.next());
console.log(enumerator.next());
console.log(enumerator.next());
