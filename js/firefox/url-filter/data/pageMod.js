//window.alert("Page matches ruleset");
console.log("Page matches ruleset");
var obj = {a: "aaa", b: [1, 2, 3]};

self.port.on("objModified", function (params) {
    "use strict";
    console.log("pageMod.js : objModified 2 = " + JSON.stringify(obj));
});

self.port.on("getElements", function (tag) {
    var elements = document.getElementsByTagName(tag);
    for (var i = 0; i < elements.length; i++) {
        self.port.emit("gotElement", elements[i].innerHTML);
    }

    var imgs = document.getElementsByTagName("img");
    console.log("pageMod.js : loads imgs " + (imgs instanceof Array) + "  length =" + imgs.length + JSON.stringify(imgs) + ", ");
    if (imgs.length > 0) {
        console.log("pageMod.js : imgs[0].src = " + imgs[0].src);
    }
    self.port.emit("modifyImg", imgs);
    obj.imgs = imgs;
    console.log("pageMod.js : objModified 1 = " + JSON.stringify(obj));
    self.port.emit("modifyObj", obj);
});
