"use strict";
var panel = require("sdk/panel");
var ui = require('sdk/ui');
var tabs = require("sdk/tabs");
var self = require('sdk/self');

var button = ui.ToggleButton({
    id: "mozilla-link",
    label: "Visit Mozilla",
    icon: {
        "16": "./icon-16.png",
        "32": "./icon-32.png",
        "64": "./icon-64.png"
    },
    onChange: handleChange
});

var panel = panel.Panel({
    //width: 300,
    //height: 300,
    contentURL: "./panel.html",
    onHide: handleHide,
    onShow: function () {
        "use strict";
        console.log("panel onShow is triggered");
        panel.port.emit('#btn');
    }
});

panel.port.on("click-link", function (url) {
    console.log("panel clicked : event = " + url);
});

panel.port.on("resizePanel", function ({width, height}) {
    console.log("resizePanel event : " + width + "*" + height);
    panel.resize(width, height);
});

function handleChange(state) {
    if (state.checked) {
        panel.show({
            position: button
        });
    }
}
function handleHide() {
    tabs.open(self.data.url("panel.html"));
    button.state('window', {checked: false});
}