"use strict";

//let { Loader, Require, unload } = require('toolkit/loader');

let {Cc, Ci, Cu} = require("chrome");


let io = Cc["@mozilla.org/network/io-service;1"]
    .getService(Ci.nsIIOService);
//let Cc = Components.classes;
//let Ci = Components.interfaces;

let httpRequestObserver = {
    observe: function (subject, topic, data) {
        if (topic == "http-on-modify-request") {
            var httpChannel = subject.QueryInterface(Ci.nsIHttpChannel);
            httpChannel.setRequestHeader("X-Hello", "World", false);
            if ("http://ajax.googleapis.com" == httpChannel.URI.prePath) {
                httpChannel.redirectTo(io.newURI("http://ajax.useso.com" + httpChannel.URI.path, null, null));
            }
        }
        if (topic == "http-on-opening-request") {
            var httpChannel = subject.QueryInterface(Ci.nsIHttpChannel);
            console.log("=-----" + httpChannel.URI.prePath + httpChannel.URI.path);
            if ("http://ajax.googleapis.com" == httpChannel.URI.prePath) {
                console.log("=----- 1111");
                try {
                    httpChannel.redirectTo(io.newURI("http://ajax.useso.com" + httpChannel.URI.path, null, null));
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
