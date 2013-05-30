// 总结：在浏览器中 this 默认是 window 对象。
// 函数执行时，this 对象总是点操作符前的对象

console.log("this === window : " + (this === window) + ""); // true

var name = "aaa";
function print() {
    console.log("name = '" + this.name + "'");
}
print(); // name = 'aaa'

var b = {
    name : "bbb"
};
b.print = print;
b.print(); // name = 'bbb'
