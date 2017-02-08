/**
 * Promise
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise
 * 测试 catch
 *   1.
 *   2. Promise#catch 会捕获前一个promise reject 值
 *   3. 前一个promise如果没有错误处理函数，或者错误处理函数没有返回值，则后续的catch不会调用
 */

function bbb(num) {
    if (typeof(num) !== 'number') {
        throw new Error("not num");
    }
    return new Promise((resolve, reject) => {
        setTimeout(function () {

            if (num < 0) {
                reject(num)
            }
            if (num === 0) {
                throw new Error("my bad");
            }
            if (num > 0) {
                resolve(num)
            }
        }, 0);
    });
}


// Promise#catch 不会捕获未处理的异常
function test10() {
    console.log("------------------------------ test10.");
    bbb("a")
        .catch(err => {
            console.log("catch : ", err);
        });
}
function test11() {
    console.log("------------------------------ test11.");
    bbb(0)
        .catch(err => {
            console.log("catch : ", err);
        });
}

// Promise#catch 会处理前面 reject 的值
function test20() {
    console.log("------------------------------ test20.");
    bbb(-1)
        .catch(err => {
            console.log("catch : ", err);
        });
}
function test21() {
    console.log("------------------------------ test21.");
    bbb(-1)
        .then(result => {
            console.log("OK   : ", result);
        }, err => {
            console.log("ERROR: ", err);
        })
        .catch(err => {
            console.log("catch : ", err);  // 不执行，没有reject。
        })
    ;
}

// Promise#catch 会捕获前一个promise#then 处理中抛出的异常。
function test30() {
    console.log("------------------------------ test30.");
    bbb(-1)
        .then(result => {
            console.log("OK   : ", result);
        }, err => {
            console.log("ERROR: ", err);
            throw new Error("my bad - then - error")
        })
        .catch(err => {
            console.log("catch : ", err);  // 不执行，没有reject。
        })
    ;
}
function test31() {
    console.log("------------------------------ test31.");
    bbb(1)
        .then(result => {
            console.log("OK   : ", result);
            throw new Error("my bad - then - ok - err")
        }, err => {
            console.log("ERROR: ", err);
        })
        .catch(err => {
            console.log("catch : ", err);  // 不执行，没有reject。
        })
    ;
}

// promise#then 的错误处理 handler 也会捕获前一个promise#then 处理中抛出的异常。
function test40() {
    console.log("------------------------------ test40.");
    bbb(1)
        .then(result => {
            console.log("OK   : ", result);
            throw new Error("my bad - then - ok - err")
        }, err => {
            console.log("ERROR: ", err);
        })

        .then(result => {
            console.log("OK1   : ", result);
            throw new Error("my bad - then - ok - err1")
        }, err => {
            console.log("ERROR1: ", err);
        })

        .catch(err => {
            console.log("catch : ", err);  // 不执行，没有reject。
        })
    ;
}

test40();


