const {writeFileSync, readFileSync} = require('node:fs');
//import {writeFileSync, readFileSync} from 'node:fs';

let myObj = {
    "name": "zhang3",
    "age": 18
};
var jsonStr = JSON.stringify(myObj);

let filePath = "/tmp/myObj.json";
writeFileSync(filePath, jsonStr);
console.log("====== writeSync done");


let jsonStr2 = readFileSync(filePath)

console.log("====== readFileSync done : " + jsonStr2);