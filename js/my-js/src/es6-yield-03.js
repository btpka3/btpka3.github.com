/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
 */

'use strict';

function aaa(done) {
    console.log(new Date(), "aaa");
    done("aaa");
    return "aaa1";
}

function bbb(done) {
    console.log(new Date(), "bbb");
    done("bbb");
}


function*xxx(){
    console.log(new Date(), "--------aaa" );
     yield  bbb();
    console.log(new Date(), "--------bbb" );
}
var eee = xxx();
eee.next();
eee.next();
eee.next();
