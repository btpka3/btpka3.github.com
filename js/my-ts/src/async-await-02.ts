"use strict";



function sleep(time) {
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            resolve(111);
        }, time);
    });
}

async function ccc() {
    console.log(new Date(), "aaa");
    await sleep(1000);
    console.log(new Date(), "bbb");
}

console.log(new Date(), "-----------start");
ccc().then(() => {
    console.log(new Date(), "-----------end");
});

