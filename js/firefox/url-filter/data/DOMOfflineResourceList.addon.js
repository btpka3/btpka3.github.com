"use strict";

// nsIDOMWindow
// nsIDOMOfflineResourceList

var tabs = require("sdk/tabs");
var self = require('sdk/self');
var tabsUtils = require('sdk/tabs/utils');
var { modelFor } = require("sdk/model/core");
var { viewFor } = require("sdk/view/core");

// Listen for tab content loads.
tabs.on('ready', function (tab) {
    console.log('tab is loaded', tab.title, tab.url);
    tab.title = "111111";

    // BrowserWindow
    let browserWindow = tabs.activeTab.window;//tabsUtils.getOwnerWindow(tabs.activeTab);
    if (!browserWindow) {
        console.log('DOMOfflineResourceList.addon : win is null + ' + browserWindow);
        return;
    }
    let chromeWindow = viewFor(browserWindow);
    let imgs = chromeWindow.document.getElementsByTagName("img");
    console.log('tab is loaded : ' + imgs.length + ", location =" + chromeWindow.document.location.href);
    if (imgs.length > 0) {
        imgs[0].alt = "captcha ~~~";
        console.log('tab is loaded : ' + imgs[0].src);
    }

    //tabsUtils.getOwnerWindow(tab)
});