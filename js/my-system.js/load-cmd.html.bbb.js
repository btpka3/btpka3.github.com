var a = require("./load-cmd.html.aaa.js");


module.exports = {
    b1: "222",
    b2: function (msg) {
        return a.a2(msg) + " bbb.";
    }
};


console.log("------------ `load-cmd.html.bbb.js` loaded ", module, module.id, module.uri);