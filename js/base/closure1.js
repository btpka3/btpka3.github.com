// 总结：在花括号（包括函数体的花括号）内声明的函数是不会被共享的，
// 在函数执行的时候（包括new操作）都会创建一个新的拷贝。
// 使用得当，会达到预期的变量访问控制效果。比如 printConstructorName。
// 使用不当，本应能节省的内存就会被占用而造成浪费：比如 printThisName
//       —— 它根本无需使用闭包，可以使用以下方式： A.prototype.printThisName = function(){...} 。

function A(name) {
    this.name = name;
    this.hobby = ["football", "basketball"];
    this.printThisName = function() {
        console.log("this.name = '" + this.name + "'");
    };
    this.printConstructorName = function() {
        console.log("name = '" + name + "'"); // 注意：直接访问的传入构造函数的 name
    };
}

var a1 = new A("a1");
a1.name = "aa1";
a1.printThisName(); // this.name = 'aa1'
a1.printConstructorName(); // name = 'a1'

var a2 = new A("a2");

var result = (a1.printThisName === a2.printThisName);
console.log("a1.printThisName == a2.printThisName : " + result);
