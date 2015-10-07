"use strict";
var tabs = require("sdk/tabs");

function onOpen(tab) {
    console.log(tab.url + " is open");
    tab.on("pageshow", logShow);
    tab.on("activate", logActivate);
    tab.on("deactivate", logDeactivate);
    tab.on("close", logClose);
}

function logShow(tab) {
    console.log(tab.url + " is loaded");
}

function logActivate(tab) {
    console.log(tab.url + " is activated");
}

function logDeactivate(tab) {
    console.log(tab.url + " is deactivated");
}

function logClose(tab) {
    console.log(tab.url + " is closed");
}

tabs.on('open', onOpen);