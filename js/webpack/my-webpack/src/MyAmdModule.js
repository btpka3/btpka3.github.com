define(function () {
    console.log("----- MyAmdModule is loaded.");

    function MyAmdModule() {
        console.log("----- MyCmdModule is newed.");

        this.hi = function (name) {
            return `MyCmdModule#hi ${name}`
        };
    }

    MyAmdModule.staticHi = function (name) {
        return `MyCmdModule.staticHi ${name}`
    };
    return MyAmdModule;
});

