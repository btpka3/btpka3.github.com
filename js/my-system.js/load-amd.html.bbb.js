define(['exports', "./load-amd.html.aaa.js"], function (exports, a) {
    console.log("------------ `load-amd.html.bbb.js` loaded ");
    exports.b1 = "222";
    exports.b2 = function (msg) {
        return a.a2(msg) + " bbb.";
    }
});

