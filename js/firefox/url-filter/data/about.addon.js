"use strict";
var {Cc, Ci, Cu,Cr, Cm, components} = require("chrome");


Cm.QueryInterface(Ci.nsIComponentRegistrar);
var io = Cc["@mozilla.org/network/io-service;1"]
    .getService(Ci.nsIIOService);

// globals
var factory;
const aboutPage_description = 'This is my custom about page';
const aboutPage_id = "24310b56-795e-40eb-9b73-f5e23390e6b8";
const aboutPage_word = 'btpka3';
const aboutPage_page = io.newChannel('data:text/html,hi this is the page that is shown when navigate to about:myaboutpage', null, null);
Cu.import("resource://gre/modules/XPCOMUtils.jsm");

function AboutCustom() {
}
AboutCustom.prototype = Object.freeze({
    classDescription: aboutPage_description,
    contractID: '@mozilla.org/network/protocol/about;1?what=' + aboutPage_word,
    classID: components.ID('{' + aboutPage_id + '}'),
    QueryInterface: XPCOMUtils.generateQI([Ci.nsIAboutModule]),

    getURIFlags: function (aURI) {
        return Ci.nsIAboutModule.ALLOW_SCRIPT;
    },

    newChannel: function (aURI) {
        let channel = aboutPage_page;
        channel.originalURI = aURI;
        return channel;
    }
});

function Factory(component) {
    this.createInstance = function (outer, iid) {
        if (outer) {
            throw Cr.NS_ERROR_NO_AGGREGATION;
        }
        return new component();
    };
    this.register = function () {
        Cm.registerFactory(component.prototype.classID, componeprototype.classDescription, component.prototype.contractID, this);
    };nt.
    this.unregister = function () {
        Cm.unregisterFactory(component.prototype.classID, this);
    };
    Object.freeze(this);
    this.register();
}

new Factory(AboutCustom);