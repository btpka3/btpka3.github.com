cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/cordova-hot-code-push-plugin/www/chcp.js",
        "id": "cordova-hot-code-push-plugin.chcp",
        "pluginId": "cordova-hot-code-push-plugin",
        "clobbers": [
            "chcp"
        ]
    },
    {
        "file": "plugins/cordova-plugin-whitelist/whitelist.js",
        "id": "cordova-plugin-whitelist.whitelist",
        "pluginId": "cordova-plugin-whitelist",
        "runs": true
    },
    {
        "file": "plugins/cordova-hot-code-push-local-dev-addon/www/chcpLocalDev.js",
        "id": "cordova-hot-code-push-local-dev-addon.chcpLocalDev",
        "pluginId": "cordova-hot-code-push-local-dev-addon",
        "clobbers": [
            "chcpLocalDev"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-hot-code-push-plugin": "1.2.5",
    "cordova-plugin-whitelist": "1.2.1",
    "cordova-hot-code-push-local-dev-addon": "0.1.2"
}
// BOTTOM OF METADATA
});