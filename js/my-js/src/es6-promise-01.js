/**
 * Promise
 * 
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise
 *
 */

function bbb() {
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
            resolve(111);
        }, 2000);
    });
}


bbb().then(result => {
    console.log("OK   : ", result);
    return 222;
}, err=> {
    console.log("ERROR: ", err);
}).then(result => {
    console.log("OK   : ", result);
}, err=> {
    console.log("ERROR: ", err);
});
