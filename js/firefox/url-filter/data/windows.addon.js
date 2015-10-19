"use strict";
var {Cc, Ci, Cu,Cr, Cm, components} = require("chrome");
var { modelFor } = require("sdk/model/core");
var { viewFor } = require("sdk/view/core");
var windows = require("sdk/windows");

var browserWindows = windows.browserWindows;

// add a listener to the 'open' event
browserWindows.on('open', function (window) {
    myOpenWindows.push(window);
});

// add a listener to the 'close' event
browserWindows.on('close', function (window) {
    console.log("A window was closed.");
});

// add a listener to the 'activate' event
browserWindows.on('activate', function (window) {
    console.log("A window was activated.");
    var activeWindowTitle = windows.activeWindow.title;
    console.log("Active window title is: " + activeWindowTitle);
});

// add a listener to the 'deactivate' event
browserWindows.on('deactivate', function (window) {
    console.log("A window was deactivated.");
});