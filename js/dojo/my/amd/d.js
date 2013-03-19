define(function(require, exports, module) {

    // 可以在模块内部随时使用require来引入并使用其他包，
    // 而不必必须在define时预先声明所有用到包
    exports.d = require("my/c").c + " DDD";
});
