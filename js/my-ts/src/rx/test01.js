"use strict";
exports.__esModule = true;
// 因为 依赖的 rxjs 是 commonjs 格式的，所以要 `import * as`
//import * as Rx from "rxjs/Rx";
var Observable_1 = require("rxjs/Observable");
//import {Rx} from 'rxjs/Rx';
require("rxjs/add/observable/of");
require("rxjs/add/operator/map");
require("rxjs/add/operator/reduce");
var a = Observable_1.Observable.of(1, 2, 3)
    .map(function (x) { return x * 2; })
    .reduce(function (r, t, idx) {
    return r + t;
}, 0);
console.log("test01 : a instanceof Rx.Observable == " + (a instanceof Observable_1.Observable));
console.log(a);
a.subscribe(function (n) { return console.log("====== result :" + n); });
