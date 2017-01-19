console.log("----- MyEs6Module is loaded.");
export default class MyEs6Module {
    constructor() {
        console.log("----- MyEs6Module is newed.")
    }

    hi(name) {
        return `MyEs6Module#hi ${name}`
    }

    static staticHi(name) {
        return `MyEs6Module.staticHi ${name}`
    }


}