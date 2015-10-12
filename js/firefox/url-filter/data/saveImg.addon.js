"use strict";
// https://developer.mozilla.org/en-US/docs/Mozilla/JavaScript_code_modules/FileUtils.jsm
const {Cc,Ci, Cu,components} = require("chrome");

Cu.import("resource://gre/modules/FileUtils.jsm");
Cu.import("resource://gre/modules/NetUtil.jsm");
Cu.import("resource://gre/modules/Downloads.jsm");
Cu.import("resource://gre/modules/osfile.jsm")
Cu.import("resource://gre/modules/Task.jsm");


var { modelFor } = require("sdk/model/core");
var { viewFor } = require("sdk/view/core");
var tools = Cc["@mozilla.org/image/tools;1"]
    .getService(Ci.imgITools);

var tabs = require("sdk/tabs");
var tab_utils = require("sdk/tabs/utils");

let io = Cc["@mozilla.org/network/io-service;1"]
    .getService(Ci.nsIIOService);


const nsIWBP = Ci.nsIWebBrowserPersist;


tabs.on("ready", mapHighLevelToLowLevel);

var i = 0;
function mapHighLevelToLowLevel(tab) {
    // get the XUL tab that corresponds to this high-level tab
    var lowLevelTab = viewFor(tab);
    // now we can, for example, access the tab's content directly
    var browser = tab_utils.getBrowserForTab(lowLevelTab);
    var doc = browser.contentDocument;
    var aReferrer = io.newURI(doc.location.href, null, null);

    var imgs = doc.getElementsByTagName("img");
    console.log(doc.title + ",    " + imgs.length);

    var cache = tools.getImgCacheForDocument(doc);
    if (imgs.length > 0) {
        i++;
        console.log("img.src = " + imgs[0].src);
        let imgURI = io.newURI(imgs[0].src, null, null);
        let props = cache.findEntryProperties(imgURI);

        let arr = [];
        let arr1 = props.getKeys(arr);
        console.log(JSON.stringify(arr1), JSON.stringify(arr));

        var webBrowserPersist = Cc["@mozilla.org/embedding/browser/nsWebBrowserPersist;1"]
            .createInstance(Ci.nsIWebBrowserPersist);

        webBrowserPersist.persistFlags = nsIWBP.PERSIST_FLAGS_REPLACE_EXISTING_FILES
            | nsIWBP.PERSIST_FLAGS_FORCE_ALLOW_COOKIES
            | nsIWBP.PERSIST_FLAGS_FROM_CACHE;

        const eFlags = nsIWBP.PERSIST_FLAGS_REPLACE_EXISTING_FILES |
            nsIWBP.PERSIST_FLAGS_FORCE_ALLOW_COOKIES;

        webBrowserPersist.saveDocument(
            doc,
            new FileUtils.File("/tmp/zll." + i),
            new FileUtils.File("/tmp/"),
            "image/jpeg",
            0,
            80
        );

        //webBrowserPersist.savePrivacyAwareURI(
        //    imgURI,
        //    null,
        //    aReferrer,
        //    Ci.nsIHttpChannel.REFERRER_POLICY_NO_REFERRER_WHEN_DOWNGRADE,
        //    null,
        //    null,
        //    new FileUtils.File("/tmp/zll." + i),
        //    doc
        //);
    }


    // get the high-level tab back from the XUL tab
    var highLevelTab = modelFor(lowLevelTab);
    console.log(highLevelTab.url);
}






