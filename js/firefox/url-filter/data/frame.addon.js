"use strict";
var panel = require("sdk/panel");
var ui = require('sdk/ui');
var tabs = require("sdk/tabs");
var self = require('sdk/self');

var frame = ui.Frame({
    url: './frame.html',
    onMessage: (e) => {
        console.log("New city: " + e.data);
    }
});

var toolbar = ui.Toolbar({
    name: "city-info",
    title: "City info",
    items: [frame]
});

frame.postMessage("mmm", frame.url);