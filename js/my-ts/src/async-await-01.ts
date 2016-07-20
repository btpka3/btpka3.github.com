"use strict";

// printDelayed is a 'Promise<void>'
async function printDelayed(elements:string[]) {
    for (const element of elements) {
        await delay(2000);
        console.log(new Date(), element);
    }
}

async function delay(milliseconds:number) {
    return new Promise<void>(resolve => {
        setTimeout(resolve, milliseconds);
    });
}

console.log(new Date(), "-----------start");
printDelayed(["Hello", "beautiful", "asynchronous", "world"]).then(() => {
    console.log();
    console.log(new Date(), "Printed every element!");
    console.log(new Date(), "-----------end");
});