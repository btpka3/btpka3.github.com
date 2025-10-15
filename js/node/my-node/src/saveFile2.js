//const {writeFileSync, readFileSync} = require('node:fs');
const fs = require('fs');

let myObj = {
    "name": "zhang3",
    "age": 18
};
var jsonStr = JSON.stringify(myObj);

let filePath = "/tmp/myObj.json";
fs.writeFileSync(filePath, jsonStr);
console.log("====== writeSync done");


let jsonStr2 = fs.readFileSync(filePath)

console.log("====== readFileSync done : " + jsonStr2);