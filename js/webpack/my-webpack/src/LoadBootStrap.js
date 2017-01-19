console.log("----- LoadBootStrap is loaded.");
function LoadBootStrap() {
    console.log("----- LoadBootStrap is newed.");

    this.hi = function (name) {

        var $script = require("scriptjs");

        $script([
            "https://unpkg.com/jquery@3.1.1/dist/jquery.min.js",
        ], 'jquery');

        $script.ready('jquery', function () {
            console.log("------------ jquery loaded");
            $script([
                "https://unpkg.com/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            ], 'bootstrap');
            $script.ready('bootstrap', function () {
                console.log("------------ bootstrap loaded");
            });

        });


        // // ensure() 方法仅仅下载代码，但不执行代码
        // require.ensure(["https://unpkg.com/jquery@3.1.1/dist/jquery.min.js"], function () {
        //
        //     $script(['jquery.js', 'my-jquery-plugin.js'], 'bundle')
        //     console.log("-------", require('https://unpkg.com/jquery@3.1.1/dist/jquery.min.js'));
        // });


        return `LoadBootStrap#hi ${name}`
    };
}
module.exports = LoadBootStrap;