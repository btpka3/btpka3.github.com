var i = 0;
module.exports = {
    add: function (success, error, opts) {
        console.log("======== browser : arguments = ", opts);
        setTimeout(function () {
            var result = {
                sum: opts[0] + opts[1]
            };
            if (i % 2 === 0) {
                success(result);
            } else {
                error(result);
            }
            i++;
        }, 1000);
    }
};

// 下面这句将 browser 平台下 将接口和该文件中实现关联起来
require("cordova/exec/proxy").add("MyCordovaPlugin", module.exports);
