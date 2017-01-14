import * as a from "./load-esm.html.aaa.js";

console.log("------------ `load-esm.html.bbb.js` loaded ");

export var b1 = "222";
export function b2(msg) {
    return a.a2(msg) + " bbb.";
}
