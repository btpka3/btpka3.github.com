exports.a1 = "111";
exports.a2 = function (msg) {
    return "aaa " + msg;
};
console.log("------------ `load-cmd.html.aaa.js` loaded ", module, module.id, module.uri);

/*
 module = {
    exports : {
        a1  : "111",
        a2  : function(msg}{}
    },
    id      : "http://localhost:63333/front-workspace/my-system.js/load-cmd.html.aaa.js",
    paths   : [],
    require : function(e){}
 }
*/