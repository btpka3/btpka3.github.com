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
    // BrowserWindow
    let browserWindow = tabs.activeTab.window;//tabsUtils.getOwnerWindow(tabs.activeTab);

    console.log('tab is loaded', tab.title, tab.url, browserWindow.document);
    tab.title = "111111";

    if (!browserWindow) {
        console.log('DOMOfflineResourceList.addon : win is null + ' + browserWindow);
        return;
    }
    let chromeWindow = viewFor(browserWindow);
    let imgs = chromeWindow.document.getElementsByTagName("img");
    // chrome://browser/content/browser.xul
    console.log('tab is loaded : ' + imgs.length + ", location =" + chromeWindow.document.location + ", " + chromeWindow.document);
    if (imgs.length > 0) {
        imgs[0].alt = "captcha ~~~";
        console.log('tab is loaded : ' + imgs[0].src);
    }

    //tabsUtils.getOwnerWindow(tab)
});