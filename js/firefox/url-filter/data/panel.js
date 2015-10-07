"use strict";
$(function () {
    window.addEventListener('click', function (event) {
        let t = event.target;
        if (t.nodeName == 'A') {
            addon.port.emit('click-link', t.toString());
            //self.port.emit('click-link', t.toString());
        }
    });

    addon.port.on("click", function (slct) {
        //console.log("begin to trigger click");
        alert(1);
        $(slct).trigger('click');
    });

    addon.port.emit('resizePanel', {
        width: document.documentElement.scrollWidth,
        height: document.documentElement.scrollHeight
    });
});
