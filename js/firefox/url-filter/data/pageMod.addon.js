var pageMod = require("sdk/page-mod")
pageMod.PageMod({
    include: "*",
    contentScriptFile: "./pageMod.js",
    contentStyleFile: "./pageMod.css",
    onAttach: function (worker) {
        worker.port.emit("getElements", "title");
        worker.port.on("gotElement", function (elementContent) {
            console.log("gotElement : " + elementContent);
        });
        worker.port.on("modifyImg", function (imgs) {
            //console.log("modifyImg " + imgs.length);
            printObj(imgs, "modifyImg : ");
            if (imgs.length > 0) {
                console.log("imgs[0].src = " + imgs[0].src);
            }

        });

        worker.port.on("modifyObj", function (obj) {
            console.log("modifyObj 1 : obj = " + JSON.stringify(obj));
            printObj(obj, "modifyObj : ");
            obj.b.push(4);
            if (obj.c) {
                obj.c++;
            } else {
                obj.c = 1;
            }
            worker.port.emit("objModified");
            console.log("modifyObj 2 : obj = " + JSON.stringify(obj));
        });
    }
});

function printObj(o, prefix, p) {
    "use strict";

    prefix = prefix ? prefix : "";
    p = p ? p + "." : "";

    for (let key in o) {
        //if (o.hasOwnProperty(key)) {
        let value = o[key];
        if ('object' == typeof (value)) {
            console.log("~~~~~~~~~~");
            printObj(value, prefix, p + key);
            console.log("~~~~~~~~~~");
        } else {
            console.log("" + prefix + p + key + " = " + value);
        }
        //} else {
        //    console.log(key + " ~~~~~~~~~~ " + o[key]);
        //}
    }
}