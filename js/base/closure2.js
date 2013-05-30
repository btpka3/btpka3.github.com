// 总结：一句话通过声明与调用匿名函数，创建闭包，及闭包内变量。
// 但是由于被变量 a 引用，所以该闭包并不会消失。
// 使用闭包，可以有效减少全局变量（避免并行开发时命名冲突），控制变量作用范围和访问权限

var a = (function() {
    var prop1 = "prop1DefaultValue";

    var exports = {};

    exports.getProp1 = function() {
        return prop1; // 注意：没有使用this关键字
    };

    // 如果不提供该函数，相当于属性prop1只读
    exports.setProp1 = function(newValue) {
        prop1 = newValue;
    }
    return exports;
})();

var result = ('prop1' in a);
console.log("'prop1' in a : " + result);

console.log("a.getProp1() = " + a.getProp1());

a.setProp1("prop1NewValue")
console.log("a.getProp1() = " + a.getProp1());
