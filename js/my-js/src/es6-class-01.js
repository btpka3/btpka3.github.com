/**
 * classes
 *
 * https://babeljs.io/learn-es2015/#ecmascript-2015-features-classes
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes
 */

class Person {


    constructor(name, memo) {
        this.name = name;
        this.age = 20;
        this.gender = "M";

        var memoKey = Symbol('memo');
        this[memoKey] = memo; // private ?
    }


    get age1() {
        return this.age;
    }

    static hi(msg) {
        return "hello " + msg;
    }


    toString() {
        return `Person : name=${this.name}, age=${this.age}, gender=${this.gender}`;
    }
}

var a = new Person("zhang3");

console.log("1. " + a, a);
console.log("2. " + a.age1);
a.age1 = 3; // 不会抛出错误，但不会修改值。
console.log("3. " + a.age1);
console.log("4. " + Person.hi("999"));

var symbols = Object.getOwnPropertySymbols(a);
console.log("5. " + symbols.length,
    symbols[0], // Symbol(memo)
    JSON.stringify(symbols[0]) // undefined
);

