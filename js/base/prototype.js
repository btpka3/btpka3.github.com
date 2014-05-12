//
// 总结:
// 1. 所有对象实例均有 __proto__ 属性，而 Obejct.prototype.__proto__ 为null，表示原型链的查找终止。
// 2. 所有的函数都有一个 prototype 对象，且该对象的 constructor 为自己。
//    如果原型对象被修改，且没有提供 constructor 属性，则会按照原型链向上找。
//    FIXME constructor 属性存在的意义是？貌似既不是标准属性，又不跨浏览器兼容，应避免使用。
//
// 3. o instanceof C 相当于判断 C.prototype === o.__proto__ || o.__proto__.__proto__ || ...
// 4. 为对象的某个属性重新赋值时，如果最底层的对象实例上操作该属性（没有的话先创建该属性），
//    之后访问该属性就不会再向上查找原型链了，除非再删除该属性。

// 参考：
// http://stackoverflow.com/questions/650764/how-does-proto-differ-from-constructor-prototype
// http://i.stack.imgur.com/KFzI3.png
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/instanceof

function A() {
}

A.prototype = {
    name : "A",
    print : function() {
        console.log("this.name = '" + this.name + "'");
    }
}

var a1 = new A();
var a2 = new A();
a1.print(); // this.name = 'A'


// false
console.log("a1 == a2 : " + (a1 == a2) + "");
// true
console.log("a1.name === a2.name : " + (a1.name === a2.name));
// true
console.log("a1.print === a2.print : " + (a1.print === a2.print));

console.log("--------------------- a1.name = 'A1'");
a1.name = 'A1';
a1.print(); // this.name = 'A1'
a2.print(); // this.name = 'A'

console.log("--------------------- A.prototype.name = 'AA'");
A.prototype.name = 'AA';
a1.print(); // this.name = 'A1'
a2.print(); // this.name = 'AA'

console.log("--------------------- delete a1.name");
delete a1.name;
a1.print(); // this.name = 'AA'
a2.print(); // this.name = 'AA'

console.log("--------------------- delete a2.name");
delete a2.name; // 不会删除原型链上的属性
a1.print(); // this.name = 'AA'
a2.print(); // this.name = 'AA'
