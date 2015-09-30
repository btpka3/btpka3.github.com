var self = require('sdk/self');

// a dummy function, to show how tests work.
// to see how to test this function, look at test/test-index.js
function dummy(text, callback) {
    callback(text);
}

exports.dummy = dummy;

var buttons = require('sdk/ui/button/action');
var tabs = require("sdk/tabs");

var button = buttons.ActionButton({
    id: "mozilla-link",
    label: "Visit Mozilla",
    icon: {
        "16": "./icon-16.png",
        "32": "./icon-32.png",
        "64": "./icon-64.png"
    },
    onClick: handleClick
});

function handleClick(state) {
    tabs.open("https://www.mozilla.org/");
}


// -------------------------------------------------
let { Loader, Require, unload } = require('toolkit/loader');
let {Cc, Ci, Cu, CC} = require("chrome");
let ioService = Cc["@mozilla.org/network/io-service;1"].getService(Ci.nsIIOService);
//let Cc = Components.classes;
//let Ci = Components.interfaces;


let httpRequestObserver = {
    observe: function (subject, topic, data) {
        if (topic == "http-on-modify-request") {
            var httpChannel = subject.QueryInterface(Ci.nsIHttpChannel);
            httpChannel.setRequestHeader("X-Hello", "World", false);
            if ("http://ajax.googleapis.com" == httpChannel.URI.prePath) {
                httpChannel.redirectTo(Services.io.newURI("http://ajax.useso.com" + httpChannel.URI.path, null, null));
            }
        }
        if (topic == "http-on-opening-request") {
            var httpChannel = subject.QueryInterface(Ci.nsIHttpChannel);
            console.log("=-----" + httpChannel.URI.prePath + httpChannel.URI.path);
            if ("http://ajax.googleapis.com" == httpChannel.URI.prePath) {
                console.log("=----- 1111");
                try {
                    httpChannel.redirectTo(ioService.newURI("http://ajax.useso.com" + httpChannel.URI.path, null, null));
                } catch (e) {
                    console.log("=----- 3333 " + e);
                }
                console.log("=----- 2222");
            }

        }
    }
};


let observerService = Cc["@mozilla.org/observer-service;1"]
    .getService(Ci.nsIObserverService);


observerService.addObserver(httpRequestObserver, 'http-on-modify-request', false);
observerService.addObserver(httpRequestObserver, 'http-on-opening-request', false);
