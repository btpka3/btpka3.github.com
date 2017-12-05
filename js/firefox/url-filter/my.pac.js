/* exported FindProxyForURL */

var blockedHosts = [];
const allow = "DIRECT";
//const deny = "PROXY 127.0.0.1:9999";
const deny = [
    {
        type: "socks",
        host: "127.0.0.1",
        port: 9999,
        proxyDNS: true,
        failoverTimeout: 5,
        //username: "zhang3",
        //password: "zhang3"
    },
    {
        type: "socks",
        host: "127.0.0.1",
        port: 1080,
        proxyDNS: true,
        failoverTimeout: 5,
        username: "zhang3",
        password: "zhang3"
    }
];

// tell the background script that we are ready
browser.runtime.sendMessage("init");

// listen for updates to the blocked host list
browser.runtime.onMessage.addListener((message) => {
    blockedHosts = message;
});

// required PAC function that will be called to determine
// if a proxy should be used.
function FindProxyForURL(url, host) {
    if (blockedHosts.indexOf(host) != -1) {
        browser.runtime.sendMessage(`Proxy-blocker: blocked ${url}`);
        return deny;
    }
    return allow;
}