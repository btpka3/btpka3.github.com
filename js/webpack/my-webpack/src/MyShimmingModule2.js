
/* FIXME 这里的 this 会翻译成 undefined.*/
console.log("----- MyShimmingModule2.js is loaded.", this, window, this === window);

function MyShimmingModule2() {
    console.log("----- MyShimmingModule2.js is newed.", this);

    this.hi = function (name) {
        return `MyShimmingModule2.js#hi ${name}`
    };
}

MyShimmingModule2.staticHi = function (name) {
    return `MyShimmingModule2.js.staticHi ${name}`
};