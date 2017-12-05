/*
 * WebRequest#onBeforeRequest()
 * https://developer.chrome.com/extensions/webRequest#event-onBeforeRequest
 *
 */

var gFontRegex = /^(https?:\/\/)fonts.googleapis.com(\/.*)/;
var gAjaxRegex = /^(https?:\/\/)ajax.googleapis.com(\/.*)/;
var newFontHost = "fonts.lug.ustc.edu.cn";
var newAjaxHost = "ajax.lug.ustc.edu.cn";


function aa(requestDetails) {
    console.log("-----1: ", requestDetails.requestId);
    console.log("-----2: " + requestDetails);
    return {authCredentials: "zhang3"};
}

chrome.webRequest.onAuthRequired.addListener(
    aa,
    {urls: ["*://*/*"]},
    ["blocking"]
);

// chrome.webRequest.onBeforeRequest.addListener(
//      callback,           // function
//      filter,             // webRequest.RequestFilter
//      opt_extraInfoSpec   // string array
// );
chrome.webRequest.onBeforeRequest.addListener(
    function (details) {

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
