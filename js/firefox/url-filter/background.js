/*
 * WebRequest#onBeforeRequest()
 * https://developer.chrome.com/extensions/webRequest#event-onBeforeRequest
 *
 */

/*
function handleClick() {
    browser.runtime.openOptionsPage();
}

browser.browserAction.onClicked.addListener(handleClick);
*/

var chrome = browser;

var gFontRegex = /^(https?:\/\/)fonts.googleapis.com(\/.*)/;
var gAjaxRegex = /^(https?:\/\/)ajax.googleapis.com(\/.*)/;
var newFontHost = "fonts.lug.ustc.edu.cn";
var newAjaxHost = "ajax.lug.ustc.edu.cn";


// function aa(requestDetails) {
//     console.log("-----1: ", requestDetails.requestId);
//     console.log("-----2: " + requestDetails);
//     return {authCredentials: "zhang3"};
// }
//
// chrome.webRequest.onAuthRequired.addListener(
//     aa,
//     {urls: ["*://*/*"]},
//     ["blocking"]
// );

// chrome.webRequest.onBeforeRequest.addListener(
//      callback,           // function
//      filter,             // webRequest.RequestFilter
//      opt_extraInfoSpec   // string array
// );
chrome.webRequest.onBeforeRequest.addListener(
    function (details) {

        console.log("-------sddd ", details);
        var redirectUrl = replaceGoogleFontUrl(details.url) || replaceGoogleAjaxUrl(details.url);
        if (redirectUrl) {
            return {redirectUrl: redirectUrl};
        }
    },
    {
        urls: [
            "*://fonts.googleapis.com/*",
            "*://ajax.googleapis.com/*"
        ]
        //types: ["main_frame", "sub_frame", "stylesheet", "script", "image", "object", "xmlhttprequest", "other"]
    },

    // callback 函数将同步执行
    ["blocking"]
);


chrome.cookies.onChanged.addListener(function (changeInfo){
    console.log("cookie changed : "+JSON.stringify(changeInfo));
});


function replaceGoogleFontUrl(srcUrl) {
    var matches = gFontRegex.exec(srcUrl);

    if (matches) {
        return matches[1] + newFontHost + matches[2];
    }
    return null;
}

function replaceGoogleAjaxUrl(srcUrl) {
    var matches = gAjaxRegex.exec(srcUrl);

    if (matches) {
        return matches[1] + newAjaxHost + matches[2];
    }
    return null;
}

console.log("-------background.js ");
// setInterval(function(){
//     browser.tabs.sendMessage()
// },1000);


// ------------------ https://github.com/mdn/webextensions-examples/blob/master/proxy-blocker/background/proxy-handler.js

// Register the proxy script
const proxyScriptURL = "my.pac.js";
const defaultSettings = {
    blockedHosts: [
        "example.com",
        "example.org",
        "172.19.0.2",
        "google.com",
        "google.com",
        "www.google.com",
        "www.google.co.jp",
        "developer.chrome.com",
        "www.google-analytics.com",
        "www.youtube.com",
        "apis.google.com",
        "cse.google.com",
        "clients1.google.com",
        "accounts.google.com",
        "www.googleapis.com",
        "i.ytimg.com",
        "i1.ytimg.com",
        "productforums.google.com",
        "chrome.google.com",
        "bugs.chromium.org",
        "yt3.ggpht.com",
        "r10---sn-nx57ynl7.googlevideo.com",
        "redirector.googlevideo.com",
        "yt3.ggpht.com",
        "facebook.com",
        'www.chromium.org'
    ]
};

browser.proxy.register(proxyScriptURL);

// Log any errors from the proxy script
browser.proxy.onProxyError.addListener(error => {
    console.error(`Proxy error: ${error.message}`);
});


// Initialize the proxy
function handleInit() {
    // update the proxy whenever stored settings change
    browser.storage.onChanged.addListener((newSettings) => {
        browser.runtime.sendMessage(newSettings.blockedHosts.newValue, {toProxyScript: true});
    });

    // get the current settings, then...
    browser.storage.local.get()
        .then((storedSettings) => {
            // if there are stored settings, update the proxy with them...
            if (storedSettings.blockedHosts) {
                browser.runtime.sendMessage(storedSettings.blockedHosts, {toProxyScript: true});
                // ...otherwise, initialize storage with the default values
            } else {
                browser.storage.local.set(defaultSettings);
            }

        })
        .catch(() => {
            console.log("Error retrieving stored settings");
        });
}

function handleMessage(message, sender) {
    // only handle messages from the proxy script
    if (sender.url != browser.extension.getURL(proxyScriptURL)) {
        return;
    }

    if (message === "init") {
        handleInit(message);
    } else {
        // after the init message the only other messages are status messages
        console.log(message);
    }
}

browser.runtime.onMessage.addListener(handleMessage);