'use strict';


function hi(msg) {
    console.log("=== " + msg + " " + (this ? this.a : "undefined"));
}

//var a="not work";
global.a = "global.a";

var o = {
    a: "o.a"
};
o.hi = hi;

o.hi("aaa");

hi("bbb");
hi.bind(o, "ccc")();
