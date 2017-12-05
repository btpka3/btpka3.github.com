
// 因为 依赖的 rxjs 是 commonjs 格式的，所以要 `import * as`
import * as Rx from "rxjs/Rx";

var a = Rx.Observable.of(1, 2, 3)
    .map(x => x * 2)
    .reduce()
;

console.log("test01 : a instanceof Rx.Observable == " + (a instanceof Rx.Observable));
console.log(a) ;
