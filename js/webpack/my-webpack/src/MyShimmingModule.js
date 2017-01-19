
// FIXME 这里的 this 会翻译成 undefined.
console.log("----- MyShimmingModule is loaded.", this, window, this === window);

function MyShimmingModule() {
    console.log("----- MyShimmingModule is newed.", this);

    this.hi = function (name) {
        return `MyShimmingModule#hi ${name}`
    };
}

MyShimmingModule.staticHi = function (name) {
    return `MyShimmingModule.staticHi ${name}`
};