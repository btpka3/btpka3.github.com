const fs = require('fs');
const glob = require('glob');
const path = require('path');
const process = require('process');
const states = require("./webpack.states");
const assert = require('assert');

// fs.writeFile('message.json', JSON.stringify([1, 2, 3]), 'utf8', (err) => {
//     if (err) throw err;
//     console.log('It\'s saved!');
// });


console.log(JSON.stringify(Object.values({a:"aaa",b:"bbb"})));
