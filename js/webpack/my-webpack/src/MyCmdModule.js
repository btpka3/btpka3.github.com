console.log("----- MyCmdModule is loaded.");
function MyCmdModule() {
    console.log("----- MyCmdModule is newed.");

    this.hi = function (name) {
        return `MyCmdModule#hi ${name}`
    };
}

MyCmdModule.staticHi = function (name) {
    return `MyCmdModule.staticHi ${name}`
};

module.exports = MyCmdModule;