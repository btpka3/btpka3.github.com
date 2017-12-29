// 因为 依赖的 rxjs 是 commonjs 格式的，所以要 `import * as`
//import * as Rx from "rxjs/Rx";


import {Observable} from 'rxjs/Observable';
// import {Observable} from 'rxjs/Rx';

//import {Rx} from 'rxjs/Rx';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/reduce';

var a = Observable.of(1, 2, 3)
    .map((x: number) => x * 2)
    .reduce((r: number, l: number, idx: number) => {
        return r + l;
    }, 0)
;

console.log("test01 : a instanceof Rx.Observable == " + (a instanceof Observable));

console.log(a);
a.subscribe((n: number) => console.log("====== result :" + n));
