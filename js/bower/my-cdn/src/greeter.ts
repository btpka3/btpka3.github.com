

/*
This is a demo for using typescript in node.js

https://github.com/DefinitelyTyped/DefinitelyTyped/tree/master/node
https://github.com/soywiz/typescript-node-definitions
 */

function greeter(person:string) {
    return "Hello, " + person;
}

var user = "Jane User";

console.log(greeter("world"));
var bower = require('bower');

var logger = bower.commands.info("jquery#2.1.4");
logger
    .on('end', function (data) {
        console.log("end : ", data);
    })
    .on('error', function (err) {
        console.log("error : ", err);
    });