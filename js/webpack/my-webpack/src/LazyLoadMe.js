console.log("----- LazyLoadMe is loaded.");
function LazyLoadMe() {
    console.log("----- LazyLoadMe is newed.");

    this.hi = function (name) {

        return `LazyLoadMe#hi ${name}`
    };
}
module.exports = LazyLoadMe;