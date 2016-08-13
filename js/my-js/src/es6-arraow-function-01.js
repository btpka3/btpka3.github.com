/**
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Functions/Arrow_functions
 */

'use strict';

var _this = this;
var a = [
    "Hydrogen",
    "Helium",
    "Lithium",
    "Beryl"
];

var a2 = a.map(function (s) {
    return s.length;
});

var a3 = a.map(s => s.length);

console.log(new Date(), "a =", a);
console.log(new Date(), "a2=", a2);
console.log(new Date(), "a3=", a3);


console.log("==================================b");
var b = {
    b1: "bbb",
    b2: function () {
        console.log(new Date(), "b2 =", this);
    },
    b3: () => {
        // 注意：箭头函数没有 bing this, 因此，this 默认是当前环境中上一层的this。
        //  (does not bind its own this, arguments, super, or new.target)
        console.log(new Date(), "b3 =", this, this === _this);
    }
};
console.log(new Date(), "b  =", b);
b.b2();
b.b3();

console.log("==================================c");
var c = {};
c.c1 = "ccc";
c.c2 = function () {
    console.log(new Date(), "c2 =", this);
};
c.c3 = () => {
    // 注意：箭头函数没有 bing this, 因此，this 默认是当前环境中上一层的this。
    //  (does not bind its own this, arguments, super, or new.target)
    console.log(new Date(), "c3 =", this, this === _this);
};
console.log(new Date(), "c  =", c);
c.c2();
c.c3();


console.log("==================================");
function Timer() {
    this.seconds = 0;
    this.i = setInterval(() => this.seconds++, 1000);
}
var timer = new Timer();
setTimeout(() => {
    console.log(timer.seconds);
    clearInterval(timer.i);
}, 3100);