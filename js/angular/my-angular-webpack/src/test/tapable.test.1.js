var Tapable = require("tapable");


// 1. 创建一个插件类
function MyClass() {
    Tapable.call(this);
}


MyClass.prototype = Object.create(Tapable.prototype);

MyClass.prototype.aaa = function () {
    // 2. 预定义插件入口名称 "start",以及相关的参数，并确定调用时机
    this.applyPlugins("start", 1, 2);
    console.log("aaa");
    this.applyPlugins("end", 3, 4);
};


let m = new MyClass();

// 3. 插件实现 1
m.plugin("start", function (a, b) {
    // ----start 1 :  { '0': 1, '1': 2 } 1 2 true
    console.log("----start 1 : ", arguments, a, b, m === this);
});
m.plugin("start", function (a, b) {
    console.log("----start 2 : ", arguments, a, b);
});
m.plugin("end", function (a, b) {
    console.log("----end 1 : ", arguments, a, b);
});
m.plugin("end", function (a, b) {
    console.log("----end 2 : ", arguments, a, b);
});

// 5. 启动
m.aaa();
